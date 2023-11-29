package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.annotation.Retention;
import java.text.DecimalFormat;

import object.*;
import entity.*;

public class UI {
    GamePanel gp;
    Font arial_40;
    Font arial_80B;
    BufferedImage keyImage;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public String currentDialogue = "";
    public int commandNum = 0;
    int subState = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", 0, 40);
        arial_80B = new Font("Arial", 1, 80);
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.down1;

        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

    }

    public void drawTitleScreen(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setFont(arial_80B);
        // game name
        String text = "Treasure Hunter";
        int x = getXforCenteredText(text, g2);
        int y = gp.tileSize * 3;
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5); // text shadow
        g2.setColor(Color.ORANGE);
        g2.drawString(text, x, y);
        // draw image
        x = gp.screenWidth / 2 - gp.tileSize;
        y += gp.tileSize;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
        // GAME MENU
        g2.setColor(Color.WHITE);
        g2.setFont(arial_40);
        // new game
        text = "New Game";
        x = getXforCenteredText(text, g2);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) g2.drawString(">", x - gp.tileSize, y);
        // load game
        text = "Load Game";
        x = getXforCenteredText(text, g2);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) g2.drawString(">", x - gp.tileSize, y);
        // exit
        text = "Exit";
        x = getXforCenteredText(text, g2);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2) g2.drawString(">", x - gp.tileSize, y);
    }

    public void drawPlayLife(Graphics2D g2) {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;
        //draw maxlife
        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;
        //draw current life
        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }

    }

    public void drawPauseScreen(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, (int) 32F));
        int frameX = gp.tileSize * 4;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight, g2);
        switch (subState) {
            case 0:
                optionsTop(frameX, frameY, g2);
                break;
            case 1:
                optionControl(frameX, frameY, g2);
                break;
            case 2:
                optionQuitGame(frameX, frameY, g2);
                break;
        }
    }

    public void optionQuitGame(int frameX, int frameY, Graphics2D g2) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 2;
        currentDialogue = "Quit the game and\ngo back to the title?";
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += gp.tileSize;
        }
        // YES
        String text = "YES";
        textX = getXforCenteredText(text, g2);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 24, textY);
            if (gp.keyH.enterPressed) {
                gp.stopMusic();
                subState = 0;
                gp.gameState = gp.TITLE;
                commandNum = 0;
            }
        }
        // NO
        text = "NO";
        textX = getXforCenteredText(text, g2);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 24, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 0;
            }
        }
    }

    public void optionControl(int frameX, int frameY, Graphics2D g2) {
        int textX, textY;
        // TITLE
        String text = "Control";
        textX = getXforCenteredText(text, g2);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        // MOVE
        g2.setFont(new Font("Arial", Font.PLAIN, 24));
        textX = frameX + gp.tileSize;
        textY += gp.tileSize + 24;
        g2.drawString("Move", textX, textY);
        // ATTACK/CONFIRM
        textY += gp.tileSize + 24;
        g2.drawString("Attack/Confirm", textX, textY);
        // STATUS
        textY += gp.tileSize + 24;
        g2.drawString("Status", textX, textY);
        // MENU
        textY += gp.tileSize + 24;
        g2.drawString("Options", textX, textY);
        textX = frameX + gp.tileSize * 5;
        textY = frameY + gp.tileSize * 2 + 24;
        g2.drawString("WASD", textX, textY);
        textY += gp.tileSize + 24;
        g2.drawString("ENTER", textX, textY);
        textY += gp.tileSize + 24;
        g2.drawString("E", textX, textY);
        textY += gp.tileSize + 24;
        g2.drawString("ESC", textX, textY);
        // BACK
        g2.setFont(new Font("Arial", Font.PLAIN, 28));
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 24, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 0;
            }
        }
    }

    public void optionsTop(int frameX, int frameY, Graphics2D g2) {
        int textX, textY;
        // TITLE
        String text = "Options";
        textX = getXforCenteredText(text, g2);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        g2.setFont(new Font("Arial", Font.PLAIN, 28));
        // MUSIC VOLUME
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Music volume", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 24, textY);
        }
        // SE VOLUME
        textY += gp.tileSize + 24;
        g2.drawString("Sound Effect", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 24, textY);
        }
        // CONTROL
        textY += gp.tileSize + 24;
        g2.drawString("Control", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 24, textY);
            if (gp.keyH.enterPressed) {
                subState = 1;
                commandNum = 0;
            }
        }
        // QUIT GAME
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Quit", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX - 24, textY);
            if (gp.keyH.enterPressed) {
                subState = 2;
                commandNum = 0;
            }
        }
        // MUSIC VOLUME BOX
        textX = frameX + gp.tileSize * 5;
        textY = frameY + gp.tileSize * 2 + 24;
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
        // SE BOX
        textY += gp.tileSize + 24;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
    }

    public void drawDialogueScreen(Graphics2D g2) {
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height, g2);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += gp.tileSize;
        y += gp.tileSize;
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawCharacterScreen(Graphics2D g2) {
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight, g2);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 50;

        //names
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight + 24;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("Shield", textX, textY);
        textY += lineHeight;

        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX, g2);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX, g2);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX, g2);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX, g2);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX, g2);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY, null);

    }

    public void drawSubWindow(int x, int y, int width, int height, Graphics2D g2) {
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXforCenteredText(String text, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    public int getXforAlignToRightText(String text, int tailX, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void drawGameOverScreen(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        String text = "Game over";
        g2.setColor(Color.BLACK);
        int x = getXforCenteredText(text, g2);
        int y = gp.tileSize * 4;
        g2.drawString(text, x, y);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x - 4, y - 4);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
        text  = "Retry";
        x = getXforCenteredText(text, g2);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 24, y);
        }

        text  = "Quit";
        x = getXforCenteredText(text, g2);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 24, y);
        }
    }

    public void draw(Graphics2D g2) {
        if (gp.gameState == gp.TITLE) drawTitleScreen(g2);
        else if (gp.gameState == gp.PAUSE) drawPauseScreen(g2);
        else if (gp.gameState == gp.GAMEOVER) {
            drawGameOverScreen(g2);
        } else {
            if (gameFinished) {
                g2.setFont(arial_40);
                g2.setColor(Color.white);
                String text = "You found the treasure!";
                int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
                int x = 768 / 2 - textLength / 2;
                int y = 576 / 2 - 48 * 3;
                g2.drawString(text, x, y);
                text = "Your Time is " + dFormat.format(playTime) + "!";
                textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
                x = 768 / 2 - textLength / 2;
                y = 576 / 2 + 48 * 4;
                g2.drawString(text, x, y);
                g2.setFont(arial_80B);
                g2.setColor(Color.yellow);
                text = "Congratulations!";
                textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
                x = 768 / 2 - textLength / 2;
                y = 576 / 2 + 48 * 2;
                g2.drawString(text, x, y);
                gp.gameThread = null;
            } else {
                g2.setFont(arial_40);
                g2.setColor(Color.white);
                drawPlayLife(g2);//draw life
                g2.drawImage(keyImage, 48 / 2, 144 / 2, 48, 48, null);
                g2.drawString("x " + gp.player.hasKey, 74, 108);
                playTime += 0.016666666666666666D;
                g2.drawString("Time:" + dFormat.format(playTime), 48 * 11, 65);
                if (messageOn) {
                    g2.setFont(g2.getFont().deriveFont(30.0F));
                    g2.drawString(message, 48 / 2, 48 * 5);
                    messageCounter++;
                    if (messageCounter > 120) {
                        messageCounter = 0;
                        messageOn = false;
                    }
                }
                if (gp.gameState == gp.DIALOGUE) {
                    drawDialogueScreen(g2);
                }
                if (gp.gameState == gp.CHARACTER) {
                    drawCharacterScreen(g2);
                }
            }
        }
    }
}



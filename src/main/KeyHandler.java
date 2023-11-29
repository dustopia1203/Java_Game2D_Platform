package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean enterPressed;
    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void titleState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) gp.ui.commandNum = 2;
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) gp.ui.commandNum = 0;
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.reset();
                gp.gameState = gp.PLAY;
            } else if (gp.ui.commandNum == 1) {
                gp.gameState = gp.PLAY;
                gp.playMusic(0);
            } else System.exit(0);
        }
    }

    public void playState(int code) {
        if (code == KeyEvent.VK_W) this.upPressed = true;
        if (code == KeyEvent.VK_S) this.downPressed = true;
        if (code == KeyEvent.VK_A) this.leftPressed = true;
        if (code == KeyEvent.VK_D) this.rightPressed = true;
        if (code == KeyEvent.VK_ENTER) this.enterPressed = true;
        if (code == KeyEvent.VK_ESCAPE) gp.gameState = gp.PAUSE;
        if (code == KeyEvent.VK_E) gp.gameState = gp.CHARACTER;
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_ESCAPE) gp.gameState = gp.PLAY;
        if (code == KeyEvent.VK_ENTER) this.enterPressed = true;
        int maxCommand = 0;
        switch (gp.ui.subState) {
            case 0:
                maxCommand = 3;
                break;
            case 1:
                maxCommand = 1;
                break;
            case 2:
                maxCommand = 2;
                break;
        }
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSE(7);
            if (gp.ui.commandNum < 0) gp.ui.commandNum = maxCommand;
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSE(7);
            if (gp.ui.commandNum > maxCommand) gp.ui.commandNum = 0;
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 0 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(7);
                }
                if (gp.ui.commandNum == 1 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.playSE(7);
                }
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 0 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(7);
                }
                if (gp.ui.commandNum == 1 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.playSE(7);
                }
            }
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) gp.gameState = gp.PLAY;
    }

    public void characterState(int code) {
        if (code == KeyEvent.VK_E) gp.gameState = gp.PLAY;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.gameState == gp.TITLE) {
            titleState(code);
        } else if (gp.gameState == gp.PLAY) {
            playState(code);
        } else if (gp.gameState == gp.PAUSE) {
            pauseState(code);
        } else if (gp.gameState == gp.DIALOGUE) {
            dialogueState(code);
        } else if (gp.gameState == gp.CHARACTER) {
            characterState(code);
        } else if (gp.gameState == gp.GAMEOVER) {
            gameOverState(code);
        }
    }

    public void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) gp.ui.commandNum = 1;
            gp.playSE(7);
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) gp.ui.commandNum = 0;
            gp.playSE(7);
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.reset();
                gp.gameState = gp.PLAY;
            } else gp.gameState = gp.TITLE;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) upPressed = false;
        if (code == KeyEvent.VK_S) downPressed = false;
        if (code == KeyEvent.VK_A) leftPressed = false;
        if (code == KeyEvent.VK_D) rightPressed = false;
        if (code == KeyEvent.VK_ENTER) enterPressed = false;
    }
}

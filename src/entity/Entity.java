package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.UltilityTool;

public class Entity {
    public GamePanel gp;
    public int worldX;
    public int worldY;
    public int speed;
    public String itemDrop;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    public boolean attacking = false;
    public String dialogues[] = new String[20];
    public Entity attacker;
    int dialogueIndex = 0;

    public int type; // player = 0, npc = 1; mons = 2;

    public BufferedImage image, image2, image3;
    public String name;
    public int defaultSpeed;

    public boolean collision = false;

    public boolean alive = true;
    public boolean dying = false;

    public int dyingCounter = 0;
    public boolean hpBarOn = false;
    public int hpBarCounter = 0;
    public int knockBackCounter = 0;

    public int maxLife;
    public int life;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;


    public int attackValue;
    public int defenseValue;

    public boolean onPath = false;
    public boolean knockBack = false;
    public String knockBackDirection;


    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public int getXdistance(Entity target) {
        int xDistant = Math.abs(worldX - target.worldX);
        return xDistant;
    }

    public int getYdistance(Entity target) {
        int yDistant = Math.abs(worldY - target.worldY);
        return yDistant;
    }

    public int getTitleDistance(Entity target) {
        int titleDistance = (getXdistance(target) + getYdistance(target)) / gp.tileSize;
        return titleDistance;
    }

    public int getGoalCol(Entity target) {
        int goalCol = (target.worldX + target.solidArea.x) / gp.tileSize;
        return goalCol;
    }

    public int getGoalRow(Entity target) {
        int goalRow = (target.worldY + target.solidArea.y) / gp.tileSize;
        return goalRow;
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UltilityTool uTool = new UltilityTool();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            scaledImage = uTool.scaleImage(scaledImage, width, height);
        } catch (Exception e) {

        }
        return scaledImage;
    }

    public void setAction() {
    }

    public void damageReaction() {
    }

    public void checkDrop() {
    }

    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] == null) {
                gp.obj[i] = droppedItem;
                gp.obj[i].worldX = worldX; // monster dead;
                gp.obj[i].worldY = worldY;
                break;
            }
        }
    }
    
    public void dropMonster(Entity droppedMon){
        for(int i = 0; i < gp.obj.length; i++){
            if(gp.monster[i] == null){
                gp.monster[i] = droppedMon;
//                gp.obj[i].worldX = worldX; // monster dead;
//                gp.obj[i].worldY = worldY;
                break;
            }
        }
    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }

        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        gp.cChecker.checkEnity(this, gp.monster);
        gp.cChecker.checkEnity(this, gp.npc);

        if (this.type == 2 && contactPlayer) {
            if (!gp.player.invincible) {
                gp.playSE(6);
                gp.player.life -= 1;
                gp.player.invincible = true;
            }
        }
    }

    public void update() {

        if (knockBack) {
            checkCollision();
            if (collisionOn) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            } else if (!collisionOn) {
                switch (gp.player.direction) {
                    case "up":
                        this.worldY -= this.speed;
                        break;
                    case "down":
                        this.worldY += this.speed;
                        break;
                    case "left":
                        this.worldX -= this.speed;
                        break;
                    case "right":
                        this.worldX += this.speed;
                        break;
                }
            }
            knockBackCounter++;
            if (knockBackCounter == 10) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        } else if (attacking) {
            attacking();
        } else {
            setAction();
            checkCollision();
            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        this.worldY -= this.speed;
                        break;
                    case "down":
                        this.worldY += this.speed;
                        break;
                    case "left":
                        this.worldX -= this.speed;
                        break;
                    case "right":
                        this.worldX += this.speed;
                        break;
                }
            }
            spriteCounter = spriteCounter + 1;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        if (this.type == 2 && contactPlayer) {
            damagePlayer(attack);
        }


        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
    }

    public void checkAttackOrNot(int rate, int straight, int horizontal) {
        boolean targetInRange = false;
        int xDis = getXdistance(gp.player);
        int yDis = getYdistance(gp.player);
        switch (direction) {
            case "up":
                if (gp.player.worldY < worldY && yDis < straight && xDis < horizontal) targetInRange = true;
                break;
            case "down":
                if (gp.player.worldY > worldY && yDis < straight && xDis < horizontal) targetInRange = true;
                break;
            case "left":
                if (gp.player.worldX < worldX && xDis < straight && yDis < horizontal) targetInRange = true;
                break;
            case "right":
                if (gp.player.worldX > worldX && xDis < straight && yDis < horizontal) targetInRange = true;
                break;
        }
        if (targetInRange) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;
            }
        }
    }

    public void checkShootOrNot(int rate, int shotInterval) {
        int i = new Random().nextInt(rate) + 1;
        if (i > 90 && !projectile.alive && shotAvailableCounter == shotInterval) {
            projectile.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;

        }
    }

    public void damagePlayer(int attack) {
        if (!gp.player.invincible) {
            gp.playSE(6);
            int damage = attack - gp.player.defense;
            if (damage < 0) {
                damage = 0;
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }

    public void setKnockBack(Entity target, Entity attacker) {
        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += 10;
        target.knockBack = true;
    }

    public void checkStartChasingOrNot(Entity target, int distance, int rate) {
        if (getTitleDistance(target) < distance) {
            int i = new Random().nextInt(rate);
            if (i > 50) {
                onPath = true;
            }
        }
    }

    public void checkStopChasingOrNot(Entity target, int distance, int rate) {
        if (getTitleDistance(target) > distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                onPath = false;
            }
        }
    }

    public void getRandomDirection() {
        actionLockCounter++;

        if (actionLockCounter == 90) {
            Random random = new Random();
            int i = random.nextInt(15) + 1; //random 1-16
            if (i % 4 == 1) {
                direction = "up";
            }
            if (i % 4 == 2) {
                direction = "down";
            }
            if (i % 4 == 3) {
                direction = "left";
            }
            if (i % 4 == 0) {
                direction = "right";
            }
            actionLockCounter = 0;
        }

    }

    public void attacking() {
        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            if (type == 2) {
                if (gp.cChecker.checkPlayer(this)) {
                    damagePlayer(attack);
                }
            } else {
                int monsterIndex = gp.cChecker.checkEnity(this, gp.monster);
                gp.player.damageMonster(monsterIndex, this, attack);
            }

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        if (dyingCounter <= 5) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        if (dyingCounter > 5 && dyingCounter <= 10)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (dyingCounter > 10 && dyingCounter <= 15)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        if (dyingCounter > 15 && dyingCounter <= 20)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (dyingCounter > 20 && dyingCounter <= 25)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        if (dyingCounter > 25 && dyingCounter <= 30)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (dyingCounter > 30 && dyingCounter <= 35)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        if (dyingCounter > 35 && dyingCounter <= 40)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (dyingCounter > 40) {
            dying = false;
            alive = false;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if (worldX + 48 > gp.player.worldX - gp.player.screenX && worldX - 48 < gp.player.worldX + gp.player.screenX && worldY + 48 > gp.player.worldY - gp.player.screenY && worldY - 48 < gp.player.worldY + gp.player.screenY) {

            int tempScreenX = screenX;
            int tempScreenY = screenY;
            switch (direction) {
                case "up":
                    if (!attacking) {
                        if (spriteNum == 1) image = up1;
                        if (spriteNum == 2) image = up2;
                    }
                    if (attacking) {
                        tempScreenY = screenY - gp.tileSize;
                        if (spriteNum == 1) image = attackUp1;
                        if (spriteNum == 2) image = attackUp2;
                    }
                    break;
                case "down":
                    if (!attacking) {
                        if (spriteNum == 1) image = down1;
                        if (spriteNum == 2) image = down2;
                    }
                    if (attacking) {
                        if (spriteNum == 1) image = attackDown1;
                        if (spriteNum == 2) image = attackDown2;
                    }
                    break;
                case "left":
                    if (!attacking) {
                        if (spriteNum == 1) image = left1;
                        if (spriteNum == 2) image = left2;
                    }
                    if (attacking) {
                        tempScreenX = screenX - gp.tileSize;
                        if (spriteNum == 1) image = attackLeft1;
                        if (spriteNum == 2) image = attackLeft2;
                    }
                    break;
                case "right":
                    if (!attacking) {
                        if (spriteNum == 1) image = right1;
                        if (spriteNum == 2) image = right2;
                    }
                    if (attacking) {
                        if (spriteNum == 1) image = attackRight1;
                        if (spriteNum == 2) image = attackRight2;
                    }
                    break;
            }

            //monster HP
            if (type == 2 && hpBarOn) {
                double oneScale = (double) gp.tileSize / maxLife;
                double hpBarValue = oneScale * life;

                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 3, gp.tileSize + 2, 12);
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 2, (int) hpBarValue, 10);

                hpBarCounter++;
                if (hpBarCounter > 300) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }
            if (dying) {
                dyingAnimation(g2);
            }
            g2.drawImage(image, tempScreenX, tempScreenY, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    public void searchPath(int goalCol, int goalRow) {

        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow, this);
        if (gp.pFinder.search()) {

            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBotY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) direction = "up";
            else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) direction = "down";
            else if (enTopY >= nextY && enBotY < nextY + gp.tileSize) {
                if (enLeftX > nextX) direction = "left";
                if (enLeftX < nextX) direction = "right";
            } else if (enTopY > nextY && enLeftX > nextX) {
                direction = "up";
                checkCollision();
                if (collision) direction = "left";
            } else if (enTopY > nextY && enLeftX < nextX) {
                direction = "up";
                checkCollision();
                if (collision) direction = "right";
            } else if (enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                checkCollision();
                if (collision) direction = "left";
            } else if (enTopY < nextY && enLeftX < nextX) {
                direction = "down";
                checkCollision();
                if (collision) direction = "right";
            }
        }
    }
}



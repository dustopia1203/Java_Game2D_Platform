package entity;

import java.util.*;

import main.*;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "Pleasure to meet you, adventurer.\nGuest you have a tough adventure.";
        dialogues[1] = "But don't be discouraged!\nThe treasure is in the hut\ndeep inside the forest on the left";
        dialogues[2] = "Slay all the monsters!\nFind all keys\nand take the treasure you deserve.";
        dialogues[3] = "God bless you, son.\nDon't worry:\nFORTIS FORTUNA ADIUVAT.";
    }

    public void setAction() {

        if (onPath == true) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
            ;
            searchPath(goalCol, goalRow);
        } else {
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

    }

    public void speak() {
        super.speak();

        //onPath = true;
    }
}

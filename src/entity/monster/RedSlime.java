/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Boots;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Rock;
import entity.monster.GreenSlime;
/**
 *
 * @author DoTranTrung
 */
public class RedSlime extends Entity{
    public RedSlime(GamePanel gp) {
        super(gp);

        itemDrop = "";
        type = 2;
        name = "Red Slime";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 8;
        life = maxLife;
        projectile = new OBJ_Rock(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/monsters/redslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monsters/redslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monsters/redslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monsters/redslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monsters/redslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monsters/redslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monsters/redslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monsters/redslime_down_2", gp.tileSize, gp.tileSize);
    }


    public void setAction() {
        if (onPath) {
            checkStopChasingOrNot(gp.player, 15, 100);
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
            checkShootOrNot(100, 30);
        } else {
            checkStartChasingOrNot(gp.player, 5, 100);
            getRandomDirection();
            checkShootOrNot(100, 30);
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        //direction = gp.player.direction;
        onPath = true;
    }

    public void checkDrop() {
        GreenSlime drop1 = new GreenSlime(gp);
        drop1.worldX = this.worldX;
        drop1.worldY = this.worldY;
        drop1.itemDrop = "Heart";
        
        GreenSlime drop2 = new GreenSlime(gp);
        drop2.worldX = this.worldX + gp.tileSize;
        drop2.worldY = this.worldY + gp.tileSize;
        drop2.itemDrop = "Heart";
        
        dropMonster(drop1);
        dropMonster(drop2);
    }
}

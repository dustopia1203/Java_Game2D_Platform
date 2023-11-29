package entity.monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Boots;
import object.OBJ_Heart;
import object.OBJ_Key;

public class Orc extends Entity{
    public Orc(GamePanel gp){
        super(gp);
        
        itemDrop = "";
        type = 2;
        name = "Orc";
        speed = 2;
        maxLife = 10;
        life = maxLife;
        attack = 2;
        defense = 2;
        
        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;
        
        getImage();
        getAttackImage();
    }
    
    public void getImage(){
        up1 = setup("/monsters/orc_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monsters/orc_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monsters/orc_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monsters/orc_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monsters/orc_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monsters/orc_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monsters/orc_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monsters/orc_right_2", gp.tileSize, gp.tileSize);
    }
    public void getAttackImage(){
        attackUp1 = setup("/monsters/orc_attack_up_1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/monsters/orc_attack_up_2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/monsters/orc_attack_down_1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/monsters/orc_attack_down_2", gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/monsters/orc_attack_left_1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/monsters/orc_attack_left_2", gp.tileSize * 2, gp.tileSize);
        attackRight1 = setup("/monsters/orc_attack_right_1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/monsters/orc_attack_right_2", gp.tileSize * 2, gp.tileSize);

    }
    
    public void setAction(){
        if(onPath){
            checkStopChasingOrNot(gp.player, 15, 100);
            searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
        }
        else {
            checkStartChasingOrNot(gp.player, 5, 100);
            getRandomDirection();
        }
        if(!attacking){
            checkAttackOrNot(30, gp.tileSize*4, gp.tileSize);
        }
    }
    
    public void damageReaction(){
        actionLockCounter = 0;
        //direction = gp.player.direction;
        onPath = true;
    }
    
    public void checkDrop(){
        if(itemDrop.equals("Key")){
            dropItem(new OBJ_Key(gp));
        }
        if(itemDrop.equals("Heart")){
            dropItem(new OBJ_Heart(gp));
        }
        if(itemDrop.equals("Boots")){
            dropItem(new OBJ_Boots(gp));
        }
    }

}

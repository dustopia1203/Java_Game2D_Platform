package main;

import entity.NPC_OldMan;
import entity.monster.GreenSlime;
import entity.monster.RedSlime;
import entity.monster.Orc;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_Door(gp);//ok
        gp.obj[0].worldX = 10 * 48;
        gp.obj[0].worldY = 12 * 48;

        gp.obj[1] = new OBJ_Door(gp);//ok
        gp.obj[1].worldX = 8 * 48;
        gp.obj[1].worldY = 28 * 48;

        gp.obj[2] = new OBJ_Door(gp);//ok
        gp.obj[2].worldX = 12 * 48;
        gp.obj[2].worldY = 23 * 48;

        gp.obj[3] = new OBJ_Chest(gp);//ok
        gp.obj[3].worldX = 10 * 48;
        gp.obj[3].worldY = 7 * 48;

        gp.obj[4] = new OBJ_Boots(gp);
        gp.obj[4].worldX = 37 * 48;
        gp.obj[4].worldY = 42 * 48;
        
        
    }
    
    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;
        
    }
    
    public void setMonster(){
        gp.monster[0] = new GreenSlime(gp);//trai tren
        gp.monster[0].worldX = gp.tileSize * 20;
        gp.monster[0].worldY = gp.tileSize * 36;
        gp.monster[0].itemDrop = "Key";
        
        
        gp.monster[1] = new RedSlime(gp);//trai tren
        gp.monster[1].worldX = gp.tileSize * 26;
        gp.monster[1].worldY = gp.tileSize * 38; 
        
        
        gp.monster[2] = new GreenSlime(gp);//giua duoi
        gp.monster[2].worldX = gp.tileSize * 38;
        gp.monster[2].worldY = gp.tileSize * 8;
        gp.monster[2].itemDrop = "Key"; 
        
        gp.monster[3] = new RedSlime(gp);//giua duoi
        gp.monster[3].worldX = gp.tileSize * 38;
        gp.monster[3].worldY = gp.tileSize * 10;
        
        gp.monster[4] = new GreenSlime(gp);//ho nuoc
        gp.monster[4].worldX = gp.tileSize * 20;
        gp.monster[4].worldY = gp.tileSize * 7;
        gp.monster[4].itemDrop = "Key"; 
        
        gp.monster[5] = new RedSlime(gp);//ho nuoc
        gp.monster[5].worldX = gp.tileSize * 26;
        gp.monster[5].worldY = gp.tileSize * 7;
        
        gp.monster[6] = new GreenSlime(gp);
        gp.monster[6].worldX = gp.tileSize * 13;
        gp.monster[6].worldY = gp.tileSize * 30;
        gp.monster[6].itemDrop = "Heart";
        
        gp.monster[7] = new Orc(gp);
        gp.monster[7].worldX = gp.tileSize * 12;
        gp.monster[7].worldY = gp.tileSize * 27;
        gp.monster[7].itemDrop = "Key";
        
    }
}



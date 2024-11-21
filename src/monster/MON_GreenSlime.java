package monster;

import java.util.Random;

import entity.Entity;
import objects.OBJ_Coin_Broze;
import objects.OBJ_CosmoCrystal;
import objects.OBJ_Heart;
import objects.OBJ_Rock;
import principal.GamePanel;

public class MON_GreenSlime extends Entity {
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        type = type_monster;
        name = "Slime Vermelho";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 7;
        life = maxLife;
        projectile = new OBJ_Rock(gp);
        attack = 2;
        defense = 0;
        exp = 5;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefultX = solidArea.x;
        solidAreaDefultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
    }

    // IA A* PathFinder
    public void setAction() {
        if (onPath == true) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
            searchPath(goalCol, goalRow);
            int i = new Random().nextInt(100) + 1;
            if(i > 197 && projectile.alive == false && shotAvailabelCounter == 30){
               projectile.set(worldX,worldY,direction,true,this); 
               gp.projectileList.add(projectile);
               
               //CHECK VACANCY
               for(int ii = 0; ii < gp.projectile[1].length; ii++){
                    if(gp.projectile[gp.currentMap][ii] == null){
                        gp.projectile[gp.currentMap][ii] = projectile;
                        break;
                    }
               }
               shotAvailabelCounter = 0;
            }

        } else {
            getRandomDirection(120);
            
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        // direction = gp.player.direction;
        onPath = true;
    }

    public void checkDrop() {
        // CAST A DIE
        int i = new Random().nextInt(100) + 1; // pick up a number from 1 to 100

        // SET THE MONSTER DROP
        /*
         * Drop de OBJ_Coin_gold: 50%
         * Drop de OBJ_Heart: 25%
         * Drop de OBJ_CosmoCrystal: 25%
         */

        if (i < 50) {
            dropItem(new OBJ_Coin_Broze(gp));
        }
        if (i > 50 && i < 75) {
            dropItem(new OBJ_Heart(gp));
        }
        if (i >= 75 && i < 100) {
            dropItem(new OBJ_CosmoCrystal(gp));
        }

    }
}
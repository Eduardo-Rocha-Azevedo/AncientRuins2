package entity;

import principal.GamePanel;

public class Projectile  extends Entity{
   Entity user;
   
    public Projectile(GamePanel gp){
        super(gp);
    }

    public void set(int wordX, int worldY , String direction, boolean alive, Entity user){
        this.worldX = wordX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = maxLife;

    }
    //? Se Nao esta se movendo colocar o speed na SUBCLASSE :)
    public void update(){
        if(user == gp.player){
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            if(monsterIndex != 999){
                gp.player.damageMonster(monsterIndex,attack, knockBackPower);
                generateParticle(user.projectile, gp.monster[gp.currentMap][monsterIndex]);
                alive = false;

            }
        }
        if(user != gp.player){
            boolean contactPlayer = gp.cChecker.checkPlayer(this);
            if(gp.player.invincible == false && contactPlayer == true){
                damagePlayer(attack);
                generateParticle(user.projectile, gp.player);
                alive = false;
            }
        }
        switch(direction){
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break; 
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }
        life--;
        if(life <= 0){
            alive = false;
        }

        spriteCouter++;
        if(spriteCouter > 12){
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCouter = 0;
        }
    }
    public boolean haveResources(Entity user){
        boolean haveResources = false;
        return haveResources;
    }

    public void subtractResource(Entity user){
        
    }
}
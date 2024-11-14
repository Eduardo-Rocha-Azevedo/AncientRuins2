package objects;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import principal.GamePanel;

public class OBJ_Fireball extends Projectile{
   
    public static final String objName = "Fireball";
    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        name = objName;
        maxLife = 80;
        speed = 7;
        attack = 5;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        up1 = setup("/projectile/fireball_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/projectile/fireball_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/projectile/fireball_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/projectile/fireball_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/projectile/fireball_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/projectile/fireball_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/projectile/fireball_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/projectile/fireball_right_2",gp.tileSize,gp.tileSize);
    }
    public boolean haveResources(Entity user){
        boolean haveResources = false;
        if(user.cosmo >= useCost){
            haveResources = true;
        }
        return haveResources;
    }

    public void subtractResource(Entity user){
        user.cosmo -= useCost;
       
    }

    public Color getParticleColor(){
        Color color = new Color(240,50,0);
        return color;
    }
    public int getParticleSize(){
        int size = 7;// pixels
        return size;
    }
    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }
}

package objects;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import principal.GamePanel;

public class OBJ_Rock extends Projectile{
    public static final String objName = "Rock";
    public OBJ_Rock(GamePanel gp) {
        super(gp);
        name = objName;
        maxLife = 50;
        speed = 7;
        attack = 5;
        useCost = 1;
        alive = false;
        
        getImage();
    }
    
    public void getImage(){
        up1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        up2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        down1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        left1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        left2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        right1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        right2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
    }

     public boolean haveResources(Entity user){
        boolean haveResources = false;
        if(user.ammo >= useCost){
            haveResources = true;
        }
        return haveResources;
    }

    public Color getParticleColor(){
        Color color = new Color(40,50,0);
        return color;
    }
    public int getParticleSize(){
        int size = 6;//6 pixels
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

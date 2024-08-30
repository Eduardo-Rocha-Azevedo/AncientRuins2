package objects;

import entity.Entity;
import entity.Projectile;
import principal.GamePanel;

public class OBJ_Rock extends Projectile{
    public static final String objName = "Rock";
    public OBJ_Rock(GamePanel gp) {
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

    public void subtractResource(Entity user){
        user.ammo -= useCost;
       
    }
}

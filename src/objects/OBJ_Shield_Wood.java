package objects;

import entity.Entity;
import principal.GamePanel;

public class OBJ_Shield_Wood extends Entity{
    GamePanel gp;

    public OBJ_Shield_Wood(GamePanel gp){
        super(gp);
        name = "Wooden Shield";
        down1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "A wooden shield";
    }

}
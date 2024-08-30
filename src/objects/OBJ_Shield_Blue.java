package objects;

import entity.Entity;
import principal.GamePanel;

public class OBJ_Shield_Blue extends Entity{
    public static final String objName = "Blue Shield";

    public OBJ_Shield_Blue(GamePanel gp){
        super(gp);
        name = objName;
        type = type_shield;
        down1 = setup("/objects/shield_blue", gp.tileSize, gp.tileSize);
        defenseValue = 5;
        description = "[Blue Shield]\nA blue shield";
       
    }
}

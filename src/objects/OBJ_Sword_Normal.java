package objects;

import entity.Entity;
import principal.GamePanel;

public class OBJ_Sword_Normal extends Entity{
    GamePanel gp;
    public static final String objName = "Normal Sword";
    public OBJ_Sword_Normal(GamePanel gp){
        super(gp);
        name = objName;
        down1 = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
        attackValue = 1;
        description = "A normal sword";
    }
}

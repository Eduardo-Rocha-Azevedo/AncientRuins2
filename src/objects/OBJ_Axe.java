package objects;

import entity.Entity;
import principal.GamePanel;

public class OBJ_Axe extends Entity {
    public static final String objName = "axe";
    public OBJ_Axe(GamePanel gp) {
        super(gp);
        type = type_axe;
        name = objName;
        attackValue = 2;
        down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
        description = "[Axe]\nA tool for cutting trees.";
        attackArea.width = 30;
        attackArea.height = 30;
    
    }

   
    
}

package objects;

import entity.Entity;
import principal.GamePanel;

public class OBJ_Potion_Red extends Entity {
    public static final String objName = "Red Potion";
    int value = 5;
    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        name = objName;
        type = type_consumable;
        down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
        description = "[Red Potion]\nHeals your life by "+value+"!";
        
    }
    
    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialog = "You drank the "+name+"!\n"+"Your life is restored by "+value+"!";
        entity.life += value;
        if(entity.life > entity.maxLife){
            entity.life = entity.maxLife;
        }
        gp.playSE(2);

    }
}

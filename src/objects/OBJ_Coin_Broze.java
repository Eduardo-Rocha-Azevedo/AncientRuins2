package objects;

import entity.Entity;
import principal.GamePanel;

public class OBJ_Coin_Broze extends Entity{
    public static final String objName = "Coin_Broze";
    public OBJ_Coin_Broze(GamePanel gp) {
        super(gp);
        name = objName;
        maxLife = 1;
        life = maxLife;
        type = type_pickOnly;
        down1 = setup("/objects/coin_bronze",gp.tileSize,gp.tileSize);
        value = 1;
    }
   
    public void use(Entity entity){
        gp.playSE(1);
        gp.ui.addMassage("Coin + "+value);
        entity.coin += value;
    }
    
}

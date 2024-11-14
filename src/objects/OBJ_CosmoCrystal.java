package objects;

import entity.Entity;
import principal.GamePanel;

public class OBJ_CosmoCrystal extends Entity {
	public static final String objName = "Cosmo Crystal";

	public OBJ_CosmoCrystal(GamePanel gp) {
		super(gp);
		type = type_pickOnly;
		name = objName;
		value = 1;
		down1 = setup("/objects/cosmo_full", gp.tileSize, gp.tileSize);
		image = setup("/objects/cosmo_full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/cosmo_blank", gp.tileSize, gp.tileSize);
	}

	public void use(Entity entity) {
		gp.playSE(2);
		gp.ui.addMassage("Cosmo + " + value);
		entity.cosmo += value;
	}
	
}
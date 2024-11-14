package objects;

import entity.Entity;
import principal.GamePanel;

public class OBJ_Heart extends Entity {
	GamePanel gp;
	public static final String objName = "Heart";

	public OBJ_Heart(GamePanel gp) {
		super(gp);
		name = objName;
		type = type_pickOnly;
		value = 2;
		down1 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
		image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/heart_half", gp.tileSize, gp.tileSize);
		image3 = setup("/objects/heart_blank", gp.tileSize, gp.tileSize);

	}

	public void use(Entity entity) {
		gp.playSE(2);
		gp.ui.addMassage("Life + " + value);
		entity.life += value;

	}
}

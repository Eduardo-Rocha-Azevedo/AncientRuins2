package objects;

import entity.Entity;
import principal.GamePanel;

public class OBJ_Key extends Entity {
	public static final String objName = "Key";
	GamePanel gp;

	public OBJ_Key(GamePanel gp) {
		super(gp);
		name = objName;
		down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
		description = "A key to open a door.";

	}
}

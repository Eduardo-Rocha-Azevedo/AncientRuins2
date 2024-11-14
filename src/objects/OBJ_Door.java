package objects;

import entity.Entity;
import principal.GamePanel;

public class OBJ_Door extends Entity {

	GamePanel gp;

	public OBJ_Door(GamePanel gp) {
		super(gp);
		down1 = setup("/objects/door.png",gp.tileSize,gp.tileSize);
	}
}

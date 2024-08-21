package objects;

import entity.Entity;
import principal.GamePanel;

public class OBJ_Chest extends Entity {

	GamePanel gp;

	public OBJ_Chest(GamePanel gp) {
		super(gp);
		down1 = setup("/objects/chestww",gp.tileSize,gp.tileSize);
		collisioOn = true;

		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefultX = solidArea.x;
		solidAreaDefultY = solidArea.y;
	}
}

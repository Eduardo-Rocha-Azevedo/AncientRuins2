package entity;

import principal.GamePanel;

public class NPC_OldMan extends Entity {

	public NPC_OldMan(GamePanel gp) {
		super(gp);
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 1;
		direction = "down";
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = 30;
		solidArea.height = 30;
		solidAreaDefultX = solidArea.x;
		solidAreaDefultY = solidArea.y;
		dialogueIndex = 0;
		getImage();
		setDialogue();
	}

	public void getImage() {
		up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
	}

	public void setDialogue() {
		dialogue[0] = "Hello, lad";
		dialogue[1] = "I'm the dialog test NPC \nlorem ipsum dolor sit amet";
		dialogue[2] = "I'm the dialog test NPC";
		dialogue[3] = "I'm the dialog test NPC";
	}

	public void speak() {
		super.speak();
		onPath = true;
	}

	public void setAction() {
		if (onPath == true) {
			int goalCol = 12;
            int goalRow = 9;
			searchPath(goalCol, goalRow);
		} 
		else {getRandomDirection(120);}

	}
}

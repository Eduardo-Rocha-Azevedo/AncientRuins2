package entity;

import java.util.Random;

import principal.GamePanel;

public class NPC_Farmer extends Entity{
    
    
    public NPC_Farmer(GamePanel gp){
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
		up1 = setup("/npc/farmer_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/farmer_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/farmer_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/farmer_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/farmer_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/farmer_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/farmer_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/farmer_right_2", gp.tileSize, gp.tileSize);
	}

	public void setDialogue() {
		dialogue[0] = "Hello, lad";
		dialogue[1] = "I'm farmer";
		dialogue[2] = "I'm the dialog test NPC";
		dialogue[3] = "I'm the dialog test NPC";
	}

	public void speak() {
		super.speak();
	}

	public void setAction() {
		actionLockCounter++;
		if (actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1;
			if (i <= 25) {
				direction = "up";
			}
			if (i > 25 && i <= 50) {
				direction = "down";
			}
			if (i > 50 && i <= 75) {
				direction = "left";
			}
			if (i > 75) {
				direction = "right";
			}
			actionLockCounter = 0;
		}
	}
}

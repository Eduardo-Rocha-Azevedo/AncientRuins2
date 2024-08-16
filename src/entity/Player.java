package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import principal.GamePanel;
import principal.KeyHandler;

public class Player extends Entity{
	
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	

	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;

		screenX = gp.screenWith/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);

		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefultX = solidArea.x;
		solidAreaDefultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		

		//attackArea.width = 48;
        //attackArea.height = 48;
		setDefultValues();
		
	}
	
	public void setDefultValues() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		defaultSpeed = 4;
		speed = defaultSpeed;
		direction = "down";
		maxLife = 6;
		life = maxLife;
		maxCosmo = 4;
		cosmo = maxCosmo;

		getImage();
	}

	public void getImage(){
	
		up1 = setup("/player/boy_up_1",gp.tileSize,gp.tileSize);
		up2 = setup("/player/boy_up_2",gp.tileSize,gp.tileSize);
		down1 = setup("/player/boy_down_1",gp.tileSize,gp.tileSize);
		down2 = setup("/player/boy_down_2",gp.tileSize,gp.tileSize);
		left1 = setup("/player/boy_left_1",gp.tileSize,gp.tileSize);
		left2 = setup("/player/boy_left_2",gp.tileSize,gp.tileSize);
		right1 = setup("/player/boy_right_1",gp.tileSize,gp.tileSize);
		right2 = setup("/player/boy_right_2",gp.tileSize,gp.tileSize);
	}
	
	
	public void update() {
		if(keyH.up == true || keyH.down == true || keyH.left == true || keyH.right == true){
				
			if(keyH.up == true) {
				direction = "up";
			
			}
			else if(keyH.down == true){
				direction = "down";
				
			}
			else if(keyH.left == true){
				direction = "left";
			
			}
			else if(keyH.right == true){
				direction = "right";
				
			}

			//CHECK TILE COLLISION
			collisioOn = false;
			gp.cChecker.checkTile(this);

			//CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);

			//CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);

			gp.eventH.checkEvent();
			gp.keyH.enterPressed = false;
			//IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisioOn == false){
				switch(direction){
					case "up":worldY -= speed;break;
					case "down":worldY += speed;break;
					case "left":worldX -= speed;break;
					case "right":worldX += speed;break;	
				}
			}
		
			spriteCouter++;
			if(spriteCouter > 12){
				if(spriteNum == 1){
					spriteNum = 2;
				}
				else if(spriteNum == 2){
					spriteNum = 1;
				}
				spriteCouter = 0;
			}
		}
	}

	public void interactNPC(int i){
		if(i != 999){
			if(gp.keyH.enterPressed == true){
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
		}
		
	}	

	public void pickUpObject(int i){
		if(i != 999){
		
		}
	}

	public void draw(Graphics g2) {

        //g2.setColor(Color.WHITE);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);

		BufferedImage image = null;
		switch(direction){

			case "up":
				if(spriteNum == 1){image = up1;}
				if(spriteNum == 2){image = up2;}break;

			case "down":
				if(spriteNum == 1){image = down1;}
				if(spriteNum == 2){image = down2;}break;

			case "left": 
				if(spriteNum == 1){image = left1;}
				if(spriteNum == 2){image = left2;}break;

			case "right":
				if(spriteNum == 1){image = right1;}
				if(spriteNum == 2){image = right2;}break;	
		}
		g2.drawImage(image, screenX, screenY, null);
	}
}












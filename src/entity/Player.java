package entity;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import principal.GamePanel;
import principal.KeyHandler;
import principal.UtiliyTool;

public class Player extends Entity{
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public int hasKey = 0;

	public Player(GamePanel gp, KeyHandler keyH) {
	
		this.gp = gp;
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

		setDefultValues();
		getPlayerImage();
	}
	
	public void setDefultValues() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
	}

	public void getPlayerImage(){
	
		up1 = setup("boy_up_1");
		up2 = setup("boy_up_2");
		down1 = setup("boy_down_1");
		down2 = setup("boy_down_2");
		left1 = setup("boy_left_1");
		left2 = setup("boy_left_2");
		right1 = setup("boy_right_1");
		right2 = setup("boy_right_2");
	}
	
	public BufferedImage setup(String imageName){
		UtiliyTool uTool = new UtiliyTool();
		BufferedImage image = null;
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/player/"+ imageName +".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		}catch(IOException e){
			e.printStackTrace();
		}
		return image;
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

			//IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisioOn == false){
				switch(direction){
					case "up":
					worldY -= speed;
					break;

					case "down":
					worldY += speed;
					break;

					case "left":
					worldX -= speed;
					break;

					case "right":
					worldX += speed;
					break;
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

	public void pickUpObject(int i){
		if(i != 999){
			String objName = gp.obj[i].name;
			
			switch(objName){
				case "Key":
				gp.playSE(1);
				hasKey++;
				gp.obj[i] = null;
				gp.ui.showMassage("You got a key!");
				break;

				case "Door":
				if(hasKey > 0){
					gp.playSE(3);
					gp.ui.showMassage("You unlocked the door!");
					gp.obj[i] = null;
					hasKey--;
				}else{
					gp.ui.showMassage("You need a key!");
				}
				break;

				case "Boots":
				gp.playSE(2);
				gp.ui.showMassage("Speed up!");
				speed++;
				gp.obj[i] = null;
				break;

				case "Chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSE(4);
				break;
			}
		}
	}

	public void draw(Graphics g2) {

        //g2.setColor(Color.WHITE);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);

		BufferedImage image = null;
		switch(direction){

			case "up":
				if(spriteNum == 1){
					image = up1;
				}
				if(spriteNum == 2){
					image = up2;
				}
				break;

			case "down":
				if(spriteNum == 1){
					image = down1;
				}
				if(spriteNum == 2){
					image = down2;
				}
				break;

			case "left":
				if(spriteNum == 1){
					image = left1;
				}
				if(spriteNum == 2){
					image = left2;
				}
				break;

			case "right":
				if(spriteNum == 1){
					image = right1;
				}
				if(spriteNum == 2){
					image = right2;
				}
				break;	
		}
		g2.drawImage(image, screenX, screenY, null);
	}
}












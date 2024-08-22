
package entity;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import principal.GamePanel;
import principal.UtiliyTool;

public class Entity {
	protected GamePanel gp;
	public int worldX, worldY;
	

	// SPRITE IMAGE
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage image, image2, image3;
	public String direction = "down";

	// COUNTER AND COLLISSION
	public boolean collisioOn = false;
	public int actionLockCounter = 0;
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public int spriteCouter = 0;
	public int spriteNum = 1;
	int dyainCounter = 0;
	int hpBarCounter = 0;

	// COLLISION
	public Rectangle solidArea = new Rectangle();
	public int solidAreaDefultX, solidAreaDefultY;
	
	public String dialogue[] = new String[20];
	public int dialogueIndex = 0;
	

	//CHARACTER STATUS
	public int maxLife;
	public int life;
	public String name;
	public int defaultSpeed;
	public int speed;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public int value;
	public int maxCosmo;
	public int cosmo;
	public Entity currentLight;
	public Entity currentWeapon;
	public Entity currentShield;
	//public Projectile projectile;
	public ArrayList <Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	public int motion1_duration;
	public int motion2_duration;
	public boolean boss = false;

	//state
	public boolean collision = false;
	boolean attacking = false;
	public boolean alive = true;
	public boolean dyain = false;
	boolean hpBarOn = false;


	//Type
	public int type; 
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword_normal = 3;
	public final int type_axe = 4;
	public final int type_shield = 5;
	public final int type_consumable = 6;
	public final int type_pickOnly = 7;
	public final int type_obstacle = 8;
	public final int type_light = 9;
	public final int type_sword_iron = 10;
	public final int type_pickaxe = 11;



	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	public void setDialogue(){}
	public void speak(){
		if(dialogue[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialog = dialogue[dialogueIndex];
        dialogueIndex++;
        switch(direction){
            case "up":direction = "down";break;
            case "down":direction = "up";break;
            case "left":direction = "right";break;
            case "right":direction = "left";break;
        }
        
	}
	public void setAction(){}
	public void update(){
		setAction();
		collisioOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this,false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);

		boolean contactPlayer = gp.cChecker.checkPlayer(this);

		if(this.type == type_monster && contactPlayer == true){
			if(gp.player.invincible == false){
				gp.player.life -= this.attack - gp.player.defense;
				gp.player.invincible = true;
			}
		}

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
	public void draw(Graphics2D g2){
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY
        ){
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
			if(invincible == true){
				hpBarOn = true;
				hpBarCounter = 0;
				changeAlpha(g2, 0.4f);
			}
				if(dyain == true){
					dyainAnimation(g2);
				}
		
				g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
				changeAlpha(g2, 1f);
		}
	}

	//animacao de dano
	public void dyainAnimation(Graphics2D g2){
		dyainCounter++;

		int i= 5;

		if(dyainCounter <= i)					       {changeAlpha(g2, 0f);}
		if(dyainCounter > i    && dyainCounter <= i *2){changeAlpha(g2, 1f);}
		if(dyainCounter > i *2 && dyainCounter <= i *3){changeAlpha(g2, 0f);}	
		if(dyainCounter > i *3 && dyainCounter <= i *4){changeAlpha(g2, 1f);}
		if(dyainCounter > i *4 && dyainCounter <= i *5){changeAlpha(g2, 0f);}
		if(dyainCounter > i *5   && dyainCounter <= i *6){changeAlpha(g2, 1f);}
		if(dyainCounter > i *6   && dyainCounter <= i *7){changeAlpha(g2, 0f);}
		if(dyainCounter > i *7   && dyainCounter <= i *8){changeAlpha(g2, 1f);}	

		if(dyainCounter > i*8){
			alive = false;
			dyain = false;
		}
		
	}

	public void changeAlpha(Graphics2D g2, float alphaValue){
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));

	}
	// carrega a img
	public BufferedImage setup(String imagePath, int width, int height) {
		UtiliyTool uTool = new UtiliyTool();
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, width, height); // it scales to tilesize , will fix for player attack(16px x
															// 32px) by adding width and height
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
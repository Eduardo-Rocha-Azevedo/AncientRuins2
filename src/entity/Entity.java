
package entity;
import java.awt.AlphaComposite;
import java.awt.Color;
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
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	public BufferedImage image, image2, image3;
	public String direction = "down";

	// COUNTER AND COLLISSION
	public boolean collisioOn = false;
	public int actionLockCounter = 0;
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public int spriteCouter = 0;
	public int spriteNum = 1;
	public int shotAvailableCounter = 0;
	int dyainCounter = 0;
	int hpBarCounter = 0;

	// COLLISION
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public Rectangle attackArea = new Rectangle(0,0, 0,0);
	public int solidAreaDefultX, solidAreaDefultY;
	
	public String dialogue[] = new String[20];
	public int dialogueIndex = 0;
	public String description = "";

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
	public int ammo;
	public Entity currentLight;
	public Entity currentWeapon;
	public Entity currentShield;
	public Projectile projectile;
	public ArrayList <Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	public int motion1_duration;
	public int motion2_duration;
	public boolean boss = false;

	// ITEM ATTRIBUTES
	public int attackValue;
	public int defenseValue;
	public int useCost;
	
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
	public void setItems(){}
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
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this,gp.npc);
		gp.cChecker.checkEntity(this,gp.monster);
		gp.cChecker.checkEntity(this,gp.iTile);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		if(this.type == type_monster && contactPlayer == true){
			damagePlayer(attack);
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

		if(invincible == true){
			invincibleCounter++;
			if(invincibleCounter > 40){
				invincible = false;
				invincibleCounter = 0;
			}
		}
		if(shotAvailableCounter < 30){
			shotAvailableCounter++;
		}
	}
	public void damagePlayer(int attack){
		if(gp.player.invincible == false){
			//we can give damage
			gp.playSE(6);
			int damage = attack - gp.player.defense;

			if(damage < 0){
				damage = 0;
			}
			gp.player.life -= damage;
			gp.player.invincible = true; 
		}
		
	}
	public void draw(Graphics2D g2){
		
		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

	
			switch(direction){
				case "up": 
				if(attacking == false) {
					if(spriteNum == 1) { image = up1;}
					if(spriteNum == 2) { image = up2;}
				}
				if(attacking == true)  {
					tempScreenY = screenY - gp.tileSize;
					if(spriteNum == 1) {image = attackUp1;}
					if(spriteNum == 2) {image = attackUp2;}
				}
				break;

			case "down": 
					if(attacking == false){

						if(spriteNum == 1) {image = down1;}
						if(spriteNum == 2) {image = down2;}
					}
					if(attacking == true){

						if(spriteNum == 1) {image = attackDown1;}
						if(spriteNum == 2) {image = attackDown2;}
					}
					break;
 				
			case "left":
					if(attacking == false){

						if(spriteNum == 1) {image = left1;}
						if(spriteNum == 2) {image = left2;}
					}
					if(attacking == true){
						tempScreenX = screenX - gp.tileSize;
						if(spriteNum == 1) {image = attackLeft1;}
						if(spriteNum == 2) {image = attackLeft2;}
					}
					break;

			case "right": 
					if(attacking == false){

						if(spriteNum == 1) {image = right1;}
						if(spriteNum == 2) {image = right2;}
					}
					if(attacking == true){
						if(spriteNum == 1) {image = attackRight1;}
						if(spriteNum == 2) {image = attackRight2;}
					}
					break;	
		}
		// Monster HP bar
		if(type == type_monster && hpBarOn == true){

			double oneScale = (double)gp.tileSize /maxLife;
			double hpBarValue = oneScale * life;

			g2.setColor(new Color(35,35,35));
			g2.fillRect(screenX - 2, screenY - 2, gp.tileSize + 4, 12);

			g2.setColor(new Color(255,0,30));
			g2.fillRect(screenX, screenY - 1, (int)hpBarValue, 10);

			hpBarCounter++;

			if(hpBarCounter > 600){
				hpBarCounter = 0;
				hpBarOn = false;	
			}
		}
		

		if(invincible == true){
			hpBarOn = true;
			hpBarCounter = 0;
			changeAlpha(g2, 0.4f);
		}

		if(dyain == true){
			dyainAnimation(g2);
		}

        g2.drawImage(image, tempScreenX, tempScreenY, null);
		changeAlpha(g2, 1f);
        }
	}
	public void damageReaction(){}
	public void use(Entity entity){}
	public void checkDrop(){}
	public void dropItem(Entity droppedItem){
		for(int i = 0; i < gp.obj.length; i++){
			if(gp.obj[i] == null){
				gp.obj[i] = droppedItem;
				gp.obj[i].worldX = worldX;
				gp.obj[i].worldY = worldY;
				break;
			}
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
			
		}
		
	}

	public void changeAlpha(Graphics2D g2, float alphaValue){
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));

	}
	public Color getParticleColor(){
        Color color = null;
        return color;
    }
    public int getParticleSize(){
        int size = 0;//6 pixels
        return size;
    }
    public int getParticleSpeed(){
        int speed = 0;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 0;
        return maxLife;
    }
	public void generateParticle(Entity generator, Entity target){
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();

		Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -1, -1); 
		Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 1, -1);
		Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -1, 1);
		Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 1, 1);
		gp.particleList.add(p1);
		gp.particleList.add(p2);
		gp.particleList.add(p3);
		gp.particleList.add(p4);
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
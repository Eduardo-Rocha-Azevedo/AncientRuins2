
package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import principal.GamePanel;
import principal.UtiliyTool;

public class Entity {
	protected GamePanel gp;
	public int worldX, worldY;

	// SPRITE IMAGE
	public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
	public BufferedImage attackUp1, attackUp2, attackUp3, attackDown1, attackDown2, attackDown3, attackLeft1,
			attackLeft2, attackLeft3, attackRight1, attackRight2, attackRight3;
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
	public int shotAvailabelCounter = 0;
	public int knockBackCounter = 0;

	// COLLISION
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public int solidAreaDefultX, solidAreaDefultY;

	public String dialogue[] = new String[20];
	public int dialogueIndex = 0;
	public String description = "";

	// CHARACTER STATUS
	public String name;
	public int maxLife;
	public int life;
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
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	public int motion1_duration;
	public int motion2_duration;
	public boolean boss = false;
	public int knockBackPower = 0;
	// Magia
	public int magicCost; // Custo de mana para usar a magia
	public int magicCooldownCounter = 0; // Contador de tempo de recarga da magia
	public boolean canCastMagic = true; // Verifica se a magia pode ser lançada
	public Projectile magicProjectile; // O projétil da magia, similar ao projétil de ataque

	// ITEM ATTRIBUTES
	public int attackValue;
	public int defenseValue;
	public int useCost;

	// state
	public boolean collision = false;
	boolean attacking = false;
	public boolean alive = true;
	public boolean dyain = false;
	boolean hpBarOn = false;
	public boolean onPath = false;
	public boolean knockBack = false;

	// Type
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

	public int getLeftX() {

		return worldX + solidArea.x;
	}

	public int getRightX() {

		return worldX + solidArea.x + solidArea.width;
	}

	public int getTopY() {

		return worldY + solidArea.y;
	}

	public int getBottomY() {

		return worldY + solidArea.y + solidArea.height;
	}

	public int getCoL() {
		return (worldX + solidArea.x) / gp.tileSize;
	}

	public int getRoW() {
		return (worldY + solidArea.y) / gp.tileSize;
	}

	public int getCenterX() {
		int centerX = worldX + left1.getWidth() / 2;
		return centerX;
	}

	public int getCenterY() {
		int centerY = worldY + up1.getHeight() / 2;
		return centerY;
	}

	public int getXDistance(Entity target) {
		int xDistance = Math.abs(getCenterX() - target.worldX);
		return xDistance;
	}

	public int getYDistance(Entity target) {
		int yDistance = Math.abs(getCenterY() - target.worldY);
		return yDistance;
	}

	public int getTileDistance(Entity target) {
		int tileDistance = (getXDistance(target) + getYDistance(target)) / gp.tileSize;
		return tileDistance;
	}

	public int getGoalCol(Entity target) {
		int goalCol = (target.worldX + target.solidArea.x) / gp.tileSize;// para seguir o player
		return goalCol;
	}

	public int getGoalRow(Entity target) {
		int goalRow = (target.worldY + target.solidArea.y) / gp.tileSize;
		return goalRow;
	}

	public int getScreenX() {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		return screenX;
	}

	public int getScreenY() {
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		return screenY;
	}

	public void setItems() {
	}

	public void setDialogue() {
	}

	public void speak() {
		if (dialogue[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialog = dialogue[dialogueIndex];
		dialogueIndex++;
		switch (direction) {
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

	public void setAction() {
	}

	public void checkCollision() {
		collisioOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		gp.cChecker.checkEntity(this, gp.iTile);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);

		if (this.type == type_monster && contactPlayer == true) {
			damagePlayer(attack);
		}
	}

	public void update() {
		if (knockBack == true) {
			checkCollision();
			if (collisioOn == true) {
				knockBackCounter = 0;
				knockBack = false;
				speed = defaultSpeed;
			} else if (collisioOn == false) {
				switch (gp.player.direction) {
					case "up"   : worldX -= attackArea.height; break;
					case "down" : worldX += attackArea.height; break;
					case "left" : worldY -= attackArea.width;  break;
					case "right": worldY += attackArea.width;  break;
				}
				knockBackCounter++;
				if (knockBackCounter == 10) {
					knockBack = false;
					knockBackCounter = 0;
					speed = defaultSpeed;
				}
			}
		} else {
			setAction();
			checkCollision();
			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if (collisioOn == false) {
				switch (direction) {
					case "up"    : worldY -= speed; break;
					case "down"  : worldY += speed; break;
					case "left"  : worldX -= speed; break;						
					case "right" : worldX += speed; break;
				}
			}
		}

		spriteCouter++;
		if (spriteCouter > 12) {
			if (spriteNum == 1) {spriteNum = 2;}
			else if (spriteNum == 2) {spriteNum = 1;}
			else if (spriteNum == 3) {spriteNum = 1;}
			spriteCouter = 0;
		}

		if (invincible == true) {
			invincibleCounter++;
			if (invincibleCounter > 40) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		if (shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
	}

	public void checkStopChassingOrNot(Entity target, int distance, int rate) {
		if (getTileDistance(target) > distance) {
			int i = new Random().nextInt(rate);
			if (i == 0) {
				onPath = false;
			}
		}
	}

	public void checkStartChasingOrNOt(Entity target, int distance, int rate) {
		if (getTileDistance(target) < distance) {
			int i = new Random().nextInt(rate);
			if (i == 0) {
				onPath = true;
			}
		}
	}

	public void checkShootOrNot(int rate, int shotInterval) {
		int i = new Random().nextInt(rate); // Sorteia de 0 a (rate - 1)

		if (i == 0 && !projectile.alive && shotAvailabelCounter >= shotInterval) {
			projectile.set(worldX, worldY, direction, true, this);
			// gp.projectileList.add(projectile);

			// Verifica se há espaço no array de projéteis
			for (int j = 0; j < gp.projectile[1].length; j++) {
				if (gp.projectile[gp.currentMap][j] == null) {
					gp.projectile[gp.currentMap][j] = projectile;
					break;
				}
			}
			shotAvailabelCounter = 0; // Reseta o contador de disparo
		}
	}

	public void getRandomDirection(int interval) {
		actionLockCounter++;
		if (actionLockCounter == interval) {

			Random random = new Random();
			int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

			if (i <= 25) {direction = "up";}
			if (i > 25 && i <= 50) {direction = "down";}
			if (i > 50 && i <= 75) {direction = "left";}
			if (i > 75 && i <= 100) {direction = "right";}
			actionLockCounter = 0;
		}
	}

	public void attacking() {
		spriteCouter++;

		if (spriteCouter <= motion1_duration) {
			spriteNum = 1;
		}
		if (spriteCouter > motion1_duration && spriteCouter <= motion2_duration) {
			spriteNum = 2;

			// Save current position
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;

			// Adjust player's wordX/Y for the attack
			switch (direction) {
				case "up":worldX -= attackArea.height;break;
				case "down":worldX += attackArea.height;break;
				case "left":worldY -= attackArea.width;break;
				case "right":worldY += attackArea.width;break;

			}
			// attackArea becomes solidArea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;

			if (type == type_monster) {
				if (gp.cChecker.checkPlayer(this) == true) {
					damagePlayer(attackValue);
				}
			} else {
				// check collision with the update worldX/Y and solidArea
				int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
				// gp.player.damageMonster(monsterIndex, this,attack,
				// currentWeapon.knockBackPower);

				int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
				gp.player.damageInteractiveTile(iTileIndex);

				int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
				gp.player.damageProjectile(projectileIndex);
			}

			// After attack, reset player's worldX/Y and solidArea
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if (spriteCouter > motion2_duration) {
			spriteNum = 1;
			spriteCouter = 0;
			attacking = false;
		}
	}

	public void damagePlayer(int attack) {
		if (gp.player.invincible == false) {
			// we can give damage
			gp.playSE(6);
			int damage = attack - gp.player.defense;

			if (damage < 0) {damage = 0;}
			gp.player.life -= damage;
			gp.player.invincible = true;
		}
	}
	
	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

			switch (direction) {

				case "up":
					if (attacking == false) {
						if (spriteNum == 1) {image = up1;}
						if (spriteNum == 2) {image = up2;}
						if (spriteNum == 3) {image = up3;}			
					}
					if (attacking == true) {
						tempScreenY = screenY - gp.tileSize;
						if (spriteNum == 1) {image = attackUp1;}
						if (spriteNum == 2) {image = attackUp2;}
						if (spriteNum == 3) {image = attackUp3;}
					}
					break;

				case "down":
					if (attacking == false) {
						if (spriteNum == 1) {image = down1;}
						if (spriteNum == 2) {image = down2;}
						if (spriteNum == 3) {image = down3;}
					}
					if (attacking == true) {
						if (spriteNum == 1) {image = attackDown1;}
						if (spriteNum == 2) {image = attackDown2;}
						if (spriteNum == 3) {image = attackDown3;}
					}
					break;

				case "left":
					if (attacking == false) {
						if (spriteNum == 1) {image = left1;}
						if (spriteNum == 2) {image = left2;}
						if (spriteNum == 3) {image = left3;}
					}
					if (attacking == true) {
						tempScreenX = screenX - gp.tileSize;
						if (spriteNum == 1) {image = attackLeft1;}
						if (spriteNum == 2) {image = attackLeft2;}
						if (spriteNum == 3) {image = attackLeft3;}
					}
					break;

				case "right":
					if (attacking == false) {
						if (spriteNum == 1) {image = right1;}
						if (spriteNum == 2) {image = right2;}
						if (spriteNum == 3) {image = right3;}
					}
					if (attacking == true) {
						if (spriteNum == 1) {image = attackRight1;}
						if (spriteNum == 2) {image = attackRight2;}
						if (spriteNum == 3) {image = attackRight3;}
					}
					break;
			}
			// Monster HP bar
			if (type == type_monster && hpBarOn == true) {

				double oneScale = (double) gp.tileSize / maxLife;
				double hpBarValue = oneScale * life;

				g2.setColor(new Color(35, 35, 35));
				g2.fillRect(screenX - 2, screenY - 2, gp.tileSize + 4, 12);

				g2.setColor(new Color(255, 0, 30));
				g2.fillRect(screenX, screenY - 1, (int) hpBarValue, 10);

				hpBarCounter++;

				if (hpBarCounter > 600) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}

			if (invincible == true) {
				hpBarOn = true;
				hpBarCounter = 0;
				changeAlpha(g2, 0.4f);
			}

			if (dyain == true) {
				dyainAnimation(g2);
			}

			g2.drawImage(image, tempScreenX, tempScreenY, null);
			changeAlpha(g2, 1f);
		}
	}

	public void damageReaction() {
	}

	public void use(Entity entity) {
	}

	public void checkDrop() {
	}

	public void dropItem(Entity droppedItem) {
		for (int i = 0; i < gp.obj[1].length; i++) {
			if (gp.obj[gp.currentMap][i] == null) {
				gp.obj[gp.currentMap][i] = droppedItem;
				gp.obj[gp.currentMap][i].worldX = worldX;
				gp.obj[gp.currentMap][i].worldY = worldY;
				break;
			}
		}
	}

	// animacao de dano
	public void dyainAnimation(Graphics2D g2) {
		dyainCounter++;

		int i = 5;

		if (dyainCounter <= i) {
			changeAlpha(g2, 0f);
		}
		if (dyainCounter > i && dyainCounter <= i * 2) {
			changeAlpha(g2, 1f);
		}
		if (dyainCounter > i * 2 && dyainCounter <= i * 3) {
			changeAlpha(g2, 0f);
		}
		if (dyainCounter > i * 3 && dyainCounter <= i * 4) {
			changeAlpha(g2, 1f);
		}
		if (dyainCounter > i * 4 && dyainCounter <= i * 5) {
			changeAlpha(g2, 0f);
		}
		if (dyainCounter > i * 5 && dyainCounter <= i * 6) {
			changeAlpha(g2, 1f);
		}
		if (dyainCounter > i * 6 && dyainCounter <= i * 7) {
			changeAlpha(g2, 0f);
		}
		if (dyainCounter > i * 7 && dyainCounter <= i * 8) {
			changeAlpha(g2, 1f);
		}
		if (dyainCounter > i * 8) {
			alive = false;
		}
	}

	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));

	}

	// PARTICLE
	public Color getParticleColor() {
		Color color = null;
		return color;
	}

	public int getParticleSize() {
		int size = 0;// 6 pixels
		return size;
	}

	public int getParticleSpeed() {
		int speed = 0;
		return speed;
	}

	public int getParticleMaxLife() {
		int maxLife = 0;
		return maxLife;
	}

	public void generateParticle(Entity generator, Entity target) {
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

	public void searchPath(int goalCol, int goalRow) {
		int startCol = (worldX + solidArea.x) / gp.tileSize;
		int startRow = (worldY + solidArea.y) / gp.tileSize;

		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
		if (gp.pFinder.search() == true) {
			// Next wordX & wordY
			int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
			int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

			// Entity's solidArea position
			int enLeftX = worldX + solidArea.x;
			int enRightX = worldX + solidArea.x + solidArea.width;
			int enTopY = worldY + solidArea.y;
			int enBottomY = worldY + solidArea.y + solidArea.height;

			if (enTopY > nextY && enLeftX >= nextX
					&& enRightX < nextX + gp.tileSize) {
				direction = "up";
			} else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "down";
			}

			else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
				// left or right
				if (enLeftX > nextX) {
					direction = "left";
				}
				if (enLeftX < nextX) {
					direction = "right";
				}

			} else if (enTopY > nextY && enLeftX > nextX) {
				// up or left
				direction = "up";
				checkCollision();
				if (collisioOn) {
					direction = "left";
				}

			} else if (enTopY > nextY && enLeftX < nextX) {
				// up or right
				direction = "up";
				checkCollision();
				if (collisioOn) {
					direction = "right";
				}
			} else if (enBottomY < nextY && enLeftX > nextX) {
				// down or left
				direction = "down";
				checkCollision();
				if (collisioOn) {
					direction = "left";
				}
			} else if (enBottomY < nextY && enLeftX < nextX) {
				// down or right
				direction = "down";
				checkCollision();
				if (collisioOn) {
					direction = "right";
				}
			}
		}
	}
}
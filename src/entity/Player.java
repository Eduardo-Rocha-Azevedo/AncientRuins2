package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import objects.OBJ_Fireball;
import objects.OBJ_GravityBall;
import objects.OBJ_Shield_Wood;
import objects.OBJ_Sword_Normal;
import principal.GamePanel;
import principal.KeyHandler;

public class Player extends Entity {

	KeyHandler keyH;
	OBJ_GravityBall gravityEffect; // Efeito de gravidade
	public final int screenX;
	public final int screenY;
	int standCounter = 0;
	public boolean attackCanceled = false;

	// Nova habilidade
	private boolean specialAbilityActive = false;
	private int specialAbilityCounter = 0;

	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;

		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;

		solidAreaDefultX = solidArea.x;
		solidAreaDefultY = solidArea.y;

		solidArea.width = 32;
		solidArea.height = 32;
		setDefultValues();
		getImage();
		getPlayerAttackImage();
		setItems();
		update();

	}

	public void setDefultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;

		defaultSpeed = 4;
		speed = defaultSpeed;
		direction = "down";

		// PLAYER STATUS
		level = 1;
		maxLife = 6;
		life = maxLife;
		maxCosmo = 4;
		cosmo = maxCosmo;
		strength = 1;
		dexterity = 1;
		exp = 0;
		nextLevelExp = 5;
		coin = 0;

		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		projectile = new OBJ_Fireball(gp);

		attack = getAttack();
		defense = getDefense();

	}

	public void setDefultPosition() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		direction = "down";
	}

	public void restoreLifeAndCosmo() {
		life = maxLife;
		cosmo = maxCosmo;
		invincible = false;
	}

	public void setItems() {
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
	}

	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		return attack = strength * currentWeapon.attackValue;
	}

	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}

	public void getImage() {
		up1 = setup("/player/player_up_1", gp.tileSize, gp.tileSize * 2);
		up2 = setup("/player/player_up_2", gp.tileSize, gp.tileSize * 2);
		up3 = setup("/player/player_up_3", gp.tileSize, gp.tileSize * 2);
		down1 = setup("/player/player_down_1", gp.tileSize, gp.tileSize * 2);
		down2 = setup("/player/player_down_2", gp.tileSize, gp.tileSize * 2);
		down3 = setup("/player/player_down_3", gp.tileSize, gp.tileSize * 2);
		left1 = setup("/player/player_left_1", gp.tileSize, gp.tileSize * 2);
		left2 = setup("/player/player_left_2", gp.tileSize, gp.tileSize * 2);
		left3 = setup("/player/player_left_3", gp.tileSize, gp.tileSize * 2);
		right1 = setup("/player/player_right_1", gp.tileSize, gp.tileSize * 2);
		right2 = setup("/player/player_right_2", gp.tileSize, gp.tileSize * 2);
		right3 = setup("/player/player_right_3", gp.tileSize, gp.tileSize * 2);
	}

	public void getPlayerAttackImage() {
		if (currentWeapon.type == type_sword_normal) {
			attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
			attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
			attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
			attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
			attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
			attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
			attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
			attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
		}
	}

	public void update() {
		// Verifica se está atacando
		if (attacking) {
			attacking();
		} else {
			// Atualiza a direção do movimento conforme pressionamento das teclas
			boolean moving = false;

			if (keyH.up || keyH.down || keyH.left || keyH.right) {
				moving = true;
				// Define a direção do movimento
				if (keyH.up)
					direction = "up";
				else if (keyH.down)
					direction = "down";
				else if (keyH.left)
					direction = "left";
				else if (keyH.right)
					direction = "right";

				// Checa as colisões
				collisioOn = false;
				gp.cChecker.checkTile(this);
				int objIndex = gp.cChecker.checkObject(this, true);
				pickUpObject(objIndex);
				int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
				interactNPC(npcIndex);
				int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
				contactMonster(monsterIndex);
				int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
				gp.eHandler.checkEvent();

				// Se não houver colisão, o jogador pode se mover
				if (!collisioOn) {
					switch (direction) {
						case "up":worldY -= speed;break;
						case "down":worldY += speed;break;
						case "left":worldX -= speed;break;
						case "right":worldX += speed;break;
					}
				}
			}

			// Atualiza a animação do movimento
			if (moving) {
				spriteCouter++;
				if (spriteCouter > 12) {
					spriteNum++;
					if (spriteNum > 3) {
						spriteNum = 1;
					}
					spriteCouter = 0;
				}
			} else {
				spriteNum = 1;
			}

			// Quando o jogador pressiona a tecla de ataque
			if (keyH.enterPressed && !attackCanceled) {
				gp.playSE(7);
				attacking = true;
				spriteCouter = 0;
			}

		}

		// Check type of projectile
		// PROJECTILE
		if (gp.keyH.gravityEffect) {
			handleGravityEffect();
		} else if(gp.keyH.shotKeyPressed){
			projectile = new OBJ_Fireball(gp);
		}

		// Lógica de disparo
		if (gp.keyH.shotKeyPressed && projectile.alive == false && shotAvailableCounter == 30
				&& projectile.haveResources(this)) {

			// Define coordenadas, direção e usuário
			projectile.set(worldX, worldY, direction, true, this);

			// Subtrai os recursos necessários
			projectile.subtractResource(this);

			// Verifica um espaço disponível para armazenar o projétil no mapa
			for (int i = 0; i < gp.projectile[gp.currentMap].length; i++) {
				if (gp.projectile[gp.currentMap][i] == null) {
					gp.projectile[gp.currentMap][i] = projectile;
					break;
				}
			}

			shotAvailableCounter = 0;
			gp.playSE(10);
		}

		// Verificação de invencibilidade
		if (invincible) {
			invincibleCounter++;
			if (invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}

		// Atualiza o contador de disparos disponíveis
		if (shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
	}

	private void handleGravityEffect() {
		
		if (gp.keyH.gravityEffect && !specialAbilityActive && shotAvailableCounter >= 30) {
			specialAbilityActive = true;
			specialAbilityCounter = 0;
			gp.keyH.gravityEffect = false;
	
			OBJ_GravityBall newProjectile = new OBJ_GravityBall(gp);
	
			if (newProjectile.haveResources(this)) {
				newProjectile.set(worldX, worldY, direction, true, this);
				newProjectile.subtractResource(this);

				for (int i = 0; i < gp.projectile[gp.currentMap].length; i++) {
					if (gp.projectile[gp.currentMap][i] == null) {
						gp.projectile[gp.currentMap][i] = newProjectile;
						break;
					}
				}
	
				shotAvailableCounter = 0;
				gp.playSE(10);

			}
		}
	
		// Contador para desativar a habilidade após 1 segundo (60 frames)
		if (specialAbilityActive) {
			specialAbilityCounter++;
			if (specialAbilityCounter > 60) { 
				specialAbilityActive = false;
				
			}
		}
	}
	

	public void attacking() {
		spriteCouter++;

		if (spriteCouter <= 5) {
			spriteNum = 1;
		}
		if (spriteCouter > 5 && spriteCouter <= 25) {
			spriteNum = 2;

			// Save current position
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;

			// Adjust player's wordX/Y for the attack
			switch(direction){
				case "up"    : worldX -= attackArea.height; break;
				case "down"  : worldX += attackArea.height; break;
				case "left"  : worldY -= attackArea.width;  break;
				case "right" : worldY += attackArea.width;  break;
			}
			// attackArea becomes solidArea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;

			// check collision with the update worldX/Y and solidArea
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex, attack, currentWeapon.knockBackPower);

			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			damageInteractiveTile(iTileIndex);

			int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
			damageProjectile(projectileIndex);

			// After attack, reset player's worldX/Y and solidArea
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if (spriteCouter > 25) {
			spriteNum = 1;
			spriteCouter = 0;
			attacking = false;
		}
		if(life > maxLife){life = maxLife;}
		if(cosmo > maxCosmo){cosmo = maxCosmo;}
	}

	// Quando o jogador interage com NPCs
	public void interactNPC(int i) {

		if (gp.keyH.enterPressed == true) {

			if (i != 999) {
				attackCanceled = true;
				gp.gameState = gp.dialogueState;
				gp.npc[gp.currentMap][i].speak();
			} else {
				attacking = true;
			}
		}
	}

	public void contactMonster(int i) {
		if (i != 999) {
			if (invincible == false && gp.monster[gp.currentMap][i].dyain == false) {
				gp.playSE(6);
				int damage = gp.monster[gp.currentMap][i].attack - defense;

				if (damage < 0) {
					damage = 0;
				}

				life -= damage;
				invincible = true;
			}
		}
	}

	// Atualização do impacto no monstro

	public void damageMonster(int i, int attack, int knockBackPower) {
		if (i != 999) {

			if (gp.monster[gp.currentMap][i].invincible == false) {

				gp.playSE(5);
				if (knockBackPower > 0) {
					knockBack(gp.monster[gp.currentMap][i], knockBackPower);
				}

				int damage = attack - gp.monster[gp.currentMap][i].defense;
				if (damage < 0) {
					damage = 0;
				}
				gp.monster[gp.currentMap][i].life -= damage;
				gp.ui.addMassage(damage + " damage to " + gp.monster[gp.currentMap][i].name);
				gp.monster[gp.currentMap][i].invincible = true;
				gp.monster[gp.currentMap][i].damageReaction();

				// Check if monster is dead and remove of list
				if (gp.monster[gp.currentMap][i].life <= 0) {
					gp.monster[gp.currentMap][i].dyain = true;
					gp.ui.addMassage("Killed " + gp.monster[gp.currentMap][i].name + "!");
					gp.ui.addMassage("EXP + " + gp.monster[gp.currentMap][i].exp);
					exp += gp.monster[gp.currentMap][i].exp;
					checkLevelUp();

				}
			}
		}
	}

	public void pickUpObject(int i) {
		if (i != 999) {
			// PIck only items
			if (gp.obj[gp.currentMap][i].type == type_pickOnly) {
				gp.obj[gp.currentMap][i].use(this);
				gp.obj[gp.currentMap][i] = null;
			} else {
				String text;
				if (inventory.size() != maxInventorySize) {
					inventory.add(gp.obj[gp.currentMap][i]);
					gp.playSE(1);
					text = "Got to " + gp.obj[gp.currentMap][i].name;
				} else {
					text = "You cannot carry any more!";
				}
				gp.ui.addMassage(text);
				gp.obj[gp.currentMap][i] = null;
			}
		}
	}

	public void damageProjectile(int i) {
		if (i != 999) {
			Entity projectile = gp.projectile[gp.currentMap][i];
			projectile.alive = false;
			generateParticle(projectile, projectile);
		}
	}

	public void damageInteractiveTile(int i) {
		if (i != 999 && gp.iTile[gp.currentMap][i].destructible == true &&
				gp.iTile[gp.currentMap][i].isCorrectItem(this) == true &&
				gp.iTile[gp.currentMap][i].invincible == false) {

			gp.iTile[gp.currentMap][i].playSE();
			gp.iTile[gp.currentMap][i].life--;
			gp.iTile[gp.currentMap][i].invincible = true;

			// generate particle
			generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
			if (gp.iTile[gp.currentMap][i].life == 0) {
				gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
			}
		}
	}

	public void knockBack(Entity entity, int knockBackPower) {
		entity.direction = direction;
		entity.speed += knockBackPower;
		entity.knockBack = true;
	}

	public void selectItem() {
		int itemIndex = gp.ui.getItemIndexOnSlot();

		if (itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);

			if (selectedItem.type == type_sword_normal || selectedItem.type == type_axe) {
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
				gp.ui.addMassage("Equipped " + selectedItem.name);
			}

			if (selectedItem.type == type_shield) {
				currentShield = selectedItem;
				defense = getDefense();
				gp.ui.addMassage("Equipped " + selectedItem.name);
			}

			if (selectedItem.type == type_consumable) {
				selectedItem.use(this);
				inventory.remove(itemIndex);
			}
		}
	}

	public void checkLevelUp() {
		if (exp >= nextLevelExp) {
			level++;
			cosmo++;
			nextLevelExp *= 5;
			maxLife += 2;
			maxCosmo++;
			strength++;
			dexterity++;

			gp.playSE(8);
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialog = "You're level " + level + " now!\n" + "You feeel stronger!";
		}
	}

	// Desenha o jogador na tela
	public void draw(Graphics g) {
		BufferedImage image = null;

		switch (direction) {
			case "up":
				if (attacking) {
					if (spriteNum == 1) {image = attackUp1;}
					else if (spriteNum == 2) {image = attackUp2;}
				} else {
					if (spriteNum == 1) {image = up1;}
					else if (spriteNum == 2) {image = up2;}
					else if (spriteNum == 3) {image = up3;}
				}
				break;
			case "down":
				if (attacking) {
					if (spriteNum == 1) {image = attackDown1;}
					 else if (spriteNum == 2) {image = attackDown2;}
				} else {
					if (spriteNum == 1) {image = down1;}
					else if (spriteNum == 2) {image = down2;}	
					else if (spriteNum == 3) {image = down3;}
				}
				break;
			case "left":
				if (attacking) {
					if (spriteNum == 1) {image = attackLeft1;}
					else if (spriteNum == 2) {image = attackLeft2;}
				} else {
					if (spriteNum == 1) {image = left1;}
					else if (spriteNum == 2) {image = left2;}
					else if (spriteNum == 3) {image = left3;}
				}
				break;
			case "right":
				if (attacking) {
					if (spriteNum == 1) {image = attackRight1;}
					else if (spriteNum == 2) {image = attackRight2;}

				} else {
					if (spriteNum == 1) {image = right1;}
					else if (spriteNum == 2) {image = right2;} 
					else if (spriteNum == 3) {image = right3;}
				}
				break;
		}

		// Desenha o personagem
		g.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}

}
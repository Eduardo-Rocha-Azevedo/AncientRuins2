package principal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import objects.OBJ_CosmoCrystal;
import objects.OBJ_Heart;

public class UI {
	GamePanel gp;
	Graphics2D g2;

	BufferedImage keyImage, heart_full, heart_half, heart_blank, cosmo_full, cosmo_blank;
	public boolean gameFinished = false;
	Font maruMonica;
	// mensage info
	public boolean messageOn = false;
	public String currentDialog = "";
	public int commandNum = 0;
	int titleScreenState = 0;
	int subState = 0;
	int counter = 0;
	// inventory
	public int slotCol = 0;
	public int slotRow = 0;

	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public Entity npc;

	public UI(GamePanel gp) {
		this.gp = gp;

		InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
		try {
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);

		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		Entity cosmo = new OBJ_CosmoCrystal(gp);
		cosmo_full = cosmo.image;
		cosmo_blank = cosmo.image2;

	}

	public void addMassage(String text) {
		message.add(text);
		messageCounter.add(0);
	}

	public void draw(Graphics2D g2) {
		this.g2 = g2;

		g2.setFont(maruMonica);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);

		// TITLE SCREEN
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		// PLAY STATE
		if (gp.gameState == gp.playState) {
			// HEARTS
			drawPlayerLife();
			drawMessage();
		}
		// PAUSE STATE
		else if (gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();

		}
		// DIALOG STATE
		else if (gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogScreen();
		}

		// CHARACTER STATE
		else if (gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory();

		}

		// OPTION STATE
		else if (gp.gameState == gp.optionState) {
			drawOptionScreen();
		}
		// GAME OVER STATE
		else if (gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		// TRANSITION STATE
		else if (gp.gameState == gp.transitionState) {
			drawTransition();
		}
		// TRADE STATE
		else if (gp.gameState == gp.tradeState) {
			drawTradeScreen();
		}
	}

	public void drawTitleScreen() {
		if (titleScreenState == 0) {
			// TITLE NAME
			// gp.playMusic(12);
			g2.setColor(new Color(0, 0, 0));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
			String text = "Ancient Ruin";
			int x = getXForCenterText(text);
			int y = gp.tileSize * 3;

			// SHADOW
			g2.setColor(Color.gray);
			g2.drawString(text, x + 5, y + 5);

			// MAIN COLOR
			g2.setColor(Color.white);
			g2.drawString(text, x, y);

			// CHARACTER IMAGE
			x = gp.screenWidth / 2 - gp.tileSize / 2;
			y += gp.tileSize * 2 - 30;
			g2.drawImage(gp.player.down1, x, y, gp.tileSize, gp.tileSize, null);

			// MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));

			text = "NEW GAME";
			x = getXForCenterText(text);
			y += gp.tileSize * 2;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x - (gp.tileSize / 2), y);

			}

			text = "LOAD GAME";
			x = getXForCenterText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x - (gp.tileSize / 2), y);
			}

			text = "QUIT";
			x = getXForCenterText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if (commandNum == 2) {
				g2.drawString(">", x - (gp.tileSize / 2), y);
			}
		}
		// History Screen
		else if (titleScreenState == 1) {
			drawHistoryScreen();

		}
	}

	public void drawHistoryScreen() {
		int x, y, width, height;

		// gp.playMusic(12);
		g2.setColor(new Color(0, 0, 0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		// SELECTION SCREEN
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));

		x = 2;
		y = 3;
		width = gp.tileSize * 20;
		height = gp.tileSize * 12;
		drawSubWindow(x, y, width, height - 5);
		// g2.drawString("Moeda: ", x+24, y +60);

		String text = " No vasto continente de Aetheron, envolto em lendas e mistérios, erguem-se as ruínas de uma";
		x = getXForCenterText(text);
		y = gp.tileSize;
		g2.drawString(text, x, y);
		text = "civilização antiga, perdida no tempo. Há muito esquecida pelos habitantes modernos, estas  ";
		x = getXForCenterText(text);
		y = gp.tileSize + 35;
		g2.drawString(text, x, y);
		text = "ruínas são um labirinto de segredos e perigos, esperando para serem explorados.               ";
		x = getXForCenterText(text);
		y += 35;
		g2.drawString(text, x, y);
		text = "Com uma antiga carta do seu avô, um renomado explorador que desapareceu procurando as";
		x = getXForCenterText(text);
		y += 35;
		g2.drawString(text, x, y);
		text = "mesmas ruínas décadas atrás, você parte em uma jornada.                                                     ";
		x = getXForCenterText(text);
		y += 35;
		g2.drawString(text, x, y);

		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
		text = "Iniciar Jogo";
		x = getXForCenterText(text);
		y += gp.tileSize * 5;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - 18, y);
			// gp.stopMusic();
		}
		text = "Voltar        ";
		x = getXForCenterText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - 18, y);

		}

	}

	public void drawMessage() {
		int messageX = gp.tileSize;
		int messageY = gp.tileSize * 4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));

		// draw message
		for (int i = 0; i < message.size(); i++) {
			if (message.get(i) != null) {
				g2.setColor(Color.lightGray);
				g2.drawString(message.get(i), messageX + 2, messageY + 2);
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);

				int counter = messageCounter.get(i) + 1; // = mesageCounter++
				messageCounter.set(i, counter);// set the counter to the array
				messageY += 45;

				// clear message
				if (messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}

	public void drawPlayerLife() {
		int x = gp.tileSize / 2;
		int y = gp.tileSize / 2;
		int iconSize = 32;
		int cosmoStartX = (gp.tileSize / 2) - 5;
		int cosmoStartY = 0;
		int i = 0;

		// draw max hearts
		while (i < gp.player.maxLife / 2) {
			g2.drawImage(heart_blank, x, y, iconSize, iconSize, null);
			i++;
			x += iconSize;
			cosmoStartY = y + iconSize;
			if (i % 8 == 0) {
				x = gp.tileSize / 2;
				y += iconSize;
			}
		}
		// reset
		x = gp.tileSize / 2;
		y = gp.tileSize / 2;
		i = 0;

		// draw current life
		while (i < gp.player.life) {
			g2.drawImage(heart_half, x, y, iconSize, iconSize, null);
			i++;
			if (i < gp.player.life) {
				g2.drawImage(heart_full, x, y, iconSize, iconSize, null);
			}
			i++;
			x += iconSize;
		}
		// draw max cosmo crystal
		x = (gp.tileSize / 2) - 5;
		y = (int) (gp.tileSize * 1.5);
		i = 0;
		while (i < gp.player.maxCosmo) {
			g2.drawImage(cosmo_blank, x, y, iconSize, iconSize, null);
			i++;
			x += 25;
		}

		// draw cosmo
		x = (gp.tileSize / 2) - 5;
		y = (int) (gp.tileSize * 1.5);
		i = 0;
		while (i < gp.player.cosmo) {
			g2.drawImage(cosmo_full, x, y, iconSize, iconSize, null);
			i++;
			x += 25;
		}
	}

	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80f));
		String text = "PAUSED";
		int x = getXForCenteredText(text);
		int y = gp.screenHeight / 2;
		g2.drawString(text, x, y);
	}

	public void drawDialogScreen() {
		// WINDOW
		int x = gp.tileSize * 3;
		int y = gp.tileSize / 2;
		int width = gp.screenWidth - (gp.tileSize * 6);
		int height = gp.tileSize * 4;

		drawSubWindow(x, y, width, height);

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32f));
		x += gp.tileSize;
		y += gp.tileSize;

		for (String line : currentDialog.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}

	public void drawCharacterScreen() {
		// Craete a frame
		final int frameX = gp.tileSize * 2;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize * 5;
		final int frameHeight = gp.tileSize * 10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		// TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32f));

		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 35;

		// NAME
		g2.drawString("Level: ", textX, textY);
		textY += lineHeight;
		g2.drawString("Life: ", textX, textY);
		textY += lineHeight;
		g2.drawString("Cosmo: ", textX, textY);
		textY += lineHeight;
		g2.drawString("Strength: ", textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity: ", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack: ", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense: ", textX, textY);
		textY += lineHeight;
		g2.drawString("Exp: ", textX, textY);
		textY += lineHeight;
		g2.drawString("Next Level: ", textX, textY);
		textY += lineHeight;
		g2.drawString("Coin: ", textX, textY);
		textY += lineHeight + 10;
		g2.drawString("Weapon: ", textX, textY);
		textY += lineHeight + 15;
		g2.drawString("Shield: ", textX, textY);
		textY += lineHeight;

		// VALUES
		int tailX = (frameX + frameWidth) - 30;
		// reset textY
		textY = frameY + gp.tileSize;
		String value;

		value = String.valueOf(gp.player.level);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.life + " / " + gp.player.maxLife);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.cosmo + " / " + gp.player.maxCosmo);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.strength);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.dexterity);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.attack);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.defense);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.exp);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.coin);
		textX = getXForAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 24, null);
		textY += gp.tileSize;

		g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 24, null);

	}

	public void drawInventory() {
		// Create a frame
		int frameX = gp.tileSize * 12;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 6;
		int frameHeight = gp.tileSize * 5;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		// Slot
		final int slotXStart = frameX + 20;
		final int slotYStart = frameY + 20;
		int slotX = slotXStart;
		int slotY = slotYStart;
		int slotSize = gp.tileSize + 3;

		// Item selector cursor
		int cursorX = slotXStart + (slotSize * slotCol);
		int cursorY = slotYStart + (slotSize * slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;

		// Draw player's items
		for (int i = 0; i < gp.player.inventory.size(); i++) {
			// Equip item
			if (gp.player.inventory.get(i) == gp.player.currentWeapon
					|| gp.player.inventory.get(i) == gp.player.currentShield) {
				g2.setColor(new Color(240, 190, 90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}

			g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
			slotX += slotSize;
			if (i == 4 || i == 9 || i == 14) {
				slotX = slotXStart;
				slotY += slotSize;
			}
		}

		// Draw cursor
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

		// Description frame
		int dFrameX = frameX;
		int dFrameY = frameY + frameHeight;
		int dFrameWidth = frameWidth;
		int dFrameHeight = gp.tileSize * 3;

		// Draw description
		int textX = dFrameX + 20;
		int textY = dFrameY + gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(27f));
		int itemIndex = getItemIndexOnSlot();

		if (itemIndex < gp.player.inventory.size()) {
			drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
			for (String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
				g2.drawString(line, textX, textY);
				textY += 30;
			}

		}

	}

	public void drawGameOverScreen() {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		int x;
		int y;
		String text;

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
		text = "GAME OVER";
		// SHADOW
		g2.setColor(Color.gray);
		x = getXForCenterText(text);
		y = gp.tileSize * 4;
		g2.drawString(text, x, y);

		// MAIN COLOR
		g2.setColor(Color.white);
		g2.drawString(text, x - 5, y - 5);

		// Retry
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
		text = "Retry";
		x = getXForCenterText(text);
		y += gp.tileSize * 4;
		g2.drawString(text, x, y);

		if (commandNum == 0) {
			g2.drawString(">", x - 40, y);

		}

		// Back to title screen
		text = "Menu";
		x = getXForCenterText(text);
		y += 55;
		g2.drawString(text, x, y);

		if (commandNum == 1) {
			g2.drawString(">", x - 40, y);
		}
	}

	public void drawOptionScreen() {

		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32f));

		// Create a frame
		int frameX = gp.tileSize * 6;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 8;
		int frameHeight = gp.tileSize * 10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		switch (subState) {
			case 0:
				options_top(frameX, frameY);
				break;
			case 1:
				options_fullScreen(frameX, frameY);
				break;
			case 2:
				options_control(frameX, frameY);
				break;
			case 3:
				options_endGame(frameX, frameY);
				break;
		}
		gp.keyH.enterPressed = false;
	}

	public void options_top(int frameX, int frameY) {
		int textX;
		int textY;

		// TITLE
		String text = "Options";
		textX = getXForCenterText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);

		// Full screen on/off
		textX = frameX + gp.tileSize;
		textY += gp.tileSize * 2;
		g2.drawString("Full Screen", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				if (gp.fullScreenOn == false) {
					gp.fullScreenOn = true;
					gp.setFullScreen();
				} else if (gp.fullScreenOn == true) {
					gp.fullScreenOn = false;

				}
				subState = 1;
			}

		}

		// Music
		textY += gp.tileSize;
		g2.drawString("Music", textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
		}

		// SE
		textY += gp.tileSize;
		g2.drawString("Sound Effect", textX, textY);
		if (commandNum == 2) {
			g2.drawString(">", textX - 25, textY);
		}

		// Controls
		textY += gp.tileSize;
		g2.drawString("Controls", textX, textY);
		if (commandNum == 3) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}

		// End Game
		textY += gp.tileSize;
		g2.drawString("Exit", textX, textY);
		if (commandNum == 4) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 3;
				commandNum = 0;
			}
		}

		// Back
		textY += gp.tileSize * 2;
		g2.drawString("Back", textX, textY);
		if (commandNum == 5) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				gp.gameState = gp.playState;
				commandNum = 0;
			}
		}

		// FULL SCREEN CHECK BOX
		textX = frameX + (int) (gp.tileSize * 4.5);
		textY = frameY + gp.tileSize * 2 + 24;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 24, 24);
		if (gp.fullScreenOn == true) {
			g2.fillRect(textX, textY, 24, 24);
		}

		// MUSIC VOLUME
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);// 120/5 = 24
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		// SE VOLUME
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);

		gp.config.saveConfig();

	}

	public void options_fullScreen(int frameX, int frameY) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize * 3;

		currentDialog = "The changes will take \neffect after restarting \nthe game.";
		for (String line : currentDialog.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 30;
		}

		// Back the menu
		textY += gp.tileSize * 3;
		g2.drawString("Back", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
			}
		}
	}

	public void options_control(int frameX, int frameY) {
		int textX;
		int textY;

		// TITLE
		String text = "Controls";
		textX = getXForCenterText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);

		textX = frameX + gp.tileSize;
		textY += gp.tileSize;
		g2.drawString("Move", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Confirm/Attack", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Shoot/Cast", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Character status", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Pause", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Options", textX, textY);
		textY += gp.tileSize;

		textX = frameX + gp.tileSize * 6;
		textY = frameY + gp.tileSize * 2;
		g2.drawString("WASD", textX, textY);
		textY += gp.tileSize;
		g2.drawString("Enter", textX, textY);
		textY += gp.tileSize;
		g2.drawString("F", textX, textY);
		textY += gp.tileSize;
		g2.drawString("E", textX, textY);
		textY += gp.tileSize;
		g2.drawString("P", textX, textY);
		textY += gp.tileSize;
		g2.drawString("ESC", textX, textY);
		textY += gp.tileSize;

		// BACK
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize * 9;
		g2.drawString("Back", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 4;
			}
		}
	}

	public void options_endGame(int frameX, int frameY) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize * 3;

		currentDialog = "Quit the game and \nreturn to the title menu?";
		for (String line : currentDialog.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 30;
		}

		// YES
		String text = "Sim";
		textX = getXForCenterText(text);
		textY += gp.tileSize * 3;
		g2.drawString(text, textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				gp.stopMusic();
				gp.gameState = gp.titleState;
				gp.ui.titleScreenState = 0;

			}
		}

		// NO
		text = "Não";
		textX = getXForCenterText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 4;
			}
		}
	}

	public void drawTransition() {
		counter++;
		g2.setColor(new Color(0, 0, 0, counter * 5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		if (counter == 50) {
			counter = 0;
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;

		}
	}

	public void drawTradeScreen() {
		switch (subState) {
			case 0:
				trade_select();
				break;
			case 1:
				trade_buy();
				break;
			case 2:
				trade_sell();
				break;
		}
		gp.keyH.enterPressed = false;
	}

	public void trade_select() {
		drawDialogScreen();

		// DRAW WINDON
		int x = gp.tileSize * 15;
		int y = gp.tileSize * 4;
		int width = gp.tileSize * 3;
		int height = (int) (gp.tileSize * 3.5);
		drawSubWindow(x, y, width, height);

		// Draw text
		x += gp.tileSize;
		y += gp.tileSize;
		g2.drawString("Buy", x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - gp.tileSize / 2, y);
		}
		y += gp.tileSize;
		g2.drawString("Sell", x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.tileSize / 2, y);
		}
		y += gp.tileSize;
		g2.drawString("Back", x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - gp.tileSize / 2, y);
		}

	}

	public void trade_buy() {

	}

	public void trade_sell() {

	}

	public int getItemIndexOnSlot() {
		int itemIndex = slotCol + (slotRow * 5);
		return itemIndex;
	}

	public void drawSubWindow(int x, int y, int width, int height) {
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);

		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}

	public int getXForCenterText(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		return x;
	}

	public int getXForCenteredText(String text) {
		int x;
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		x = gp.screenWidth / 2 - length / 2;
		return x;
	}

	public int getXForAlignToRightText(String text, int tailX) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
}

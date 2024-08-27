package principal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;

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
	public String message = "";
	public String currentDialog = "";
	int messageCounter = 0;
	public int commandNum = 0;
	int titleScreenState = 0;

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

	public void showMassage(String text) {
		message = text;
		messageOn = true;
	}

	public void draw(Graphics2D g2) {
		this.g2 = g2;

		g2.setFont(maruMonica);
		g2.setColor(Color.WHITE);

		// TITLE SCREEN
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		// PLAY STATE
		if (gp.gameState == gp.playState) {
			drawPlayerLife();
		}
		// PAUSE STATE
		if (gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		// DIALOGUE STATE
		if (gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogScreen();
		}
		  //CHARACTER STATE
		  else if(gp.gameState == gp.characterState){
            drawPlayerLife();
            drawCharacterScreen();
        }
	}

	public void drawTitleScreen() {
		if (titleScreenState == 0) {
			// title name
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));
			String text = "Ancient Ruin 2";
			int x = getXForCenterText(text);
			int y = gp.tileSize * 3;

			// shadow
			g2.setColor(new Color(128, 128, 128, 220));
			g2.drawString(text, x + 5, y + 5);

			// main color
			g2.setColor(Color.white);
			g2.drawString(text, x, y);

			// player img
			x = gp.screenWith / 2 - (gp.tileSize * 2) / 2;
			y += gp.tileSize * 2;
			g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

			// Menu
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 38f));
			text = "New Game";
			x = getXForCenterText(text);
			y += gp.tileSize * 3.5;
			g2.drawString(text, x, y);

			if (commandNum == 0) {
				g2.drawString(">", x - gp.tileSize, y);
			}

			text = "Load Game";
			x = getXForCenterText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			if (commandNum == 1) {
				g2.drawString(">", x - gp.tileSize, y);
			}

			text = "Quit";
			x = getXForCenterText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			if (commandNum == 2) {
				g2.drawString(">", x - gp.tileSize, y);
			}

		} else if (titleScreenState == 1) {
			// class selection screen
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));

			String text = "Add history later";
			int x = getXForCenterText(text);
			int y = gp.tileSize * 3;
			g2.drawString(text, x, y);

			text = "Start";
			x = getXForCenterText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x - gp.tileSize, y);

			}

			text = "Back";
			x = getXForCenterText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x - gp.tileSize, y);
			}

		}

	}

	public void drawHistoryScreen() {
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
		int x = gp.tileSize * 2;
		int y = gp.tileSize / 2;
		int width = gp.screenWith - (gp.tileSize * 4);
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

	public void drawCharacterScreen(){
		 //Craete a frame
		 final int  frameX = gp.tileSize*2;
		 final int  frameY = gp.tileSize;
		 final int  frameWidth = gp.tileSize*5;
		 final int  frameHeight = gp.tileSize*10;
		 drawSubWindow(frameX, frameY, frameWidth, frameHeight);
 
		 //TEXT
		 g2.setColor(Color.white);
		 g2.setFont(g2.getFont().deriveFont(32f));
 
		 int textX = frameX + 20;
		 int textY = frameY + gp.tileSize; 
		 final int lineHeight = 35;
 
		 //NAME
		 g2.drawString("Level: ",textX, textY);
		 textY += lineHeight;
		 g2.drawString("Life: ",textX, textY);
		 textY += lineHeight;
		 g2.drawString("Strength: ",textX, textY);
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
		 textY += lineHeight + 20;
		 g2.drawString("Weapon: ", textX, textY);
		 textY += lineHeight + 15;
		 g2.drawString("Shield: ", textX, textY);
		 textY += lineHeight;
 
		 //VALUES
		 int tailX = (frameX + frameWidth) - 30;
		 //reset textY
		 textY = frameY + gp.tileSize;
		 String value;
 
		 value = String.valueOf(gp.player.level);
		 textX = getXForAlignToRightText(value, tailX);
		 g2.drawString(value, textX, textY);
		 textY += lineHeight;
 
		 value = String.valueOf(gp.player.life +" / " + gp.player.maxLife);
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
 
		 g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 14, null);
		 textY += gp.tileSize;
 
		 g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 14, null);
		
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
		int x = gp.screenWith / 2 - length / 2;
		return x;
	}

	public int getXForCenteredText(String text) {
		int x;
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		x = gp.screenWith / 2 - length / 2;
		return x;
	}
	
	public int getXForAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length; 
        return x;
    }
}

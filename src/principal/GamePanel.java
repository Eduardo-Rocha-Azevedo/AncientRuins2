package principal;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManeger;
import tile_interactive.InteractiveTile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

	// screen settings
	final int originalTilesSize = 16; // 16px X 16px
	final int scale = 3;

	public final int tileSize = originalTilesSize * scale;
	public final int maxScreenCols = 20;
	public final int maxScreenRows = 12;
	public final int screenWidth = tileSize * maxScreenCols; // 960px
	public final int screenHeight = tileSize * maxScreenRows; // 576px

	// World settings
	public final int maxWorldCol = 250;
	public final int maxWorldRow = 250;
	// Full Screen
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	// FPS
	int FPS = 60;

	// SYSTEM
	TileManeger tileM = new TileManeger(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread;
	public EventHandler eHandler = new EventHandler(this);

	// ENTITY AND OBJECTS
	public Player player = new Player(this, keyH);
	public Entity npc[] = new Entity[10];
	public Entity obj[] = new Entity[10];
	public Entity monster[] = new Entity[20];
	public ArrayList<Entity> projectileList = new ArrayList<>();
	public InteractiveTile iTile[] = new InteractiveTile[80];
	public ArrayList<Entity> particleList = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();

	// Game State
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;

	// constructor
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(new Color(0, 0, 0));
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame() {
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
		// playMusic(0);

		gameState = titleState;
		tempScreen = new BufferedImage(screenWidth2, screenHeight2, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) tempScreen.getGraphics();
		setFullScreen();
	}

	public void setFullScreen() {
		// GET LOCAL SCREEN DEVICE
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);

		// GET SCREEN SIZE
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();
	}

	public void startGameThread() {
		System.out.println("Iniciando o thread do jogo...");
		if (gameThread == null) {
			gameThread = new Thread(this);
			gameThread.start();
		}
	}

	@Override
	public void run() {
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				drawToTempScreen();
				drawToScreen();
				delta--;
				drawCount++;
			}

			if (timer >= 1000000000) {
				// System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}

	public void update() {
		if (gameState == playState) {
			// player
			player.update();

			// NPC
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					npc[i].update();
				}
			}
			// MONSTER
			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					if (monster[i].alive == true && monster[i].dyain == false) {
						monster[i].update();
					}
					if (monster[i].alive == false) {
						monster[i].checkDrop();
						monster[i] = null;
					}
				}
			}

			// PROJECTILE
			for (int i = 0; i < projectileList.size(); i++) {
				if (projectileList.get(i) != null) {
					if (projectileList.get(i).alive == true) {
						projectileList.get(i).update();
					}

					if (projectileList.get(i).alive == false) {
						projectileList.remove(i);
					}
				}
			}

			// PARTICLE
			for (int i = 0; i < particleList.size(); i++) {
				if (particleList.get(i) != null) {
					if (particleList.get(i).alive == true) {
						particleList.get(i).update();
					}

					if (particleList.get(i).alive == false) {
						particleList.remove(i);
					}
				}
			}

			for (int i = 0; i < iTile.length; i++) {
				if (iTile[i] != null) {
					iTile[i].update();
				}
			}
		}
		if (gameState == pauseState) {
			// nothing yet
		}
	}

	public void drawToScreen() {
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
	}

	public void drawToTempScreen() {

		// TITLE MENU
		if (gameState == titleState) {
			ui.draw(g2);
		}
		// Others
		else {
			// TILE
			tileM.draw(g2);
			// Interactive Tile
			for (int i = 0; i < iTile.length; i++) {
				if (iTile[i] != null) {
					iTile[i].draw(g2);
				}
			}

			// ADD ENTITIES TO THE LIST
			entityList.add(player);
			// NPC
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					entityList.add(npc[i]);
				}
			}
			// OBJECT
			for (int i = 0; i < obj.length; i++) {
				if (obj[i] != null) {
					entityList.add(obj[i]);
				}
			}

			// MONSTER
			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					entityList.add(monster[i]);
				}
			}

			// projectile
			for (int i = 0; i < projectileList.size(); i++) {
				if (projectileList.get(i) != null) {
					entityList.add(projectileList.get(i));
				}
			}
			// particle
			for (int i = 0; i < particleList.size(); i++) {
				if (particleList.get(i) != null) {
					entityList.add(particleList.get(i));
				}
			}

			// SORT
			Collections.sort(entityList, new Comparator<Entity>() {
				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
			});

			// DRAW ENTITIES
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}

			// EMPTY ENTITY LIST
			entityList.clear();

			// UI
			ui.draw(g2);

			// DEBUG
			if (keyH.showDebugText == true) {
				g2.setFont(new Font("Arial", Font.PLAIN, 20));
				g2.setColor(Color.WHITE);
				int x = 10;
				int y = 400;

				int lineHeight = 20;

				g2.drawString("worldX: " + player.worldX, x, y);
				y += lineHeight;
				g2.drawString("worldY: " + player.worldY, x, y);
				y += lineHeight;
				g2.drawString("col: " + (player.worldX + player.solidArea.x) / tileSize, x, y);
				y += lineHeight;
				g2.drawString("row: " + (player.worldY + player.solidArea.y) / tileSize, x, y);
			}
		}

	}

	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopMusic() {
		music.stop();
	}

	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}

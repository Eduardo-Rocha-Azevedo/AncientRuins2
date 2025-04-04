package principal;

import javax.swing.JPanel;

import ai.PathFinder;
import entity.Entity;
import entity.GravityExplosion;
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
	public final int maxMap = 10; // if you want to add more maps, change this value
	public int currentMap = 0;
	// Full Screen
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean fullScreenOn = false;
	// FPS
	int FPS = 60;

	// SYSTEM
	public TileManeger tileM = new TileManeger(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread;
	public EventHandler eHandler = new EventHandler(this);
	Config config = new Config(this);
	public PathFinder pFinder = new PathFinder(this);

	// ENTITY AND OBJECTS
	public Player player = new Player(this, keyH);
	public Entity npc[][] = new Entity[maxMap][10];
	public Entity obj[][] = new Entity[maxMap][80];
	public Entity monster[][] = new Entity[maxMap][20];
	public InteractiveTile iTile[][] = new InteractiveTile[maxMap][80];
	public Entity projectile[][] = new Entity[maxMap][80];
	public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	public ArrayList<GravityExplosion> explosions = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();

	// Game State
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;
	public final int optionState = 5;
	public final int gameOverState = 6;
	public final int transitionState = 7;
	public final int tradeState = 8;

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
		if(fullScreenOn == true){
			setFullScreen();
		}
	}
	public void retry(){
		player.setDefultPosition();
		player.restoreLifeAndCosmo();
		aSetter.setNPC();
		aSetter.setMonster();
		
	}
	public void restart(){
		player.setDefultValues();
		player.setDefultPosition();
		player.setItems();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
		stopMusic();
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


	public void update() {
		if (gameState == playState) {
			// player
			player.update();

			// NPC
			for (int i = 0; i < npc[1].length; i++) {
				if (npc[currentMap][i] != null) {
					npc[currentMap][i].update();
				}
			}
			// MONSTER
			for (int i = 0; i < monster[1].length; i++) {
				if (monster[currentMap][i] != null) {
					if (monster[currentMap][i].alive == true && monster[currentMap][i].dyain == false) {
						monster[currentMap][i].update();
					}
					if (monster[currentMap][i].alive == false) {
						monster[currentMap][i].checkDrop();
						monster[currentMap][i] = null;
					}
				}
			}

			// PROJECTILE
			for (int i = 0; i < projectile[1].length; i++) {
				if (projectile[currentMap][i] != null) {
					if (projectile[currentMap][i].alive == true) {
						projectile[currentMap][i].update();
					}

					if (projectile[currentMap][i].alive == false) {
						projectile[currentMap][i] = null;
					}
				}
			}
			// Atualiza as explosões
			for (int i = 0; i < explosions.size(); i++) {
				explosions.get(i).update();
				if (!explosions.get(i).isActive()) {
					explosions.remove(i); // Remove explosões concluídas
					i--;
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

			for (int i = 0; i < iTile[1].length; i++) {
				if (iTile[currentMap][i] != null) {
					iTile[currentMap][i].update();
				}
			}
		}
		if (gameState == pauseState) {
			// nothing yet
		}
	}
	public void drawToTempScreen(){
		//debug
		long drawStart = 0;
	  
		if(keyH.showDebugText == true){
			 drawStart = System.nanoTime(); 
		}

		//TITLE SCREEN
		if(gameState == titleState){
			ui.draw(g2);
		}   

		//OTHERS
		else{
			//TILE
			tileM.draw(g2);
			
			//INTERACTIVE TILE
			for(int i = 0; i < iTile[1].length; i++){
				if(iTile[currentMap][i] != null){
					iTile[currentMap][i].draw(g2);
				}
			}   

			// Add entities to the list
			entityList.add(player);

			//npc
			for(int i = 0; i < npc[1].length; i++){
				if(npc[currentMap][i] != null){
					entityList.add(npc[currentMap][i]);
				}
			}
			//obj
			for(int i = 0; i < obj[1].length; i++){
				if(obj[currentMap][i] != null){
					entityList.add(obj[currentMap][i]);
				}
			}
			//monster
			for(int i = 0; i < monster[1].length; i++){
				if(monster[currentMap][i] != null){
					entityList.add(monster[currentMap][i]);
				}
			}

			//projectile
			for(int i = 0; i < projectile[1].length; i++){
				if(projectile[currentMap][i] != null){
					entityList.add(projectile[currentMap][i]);
				}
			}

			//particle
			for(int i = 0; i < particleList.size(); i++){
				if(particleList.get(i) != null){
					entityList.add(particleList.get(i));
				}
			}
			 // Desenha todas as explosões
			 for (GravityExplosion explosion : explosions) {
				explosion.draw(g2);
			}
		

			//sort
			Collections.sort(entityList, Comparator.comparingInt((Entity e) -> e.worldY)
                                       .thenComparingInt(e -> e.worldX));


			//draw entities
			for(int i = 0; i < entityList.size(); i++){
				entityList.get(i).draw(g2);
			}

			//empty list
			entityList.clear();
			//UI
			ui.draw(g2);
		}
	   

		//debug
		if(keyH.showDebugText == true){
			long drawEnd = System.nanoTime();
			long passed  = drawEnd - drawStart;

			g2.setFont(new Font("arial", Font.PLAIN, 15));
			g2.setColor(Color.WHITE);

			int x = 10;
			int y = 400;
			int lineHeight = 20;

			g2.drawString("WorldX: "+ player.worldX, x, y); y += lineHeight;
			g2.drawString("WorldY: " + player.worldY , x, y); y += lineHeight;
			g2.drawString("Col: "+ (player.worldX + player.solidArea.x)/tileSize, x, y); y += lineHeight;
			g2.drawString("Row: " + (player.worldY + player.solidArea.y)/tileSize, x, y); y += lineHeight;
			g2.drawString("Draw Time: " + passed, x, y);
		   
	   }
   }

	public void drawToScreen() {
		Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);    
        g.dispose();
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
}
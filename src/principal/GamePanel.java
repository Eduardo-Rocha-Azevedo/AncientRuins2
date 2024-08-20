package principal;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManeger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {

	// screen settings
	final int originalTilesSize = 16; // 16px X 16px
	final int scale = 3;

	public final int tileSize = originalTilesSize * scale;
	public final int maxScreenCols = 20;
	public final int maxScreenRows = 12;
	public final int screenWith = tileSize * maxScreenCols; // 768px
	public final int screenHeight = tileSize * maxScreenRows; // 576px

	// World settings
	public final int maxWorldCol = 250;
	public final int maxWorldRow = 250;

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
	public EventHandler eventH = new EventHandler(this);

	// ENTITY AND OBJECTS
	public Player player = new Player(this, keyH);
	public Entity npc[] = new Entity[10];
	public Entity obj[] = new Entity[10];

	// Game State
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;

	// constructor
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWith, screenHeight));
		this.setBackground(new Color(0, 0, 0));
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame() {
		aSetter.setObject();
		aSetter.setNPC();
		// playMusic(0);
		gameState = titleState;
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
				repaint();
				//System.out.println("atualizando e renderizando");
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
		}
		if (gameState == pauseState) {
			// nothing yet
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//System.out.println("renderizando");
		Graphics2D g2 = (Graphics2D) g;

		// TITLE MENU
		if (gameState == titleState) {
			ui.draw(g2);
		}
		// Others
		else {
			// TILE
			tileM.draw(g2);
			// PLAYER
			player.draw(g2);
			// OBJECTS
			for (int i = 0; i < obj.length; i++) {
				if (obj[i] != null) {
					obj[i].draw(g2);
				}
			}
			// NPC
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					npc[i].draw(g2);
				}
			}
			// UI
			ui.draw(g2);

		}
		g2.dispose();

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

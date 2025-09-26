package tile_interactive;

import java.awt.Color;

import entity.Particle;
import principal.GamePanel;

public class IT_BigTree extends InteractiveTile {

    public IT_BigTree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        destructible = true;
        down1 = setup("/tiles_interatives/big_tree", gp.tileSize * 5, gp.tileSize * 8);
        int treeWidth = gp.tileSize * 5;
        int treeHeight = gp.tileSize * 8;
        int trunkWidth = 16;
        int trunkHeight = 40;

        solidArea.x = (treeWidth / 2) - (trunkWidth / 2); // centraliza horizontalmente
        solidArea.y = treeHeight - trunkHeight - 75;
        solidArea.width = trunkWidth;
        solidArea.height = trunkHeight;

    }

    public void generateLeafParticles() {
        int numLeaves = 1 + (int) (Math.random() * 2); // 1 ou 2 folhas por frame

        for (int i = 0; i < numLeaves; i++) {
            // gerar próximo do tronco
            int offsetX = solidArea.x + (int) (Math.random() * solidArea.width);
            int offsetY = solidArea.y; // topo do tronco

            int xd = (int) (Math.random() * 3 - 1); // -1, 0, 1
            int yd = (int) (Math.random() * 2 + 1); // 1 ou 2
            Color leafColor = new Color(50 + (int) (Math.random() * 100), 120 + (int) (Math.random() * 60), 0);

            Particle leaf = new Particle(gp, this, leafColor, 6, 1, 60 + (int) (Math.random() * 30), xd, yd);

            // ajustar posição inicial da folha
            leaf.worldX = worldX + offsetX;
            leaf.worldY = worldY + offsetY;

            gp.particleList.add(leaf);
        }
    }

    public void update() {
        if (Math.random() < 0.003) { // 3% de chance por frame
            generateLeafParticles();
        }
    }
}

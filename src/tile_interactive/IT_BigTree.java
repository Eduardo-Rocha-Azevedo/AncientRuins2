package tile_interactive;

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
}

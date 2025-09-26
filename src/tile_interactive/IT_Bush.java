package tile_interactive;

import principal.GamePanel;

public class IT_Bush extends InteractiveTile {

    public IT_Bush(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        destructible = true;
        down1 = setup("/tiles_interatives/arbusto", gp.tileSize * 2, gp.tileSize * 2);
        int bushWidth = gp.tileSize * 2;
        int bushHeight = gp.tileSize * 2;
        int trunkWidth = 16;
        int trunkHeight = 40;

        solidArea.x = (bushWidth / 2) - (trunkWidth / 2); // centraliza horizontalmente
        solidArea.y = bushHeight - trunkHeight - 75;
        solidArea.width = trunkWidth;
        solidArea.height = trunkHeight;

    }
}

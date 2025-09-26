package tile_interactive;

import principal.GamePanel;

public class IT_Blueberry extends InteractiveTile {

    public IT_Blueberry(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        destructible = true;
        down1 = setup("/tiles_interatives/blue_berry", gp.tileSize * 2, gp.tileSize * 3);
        int bushWidth = gp.tileSize * 2;
        int bushHeight = gp.tileSize * 2;
        int trunkWidth = 16;
        int trunkHeight = 40;
    }
    
}

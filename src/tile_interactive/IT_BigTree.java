package tile_interactive;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import principal.GamePanel;

public class IT_BigTree extends InteractiveTile {

    // Hitbox separada para a copa
    public Rectangle crownArea;

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
       

        // hitbox do tronco
        solidArea.x = (treeWidth / 2) - (trunkWidth / 2);
        solidArea.y = treeHeight - trunkHeight - 75;
        solidArea.width = trunkWidth;
        solidArea.height = trunkHeight;

        // hitbox da copa (exemplo: parte superior da árvore)
        crownArea = new Rectangle(0, 0, treeWidth, treeHeight - trunkHeight); // toda a copa
    }
    
    @Override
    public void draw(Graphics2D g2) {
        // Desenha a árvore inteira atrás do player
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        g2.drawImage(down1, screenX, screenY, null);

      
    }
}

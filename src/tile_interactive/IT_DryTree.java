package tile_interactive;

import entity.Entity;

import principal.GamePanel;

public class IT_DryTree  extends InteractiveTile{
    public IT_DryTree(GamePanel gp, int col, int row){
        super(gp,col,row);
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        destructible = true;
        down1 = setup("/tiles_interatives/drytree",gp.tileSize,gp.tileSize);
        life = 3;
    }
    public boolean isCorrectItem(Entity entity){
        boolean isCorrectItem = false;
        if(entity.currentWeapon.type == type_axe){
            isCorrectItem = true;
        }
        return isCorrectItem;
    }
    public void playSE(){
        gp.playSE(11);
    }
       
    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
        return tile;
    }

}
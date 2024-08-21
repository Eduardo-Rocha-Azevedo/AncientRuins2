package principal;

import entity.NPC_OldMan;
import objects.OBJ_Chest;

public class AssetSetter {
	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setObject() {
		int i = 0;
		/*gp.obj[i] = new OBJ_Chest(gp);
		gp.obj[i].worldX = gp.tileSize * 23;
		gp.obj[i].worldY = gp.tileSize * 23;i++;
		
		gp.obj[i] = new OBJ_Chest(gp);
		gp.obj[i].worldX = gp.tileSize * 23;
		gp.obj[i].worldY = gp.tileSize * 25;
		i++;*/
	}

	public void setNPC() {
		int i = 0;
		gp.npc[i] = new NPC_OldMan(gp);
		gp.npc[i].worldX = gp.tileSize * 21;
		gp.npc[i].worldY = gp.tileSize * 21;
	}
}

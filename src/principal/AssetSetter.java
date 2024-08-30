package principal;

import entity.NPC_Farmer;
import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import objects.OBJ_Axe;
import objects.OBJ_Chest;
import objects.OBJ_Coin_Broze;
import objects.OBJ_Key;
import objects.OBJ_Potion_Red;
import objects.OBJ_Shield_Blue;

public class AssetSetter {
	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setObject() {
		int i = 0;
		gp.obj[i] = new OBJ_Coin_Broze(gp);
		gp.obj[i].worldX = gp.tileSize * 25;
		gp.obj[i].worldY = gp.tileSize * 23;
		i++;

		gp.obj[i] = new OBJ_Axe(gp);
		gp.obj[i].worldX = gp.tileSize * 31;
		gp.obj[i].worldY = gp.tileSize * 21;
		i++;

		gp.obj[i] = new OBJ_Shield_Blue(gp);
		gp.obj[i].worldX = gp.tileSize * 37;
		gp.obj[i].worldY = gp.tileSize * 21;
		i++;

		gp.obj[i] = new OBJ_Potion_Red(gp);
		gp.obj[i].worldX = gp.tileSize * 21;
		gp.obj[i].worldY = gp.tileSize * 23;
		i++;
	}

	public void setNPC() {
		int i = 0;
		gp.npc[i] = new NPC_OldMan(gp);
		gp.npc[i].worldX = gp.tileSize * 21;
		gp.npc[i].worldY = gp.tileSize * 21;
		i++;

		gp.npc[i] = new NPC_Farmer(gp);
		gp.npc[i].worldX = gp.tileSize * 14;
		gp.npc[i].worldY = gp.tileSize * 63;
		
	}

	public void setMonster() {
		    //Slime ===================================
			int i = 0;
			gp.monster[i] = new MON_GreenSlime(gp);
			gp.monster[i].worldX = gp.tileSize*23;
			gp.monster[i].worldY = gp.tileSize*23;
			i++;
	
			gp.monster[i] = new MON_GreenSlime(gp);
			gp.monster[i].worldX = gp.tileSize*23;
			gp.monster[i].worldY = gp.tileSize*38;
			i++;
	
			gp.monster[i] = new MON_GreenSlime(gp);
			gp.monster[i].worldX = gp.tileSize*23;
			gp.monster[i].worldY = gp.tileSize*35;
			i++;
	
			gp.monster[i] = new MON_GreenSlime(gp);
			gp.monster[i].worldX = gp.tileSize*20;
			gp.monster[i].worldY = gp.tileSize*36;
			i++;
	
			gp.monster[i] = new MON_GreenSlime(gp);
			gp.monster[i].worldX = gp.tileSize*22;
			gp.monster[i].worldY = gp.tileSize*36;
			i++;
	
			gp.monster[i] = new MON_GreenSlime(gp);
			gp.monster[i].worldX = gp.tileSize*23;
			gp.monster[i].worldY = gp.tileSize*36;
			i++;
		}
	
}

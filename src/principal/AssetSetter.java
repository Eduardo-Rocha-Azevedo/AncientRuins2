package principal;

import entity.NPC_Farmer;
import entity.NPC_Merchant;
import entity.NPC_Old_man;
import monster.MON_GreenSlime;
import objects.OBJ_Axe;
import objects.OBJ_Chest;
import objects.OBJ_Coin_Broze;
import objects.OBJ_Key;
import objects.OBJ_Potion_Red;
import objects.OBJ_Shield_Blue;
import tile_interactive.IT_BigTree;
import tile_interactive.IT_Blueberry;
import tile_interactive.IT_Bush;
import tile_interactive.IT_DryTree;

public class AssetSetter {
	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setObject() {
		int i = 0;
		int mapNum = 0;
		gp.obj[mapNum][i] = new OBJ_Coin_Broze(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 25;
		gp.obj[mapNum][i].worldY = gp.tileSize * 23;
		i++;

		gp.obj[mapNum][i] = new OBJ_Axe(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 31;
		gp.obj[mapNum][i].worldY = gp.tileSize * 21;
		i++;

		gp.obj[mapNum][i] = new OBJ_Shield_Blue(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 37;
		gp.obj[mapNum][i].worldY = gp.tileSize * 21;
		i++;

		gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 21;
		gp.obj[mapNum][i].worldY = gp.tileSize * 23;
		i++;
	}

	public void setNPC() {
		int i = 0;
		int mapNum = 0;
		gp.npc[mapNum][i] = new NPC_Old_man(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 21;
		gp.npc[mapNum][i].worldY = gp.tileSize * 21;
		i++;

		gp.npc[mapNum][i] = new NPC_Farmer(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 14;
		gp.npc[mapNum][i].worldY = gp.tileSize * 63;
		
		// Map 1 = merchant
		mapNum = 1;
		i = 0;
		gp.npc[mapNum][i] = new NPC_Merchant(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 12;
		gp.npc[mapNum][i].worldY = gp.tileSize * 7;
		i++;
	}

	public void setMonster() {
		    //Slime ===================================
			int i = 0;
			int mapNum = 0;
			
			gp.monster[mapNum][i] = new MON_GreenSlime(gp);
			gp.monster[mapNum][i].worldX = gp.tileSize*23;
			gp.monster[mapNum][i].worldY = gp.tileSize*38;
			i++;
	
			gp.monster[mapNum][i] = new MON_GreenSlime(gp);
			gp.monster[mapNum][i].worldX = gp.tileSize*23;
			gp.monster[mapNum][i].worldY = gp.tileSize*35;
			i++;
	
			gp.monster[mapNum][i] = new MON_GreenSlime(gp);
			gp.monster[mapNum][i].worldX = gp.tileSize*20;
			gp.monster[mapNum][i].worldY = gp.tileSize*36;
			i++;
	
			gp.monster[mapNum][i] = new MON_GreenSlime(gp);
			gp.monster[mapNum][i].worldX = gp.tileSize*22;
			gp.monster[mapNum][i].worldY = gp.tileSize*36;
			i++;
	
			gp.monster[mapNum][i] = new MON_GreenSlime(gp);
			gp.monster[mapNum][i].worldX = gp.tileSize*23;
			gp.monster[mapNum][i].worldY = gp.tileSize*36;
			i++;
		}
	public void setInteractiveTile(){
		int i = 0;
		int mapNum = 0;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 27,12);i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 28,12);i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 29,12);i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 30,12);i++;
		gp.iTile[mapNum][i] = new IT_BigTree(gp, 25,21);i++;
		gp.iTile[mapNum][i] = new IT_BigTree(gp, 10,27);i++;
		gp.iTile[mapNum][i] = new IT_BigTree(gp, 25,28);i++;
		gp.iTile[mapNum][i] = new IT_BigTree(gp, 27,27);i++;
		gp.iTile[mapNum][i] = new IT_BigTree(gp, 31,27);i++;
		gp.iTile[mapNum][i] = new IT_BigTree(gp, 17,20);i++;
		gp.iTile[mapNum][i] = new IT_Bush(gp, 20,26);i++;
		gp.iTile[mapNum][i] = new IT_Bush(gp, 32,24);i++;
		gp.iTile[mapNum][i] = new IT_Bush(gp, 30,40);i++;
		gp.iTile[mapNum][i] = new IT_Blueberry(gp, 34,42);i++;
	
	}
}

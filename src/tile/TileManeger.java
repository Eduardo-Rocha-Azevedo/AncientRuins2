package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import principal.GamePanel;
import principal.UtiliyTool;

public class TileManeger {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][];
	boolean drawPath = true;

	public TileManeger(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[200];
		mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap("/maps/mapa.txt",0);
		loadMap("/maps/interior01.txt",1);
		loadMap("/maps/dangeon1.txt",2);
		loadMap("/maps/dangeon2.txt",3);
	}

	public void getTileImage() {
		setup(0, "000", true);
		setup(1, "001", false);
		setup(2, "002", false);
		setup(3, "003", false);
		setup(4, "004", false);
		setup(5, "005", false);
		setup(6, "006", false);
		setup(7, "007", false);
		setup(8, "008", false);
		setup(9, "009", false);
		setup(10, "010", false);
		setup(11, "011", false);
		setup(12, "012", false);
		setup(13, "013", false);
		setup(14, "014", false);
		setup(15, "015", false);
		setup(16, "016", false);
		setup(17, "017", false);
		setup(18, "018", false);
		setup(19, "019", false);
		setup(20, "020", false);
		setup(21, "021", false);
		setup(22, "022", false);
		setup(23, "023", false);
		setup(24, "024", false);
		setup(25, "025", false);
		setup(26, "026", false);
		setup(27, "027", false);
		setup(28, "028", false);
		setup(29, "029", false);
		setup(30, "030", false);
		setup(31, "031", false);
		setup(32, "032", false);
		setup(33, "033", false);
		setup(34, "034", false);
		setup(35, "035", false);
		setup(36, "036", false);
		setup(37, "037", false);
		setup(38, "038", false);
		setup(39, "039", false);
		setup(40, "040", true);
		setup(41, "041", true);
		setup(42, "042", true);
		setup(43, "043", true);
		setup(44, "044", true);
		setup(45, "045", true);
		setup(46, "046", false);
		setup(47, "047", true);
		setup(48, "048", true);
		setup(49, "049", true);
		setup(50, "050", true);
		setup(51, "051", true);
		setup(52, "052", true);
		setup(53, "053", true);
		setup(54, "054", false);
		setup(55, "055", true);
		setup(56, "056", true);
		setup(57, "057", true);
		setup(58, "058", true);
		setup(59, "059", true);
		setup(60, "060", true);
		setup(61, "061", true);
		setup(62, "062", true);
		setup(63, "063", true);
		setup(64, "064", false);
		setup(65, "065", false);
		setup(66, "066", false);
		setup(67, "067", false);
		setup(68, "068", false);
		setup(69, "069", false);
		setup(70, "070", false);
		setup(71, "071", false);
		setup(72, "072", false);
		setup(73, "073", false);
		//setup(74, "074", true);
		
	}

	public void setup(int index, String imageName, boolean collision) {
		UtiliyTool uTool = new UtiliyTool();
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadMap(String mapName, int map) {
		try {
			InputStream is = getClass().getResourceAsStream(mapName);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;

			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();

				while (col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");

					int num = Integer.parseInt(numbers[col]);

					mapTileNum[map][col][row] = num;
					col++;
				}
				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();

		} catch (Exception e) {

		}
	}

	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;

		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

			// Verificação nula adicionada
			if (tile[tileNum] != null && tile[tileNum].image != null) {
				int worldX = worldCol * gp.tileSize;
				int worldY = worldRow * gp.tileSize;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;

				if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
						&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
						&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
						&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
					g2.drawImage(tile[tileNum].image, screenX, screenY, null);
				}
			}
			worldCol++;

			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		if(drawPath){
			g2.setColor(new Color(255,0,0,70));
			for(int i = 0; i < gp.pFinder.pathList.size(); i++){
				int worldX = gp.pFinder.pathList.get(i).col *gp.tileSize;
				int worldY = gp.pFinder.pathList.get(i).row *gp.tileSize;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
	
				g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
			}
		}
	}

}

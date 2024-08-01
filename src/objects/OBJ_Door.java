package objects;

import java.io.IOException;
import javax.imageio.ImageIO;
import principal.GamePanel;

public class OBJ_Door extends SuperObject {

    GamePanel gp;

    public OBJ_Door(GamePanel gp){
        name = "Door";
        this.gp = gp;
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch(IOException e){
            System.out.println("Erro ao carregar imagem da porta");
        }

        collision = true;
        
    }
}

package principal;

import java.awt.Rectangle;

public class EventHandler {
    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefultX, eventRectDefultY;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefultX = eventRect.x;
        eventRectDefultY = eventRect.y;
    }

    public void checkEvent(){
        if(gp.eventH.hit(27, 16, "right") == true){damagePit(gp.dialogueState);}
        if(gp.eventH.hit(23, 12, "up") == true){healingPool(gp.dialogueState);} 
        if(gp.eventH.hit(25, 27, "any") == true){teleport(47,144);}
        if (gp.eventH.hit(48, 149, "any") == true) {teleport(23, 27);}

    }
    public void healingPool(int gameState){
        if(gp.keyH.enterPressed == true ){
            if(gp.player.life < gp.player.maxLife){
                gp.gameState = gameState;
                gp.ui.currentDialog = "You found a healing pool!";
                gp.player.life = gp.player.maxLife;
            }else{
                gp.gameState = gameState;
                gp.ui.currentDialog = "You are already at full health!";
            }

        }

    }
    public void damagePit(int gameState){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialog = "You fell in a pit!";
        gp.player.life--;
    } 

    public boolean hit(int eventCol, int eventRow, String reqDirection){
        boolean hit = false;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect.x = (eventCol * gp.tileSize)+ eventRect.x;
        eventRect.y = (eventRow * gp.tileSize)+ eventRect.y;

        // CHECK IF PLAYER HIT THE EVENT
        if(gp.player.solidArea.intersects(eventRect)){
            if(gp.player.direction.equals(reqDirection) || reqDirection.equals("any")){
                hit = true;
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefultX;
        gp.player.solidArea.y = gp.player.solidAreaDefultY;
        eventRect.x = eventRectDefultX;
        eventRect.y = eventRectDefultY;
        return hit;
    }

    public void teleport(int col, int row){
        gp.player.worldX = col * gp.tileSize;
        gp.player.worldY = row * gp.tileSize;
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialog = "You have been teleported!";
    }
}
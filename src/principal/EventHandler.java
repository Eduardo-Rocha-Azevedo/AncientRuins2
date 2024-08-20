package principal;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefultY = eventRect[col][row].y;  
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }  
        }
        
    }

    public void checkEvent(){
        // CHECK DISTANCE TO EVENT
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if(distance > gp.tileSize){
            canTouchEvent = true;
        }
        else if(canTouchEvent == true){
            if(gp.eventH.hit(27, 16, "right") == true){damagePit(27,16,gp.dialogueState);}
            if(gp.eventH.hit(23, 12, "up") == true){healingPool(23,12,gp.dialogueState);} 
        }
    }
    public void healingPool(int col, int row,int gameState){
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
    public void damagePit(int col, int row, int gameState){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialog = "You fell in a pit!";
        gp.player.life--;
        //eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    } 

    public boolean hit(int col, int row, String reqDirection){
        boolean hit = false;
        gp.player.solidArea.x = gp.player.worldX  + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY  + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        // CHECK IF PLAYER HIT THE EVENT
        if(gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false){
            if(gp.player.direction.equals(reqDirection) || reqDirection.equals("any")){
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefultX;
        gp.player.solidArea.y = gp.player.solidAreaDefultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefultY;
        return hit;
    }

    public void teleport(int col, int row){
        gp.player.worldX = col * gp.tileSize;
        gp.player.worldY = row * gp.tileSize;
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialog = "You have been teleported!";
    }
}
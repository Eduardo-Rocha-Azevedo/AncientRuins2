package principal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
  
    BufferedImage keyImage;
    public boolean gameFinished = false;
    Font maruMonica;
    //mensage info
    public boolean messageOn = false;
    public String message = "";
    public String currentDialog = "";
    int messageCounter = 0;
    public int commandNum = 0;
    int titleScreenState = 0;
    
    public UI(GamePanel gp){
        this.gp = gp;

       
        InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
        try{
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);

        }catch(FontFormatException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        
       
    }

    public void showMassage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        
        g2.setFont(maruMonica);
        g2.setColor(Color.WHITE);
        
        // TITLE SCREEN
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        // PLAY STATE
        if (gp.gameState == gp.playState) {
            // Desenhe os elementos do estado de jogo aqui
        }
        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
            drawDialogScreen();
        }
    }
    public void drawTitleScreen() {
        if(titleScreenState == 0){
            //title name
           g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));
           String text = "Ancient Ruin 2";
           int x = getXForCenterText(text);
           int y = gp.tileSize*3;

           //shadow
           g2.setColor(new Color(128,128,128,220));
           g2.drawString(text, x+5, y+5);

           //main color
           g2.setColor(Color.white);
           g2.drawString(text, x, y);
           
           //player img
           x = gp.screenWith/2 - (gp.tileSize*2)/2;
           y += gp.tileSize*2;
           g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

           //Menu
           g2.setFont(g2.getFont().deriveFont(Font.BOLD, 38f));
           text = "New Game";
           x = getXForCenterText(text);
           y += gp.tileSize*3.5;
           g2.drawString(text, x, y);

           if(commandNum == 0){
               g2.drawString(">", x-gp.tileSize, y);
           }

           text = "Load Game";
           x = getXForCenterText(text);
           y += gp.tileSize;
           g2.drawString(text, x, y);

           if(commandNum == 1){
               g2.drawString(">", x-gp.tileSize, y);
           }

           text = "Quit";
           x = getXForCenterText(text);
           y += gp.tileSize;
           g2.drawString(text, x, y);

           if(commandNum == 2){
               g2.drawString(">", x-gp.tileSize, y);
           }

       }
       else if (titleScreenState == 1){
           // class selection screen
           g2.setColor(Color.white);
           g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));

           String text = "Add history later";
           int x = getXForCenterText(text);
           int y = gp.tileSize*3;
           g2.drawString(text, x, y);
          


           text = "Start";
           x = getXForCenterText(text);
           y += gp.tileSize;
           g2.drawString(text, x, y);
           if (commandNum == 0) {
               g2.drawString(">", x-gp.tileSize, y);
               
           }

       
           text = "Back";
           x = getXForCenterText(text);
           y += gp.tileSize;
           g2.drawString(text, x, y);
           if(commandNum == 1){
               g2.drawString(">", x-gp.tileSize, y);
           }

       }
      
	}
    public void drawHistoryScreen() {}
    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80f));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }
    public void drawDialogScreen(){
        //WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWith - (gp.tileSize*4);
        int height = gp.tileSize*4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32f));
        x += gp.tileSize;
        y += gp.tileSize;
        
        for(String line:currentDialog.split("\n")) {
        	g2.drawString(line, x, y);
        	y += 40;
        } 
    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        
        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25,25);
    }

    public int  getXForCenterText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWith/2 - length/2; 
        return x;
    }
    public int getXForCenteredText(String text){
        int x;
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWith/2 - length/2;
        return x;
    }
}

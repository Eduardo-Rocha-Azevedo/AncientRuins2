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

    public void draw(Graphics2D g2){
        this.g2 = g2;
        
        g2.setFont(maruMonica);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);

        // PLAY STATE
        if(gp.gameState == gp.playState){

        }
        // PAUSE STATE
        if(gp.gameState == gp.pauseState){
           drawPauseScreen();
        }

        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawDialogScreen();
        }
 
    }
    
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

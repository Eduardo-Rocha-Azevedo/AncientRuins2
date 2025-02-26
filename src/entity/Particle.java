package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import principal.GamePanel;

public class Particle extends Entity{
    Entity generator;
    Color color;
    public int size;
    int xd;
    int yd;
    float opacity = 1.0f; // Para efeito de fade (transparência)

    public Particle(GamePanel gp, Entity generator, Color color, int size, int speed ,int maxLife, int xd, int yd){
        super(gp);
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;

        life = maxLife;

        int offset = (gp.tileSize / 2) - (size / 2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }

    public void update(){
        life--;
        
        // A gravidade faz com que as partículas desçam mais lentamente
        if (life < maxLife / 3) {
            yd++;  // Aumenta a velocidade para criar o efeito de queda
        }
        
        // Movimento baseado na velocidade e direção
        worldX += xd * speed;
        worldY += yd * speed;

        // Fade das partículas
        if (life < maxLife / 2) {
            opacity -= 0.02f;  // Gradualmente desaparece
            if (opacity < 0) opacity = 0;
        }

        // Desativar quando a vida expirar
        if (life == 0) {
            alive = false;
        }
    }

    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Modificar a cor com base na opacidade para o efeito de fade
        Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (opacity * 255));
        g2.setColor(newColor);
        
        // Reduzir o tamanho da partícula conforme ela envelhece
        if (life < maxLife / 2) {
            size = (int) (size * 0.8);  // Diminui o tamanho para dar o efeito de dissipação
        }
        
        g2.fillRect(screenX, screenY, size, size); 
    }
    public void setDirection(int xd, int yd) {
        this.xd = xd;
        this.yd = yd;
    }
    public int getSize() {
        return this.size;
    }    
}

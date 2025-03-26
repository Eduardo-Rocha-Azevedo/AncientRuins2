package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import principal.GamePanel;

public class GravityExplosion {
    private int x, y;
    private int radius = 0;
    private int maxRadius = 40;
    private int alpha = 255; // Transparência do círculo
    private boolean active = true; // Controla se ainda deve ser desenhado
    private GamePanel gp;

    public GravityExplosion(GamePanel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;
    }

    public void update() {
        if (active) {
            radius += 3; // O círculo cresce a cada frame
            alpha -= 10; // O círculo desaparece gradualmente

            if (alpha <= 0 || radius >= maxRadius) {
                active = false; // Desativa o efeito quando a transparência acabar
            }
        }
    }

    public void draw(Graphics2D g2) {
        if (active) {
            g2.setColor(new Color(100, 100, 255, Math.max(alpha, 0)));
            g2.setStroke(new BasicStroke(3));
            g2.drawOval(x - radius / 2, y - radius / 2, radius, radius);
        }
    }

    public boolean isActive() {
        return active;
    }
}

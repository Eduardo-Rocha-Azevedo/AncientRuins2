package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import principal.GamePanel;

public class GravityParticle extends Particle {

    private double angle;
    private double distance;
    private double centerX;
    private double centerY;

    public GravityParticle(GamePanel gp, AreaExplosion explosion, Color color, int size, int maxLife, double angle, double distance) {
        super(gp, explosion, color, size, 0, maxLife, 0, 0);
        this.angle = angle;
        this.distance = distance;
        this.centerX = explosion.getX();
        this.centerY = explosion.getY();
    }

    @Override
    public void update() {
        angle += 0.5 ; // Aumenta o ângulo para criar o movimento circular
        distance = Math.max(distance - 1.4, 0);

        float lifeProgress = (float) life / maxLife;
        alpha = Math.max(0, Math.min(255, (int)(255 * lifeProgress)));


        // Cor dinâmica pulsando
        int r = (int) (150 + 100 * Math.sin(angle * 2));
        int g = (int) (50 + 50 * Math.cos(angle * 3));
        int b = 255;
        color = new Color(clamp(r), clamp(g), clamp(b));

        updatePosition();
        life--;
        if (life <= 0) alive = false;
    }

    private int clamp(int val) {
        return Math.max(0, Math.min(255, val));
    }

    private void updatePosition() {
        double offsetX = Math.cos(angle) * distance;
        double offsetY = Math.sin(angle) * distance;
        worldX = (int) (centerX + offsetX);
        worldY = (int) (centerY + offsetY);
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (screenX + size > 0 && screenX - size < gp.screenWidth &&
            screenY + size > 0 && screenY - size < gp.screenHeight) {

            var originalComposite = g2.getComposite();

            int dynamicSize = (int) (size * ((float) life / maxLife));

            // Rastro (cauda) com menos opacidade e cor mais escura
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
            g2.setColor(color.darker());
            g2.fillOval(screenX - dynamicSize, screenY - dynamicSize, dynamicSize * 2, dynamicSize * 2);

            // Partícula principal
            float alphaValue = Math.max(0f, Math.min(1f, alpha / 255f));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
            
            g2.setColor(color);
            g2.fillOval(screenX - dynamicSize / 2, screenY - dynamicSize / 2, dynamicSize, dynamicSize);

            g2.setComposite(originalComposite);
        }
    }
}

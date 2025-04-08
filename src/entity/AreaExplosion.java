package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.BasicStroke;
import principal.GamePanel;

public class AreaExplosion extends Entity {
    private GamePanel gp;
    private int x, y;
    public int radius = 0;
    private int maxRadius = 150;
    private int alpha = 255;
    private boolean active = true;
    private int lifeSpan = 500;
    private boolean reachedMaxSize = false;
    private int blinkCounter = 0;
    private boolean visible = true;
    private List<Particle> particles = new ArrayList<>();
    private Color explosionColor;
    private boolean isFromGravityBall;
    private boolean particlesCreated = false;
    protected int xd;
    private int yd;

    public AreaExplosion(GamePanel gp, int x, int y, Color color, boolean isFromGravityBall) {
        super(gp);
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.explosionColor = color != null ? color : new Color(5, 0, 0); // Padrão vermelho se nulo
        this.isFromGravityBall = isFromGravityBall;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getXd() {
        return xd;
    }
    public int getYd() {
        return yd;
    }

    public void update() {
        if (active) {
            if (!reachedMaxSize) {
                radius += 3;
                alpha = Math.max(alpha - 1, 0);
                if (radius >= maxRadius) {
                    reachedMaxSize = true;
                    lifeSpan = 300;
                }
            } else {
                lifeSpan--;
                blinkCounter++;
                if (blinkCounter % 18 == 0)
                    visible = !visible;
                if (lifeSpan <= 0)
                    active = false;
            }
            if (reachedMaxSize && !particlesCreated) {
                createParticles();
                particlesCreated = true;
            }
            


            for (Particle p : particles)
                p.update();
        }
    }

    private void createParticles() {
        int numParticles = 15;
        int particleSize = 6;
        int maxLife = 250;
        double startDistance = 165; // distância inicial das partículas do centro da explosão
    
        for (int i = 0; i < numParticles; i++) {
            double angle = Math.toRadians((360.0 / numParticles) * i);
    
            if (isFromGravityBall) {
                particles.add(new GravityParticle(gp, this, new Color(150, 51, 255), particleSize, maxLife, angle, startDistance));
            } else {
                int xd = (int) (Math.cos(angle) * startDistance);
                int yd = (int) (Math.sin(angle) * startDistance);
                particles.add(new Particle(gp, this, new Color(255, 150, 0), particleSize, 1, maxLife, xd, yd));
            }
        }
    }
    

    public void draw(Graphics2D g2) {
        if (active && visible) {
            int screenX = x - gp.player.worldX + gp.player.screenX;
            int screenY = y - gp.player.worldY + gp.player.screenY;

            if (screenX + radius > 0 && screenX - radius < gp.screenWidth &&
                    screenY + radius > 0 && screenY - radius < gp.screenHeight) {

                g2.setStroke(new BasicStroke(3));
                g2.setColor(new Color(explosionColor.getRed(),explosionColor.getGreen(),explosionColor.getBlue(),alpha));
                g2.fillOval(screenX - radius, screenY - radius, radius * 2, radius * 2);


                g2.setColor(explosionColor);
                g2.drawOval(screenX - radius, screenY - radius, radius * 2, radius * 2);
            }

        }
        for (Particle p : particles)
            p.draw(g2);
    }

    public boolean isActive() {
        return active;
    }
}

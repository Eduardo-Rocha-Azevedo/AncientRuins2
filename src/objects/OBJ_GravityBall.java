package objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import entity.Entity; 
import entity.Projectile;
import principal.GamePanel;

public class OBJ_GravityBall extends Projectile {

    public static final String objName = "GravityBall";

    // Variáveis do efeito de círculo
    private boolean effectActive = false;
    private int effectTimer = 30; // Duração do efeito (frames)
    private int alpha = 255; // Transparência do efeito

    public OBJ_GravityBall(GamePanel gp) {
        super(gp);
        name = objName;
        maxLife = 80;
        speed = 7;
        attack = 5;
        useCost = 1;
        alive = false;
        knockBackPower = 1;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectile/gravity_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/projectile/gravity_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/projectile/fireball_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/projectile/fireball_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/projectile/fireball_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/projectile/fireball_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/projectile/fireball_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/projectile/fireball_right_2", gp.tileSize, gp.tileSize);
    }

    public boolean haveResources(Entity user) {
        return user.cosmo >= useCost;
    }

    public void subtractResource(Entity user) {
        // user.cosmo -= useCost;
    }

    public Color getParticleColor() {
        return new Color(153, 0, 24);
    }
    public void update() {
        // Atualiza a lógica do projétil
        super.update();

        // Se o projétil morreu, inicia o efeito de círculo
        if (!alive && !effectActive) {
            effectActive = true;  // Ativa o efeito
        }

        // Controla a duração do efeito de círculo
        if (effectActive) {
            effectTimer--;
            alpha -= 8;  // Aumenta o decremento de transparência

            // Quando o tempo do efeito acabar, desativa o efeito
            if (effectTimer <= 0) {
                effectActive = false;
            }
        }
    }

    public void draw(Graphics2D g2) {
        super.draw(g2);  // Desenha o projétil

        // Se o efeito de círculo estiver ativo, desenha o círculo
        if (effectActive) {
            g2.setColor(new Color(100, 100, 255, Math.max(alpha, 0)));  // Cor azul com transparência
            g2.setStroke(new BasicStroke(3));  // Define espessura do círculo
            g2.drawOval(worldX - 16, worldY - 16, 32, 32);  // Desenha o círculo ao redor do projétil
        }
    }
}

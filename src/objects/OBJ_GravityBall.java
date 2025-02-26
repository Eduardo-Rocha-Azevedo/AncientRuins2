package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.Entity;
import entity.GravityEffect;  // Importando a classe GravityEffect
import entity.Projectile;
import principal.GamePanel;

public class OBJ_GravityBall extends Projectile {
   
    public static final String objName = "GravityBall";
    private GravityEffect gravityEffect; // A instância do efeito de gravidade
    private boolean effectTriggered = false; // Para garantir que o efeito é acionado uma vez

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
        up1 = setup("/projectile/fireball_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/projectile/fireball_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/projectile/fireball_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/projectile/fireball_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/projectile/fireball_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/projectile/fireball_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/projectile/fireball_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/projectile/fireball_right_2", gp.tileSize, gp.tileSize);
    }

    public boolean haveResources(Entity user) {
        boolean haveResources = false;
        if (user.cosmo >= useCost) {
            haveResources = true;
        }
        return haveResources;
    }

    public void subtractResource(Entity user) {
        // user.cosmo -= useCost; // Subtrai os recursos necessários (se necessário)
    }

    public Color getParticleColor() {
        Color color = new Color(240, 50, 0);
        return color;
    }

    public int getParticleSize() {
        int size = 7; // pixels
        return size;
    }

    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }

    // Método para ser chamado quando o projétil "morre" ou atinge o destino
    public void triggerGravityEffect(Entity target) {
        if (!effectTriggered) {
            // Criar o efeito de gravidade (tempo de vida de 5 segundos, raio de 100 pixels e força de 0.5)
            gravityEffect = new GravityEffect(gp, target, 0.5, 100, 5);
            effectTriggered = true; // Garantir que o efeito é acionado apenas uma vez
        }
    }

    
    

    // Método para desenhar a GravityBall e o efeito visual de gravidade
    public void draw(Graphics2D g2) {
        super.draw(g2);  // Se necessário, chamar o método draw da classe mãe (Projectile)
        
        // Desenhar o efeito de gravidade se ele estiver ativo
        if (gravityEffect != null) {
            gravityEffect.draw(g2);
        }
    }
}

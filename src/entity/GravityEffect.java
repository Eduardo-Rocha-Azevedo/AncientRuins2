package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import principal.GamePanel;

public class GravityEffect extends Entity {
    private Entity target; // Entidade que será afetada pela gravidade
    private double gravityStrength; // A intensidade da gravidade (quanto mais forte, mais rápido será o efeito)
    private int effectRadius; // O raio da área de efeito da gravidade
    private long timeAlive; // Tempo de vida do efeito (quanto tempo ele vai durar)
    private long startTime; // Tempo de início do efeito

    // Construtor da classe GravityEffect
    public GravityEffect(GamePanel gp, Entity target, double gravityStrength, int effectRadius, int timeAlive) {
        super(gp);
        this.target = target;
        this.gravityStrength = gravityStrength;
        this.effectRadius = effectRadius;
        this.timeAlive = timeAlive * 1000; // Convertendo para milissegundos
        this.startTime = System.currentTimeMillis();
    }

    // Atualiza o efeito de gravidade e aplica o movimento gravitacional
    public void update() {
        // Verificar se o tempo de vida expirou
        if (System.currentTimeMillis() - startTime >= timeAlive) {
            // O efeito de gravidade expirou
            return;
        }

        // Aplicando o efeito de gravidade sobre a entidade alvo
        applyGravity();
    }

    // Aplica a gravidade sobre a entidade alvo
    private void applyGravity() {
        // Verificando a distância entre o efeito de gravidade e a entidade alvo
        double dx = target.worldX - this.worldX;
        double dy = target.worldY - this.worldY;
        double distance = Math.sqrt(dx * dx + dy * dy); // Distância entre o ponto central e a entidade alvo
        
        if (distance < effectRadius) {
            // Calculando a força de atração com base na distância e na intensidade da gravidade
            double force = (gravityStrength * (effectRadius - distance)) / effectRadius;

            // Evitar que a força fique muito alta para objetos muito próximos
            force = Math.max(force, 0); // Não permitir força negativa

            // Aplique a força de atração na direção do centro da gravidade
            double angle = Math.atan2(dy, dx);
            int pullX = (int) (Math.cos(angle) * force);
            int pullY = (int) (Math.sin(angle) * force);

            // Mover a entidade de acordo com a força aplicada
            target.worldX -= pullX;
            target.worldY -= pullY;
        }
    }

    // Desenha o efeito visual do campo gravitacional (um círculo de atração)
    public void draw(Graphics2D g2) {
        // Mudando a cor conforme o tempo de vida do efeito
        long elapsedTime = System.currentTimeMillis() - startTime;
        float alpha = 1.0f - Math.min((float) elapsedTime / (float) timeAlive, 1.0f); // Decrease alpha over time
        
        // Cor do campo de gravidade com transparência
        g2.setColor(new Color(0, 0, 255, (int) (alpha * 255))); // Transparência gradual
        g2.fillOval(worldX - effectRadius / 2, worldY - effectRadius / 2, effectRadius, effectRadius);
    }
}

package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.BasicStroke;
import principal.GamePanel;

public class GravityExplosion extends Entity{
    private GamePanel gp;
    private int x, y;
    public int radius = 0;
    private int maxRadius = 200; // Tamanho máximo do círculo
    private int alpha = 255; // Transparência do círculo
    private boolean active = true; // Controla se ainda deve ser desenhado
    private int lifeSpan = 500; // 5 segundos a 60 FPS
    private boolean reachedMaxSize = false;
    private int blinkCounter = 0; // Contador para o efeito de piscar
    private boolean visible = true; // Define se está visível
    private List<Particle> particles = new ArrayList<>(); // Lista de partículas

    public GravityExplosion(GamePanel gp, int x, int y) {
        super(gp);
        this.gp = gp;
        this.x = x;
        this.y = y;
    }

    public void update() {
        if (active) {
            if (!reachedMaxSize) {
                radius += 3; // Cresce a cada frame
                alpha--;
                if (radius >= maxRadius) {
                    reachedMaxSize = true; // Marca que atingiu o tamanho máximo
                    lifeSpan = 300; // Inicia os 5 segundos de duração
                }
            } else {
                lifeSpan--; // Conta o tempo até desaparecer

                // Alterna a visibilidade a cada 10 frames
                blinkCounter++;
                if (blinkCounter % 18 == 0) {
                    visible = !visible;
                }

                if (lifeSpan <= 0) {
                    active = false; // Desativa o efeito
                }
            }

            // Gerar partículas giratórias após atingir o tamanho máximo
            if (reachedMaxSize) {
                createParticles();
            }

            // Atualizar as partículas
            for (Particle p : particles) {
                p.update();
            }
        }
    }

    // Criar partículas giratórias ao redor do centro
    private void createParticles() {
        int numParticles = 8; // Quantidade de partículas
        int particleSize = 5; // Tamanho das partículas
        int particleSpeed = 1; // Velocidade das partículas
        int maxLife = 100; // Vida das partículas

        // Criando partículas com movimento circular em torno do centro
        for (int i = 0; i < numParticles; i++) {
            double angle = Math.toRadians((360 / numParticles) * i); // Calcular a direção de cada partícula
            int xd = (int) (Math.cos(angle) * radius); // Deslocamento no eixo X
            int yd = (int) (Math.sin(angle) * radius); // Deslocamento no eixo Y

            // Adicionar a partícula à lista
            particles.add(new Particle(gp, this, new Color(150, 51, 150), particleSize, particleSpeed, maxLife, xd, yd));
        }
    }

    public void draw(Graphics2D g2) {
        if (active && visible) {
            // Converter coordenadas do mundo para a tela
            int screenX = x - gp.player.worldX + gp.player.screenX;
            int screenY = y - gp.player.worldY + gp.player.screenY;

            // Verificar se está dentro da área visível do jogador
            if (screenX + radius > 0 && screenX - radius < gp.screenWidth &&
                    screenY + radius > 0 && screenY - radius < gp.screenHeight) {
                // Desenhar o círculo
                g2.setColor(new Color(150, 51, 150, alpha));
                g2.setStroke(new BasicStroke(3));
                g2.drawOval(screenX - radius / 2, screenY - radius / 2, radius * 2, radius * 2);
                g2.fillOval(screenX - radius / 2, screenY - radius / 2, radius * 2, radius * 2);
            }

            // Desenhar as partículas
            for (Particle p : particles) {
                p.draw(g2);
            }
        }
    }

    public boolean isActive() {
        return active;
    }
}

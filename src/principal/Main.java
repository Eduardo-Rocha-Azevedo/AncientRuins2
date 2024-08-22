package principal;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
    	System.out.println("iniciando o jogo");
        JFrame window = new JFrame("Ancient Ruins 2");
        GamePanel gamePanel = new GamePanel();
        
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null); // Centraliza a janela
        window.setVisible(true);
        
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}


package principal;

import javax.swing.JFrame;

public class Main {
    public static JFrame window;
    public static void main(String[] args) { 
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Ancient Ruins 2");
      
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        gamePanel.config.loadConfig();
        if(gamePanel.fullScreenOn == true){
            window.setUndecorated(true);
        }
      
        window.pack();

        window.setLocationRelativeTo(null); // Centraliza a janela
        window.setVisible(true);
        
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }

}

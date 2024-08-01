package principal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean up,down, left, right;
    boolean checkDrawTime = false;

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
           up = true;
        }
        if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            down = true;
        }
        if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
           left = true;
        }
        if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            right = true;
        }
		if(code == KeyEvent.VK_T) {
            if(checkDrawTime == false){
                checkDrawTime = true;
            }
            else if(checkDrawTime == true){
                checkDrawTime = false;
            }
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
           up = false;
        }
        if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            down = false;
        }
        if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
            left = false;
        }
        if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            right = false;
        }
		
	}
    
}

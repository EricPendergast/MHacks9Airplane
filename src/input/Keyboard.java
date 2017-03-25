package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;

public class Keyboard implements KeyListener {
    private static ArrayList<KeyListener> listeners = new ArrayList<KeyListener>();
    
	public static boolean[] keys = new boolean[1000];
	
	public void keyPressed(KeyEvent e) {
        for (KeyListener l : listeners)
            l.keyPressed(e);
		keys[e.getKeyCode()] = true;
	}
	public void keyReleased(KeyEvent e) {
        for (KeyListener l : listeners)
            l.keyReleased(e);
		keys[e.getKeyCode()] = false;
	}
	public void keyTyped(KeyEvent e) {
        for (KeyListener l : listeners)
            l.keyTyped(e);
		keys[e.getKeyCode()] = true;
	}

}

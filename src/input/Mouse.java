package input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import java.util.ArrayList;

public class Mouse implements MouseListener, MouseMotionListener{
	public static Point button1At = new Point(-1,-1);
	public static boolean button1Pressed = false;
	public static boolean button2Pressed = false;
	public static int scale = 1;
	public static JFrame frame;
    
    private ArrayList<MouseListener> listeners = new ArrayList<MouseListener>();
    public void addMouseListener(MouseListener l) {
        listeners.add(l);
    }
    public void removeMouseListener(MouseListener l) {
        listeners.remove(l);
    }
	public Mouse(){
		scale = 1;
	}
	public Mouse(int scale){
		this.scale = scale;
	}
	public Mouse(int scale, JFrame frame){
		this(scale);
		this.frame = frame;
		
	}
	@Override
	public void mouseMoved(MouseEvent e){
        for (MouseListener l : listeners)
            l.mouseMoved(e);
		button1At.setLocation(new Point(e.getPoint().x/scale, e.getPoint().y/scale));
	}
	@Override
	public void mouseClicked(MouseEvent e) {
        for (MouseListener l : listeners)
            l.mouseClicked(e);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
        for (MouseListener l : listeners)
            l.mouseEntered(e);
	}
	@Override
	public void mouseExited(MouseEvent e) {
        for (MouseListener l : listeners)
            l.mouseExited(e);
		button1At.setLocation(-1,-1);
		button1Pressed = false;
	}
	@Override
	public void mousePressed(MouseEvent e) {
        for (MouseListener l : listeners)
            l.mousePressed(e);
        
		if(e.getButton() == MouseEvent.BUTTON1)
			button1Pressed = true;
		if(e.getButton() == MouseEvent.BUTTON3)
			button2Pressed = true;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
        for (MouseListener l : listeners)
            l.mouseReleased(e);
		if(e.getButton() == MouseEvent.BUTTON1)
			button1Pressed = false;
		if(e.getButton() == MouseEvent.BUTTON3)
			button2Pressed = false;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
        for (MouseListener l : listeners)
            l.mouseDragged(e);
		button1At.setLocation(new Point(e.getPoint().x/scale, e.getPoint().y/scale));
	}
	public void setScale(int s){
		scale = s;
	}
	public static JFrame getFrame(){
		return frame;
	}
}

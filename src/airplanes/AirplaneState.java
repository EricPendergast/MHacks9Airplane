package airplanes;

import input.*;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import states.Game;
import states.NodeState;
import java.util.*;
import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

public class AirplaneState extends NodeState {
    ASListener listener;
    
    ArrayList<Airplane> airplanes = new ArrayList<Airplane>();
    
    boolean planeSelected = false;
    int selectedPlaneIndex = -1;
    
    //REQUIRES: game is valid
    //MODIFIES: this
    //EFFECTS: initializes 'this' with a game object
	public AirplaneState(){
        listener = new ASListener();
        Mouse.addMouseListener(listener);
	}
   
    //MODIFIES: this
    //EFFECTS: does all the updating stuff
	boolean updated = false;
	public void update(){
        evalUserInput();
		super.update();
		if(!updated){
			updated = true;
		}

		for (Airplane airplane :
				airplanes) {
			airplane.update(1.0/60);
		}
        
        checkSelection();
	}
    
    // If the user clicks, the closest plane is selected, and as the user drags, a path is drawn.
    public void checkSelection() {
        if (listener.pressInit()) {
            int closestPlaneIndex = 0;
            double closestPlaneValue = airplanes.get(0).getDistance(Mouse.button1At.x, Mouse.button1At.y);
            // Searching for the closest plane
            for (int i = 1; i < airplanes.size(); i++) {
                double dist = airplanes.get(i).getDistance(Mouse.button1At.x, Mouse.button1At.y);
                if (dist < closestPlaneValue) {
                    closestPlaneValue = dist;
                    closestPlaneIndex = i;
                }
            }
            // If the closest plane is closer than  its girth, the user has
            // selected the plane.
            if (closestPlaneValue <= airplanes.get(closestPlaneIndex).getGirth()){
                planeSelected = true;
                selectedPlaneIndex = closestPlaneIndex;
            }
        } else if (listener.mouseHeld() && planeSelected) {
            System.out.println("adding to path");
            airplanes.get(selectedPlaneIndex).pushToPath(new Point2D.Double(Mouse.button1At.x, Mouse.button1At.y));
        } else if (listener.releaseInit()) {
            planeSelected = false;
            selectedPlaneIndex = -1;
        }
        
        
    }
    
    // REQUIRES: 'plane' is valid
    // MODIFIES: this
    // EFFECTS: adds 'plane' to the list of 
    public void addAirplane(Airplane plane) {
        airplanes.add(plane);
    }
    
    // EFFECTS: none
    public void render(Graphics2D g2) {
        for (Airplane a : airplanes) {
			a.render(g2);
		}
        
        if (planeSelected) {
            g2.drawLine((int)airplanes.get(selectedPlaneIndex).getX(), (int)airplanes.get(selectedPlaneIndex).getY(), 
                    Mouse.button1At.x, Mouse.button1At.y);
        }
    }
    
    // MODIFIES: this
    // EFFECTS: changes the state of 'this' depending on user input
    private void evalUserInput() {
        //if (Mouse.button1Pressed)
            //System.out.println("Mouse 1");
    }
}


class ASListener implements MasterMouse {
    boolean pressInit = false;
    boolean releaseInit = false;
    
    boolean mouseHold = false;
    
    // MODIFIES: 'pressInit'
    // EFFECTS: returns whether the mouse was initially pressed. In other
    // words, whether the mouse went from being not pressed to pressed since
    // the last time this method was called. This method will only be true the
    // first time it is called.
    public boolean pressInit() {
        if (pressInit){
            pressInit = false;
            return true;
        }
        return false;
    }
    public boolean releaseInit() {
        if (releaseInit){
            releaseInit = false;
            return true;
        }
        return false;
    }
    
    public boolean mouseHeld() {
        return mouseHold;
    }
    
	public void mouseMoved(MouseEvent e){
        //System.out.println("MOUSE MOVED");
	}
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
        pressInit = true;
        mouseHold = true;
	}
	public void mouseReleased(MouseEvent e) {
        releaseInit = true;
        mouseHold = false;
	}
	public void mouseDragged(MouseEvent e) {
        //System.out.println("MOUSE DRAGGED");
	}
    
}

package airplanes;

import input.Keyboard;
import input.Mouse;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import states.Game;
import states.NodeState;
import java.util.*;
import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AirplaneState extends NodeState {
    
    ArrayList<Airplane> airplanes = new ArrayList<Airplane>();
    //REQUIRES: game is valid
    //MODIFIES: this
    //EFFECTS: initializes 'this' with a game object
	public AirplaneState(){
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
	}
    
    // REQUIRES: 'plane' is valid
    // MODIFIES: this
    // EFFECTS: adds 'plane' to the list of 
    public void addAirplane(Airplane plane) {
        airplanes.add(plane);
    }
    
    // EFFECTS: none
    public void render(Graphics2D g2) {
        for (Airplane a : airplanes)
            a.render(g2);
    }
    
    // MODIFIES: this
    // EFFECTS: changes the state of 'this' depending on user input
    private void evalUserInput() {
        if (Mouse.button1Pressed)
            System.out.println("Mouse 1");
    }
}


class ASListener implements MouseListener {
	public void mouseMoved(MouseEvent e){
	}
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseDragged(MouseEvent e) {
	}
    
}

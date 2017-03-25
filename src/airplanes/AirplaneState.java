package airplanes;

import input.Keyboard;
import input.Mouse;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import states.Game;
import states.NodeState;
import java.util.*;
import java.awt.*;

public class AirplaneState extends NodeState {
	Game game;
    ArrayList<Airplane> airplanes = new ArrayList<Airplane>();
    //REQUIRES: game is valid
    //MODIFIES: this
    //EFFECTS: initializes 'this' with a game object
	public AirplaneState(Game game){
		this.game = game;
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
	}
    
    // REQUIRES: 'plane' is valid
    // MODIFIES: this
    // EFFECTS: adds 'plane' to the list of 
    public void addAirplane(Airplane plane) {
        airplanes.add(new Airplane());
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

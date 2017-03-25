package airplanes;

import input.Keyboard;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import states.Game;
import states.NodeState;
import java.util.*;
import java.awt.*;

public class AirplaneState extends NodeState{
	Game game;
    ArrayList<Airplane> airplanes = new ArrayList<Airplane>();
	public AirplaneState(Game game){
		this.game = game;
        airplanes.add(new Airplane());
	}
   
	boolean updated = false;
	public void update(){
		super.update();
		if(!updated){
			updated = true;
		}
	}
    
    public void render(Graphics2D g2) {
        for (Airplane a : airplanes)
            a.render(g2);
    }
}

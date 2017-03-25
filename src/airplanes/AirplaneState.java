package airplanes;

import input.Keyboard;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import states.Game;
import states.NodeState;
import java.util.*;

public class AirplaneState extends NodeState{
	Game game;
    ArrayList<Airplane> airplanes = new ArrayList<Airplane>();
	public AirplaneState(Game game){
		this.game = game;
	}
   
	boolean updated = false;
	public void update(){
		super.update();
		if(!updated){
			updated = true;
		}
	}
}


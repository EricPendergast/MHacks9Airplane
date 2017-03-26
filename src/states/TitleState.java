package states;

import input.*;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import states.Game;
import states.NodeState;
import java.util.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import nodes.*;


public class TitleState extends NodeState {
    Game game;
    //Rectangle playButton;
    
    public TitleState(Game game) {
        this.game = game;
        //Rectangle playButton = new Rectangle(0,0,100,100);
       //System.out.println(playButton);
    }
    
    public void update() {
       //super.update();
       //System.out.println(playButton);
       //playButton.contains(0,0);
       if (Mouse.button1Pressed && (new Rectangle(0,0,100,100)).contains(Mouse.button1At.x, Mouse.button1At.y))
           game.setStateIndex(1);
    }
}

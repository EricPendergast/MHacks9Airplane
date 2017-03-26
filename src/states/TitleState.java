package states;

import input.*;

import java.awt.*;
import java.awt.event.KeyEvent;

import states.Game;
import states.NodeState;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import nodes.*;

import javax.imageio.ImageIO;




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
       if (Mouse.button1Pressed && (new Rectangle(0,0,2000,2000)).contains(Mouse.button1At.x, Mouse.button1At.y))
           game.setStateIndex(1);
    }

    public void render(Graphics2D g2) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("ATCS-Title.jpg"));
        } catch (IOException e) {
            System.out.println("Can't find file");
        }

        g2.drawImage(img, 0, 0, null);
    }
}

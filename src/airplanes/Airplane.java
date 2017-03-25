package airplanes;

import java.util.*;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;

public class Airplane {
    //x pos and y pos
    double x = 0;
    double y = 0;;
    // The radius of the plane
    double girth = 0;
    
    // Queue of positions. This is reset whenever the player draws a new path
    // for the plane.
    ArrayList<Point2D.Double> path = new ArrayList<Point2D.Double>();
    
    //EFFECTS: returns the distance from 'this' to 'airplane'
    double getDistance(double x, double y) {
        return Math.sqrt(Math.pow(this.x+x,2) + Math.pow(this.y+y,2));
    }
    
    //returns true if distance is in critial threshold and false otherwise
    //REQUIRES: 'a' is valid
    //EFFECTS: returns whether or not 'this' and 'a' are colliding
    boolean collide(Airplane a){
        double dist = getDistance(a);
        return dist-girth-a.girth < 0;
    }
    
    //MODIFIES: g2
    //EFFECTS: renders the airplane to g2
    void render(Graphics2D g2) {
        g2.translate(x,y);
        g2.drawOval(0,0,50,50);
        g2.translate(-x,-y);
    }
}

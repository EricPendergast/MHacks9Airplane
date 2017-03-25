package airplanes;

import java.util.*;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;

class Airplane {
    //x pos and y pos
    double x = 0;
    double y = 0;;
    double girth = 0;
    
    // Queue of positions. This is reset whenever the player draws a new path
    // for the plane.
    ArrayList<Point2D.Double> path = new ArrayList<Point2D.Double>();
    
    //returns distance between this and another airplane object 
    double getDistance(Airplane airplane) {
        return Math.sqrt(Math.pow(x+airplane.x,2) + Math.pow(y+airplane.y,2));
        
    }
    //returns true if distance is in critial threshold and false otherwise
    boolean collis(Airplane a){
        double dist = getDistance(a);
        return dist-girth-a.girth < 0;
    }
    
    void render(Graphics2D g2) {
        g2.translate(x,y);
        g2.drawOval(0,0,50,50);
        g2.translate(-x,-y);
    }
}

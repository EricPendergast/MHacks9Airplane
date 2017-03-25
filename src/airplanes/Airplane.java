package airplanes;

import java.util.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.geom.GeneralPath;


public class Airplane {
    //x pos and y pos
    double x = 0;
    double y = 0;
    double speed = 0;
    // Theta is zero 
    double theta = 0;
    // The radius of the plane
    double girth = 0;
    
    
    Airplane(double x, double y) {
        this.x = x;
        this.y = y;
    }
    Airplane(double x, double y, double theta) {
        
    }
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
        double dist = getDistance(a.x, a.y);
        return dist-girth-a.girth < 0;
    }
    
    double getSpeed() {
        return speed;
    }
    
    //MODIFIES: g2
    //EFFECTS: renders the airplane to g2
    void render(Graphics2D g) {
        int inx = (int)x;
        int iny = (int)y;
        Graphics2D g2 = (Graphics2D)g;
        int x1Points[] = {0, 10, 0, -10, 0};
        int y1Points[] = {0,20,15,20, 0};
        GeneralPath polygon =
                new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                        x1Points.length);
        polygon.moveTo(x1Points[0], y1Points[0]);

        for (int index = 1; index < x1Points.length; index++) {
            polygon.lineTo(x1Points[index], y1Points[index]);
        };

        polygon.closePath();
        g2.translate(inx, iny);
        g2.setColor(Color.BLUE);
        g2.setBackground(Color.darkGray);
        g2.draw(polygon);
        g2.fill(polygon);
    }
    
    public void update(double dt) {
        // Update velocities
        double dx = speed * Math.cos(theta) * dt;
        double dy = speed * Math.sin(theta) * dt;
        x += dx;
        y += dy;
        
        if (path.size() > 0) {
            if (getDistance(path.get(0).x, path.get(0).y) < 42)
                path.remove(0);
            
            rotateTowards(path.get(0).x, path.get(0).y);
        }
    }
    
    //public void setPath<F4
    
    private void rotateTowards(double x, double y) {
        double dx = x - this.x;
        double dy = y - this.y;
        theta = Math.atan(dy/dx);
    }
    
}

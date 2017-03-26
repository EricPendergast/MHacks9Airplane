package airplanes;

import java.util.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.geom.GeneralPath;


public class Airplane {
    //x pos and y pos
    private double x = 500;
    private double y = 300;
    private double speed = 100;
    // The angle of the plane. Theta is zero when plane is pointing right
    private double theta = 0;
    private boolean crashing = false;

    // The maximum distance the user has to click from the center of the
    // airplane in order to select it.
    private double selectDistance = 100;
    // Radius of the plane. This is used for detecting collisions.
    private double girth = 10;
    // The rate of change of theta with respect to time when circling
    private double dTheta = .01;
    private final double defFuel = 3000;
    private double fuel = defFuel;
    private Color color;

    // The minimum angle per time the plane can turn at.
    private double thetaThresh = 100;
    
    // Queue of positions. This is reset whenever the player draws a new path
    // for the plane. The first item in 'path' is the first point that the
    // plane targets
    private ArrayList<Point2D.Double> path = new ArrayList<Point2D.Double>();

    public int[]FireArrayX = {}
    public Airplane() {
        path.add(new Point2D.Double(100, 300));
        path.add(new Point2D.Double(100, 100));
    }
    
    public Airplane(double x, double y, double destX, double destY, double speed, Color color) {
        this.x = x;
        this.y = y;

        path.add(new Point2D.Double(destX, destY));

        this.speed = speed;
        this.color = color;
    }

    //EFFECTS: returns the distance from 'this' to 'airplane'
    double getDistance(double x, double y) {
        return Math.sqrt(Math.pow(this.x-x,2) + Math.pow(this.y-y,2));
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

    private double rotateX(double x, double y, double theta) {
        return Math.cos(theta) * x - Math.sin(theta) * y;
    }

    private double rotateY(double x, double y, double theta) {
        return Math.sin(theta) * x + Math.cos(theta) * y;
    }

    //MODIFIES: g2
    //EFFECTS: renders the airplane to g2
    void render(Graphics2D g) {
        drawPlane(g);
        drawGBar(g);
        drawRBar(g);
        drawPath(g);
        drawPlane(g);
    }
    
    private void drawPlane(Graphics2D g2) {
        int inx = (int)x;
        int iny = (int)y;
        int x1Points[] = {0, 1, 1, 7, 7, 1, 1, 3, 1, 0, -1, -3, -1, -1, -6, -6, -1, -1};
        int y1Points[] = {-10,-8,-5,0, 1,-1, 5, 7, 7, 9, 7, 7, 5, -1, 1, 0, -5, -8};

        // Rotate shape
        for (int i = 0; i < x1Points.length; i++) {
            double newX = rotateX(x1Points[i], y1Points[i], theta + Math.PI / 2);
            double newY = rotateY(x1Points[i], y1Points[i], theta + Math.PI / 2);
            x1Points[i] = (int)  Math.floor(newX);
            y1Points[i] = (int)  Math.floor(newY);
            x1Points[i] = x1Points[i] * 3;
            y1Points[i] = y1Points[i] * 3;
        }

        // Drawing Airplane
        GeneralPath polygon =
                new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                        x1Points.length);
        polygon.moveTo(x1Points[0], y1Points[0]);

        for (int index = 1; index < x1Points.length; index++) {
            polygon.lineTo(x1Points[index], y1Points[index]);
        };

        polygon.closePath();
        g2.translate(inx, iny);
        g2.setColor(color);
        g2.draw(polygon);
        g2.fill(polygon);
        g2.translate(-inx, -iny);
    }

    private void drawGBar(Graphics2D g2) {
        double barLen = 30;
        int barHeight = 5;
        double gLen = (fuel / defFuel) * barLen;
        double rLen = (fuel / defFuel) * barLen;

        int x1 = (int) (-barLen / 2);
        int x2 = (int) (barLen / 2);
        int y1 = 25;
        int y2 = y1 - barHeight;

        int x1gPoints[] = {x1, (int) (x1 + gLen), (int) (x1 + gLen), x1};
        int x1rPoints[] = {(int) (x1 + gLen), (int) (x1 + gLen), x2, x2};
        int y1Points[] = {y1, y1, y2, y2};

        // Drawing Green
        GeneralPath polygon =
                new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                        x1gPoints.length);
        polygon.moveTo(x1gPoints[0], y1Points[0]);

        for (int index = 1; index < x1gPoints.length; index++) {
            polygon.lineTo(x1gPoints[index], y1Points[index]);
        };

        polygon.closePath();
        g2.translate(x, y);
        g2.setColor(Color.green);
        g2.draw(polygon);
        g2.fill(polygon);
        g2.setColor(Color.black);
        g2.translate(-x, -y);
    }

    private void drawRBar(Graphics2D g2) {
        double barLen = 30;
        int barHeight = 5;
        double gLen = (fuel / defFuel) * barLen;

        int x1 = (int) (-barLen / 2);
        int x2 = (int) (barLen / 2);
        int y1 = 25;
        int y2 = y1 - barHeight;

        int x1gPoints[] = {x2, (int) (x1 + gLen), (int) (x1 + gLen), x2};
        int y1Points[] = {y1, y1, y2, y2};

        // Drawing Green
        GeneralPath polygon =
                new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                        x1gPoints.length);
        polygon.moveTo(x1gPoints[0], y1Points[0]);

        for (int index = 1; index < x1gPoints.length; index++) {
            polygon.lineTo(x1gPoints[index], y1Points[index]);
        };

        polygon.closePath();
        g2.translate(x, y);
        g2.setColor(Color.red);
        g2.draw(polygon);
        g2.fill(polygon);
        g2.translate(-x, -y);
    }

    private void drawPath(Graphics2D g2) {
        g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(color);
        if(path.size() > 0)
            g2.drawLine((int)path.get(0).x, (int)path.get(0).y, (int)x, (int)y);
        
        for(int i = 0; i+1 < path.size(); i++) {
            g2.drawLine((int)path.get(i).x, (int)path.get(i).y,
                        (int)path.get(i+1).x, (int)path.get(i+1).y);
        }
        
        g2.setStroke(new BasicStroke(1));
    }
    
    public void update(double dt) {
        // Update velocities
        double dx = speed * Math.cos(theta) * dt;
        double dy = speed * Math.sin(theta) * dt;
        x += dx;
        y += dy;

        // Reduce fuel

        fuel -= 1;
        if (fuel <= 0) {
            System.out.println("PLANE CRASHED");
            crashing = true;
        }

        // Either go to destination, or move in a circle
        if (path.size() > 0) {
            rotateTowards(path.get(0).x, path.get(0).y);

            while ( path.size() > 0 && 
                    getDistance(path.get(0).x, path.get(0).y) < 42) {
                rotateTowards(path.get(0).x, path.get(0).y);
                path.remove(0);
            }
        } else {
            theta += dTheta;
        }
    }
    
    private void rotateTowards(double x, double y) {
        double dx = x - this.x;
        double dy = y - this.y;
        theta = Math.atan2(dy, dx);
    }
    
    
    //public Point2D.Double applyThreshold(Point2D.Double velocity, Point2D.Double proposed) {
    //    double magV = Math.sqrt(velocity.x*velocity.x + velocity.y*velocity.y);
    //    double magP = Math.sqrt(proposed.x*proposed.x + proposed.y*proposed.y);
    //    double angleBetween =
    //            Math.acos((velocity.x*proposed.x + velocity.y*proposed.y) /
    //            (magV * magP));
    //
    //    double magicNumber = thetaThresh / (magV+magP);
    //    System.out.println(angleBetween);
    //
    //    if (angleBetween < magicNumber) {
    //        // rotate 'proposed' until its angle with 'velocity' is the
    //        // threshold
    //        double angleToRotate = magicNumber - angleBetween;
    //        double cos = Math.cos(angleToRotate);
    //        double sin = Math.sin(angleToRotate);
    //        return new Point2D.Double(  proposed.x*cos - proposed.y*sin,
    //                                    proposed.x*sin + proposed.y*cos);
    //
    //    }
    //
    //    return new Point2D.Double(proposed.x, proposed.y);
    //}
    
    public void crashseq(){
        speed = this.speed * 0.9;
        if(Math.floor(speed) < 10){
            this.explodeseq();
        }
    }
    private void explodeseq(){

    }
    
    public void pushToPath(Point2D.Double point) {
        //Point2D.Double vel = new Point2D.Double(speed*Math.cos(theta), speed*Math.sin(theta));
        //path.add(applyThreshold(vel, point));
        path.add(point);
    }
    
    public void resetPath() {
        path.clear();
    }
    
    public double getGirth() { return girth; }
    public double getSelectDistance() { return selectDistance; }
    public double getX() {return x;}
    public double getY() {return y;}
    public double getDX() {return speed * Math.cos(theta);}
    public double getDY() {return speed * Math.sin(theta);}
    public double getTheta() {return theta;}
    public boolean getC(){return crashing;}
}



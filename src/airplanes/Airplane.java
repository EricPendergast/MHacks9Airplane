package airplanes;

import java.util.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.geom.GeneralPath;


public class Airplane {
    //x pos and y pos
    double x = 500;
    double y = 300;
    private double speed = 100;
    double theta = 0;   // Theta is zero when plane is pointing right
    private double girth = 10;   // Radius of the plane
    private double dTheta = .01;
    private final double defFuel = 3000;
    private double fuel = defFuel;
    private Color color;
    
    // Queue of positions. This is reset whenever the player draws a new path
    // for the plane.
    ArrayList<Point2D.Double> path = new ArrayList<Point2D.Double>();

    
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
        AffineTransform oldXForm = g.getTransform();
        drawPlane(g);
        g.setTransform(oldXForm);
        drawGBar(g);
        //drawRBar(g);
        drawPath(g);
    }
    
    private void drawPlane(Graphics2D g2) {
        int inx = (int)x;
        int iny = (int)y;
        int x1Points[] = {0, 10, 0, -10, 0};
        int y1Points[] = {-10,10,5,10, -10};

        // Rotate shape
        for (int i = 0; i < x1Points.length; i++) {
            double newX = rotateX(x1Points[i], y1Points[i], theta + Math.PI / 2);
            double newY = rotateY(x1Points[i], y1Points[i], theta + Math.PI / 2);
            x1Points[i] = (int) newX;
            y1Points[i] = (int) newY;
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
        g2.setBackground(Color.darkGray);
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

        // Drawing Red
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
    }

    private void drawPath(Graphics2D g2) {
        g2.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        if(path.size() > 0)
            g2.drawLine((int)path.get(0).x, (int)path.get(0).y, (int)x, (int)y);
        
        for(int i = 0; i+1 < path.size(); i++) {
            g2.drawLine((int)path.get(i).x, (int)path.get(i).y, (int)path.get(i+1).x, (int)path.get(i+1).y);
        }
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
        }

        // Either go to destination, or move in a circle
        if (path.size() > 0) {
            rotateTowards(path.get(0).x, path.get(0).y);

            if (getDistance(path.get(0).x, path.get(0).y) < 42) {
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
    
}



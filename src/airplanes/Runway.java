package airplanes;

import java.awt.*;

/**
 * Created by daniel on 3/25/17.
 */
public class Runway {
    double x;
    double y;
    double length;
    double width;
    Color color;

    public Runway(double x, double y, double length, double width, Color color) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
        this.color = color;
    }

    public boolean detectLand(Airplane airplane) {
        if (airplane.x < x + width / 2 && airplane.x > x - width / 2 &&
                airplane.y > y - length / 2 && airplane.y < y - length / 2 + 100
                && Math.abs(airplane.theta - Math.PI / 2) < Math.PI / 7) {
            return true;
        }
        return false;
    }
}

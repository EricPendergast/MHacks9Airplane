package airplanes;

import java.awt.*;
import java.awt.geom.GeneralPath;

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
        return (airplane.getX() < x + width / 2 &&
                airplane.getX() > x - width / 2 &&
                airplane.getY() > y - length / 2 &&
                airplane.getY() < y - length / 2 + 100 &&
                Math.abs(airplane.getTheta() - Math.PI / 2) < Math.PI / 7);
    }

    //MODIFIES: g2
    //EFFECTS: renders the airplane to g2
    void render(Graphics2D g) {
        drawRunway(g);
    }

    private void drawRunway(Graphics2D g2) {
        int xCorner = (int) (width / 2);
        int yCorner = (int) (length / 2);
        int x1Points[] = {xCorner, xCorner, (int)(xCorner - width), (int)(xCorner - width)};
        int y1Points[] = {yCorner, (int)(yCorner - length), (int)(yCorner - length), yCorner};

        // Drawing Runway
        GeneralPath polygon =
                new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                        x1Points.length);
        polygon.moveTo(x1Points[0], y1Points[0]);

        for (int index = 1; index < x1Points.length; index++) {
            polygon.lineTo(x1Points[index], y1Points[index]);
        };

        polygon.closePath();
        g2.translate(x, y);
        g2.setColor(this.color);
        g2.draw(polygon);
        g2.fill(polygon);
        g2.translate(-x, -y);
    }
}

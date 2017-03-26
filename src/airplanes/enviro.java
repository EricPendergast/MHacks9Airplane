package airplanes;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D.Double;
import java.awt.Shape;
import java.util.ArrayList;
/**
 * Created by Brian on 3/26/2017.
 */
public class enviro {
    public double xloc = 0;
    public double yloc = 0;
    public boolean collis = false;
    public Color color;
    int x_level1_tower1[] = {0, 40, 40, 0};
    int y_level1_tower1[] = {0, 0, 40, 40};
    public enviro(int size, int center, boolean hitbox, int level, Color incolor){
        x_level1_tower1[0] = center;
        x_level1_tower1[1] = center + size;
        x_level1_tower1[2] = center + size;
        x_level1_tower1[3] = center;
        y_level1_tower1[0] = center;
        y_level1_tower1[1] = center;
        y_level1_tower1[2] = center + size;
        y_level1_tower1[3] = center + size;
        color = incolor;
    }

    void render(Graphics2D g) {
        denviro(g);
    }

    public void denviro(Graphics2D g2) {
        GeneralPath polygon =
                new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                        x_level1_tower1.length);
        polygon.moveTo(x_level1_tower1[0], y_level1_tower1[0]);

        for (int index = 1; index < x_level1_tower1.length; index++) {
            polygon.lineTo(x_level1_tower1[index], y_level1_tower1[index]);
        }

        polygon.closePath();
        g2.setColor(color);
        g2.draw(polygon);
        g2.fill(polygon);
    }
}
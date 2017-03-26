package airplanes;

import javax.imageio.ImageIO;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D.Double;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by daniel on 3/26/17.
 */
public class Hud {
    public double xloc = 0;
    public double yloc = 0;
    public boolean collis = false;
    int x_level1_tower1[] = {0, 40, 40, 0};
    int y_level1_tower1[] = {0, 0, 40, 40};
    public Hud() {

    }

    void render(Graphics2D g) {
        drawHud(g);
    }

    public void drawHud(Graphics2D g2) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("person_1.jpg"));
        } catch (IOException e) {
            System.out.println("Can't find file");
        }

        g2.drawImage(img, 0, 0, null);
    }
}

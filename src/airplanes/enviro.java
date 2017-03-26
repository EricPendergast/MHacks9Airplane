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
 * Created by Brian on 3/26/2017.
 */
public class enviro {
    public double xloc = 0;
    public double yloc = 0;
    public boolean collis = false;
    public Color color;
    int x_level1_tower1[] = {0, 40, 40, 0};
    int y_level1_tower1[] = {0, 0, 40, 40};
    int x_tree[] = {0, 2, 1, 1, 4, 5, 5, 4, 3, -3, -4, -5, -5, -4, -1, -1, -2};
    int y_tree[] = {-6, -7, -5, 1, 2, 4, 6, 8, 10, 10, 8, 6, 4, 2, 1, -5, -7};
    public enviro(int size, int center, boolean hitbox, int level, Color inColor){
        x_level1_tower1[0] = center;
        x_level1_tower1[1] = center + size;
        x_level1_tower1[2] = center + size;
        x_level1_tower1[3] = center;
        y_level1_tower1[0] = center;
        y_level1_tower1[1] = center;
        y_level1_tower1[2] = center + size;
        y_level1_tower1[3] = center + size;
        color = inColor;
    }

//    public void drawTree(Graphics2D g2){
//        GeneralPath polygon =
//                new GeneralPath(GeneralPath.WIND_EVEN_ODD,
//                        x_tree.length);
//        polygon.moveTo(x_tree[0], y_tree[0]);
//
//        for (int index = 1; index < x_tree.length; index++) {
//            polygon.lineTo(x_tree[index], y_tree[index]);
//        }
//
//        polygon.closePath();
//        g2.setColor(Color.CYAN);
//        g2.draw(polygon);
//        g2.fill(polygon);
//        int[] x_tree_top = new int[12];
//        int[] y_tree_top = new int[12];
//        for(int i = 3; i < x_tree.length-2; i++){
//            x_tree_top[i] =x_tree[i];
//            y_tree_top[i] =y_tree[i];
//        }
//        polygon.moveTo(x_tree_top[0], y_tree_top[0]);
//        for (int index = 1; index < x_tree_top.length; index++) {
//            polygon.lineTo(x_tree_top[index], y_tree_top[index]);
//        }
//        polygon.closePath();
//        g2.setColor(Color.GREEN);
//        g2.draw(polygon);
//        g2.fill(polygon);
//    }
    void render(Graphics2D g) {
        drawEnviro(g);
//        drawTree(g);
    }

    public void drawEnviro(Graphics2D g2) {
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

//        BufferedImage img = null;
//        try {
//            img = ImageIO.read(new File("person_1.jpg"));
//        } catch (IOException e) {
//            System.out.println("Can't find file");
//        }
//
//        g2.drawImage(img, 0, 0, null);
    }
}
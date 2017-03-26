package main;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import input.FileRead;
import airplanes.*;
import states.*;
import java.util.*;

public class Start {
    public static Color[] niceColors = {new Color(0x89C73E), new Color(0xBAC5CC)};
    AirplaneState[] levels = new AirplaneState[10];

    public static void main(String[] args) throws IOException{
        Game game = new Game(1200, 750, Integer.parseInt(args[0]));

        //TitleState title = new TitleState();
        //game.addState(title);
        
        AirplaneState airplaneState = new AirplaneState(.01, 100, 20, 30);
        airplaneState.addRunway(new Runway(500, 500, 300, 50, Color.blue));
        airplaneState.addRunway(new Runway(800, 300, 300, 50, Color.red));
        airplaneState.addEnviros(new enviro(2000, 0, true, 1, new Color(12, 173, 12)));
        airplaneState.addEnviros(new enviro(40, 40, true, 1 , Color.orange));
        airplaneState.addEnviros(new enviro(40, 40, true, 1, Color.DARK_GRAY ));
        airplaneState.addEnviros(new enviro(40, 600, true, 1, Color.DARK_GRAY ));
        airplaneState.addEnviros(new enviro(40, 90, true, 1, Color.DARK_GRAY ));

        game.addState(airplaneState);
        
        game.start();
        //game.addState(cells);
        //game.addState(new StartMenu(cells.getRunner(),game));
        //game.addState(new CellState(game, new Tutorial()));
        //game.setStateIndex(1);
        //game.start();
    }

    public static BufferedReader getReader(String fileLoc){
        BufferedReader br = null;
        try {
            URL url = Start.class.getResource(fileLoc);
            System.out.println(url);
            br = new BufferedReader(new FileReader(url.getPath()));
        } catch (FileNotFoundException e) {e.printStackTrace();}

        return br;
    } 
    public static BufferedWriter getWriter(String fileLoc){
        BufferedWriter bw = null;
        try {
            URL url = FileRead.class.getResource(fileLoc);
            bw = new BufferedWriter(new FileWriter(url.getPath()));
        } catch (IOException e) {e.printStackTrace();}

        return bw;
    }
}

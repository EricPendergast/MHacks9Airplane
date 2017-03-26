package airplanes;

import input.*;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import states.Game;
import states.NodeState;
import java.util.*;
import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

public class AirplaneState extends NodeState {
    ArrayList<Airplane> airplanes = new ArrayList<Airplane>();
    ArrayList<Runway> runways = new ArrayList<>();

    // Rnd level stuff
    Random rnd = new Random();
    double planeFreq = .05;
    double meanVel = 100;
    double stdVel = 20;
    int numPlanes = 30;
    ArrayList<Color> planeTypes = new ArrayList<>();

    ArrayList<enviro> enviros = new ArrayList<>();

    ASListener listener;
    
    boolean planeSelected = false;
    Airplane selectedPlane = null;
    
    //REQUIRES: game is valid
    //MODIFIES: this
    //EFFECTS: initializes 'this' with a game object
	public AirplaneState(double pf, double meanV, double stdVel, int numPlanes){
        listener = new ASListener();
        Mouse.addMouseListener(listener);

        this.planeFreq = pf;
        this.meanVel = meanV;
        this.stdVel = stdVel;
        this.numPlanes = numPlanes;
        this.planeTypes = planeTypes;

        planeTypes.add(Color.blue);
        planeTypes.add(Color.red);
	}
   
    //MODIFIES: this
    //EFFECTS: does all the updating stuff
	boolean updated = false;
	public void update(){
        evalUserInput();
		super.update();
		if(!updated){
			updated = true;
		}

		// Update all airplanes
        for (Airplane airplane : airplanes) {
		    if(!airplane.getC()){
		        airplane.update(1.0/60);
            }

		}

		// Generates random places
		rndFuncs();

		// COMMENT OUT UNTIL CHECK SELECTION METHOD FIXED
		landedPlanes();

        if (collisionDetection()) {
            System.out.println("You lose!");
        }

        if (winLevel()) {
            System.out.println("You win!!!");
        }

        checkSelection();
	}

	private void rndFuncs() {
	    if (numPlanes > 0 && rnd.nextDouble() < planeFreq) {
	        makeNewPlane();
	        numPlanes--;
        }
    }

    private void makeNewPlane() {
	    int rand = rnd.nextInt(4);
	    double destX = 600 + rnd.nextGaussian() * 150;
        double destY = 325 + rnd.nextGaussian() * 100;
        double vel = rnd.nextGaussian() * stdVel + meanVel;

        Color color = planeTypes.get(rnd.nextInt(planeTypes.size()));

	    if (rand == 0) {
            addAirplane(new Airplane(-100, rnd.nextInt(550) + 100, destX, destY, vel, color));
        } else if (rand == 1) {
            addAirplane(new Airplane(1300, rnd.nextInt(550) + 100, destX, destY, vel, color));
        } else if (rand == 2) {
            addAirplane(new Airplane(rnd.nextInt(900) + 100, -100, destX, destY, vel, color));
        } else if (rand == 4) {
            addAirplane(new Airplane(rnd.nextInt(900) + 100, 850, destX, destY, vel, color));
        }
    }

	private void landedPlanes() {
        // Delete the airplanes that land
        ArrayList<Airplane> toDelete = new ArrayList<>();

        for (Airplane a : airplanes) {
            for (Runway r : runways) {
                if (r.detectLand(a)) {
                    toDelete.add(a);
                }
            }
        }

        for (Airplane a : toDelete) {
            airplanes.remove(a);
        }
    }

    private boolean collisionDetection() {
        for (Airplane a1 : airplanes) {
            for (Airplane a2 : airplanes) {
                if (!a1.equals(a2) && a1.collide(a2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean winLevel() {
	    if (numPlanes == 0 && airplanes.size() == 0) {
	        return true;
        }
        return false;
    }

    // If the user clicks, the closest plane is selected, and as the user
    // drags, a path is drawn.
    public void checkSelection() {
        if (listener.pressInit() && airplanes.size() > 0) {
            int closestPlaneIndex = 0;
            double closestPlaneValue = airplanes.get(0).getDistance(Mouse.button1At.x, Mouse.button1At.y);
            // Searching for the closest plane
            for (int i = 1; i < airplanes.size(); i++) {
                double dist = airplanes.get(i).getDistance(Mouse.button1At.x, Mouse.button1At.y);
                if (dist < closestPlaneValue) {
                    closestPlaneValue = dist;
                    closestPlaneIndex = i;
                }
            }
            
            // If the closest plane is closer than its select distance, the
            // user has selected the plane.
            if (closestPlaneValue <= airplanes.get(closestPlaneIndex).getSelectDistance()){
                planeSelected = true;
                selectedPlane = airplanes.get(closestPlaneIndex);
                selectedPlane.resetPath();
            }
        } else if (listener.mouseHeld() && planeSelected) {
            //Airplane plane = airplanes.get(selectedPlaneIndex);
            //plane.pushToPath(plane.applyThreshold(new Point2D.Double(plane.getDX(), plane.getDY()), new Point2D.Double(Mouse.button1At.x, Mouse.button1At.y)));
            selectedPlane.pushToPath(new Point2D.Double(Mouse.button1At.x, Mouse.button1At.y));
        } else if (listener.releaseInit()) {
            planeSelected = false;
            selectedPlane = null;
        }
        
    }
    
    
    // REQUIRES: 'plane' is valid
    // MODIFIES: this
    // EFFECTS: adds 'plane' to the list of 
    public void addAirplane(Airplane plane) {
        airplanes.add(plane);
    }

    public void addRunway(Runway runway) { runways.add(runway);}

    public void addEnviros(enviro environment){enviros.add(environment);}
    
    // EFFECTS: none
    public void render(Graphics2D g2) {
        for (enviro e: enviros){
            e.render(g2);
        }

        for (Runway r : runways) {
            r.render(g2);
        }

        for (Airplane a : airplanes) {
			a.render(g2);
		}
		for (Airplane a : airplanes) {
            if (a.getC())
                a.crashseq(g2);
        }
    }
    
    // MODIFIES: this
    // EFFECTS: changes the state of 'this' depending on user input
    private void evalUserInput() {
        //if (Mouse.button1Pressed)
            //System.out.println("Mouse 1");
    }
}

class ASListener implements MasterMouse {
    boolean pressInit = false;
    boolean releaseInit = false;
    
    boolean mouseHold = false;
    
    // MODIFIES: 'pressInit'
    // EFFECTS: returns whether the mouse was initially pressed. In other
    // words, whether the mouse went from being not pressed to pressed since
    // the last time this method was called. This method will only be true the
    // first time it is called.
    public boolean pressInit() {
        if (pressInit){
            pressInit = false;
            return true;
        }
        return false;
    }
    public boolean releaseInit() {
        if (releaseInit){
            releaseInit = false;
            return true;
        }
        return false;
    }
    
    public boolean mouseHeld() {
        return mouseHold;
    }
    
	public void mouseMoved(MouseEvent e){
        //System.out.println("MOUSE MOVED");
	}
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
        pressInit = true;
        mouseHold = true;
	}
	public void mouseReleased(MouseEvent e) {
        releaseInit = true;
        mouseHold = false;
	}
	public void mouseDragged(MouseEvent e) {
        
	}
}

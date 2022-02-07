
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;


class DrawWorld extends JPanel {




    // Target x and velocity (It doesn't move in the Y direction).
    double targetX=0, targetVX=10;

    // Missile (x,y) and velocities in x,y directions.
    double missileX=0, missileY=300, missileVX=20, missileVY=0;

    // Missile acceleration towards earth.
    double missileAY = -3;

    // Timestep.
    double delT = 0.1;

    // Compute move for missile when this has been set by user.
    boolean missileFired = false;

    // We'll use a new thread for each reset.
    Thread currentThread;



    public void paintComponent (Graphics g)
    {
        super.paintComponent (g);

        // so you have your actual code to draw the grid in 
        // paintComponent and you have it draw global variables 
        // and then you change the global variables and do this.repaint()

        // so you have to get access to the ever changing grid object 
        // from within the paint component method
        // the grid object is within the world instance of class CritterWorld
        //CritterWorldGUI2.CritterWorld

        // Clear.
        Dimension D = this.getSize();
        g.setColor (Color.white);
        g.fillRect (0,0, D.width,D.height);
        
        // Draw target.
        g.setColor (Color.blue);
        int x = (int) targetX;
        g.fillRect (x,D.height-20, 20, 20);

        // Draw missile.
        g.setColor (Color.red);
        x = (int) missileX;
        int y = D.height - (int) missileY;
        g.fillOval (x,y, 10, 10);
    }


    void reset ()
    {
	// Initialize the "physics"
        missileFired = false;
        targetX = 0;
        targetVX = 10;
        missileX = 0;
        missileY = 300;
        missileVY = 0;
        missileVX = 20;

        // Create and start off a thread for the animation.
        if (currentThread != null) {
            currentThread = null;
        }
        currentThread = new Thread () {
            public void run () 
            {
		animate ();
	    }
        };
        currentThread.start();
    }


    void animate ()
    {
        while (true) {

            // Do the "physics" for each step
            targetX += targetVX*delT;
            if (missileFired) {
                missileX += missileVX*delT;
                missileVY += missileAY*delT;
                missileY += missileVY*delT;
            }

	    // Redraw the screen.
            this.repaint ();

	    // Pause between successive re-drawings to create animation effect.
            try {
                Thread.sleep (50);
            }
            catch (InterruptedException e){
                System.out.println (e);
            }

        } //endwhile
    }
    
    
    void fire ()
    {
        missileFired = true;
    }
    
}




public class CritterWorldGUI2 extends JFrame implements ActionListener {

    // This is the JPanel on which we draw, and the one that
    // will override paintComponent()
    DrawWorld drawWorld;
    CritterWorld world;

    public CritterWorldGUI2 () 
    {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e){
            e.printStackTrace();
        }

        // Set the title and other frame parameters. 
        this.setTitle ("CW GUI");
        this.setResizable (true);
        this.setSize (700, 400);
        Container cPane = this.getContentPane();

        // Set a font
        Font f = new Font ("Papyrus", Font.BOLD, 20);

        //Create label for title
        JLabel label = new JLabel("CritterWorld");
        label.setFont(f);

        // Create panel to place title label
        JPanel topPanel = new JPanel ();
        topPanel.setLayout (new FlowLayout());
        topPanel.add(label);
        topPanel.setBackground(Color.WHITE);
        cPane.add (topPanel, BorderLayout.NORTH);

	// Create the center panel on which to draw and place in the center.
        drawWorld = new DrawWorld ();
        cPane.add (drawWorld, BorderLayout.CENTER);

	// This panel serves as the container for the two buttons:

         // Use a panel for clear and start buttons. 
         JPanel bottomPanel = new JPanel ();
         bottomPanel.setLayout (new FlowLayout());
         bottomPanel.setBackground(Color.WHITE);
         cPane.add (bottomPanel, BorderLayout.SOUTH);

 
         // Now insert the color buttons. 
         JButton resetB = new JButton ("Reset");
         resetB.setBackground (Color.orange);
         resetB.addActionListener (this);
         resetB.setFont(f);
         bottomPanel.add (resetB);
     
         JButton nextB = new JButton ("Next");
         nextB.setBackground (Color.green);
         nextB.addActionListener (this);
         nextB.setFont(f);
         bottomPanel.add (nextB);
 
         JButton nextPlusB = new JButton ("Next+");
         nextPlusB.setBackground (Color.orange);
         nextPlusB.addActionListener (this);
         nextPlusB.setFont(f);
         bottomPanel.add (nextPlusB);
 
         JButton randomizeB = new JButton ("Randomize");
         randomizeB.setBackground (Color.green);
         randomizeB.addActionListener (this);
         randomizeB.setFont(f);
         bottomPanel.add (randomizeB);
 
         JButton completeB = new JButton ("Complete");
         completeB.setBackground (Color.orange);
         completeB.addActionListener (this);
         completeB.setFont(f);
         bottomPanel.add (completeB);
 
         JButton quitB = new JButton ("Quit");
         quitB.setBackground (Color.green);
         quitB.addActionListener (this);
         quitB.setFont(f);
         bottomPanel.add (quitB);

        this.setVisible (true);
    }

    public void actionPerformed (ActionEvent a)
    {
   
	if (a.getActionCommand().equals("Reset")) {
	    // We'll put the animation and calculation code in DrawPanel
	    drawWorld.reset ();
	}
	else if (a.getActionCommand().equals("Fire")) {
	    drawWorld.fire ();
	}
    else if (a.getActionCommand().equals("Quit")){
        System.exit (0);
    }
	else {
	    System.out.println ("ERROR: no such action");
	}
    }

    public static void simulateSteps (int N, int T, ArrayList<Coord> startLocations, ArrayList<Coord> rockLocations, Coord escapeHatch)
    {
	world = new CritterWorld (N, T, startLocations, rockLocations, escapeHatch);

	System.out.println (world);
	int t = 0;
	while (t < T) {
	    boolean isOver = world.nextStep ();
	    System.out.println (world);
	    if (isOver) {
		break;
	    }
	    t = t + 1;
	}
	System.out.println ("Simulation time: t=" + t + " steps");
	world.printStats ();
    }

    static void test1 ()
    {
	int N = 5;      // Size of grid: N x N
	int T = 100;    // Max # steps to simulate (it could end earlier).

	// Place critters, rock, and escape hatch:
	ArrayList<Coord> critterStartLocations = new ArrayList<>();
	critterStartLocations.add ( new Coord(0,0) );
	critterStartLocations.add ( new Coord(0,1) );
	critterStartLocations.add ( new Coord(0,2) );

	ArrayList<Coord> rockLocations = new ArrayList<>();
	// No rocks.

	Coord escape = new Coord (4,4);
	
	simulateSteps (N, T, critterStartLocations, rockLocations, escape);
    }


    public static void main (String[] argv)
    {
        test1();
    }


}


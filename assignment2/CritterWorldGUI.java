
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;

import jdk.jfr.Label;

import java.util.*;


class MyPanel extends JPanel {

    CritterWorld world;

    int N = 8;      // Size of grid: N x N
	int T = 200;    // Max # steps to simulate (it could end earlier).

	// Place critters, rock, and escape hatch:
	ArrayList<Coord> critterStartLocations = new ArrayList<>();
	ArrayList<Coord> rockLocations = new ArrayList<>();
	Coord escape = new Coord (7,4);
	

    public MyPanel(){

        //add critter locations
        critterStartLocations.add ( new Coord(0,0) );
        critterStartLocations.add ( new Coord(0,1) );
        critterStartLocations.add ( new Coord(0,2) );
        critterStartLocations.add ( new Coord(0,3) );
        critterStartLocations.add ( new Coord(0,5) );
        //add rock locations
        rockLocations.add ( new Coord(3,3) );
        rockLocations.add ( new Coord(3,4) );
        test2();
        

    }

    public void test2(){

        ArrayList<Coord> critterStartLocations = new ArrayList<>();
        critterStartLocations.add ( new Coord(0,0) );
        critterStartLocations.add ( new Coord(0,1) );
        critterStartLocations.add ( new Coord(0,2) );
        critterStartLocations.add ( new Coord(0,3) );
        critterStartLocations.add ( new Coord(0,5) );

        world = new CritterWorld(N, T, critterStartLocations, rockLocations, escape);

    }

    public void paintComponent (Graphics g)
    {
        super.paintComponent (g);

        int h = 560/8;

        int center = h/2;


        int x = 70;
        int y = 10;

        Font f = new Font ("Serif", Font.PLAIN, 25);
        g.setFont (f);

        FontMetrics fm = g.getFontMetrics(f);

        int fontheight = fm.getHeight();


        for(int i=0; i<8; i++){
            
            y = 0;

            for(int j=0; j<8; j++){
                g.drawRect(x, y, h, h);
                g.drawString(world.grid[j][i].toString(), (x+21), (y+fontheight+21));

                y = y + h;
            }
            x += h;
        }
        
    } 


    void next(){ // one critter one step
        world.nextStep();
        this.repaint ();
    }

    void nextPlus(){ // all critters next step
        for(int i=0; i<world.critterList.size(); i++){
            world.nextStep();
        }
        this.repaint ();
    }

    void complete(){  // finish simulation

        while (world.numMoves < 300) {
            boolean isOver = world.nextStep ();
            if (isOver) {
            break;
            }
        }
        this.repaint();
    }

    void randomize(){  
    // change order of moves or order of start locations?
    Collections.shuffle(world.critterList);
    
    }

    void reset ()
    {
	// Initialize eveything again
    //this is not resetting the global variables 
    // CritterWorldGUI f = new CritterWorldGUI(); 
    test2();
    this.repaint();
    }
}



public class CritterWorldGUI extends JFrame implements ActionListener {

    // This is the JPanel on which we draw, and the one that
    // will override paintComponent()

    MyPanel worldPanel;

    public CritterWorldGUI () 
    {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e){
            e.printStackTrace();
        }

        // Set the title and other frame parameters. 
        this.setTitle ("CW GUI");
        this.setResizable (false);
        this.setSize (700, 675);
        Container cPane = this.getContentPane();

        // Set a font
        Font f = new Font ("Serif", Font.BOLD, 30);

        //Create label for title
        JLabel label = new JLabel("CritterWorld");
        label.setFont(f);

        f = new Font ("Serif", Font.PLAIN, 20);

        // Create panel to place title label
        JPanel topPanel = new JPanel ();
        //topPanel.setLayout (new FlowLayout());
        topPanel.add(label);
        topPanel.setBackground(Color.white);
        cPane.add (topPanel, BorderLayout.NORTH);

	// Create the center panel on which to draw and place in the center.
        worldPanel = new MyPanel ();
        worldPanel.setLayout(new FlowLayout());
        worldPanel.setBackground(Color.white);
        cPane.add (worldPanel, BorderLayout.CENTER);

	// This panel serves as the container for the two buttons:

         // Use a panel for clear and start buttons. 
         JPanel bottomPanel = new JPanel ();
        // bottomPanel.setLayout (new FlowLayout());
         bottomPanel.setBackground(Color.white);
         cPane.add (bottomPanel, BorderLayout.SOUTH);

 
         // Now insert the color buttons. 
         JButton resetB = new JButton ("Reset");
         resetB.setBackground (Color.green);
         resetB.addActionListener (this);
         resetB.setFont(f);
         bottomPanel.add (resetB);

         JButton randomizeB = new JButton ("Randomize");
         randomizeB.setBackground (Color.green);
         randomizeB.addActionListener (this);
         randomizeB.setFont(f);
         bottomPanel.add (randomizeB);
     
         JButton nextB = new JButton ("Next");
         nextB.setBackground (Color.green);
         nextB.addActionListener (this);
         nextB.setFont(f);
         bottomPanel.add (nextB);
 
         JButton nextPlusB = new JButton ("Next+");
         nextPlusB.setBackground (Color.green);
         nextPlusB.addActionListener (this);
         nextPlusB.setFont(f);
         bottomPanel.add (nextPlusB);
 

 
         JButton completeB = new JButton ("Complete");
         completeB.setBackground (Color.green);
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
	    worldPanel.reset ();
	}
	else if (a.getActionCommand().equals("Next")) {
	    worldPanel.next();
	}
    else if (a.getActionCommand().equals("Next+")) {
	    worldPanel.nextPlus();
	}
    else if (a.getActionCommand().equals("Complete")) {
	    worldPanel.complete();
	}
    else if (a.getActionCommand().equals("Quit")){
        System.exit (0);
    }
    else if (a.getActionCommand().equals("Randomize")){
        worldPanel.randomize();
    }
	else {
	    System.out.println ("ERROR: no such action");
	}
    }


    public static void main (String[] argv)
    {
        CritterWorldGUI f = new CritterWorldGUI();
    }


}


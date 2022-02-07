import java.awt.*;
import javax.swing.*;
import java.util.*;

import java.awt.event.*; // Needed for ActionListener. 

class MyPanel extends JPanel implements MouseListener {

    public MyPanel ()
    {
        this.setBackground (Color.white);
        // Add panel's mouse-listening to panel. 
        this.addMouseListener (this);

    }

    // These methods are required to implement  
    // the MouseListener interface. 

    public void mouseClicked (MouseEvent m)
    {

        this.repaint();
    }

    public void paintComponent (Graphics g){
        super.paintComponent(g);
       // for(int i=0; i< CritterWorldTester.world.N)
    }

    // We need to implement these methods, but 
    // don't actually have to do anything inside. 
    public void mouseEntered (MouseEvent m) {}
    public void mouseExited (MouseEvent m) {}
    public void mousePressed (MouseEvent m) {}
    public void mouseReleased (MouseEvent m) {}


}



class MyFrame extends JFrame 
    implements ActionListener {

    MyPanel boardPanel;

    // Constructor. 
    public MyFrame (int width, int height)
    {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e){
            e.printStackTrace();
        }
        
        // Set the title and other frame parameters. 
        this.setTitle ("CW GUI");
        this.setResizable (true);
        this.setSize (width, height);

        Container cPane = this.getContentPane();
        // cPane.setLayout (new BorderLayout());     

        Font f = new Font ("Papyrus", Font.BOLD, 20);

        // The panel for drawing. 
        boardPanel = new MyPanel ();
        cPane.add (boardPanel, BorderLayout.CENTER);

        JLabel label = new JLabel("CritterWorld");
        label.setFont(f);

        JPanel topPanel = new JPanel ();
        topPanel.setLayout (new FlowLayout());
        topPanel.add(label);
        topPanel.setBackground(Color.WHITE);
        cPane.add (topPanel, BorderLayout.NORTH);

        // Use a panel for clear and start buttons. 
        JPanel bottomPanel = new JPanel ();
        bottomPanel.setLayout (new FlowLayout());
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


        // Show the frame. 
        this.setVisible (true);
    }

    // This method is required to implement the  
    // ActionListener interface. 
    public void actionPerformed (ActionEvent a)
    {
        String label = a.getActionCommand();
        if (label.equalsIgnoreCase ("Quit"))
            System.exit (0);
        else if (label.equalsIgnoreCase ("Orange")){

        }
        else if (label.equalsIgnoreCase ("Green")){

        }
    }  

} 


public class CritterWorldGUI {

    public static void main (String[] argv)
    {
        
        MyFrame f = new MyFrame (700, 300);
        test1();
    }

    static void simulateSteps (int N, int T, ArrayList<Coord> startLocations, ArrayList<Coord> rockLocations, Coord escapeHatch)
    {
	CritterWorld world = new CritterWorld (N, T, startLocations, rockLocations, escapeHatch);

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

    static void estimateSteps (int N, int T, ArrayList<Coord> startLocations, ArrayList<Coord> rockLocations, Coord escapeHatch)
    {
	int numRuns = 100;
	double total = 0;
	double numSamples = 0;
	for (int n=0; n<numRuns; n++) {
	    CritterWorld world = new CritterWorld (N, T, startLocations, rockLocations, escapeHatch);
	    int t = 0;
	    while (t < T) {
		boolean isOver = world.nextStep ();
		if (isOver) {
		    break;
		}
		t = t + 1;
	    }
	    if (t < T) {
		total += t;
		numSamples ++;
	    }
	}
	System.out.println ("Number of samples: numRuns = " + numSamples + ":" + numRuns);
	System.out.println ("Average time to escape: " + (total/numSamples));
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

}





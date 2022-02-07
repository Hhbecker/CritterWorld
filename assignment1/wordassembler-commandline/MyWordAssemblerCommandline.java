import java.util.*;


// First of several classes:

class Dictionary {

    HashSet<String> scrabbleWords = new HashSet<>();

    public Dictionary ()
    {
        scrabbleWords = WordTool.getScrabbleWordsUpperCase ();
    }

    public boolean isWord (String w)
    {
        return scrabbleWords.contains(w.toUpperCase());

    }

}


class Tile {

    char letter;
    int score;

    public Tile (char c, int s)
    {
	letter = c;  score = s;
    }

    public String toString ()
    {
	return String.format ("%c(%2d)", letter, score);
    }

}


class TileBoard {

    int M=11, N=10;             // M rows, N columns
    Tile[][] letters;           // Each tile.
    ArrayList<Tile> allTiles;   // The entire scrabble tile list.
    int currentColumn = -1;     // To track the last row

    public TileBoard ()
    {
	makeScrabbleBag ();     // Sets up the score for each tile.
	generateRandom ();      // Make a random board.
    }

    public Tile getTile (int i, int j)
    {
	if ((i < 0) || (i >= M)) {
	    return null;
	}
	if ((j < 0) || (j >= N)) {
	    return null;
	}
	if ((i==M-1) && (j > currentColumn)) {
	    return null;
	}
	return letters[i][j];
    }

    void makeScrabbleBag ()
    {
	allTiles = new ArrayList<>();
	// #tiles total: how many of each, e.g. 9 "a" tiles.
	// 2:  Blank x 2. But we're not adding them because it's
	// difficult to specify the letter commandline. Instead,
	// we'll add two more s' 
	// allTiles.addAll (makeTiles(2,' ',0));
	// 68: 1 point:  e12, a9, i9, o8, n6, r6, t6, l4, s4, u4
	allTiles.addAll (makeTiles(12,'E',1));
	allTiles.addAll (makeTiles(9,'A',1));
	allTiles.addAll (makeTiles(9,'I',1));
	allTiles.addAll (makeTiles(8,'O',1));
	allTiles.addAll (makeTiles(6,'N',1));
	allTiles.addAll (makeTiles(6,'R',1));
	allTiles.addAll (makeTiles(6,'T',1));
	allTiles.addAll (makeTiles(4,'L',1));
	allTiles.addAll (makeTiles(6,'S',1));
	allTiles.addAll (makeTiles(4,'U',1));
	// 7:  2 points: d4, g3
	allTiles.addAll (makeTiles(4,'D',2));
	allTiles.addAll (makeTiles(3,'G',2));
	// 8:  3 points: b2, c2, m2, p2
	allTiles.addAll (makeTiles(2,'B',3));
	allTiles.addAll (makeTiles(2,'C',3));
	allTiles.addAll (makeTiles(2,'M',3));
	allTiles.addAll (makeTiles(2,'P',3));
	// 10: 4 points: f2, h2, v2, w2, y2
	allTiles.addAll (makeTiles(2,'F',4));
	allTiles.addAll (makeTiles(2,'H',4));
	allTiles.addAll (makeTiles(2,'V',4));
	allTiles.addAll (makeTiles(2,'W',4));
	allTiles.addAll (makeTiles(2,'Y',4));
	// 1:  5 points: k1
	allTiles.addAll (makeTiles(1,'K',5));
	// 2:  8 points: j1, x1
	allTiles.addAll (makeTiles(1,'J',8));
	allTiles.addAll (makeTiles(1,'X',8));
	// 2:  10 points: q1, z1
	allTiles.addAll (makeTiles(1,'Q',10));
	allTiles.addAll (makeTiles(1,'Z',10));
	// Total # tiles: 100

	// Check size:
	if (allTiles.size() != 100) {
	    System.out.println ("ERROR: # tiles=" + allTiles.size());
	    System.exit (0);
	}
	// Check all letters present:
	HashSet<Character> lettersInTiles = new HashSet<>();
	for (Tile t: allTiles) {
	    lettersInTiles.add (t.letter);
	}
	for (char c='A'; c<='Z'; c++) {
	    if (! lettersInTiles.contains(c) ) {
		System.out.println ("ERROR: c=" + c + " not in list");
	    }
	}

    }

    ArrayList<Tile> makeTiles (int numTiles, char letter, int score)
    {
	ArrayList<Tile> tiles = new ArrayList<>();
	for (int k=0; k<numTiles; k++) {
	    tiles.add (new Tile (letter,score));
	}
	return tiles;
    }
    
    void shuffle ()
    {
	Random rand = new Random ();
	for (int i=0; i<allTiles.size(); i++) {
	    // Pick random# between i and size-1
	    int k = i + rand.nextInt (allTiles.size()-i);
	    // Swap
	    Tile t = allTiles.get (i);
	    Tile t2 = allTiles.get (k);
	    allTiles.set (i, t2);
	    allTiles.set (k, t);
	}
    }

    void generateRandom ()
    {
	shuffle ();
	letters = new Tile [M][N];
	int count = 0;
	for (int i=0; i<M-1; i++) {
	    for (int j=0; j<N; j++) {
		letters[i][j] = allTiles.get (count++);
	    }
	}
	currentColumn = -1;
    }


    public String toString ()
    {
	String s = "\nTileBoard:\n";
	for (int i=0; i<M-1; i++) {
	    for (int j=0; j<N; j++) {
		if (letters[i][j] == null) {
		    s += "       ";
		}
		else {
		    s += "  " + letters[i][j];
		}
	    }
	    s += "\n";
	}
	// Now the column numbers
	for (int j=0; j<N; j++) {
	    s += "   " + String.format("%2d",j) + "  ";
	}
	// Last row: the played tiles
	s += "\n";
	for (int j=0; j<=currentColumn; j++) {
	    s += "  " + letters[M-1][j];
	}
	s += "\n";
	return s;
    }

    public boolean popColumn (int k)
    {
	// INSERT YOUR CODE HERE. The user has selected column k.
	// Check if k is a valid column
	// Pull out and print the letter in row M-2 column k
	// Put that letter in the lowest row (M-1) in the next available column ********
	// Shift column k downwards
	// If there's a problem, return false, otherwise return true.

	// I think the board is split so the word you build is an arrayList and not in the 2d array

        if(k<0){
			return false;
		}
        if(k>letters[0].length-1){
			return false;
		}

		System.out.println("Before increment = " + currentColumn);
        currentColumn++;
		System.out.println("After increment = " + currentColumn);

        letters[M-1][currentColumn] = letters[M-2][k];
        
        for(int i=letters.length-2; i>=0; i--){
            if(i==0){
                letters[i][k] = null;
            }
			else {
                letters[i][k] = letters[i-1][k];
			}
		}

		return true;
    

    }

    public String getLastRow ()
    {
	String w = "";
	for (int j=0; j<=currentColumn; j++) {
	    w += letters[M-1][j].letter;
	}
	return w;
    }

    public int scoreLastRow ()
    {
	int score = 0;
	for (int j=0; j<=currentColumn; j++) {
	    score += letters[M-1][j].score;
	}
	return score;
    }

    public void clearLastRow ()
    {
	currentColumn = -1;
    }

}

public class MyWordAssemblerCommandline {

    public static void main (String[] argv)
    {
	TileBoard board = new TileBoard ();
	Dictionary dict = new Dictionary ();
	int score = 0;

	while (true) {

	    System.out.println (board);
	    System.out.println ("Current score: " + score);

	    int col = IOTool.readIntFromTerminal ("Enter column (-1 to make-word) >> ");
	    if (col < 0) {
		// See if this is a legal word. If so, get its score and add.
		String w = board.getLastRow ();
		if (! dict.isWord(w)) {
		    System.out.println ("Illegal word: " + w);
		    System.exit (0);
		}
		score += board.scoreLastRow ();
		board.clearLastRow ();
	    }
	    else if (! board.popColumn(col) ) {
		// If pop did not occur.
		System.out.println (" >> Illegal column: " + col);
		System.exit (0);
	    }
	}
    }

}

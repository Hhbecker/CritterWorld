import java.util.*;

public class CritterWorld {

    int N, T;
    Coord escape;
    ArrayList<Coord> rockCoords;
    ArrayList<Coord> cCoords;
    ArrayList<Critter> critterList;
    ArrayList<Cell> possibleMoves;
    int nextCritter = 0;
    Cell [][] grid;
    int numMoves;

    public CritterWorld (int N, int T, ArrayList<Coord> critterStartCoords, ArrayList<Coord> rockCoords, Coord escape)
    {
	this.N = N;   
    this.T = T;
	this.escape = escape;
	this.rockCoords = rockCoords;
    this.cCoords = critterStartCoords;

    Critter.IDCount = 1;

	//  ... Build the 2D grid ...
        creatGrid();
        addCritters();
        addRockAndEscape();
    }

    // Return true if over.

    public boolean nextStep ()
    {
	// ... this is where you need the critterworld logic of moves,
	// state changes, neighboring cells etc ...

    // TO DO
    // 1. create randomize and reset buttons 
    // 2. play with fonts and colors
    // 3. comment and organize everything
    // CHANGE ASSIGNMENT ONE VERSION MOVE LOGIC
    // submit

        if(critterList.size() <= nextCritter){
            nextCritter = 0;
        }

        //move next critter
        moveCritter(critterList.get(nextCritter));
        // increment nextCritter index 
        nextCritter++;

        numMoves++;

        // if all critters have escaped return true
        if(critterList.size() == 0){
            return true;
        }
        else{
            return false;
        }

    }

    public void moveCritter(Critter c){

        //  first get critter 1 to move randomly in available cells only and update cell status and critter location
        // then get critters to recognize escape hatch and dissapear 
        // then work on gui 
        // if I feel like adding more I can later 

        // 1. Check critter type
        //    -if type = S normalMove(c)
        //    -if type = F leaderMove(c)
        //    -if type = L followerMove(c)

        if(c.getType().equals("S")){
            normalMove(c);
        }
        else if(c.getType().equals("L")){
            leaderMove(c);
        }
        else if(c.getType().equals("F")){
            followerMove(c);
        }

    }

    public void normalMove(Critter c){
        // check for escape hatch neighbor
    
        if(c.getCoord().isNeighbor(escape)){
            //if(critterList) if there are critters of type s become leader
            //if there are no cirtters of type s become follower 
            c.setType("L");
            grid[c.getCoord().getX()][c.getCoord().getY()].setStatus(c.unique);
            leaderMove(c);
            return;
        }

        for(int i=0; i<critterList.size(); i++){
            if(critterList.get(i).getType().equals("L")){
                if(c.getCoord().isNeighbor(critterList.get(i).getCoord())){
                    c.setType("F");
                    grid[c.getCoord().getX()][c.getCoord().getY()].setStatus(c.unique);
                    followerMove(c);
                    return;
                 }
            }
        }    

        // for list of critters if neighbor is true 
        // if type is F this
        // if type is L this

        //make array of 8 beighboring cells based on current location
        getMoves(c);

        int nextMove = RandTool.uniform(0, possibleMoves.size()-1);
        //set old grid status to null
        grid[c.getCoord().getX()][c.getCoord().getY()].setStatus("empty");
        //set critter coord to new location 
        c.setCoord(possibleMoves.get(nextMove).getCoord());
        //set new critter location grid status to 2
        grid[c.getCoord().getX()][c.getCoord().getY()].setStatus(c.unique);
        
  
        //iterate through neghbors
        // if status is -2 get cirtter and critter type then remove from list 
    
        //choose random index
        //update coord in critter object
        //update grid critter location
        //update grid status for old and new critter location

        //check for neighbors that might move away 
        for(int i=0; i<critterList.size(); i++){
            if(critterList.get(i).getType().equals("L")){
                if(c.getCoord().isNeighbor(critterList.get(i).getCoord())){
                    c.setType("F");
                    grid[c.getCoord().getX()][c.getCoord().getY()].setStatus(c.unique);
                    return;
                 }
            }
        }  

    }

    public void followerMove(Critter c){
        // if escape hatch is neighbor dissapear 
        // if not, move toward escape hatch 
        if(c.getCoord().isNeighbor(escape)){
            //if(critterList) if there are critters of type s become leader
            //if there are no cirtters of type s become follower 
            critterList.remove(c);
            grid[c.getCoord().getX()][c.getCoord().getY()].setStatus("empty");
            return;
        }

        //make array of 8 neighboring cells based on current location
        getMoves(c);

        Coord bestMove = c.moveCloserTo(escape);
        if(possibleMoves.contains(grid[bestMove.getX()][bestMove.getY()])){
            //set old grid status to null
            grid[c.getCoord().getX()][c.getCoord().getY()].setStatus("empty");
            //set critter coord to new location 
            c.setCoord(bestMove);
            //set new critter location grid status to 2
            grid[bestMove.getX()][bestMove.getY()].setStatus(c.unique);

        }

        else{ 
            int nextMove = RandTool.uniform(0, possibleMoves.size()-1);
            //set old grid status to null
            grid[c.getCoord().getX()][c.getCoord().getY()].setStatus("empty");
            //set critter coord to new location 
            c.setCoord(possibleMoves.get(nextMove).getCoord());
            //set new critter location grid status to 2
            grid[c.getCoord().getX()][c.getCoord().getY()].setStatus(c.unique);
        }
        
        // check again for critter or escape hatch neighbors
    }

    public void leaderMove(Critter c){
        //if number of critters of type s is not zero move towards closest type s critter
        //if no more type s critters move towards escape hatch

        for(int i=0; i<critterList.size(); i++){
            if(critterList.get(i).getType().equals("S")){

                getMoves(c);

                Coord bestMove = c.moveCloserTo(critterList.get(i).getCoord());
                if(possibleMoves.contains(grid[bestMove.getX()][bestMove.getY()])){
                    //set old grid status to null
                    grid[c.getCoord().getX()][c.getCoord().getY()].setStatus("empty");
                    //set critter coord to new location 
                    c.setCoord(bestMove);
                    //set new critter location grid status to 2
                    grid[bestMove.getX()][bestMove.getY()].setStatus(c.unique);
                    return;
        
                }
                else {
                    int nextMove = RandTool.uniform(0, possibleMoves.size()-1);
                    //set old grid status to null
                    grid[c.getCoord().getX()][c.getCoord().getY()].setStatus("empty");
                    //set critter coord to new location 
                    c.setCoord(possibleMoves.get(nextMove).getCoord());
                    //set new critter location grid status to 2
                    grid[c.getCoord().getX()][c.getCoord().getY()].setStatus(c.unique);
                    return;
                }
            }
        } 
        // if you get to here there are no critters of type S in critterList so change Leader status to Follower
        c.setType("F");
        grid[c.getCoord().getX()][c.getCoord().getY()].setStatus(c.unique);
        followerMove(c);
        return;
    }

    public void getMoves(Critter c){
        // for every possible combo of I and J test wether its a validCoord
        // if it is add it to neighbors
        possibleMoves = new ArrayList<Cell>();

        // check if the moves are valid 

        // repeat for all 8 neighbors
        if(validCoord(c.getCoord().getX()-1, c.getCoord().getY()-1)){
            possibleMoves.add(grid[c.getCoord().getX()-1][c.getCoord().getY()-1]);
        }
        if(validCoord(c.getCoord().getX(), c.getCoord().getY()-1)){
            possibleMoves.add(grid[c.getCoord().getX()][c.getCoord().getY()-1]);
        }
        if(validCoord(c.getCoord().getX()+1, c.getCoord().getY()-1)){
            possibleMoves.add(grid[c.getCoord().getX()+1][c.getCoord().getY()-1]);
        }
        if(validCoord(c.getCoord().getX()-1, c.getCoord().getY())){
            possibleMoves.add(grid[c.getCoord().getX()-1][c.getCoord().getY()]);
        }
        if(validCoord(c.getCoord().getX()+1, c.getCoord().getY())){
            possibleMoves.add(grid[c.getCoord().getX()+1][c.getCoord().getY()]);
        }
        if(validCoord(c.getCoord().getX()-1, c.getCoord().getY()+1)){
            possibleMoves.add(grid[c.getCoord().getX()-1][c.getCoord().getY()+1]);
        }
        if(validCoord(c.getCoord().getX(), c.getCoord().getY()+1)){
            possibleMoves.add(grid[c.getCoord().getX()][c.getCoord().getY()+1]);
        }
        if(validCoord(c.getCoord().getX()+1, c.getCoord().getY()+1)){
            possibleMoves.add(grid[c.getCoord().getX()+1][c.getCoord().getY()+1]);
        }
        
    }

    public boolean validCoord(int x, int y){
        if( (x < 0 || x > N-1) ){
            return false;
        }
        if( (y < 0 || y > N-1) ){
            return false;
        }
        if(!(grid[x][y].getStatus().equals("empty"))){
            return false;
        }
        return true;
    }

    public void addCritters(){
        
        critterList = new ArrayList<Critter> (); 

        for(int i=0; i<cCoords.size(); i++){
            //make array of critter objects
            critterList.add(new Critter(cCoords.get(i)));

            // set the grid coord status to a critter for each critter coord in critterStartCoord array
            grid[cCoords.get(i).getX()][cCoords.get(i).getY()].setStatus(critterList.get(i).unique);

        }
    }

    public void creatGrid(){
        grid = new Cell[N][N];

        for(int i=0; i< N; i++){
            for(int j=0; j<N; j++){
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    public void addRockAndEscape(){
        for(int i=0; i<rockCoords.size(); i++){
            // set the grid coord status to a critter for each critter coord in critterStartCoord array
            grid[rockCoords.get(i).getX()][rockCoords.get(i).getY()].setStatus("X");
        }

        grid[escape.getX()][escape.getY()].setStatus("H");

    }


    public String toString ()
    {
	    String s = "----------------- CritterWorld ----------------\n";

	// ... Write the code to build the 2D output here ...

        for (int i=0; i<N; i++) {
	        for (int j=0; j<N; j++) {
                s += "  " + grid[i][j].toString();  
            }
            s += "\n";
        }


	return s;
    }

    public void printStats ()
    {
	// ... Print the list of critters that escaped ...
	System.out.println ("Escaped critters: " + (cCoords.size() - critterList.size()));

	// ... Print the list of critters that escaped ...
    }

}

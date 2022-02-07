
public class Coord {
    
    int row = -1;
    int col = -1;

    public Coord (int row, int col)
    {
	this.row = row;
	this.col = col;
    }

    public String toString ()
    {
	return "Coords:[" + row + "," + col + "]";
    }

    public int getX(){
        return row;
    }

    public int getY(){
        return col;
    }

    public boolean equals (Object obj) 
    {
	// ... this will be a useful method to write ...
    return false;
    }

    public boolean isNeighbor (Coord c)
    {
	// ... this will be a useful method to write ...

    if(c.getX() == row && c.getY() == col +1 || c.getX() == row && c.getY() == col -1){
        return true;
    }
    else if (c.getX() == row + 1 && c.getY() == col +1 || c.getX() == row +1 && c.getY() == col -1) {
        return true;
    }
    else if (c.getX() == row -1 && c.getY() == col +1 || c.getX() == row -1 && c.getY() == col -1) {
        return true;
    }
    else if (c.getX() == row -1 && c.getY() == col || c.getX() == row +1 && c.getY() == col) {
        return true;
    }
    else{ 
        return false;
    }
    }

}

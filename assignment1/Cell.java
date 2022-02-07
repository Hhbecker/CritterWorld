
public class Cell {

    // Store in here: the coordinates of this cell.

    Coord coords;

    // variable to store what the cell currently has:
    // a rock, the hatch, a critter, or is it currently unoccupied?
    // null = unnocupied 
    // escape hatch = status of H
    //rock = status of X 
    //critter = unique
    String status = "empty";

    // constructor to take the coordinates (row, column)
    public Cell (int row, int col){

        coords = new Coord(row, col);
        
    }

    // Method to update the status.
    public void setStatus(String newStatus){
        status = newStatus;
    }

    public String getStatus(){
        return status;
    }

    public Coord getCoord (){

        return coords;
    }

    // Define toString() to output a '-' for empty, 'H' for hatch,
    // 'X' for a rock, and the critter ID and status, as the 
    // sample output shows.
    public String toString(){
        if(status.equals("empty")){
            return " - ";
        }
        else if (status.equals("H")){
            return " H ";
        }
        else if (status.equals("X")){
            return " X ";
        }

        else {
            return status;
        }
        
    }


 
}

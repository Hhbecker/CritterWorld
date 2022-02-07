import java.util.*;

public class Critter {

    // Some variables already defined for you.
    static int IDCount = 1;
    int ID;
    Coord location;
    String type = "S";
    String unique;

    public Critter (Coord location )
    {
	this.ID = IDCount++;
	this.location = location;
    unique = type + ID;
    //unique = type + " " + ID;
    }

    public String getType(){
        return type;
    }

    public Coord getCoord(){
        return location;
    }

    public void setCoord(Coord c){
        location = c;
    }

    public void setType(String s){
        type = s;
        unique = type + " " + ID;
    }

    public Coord moveCloserTo(Coord c){
        // instead figure out wether x destination is greter than, equal to, or less than current location
        // figure out wether y destination is greater than, equal to, or less than current location
        // add subtract or keep x and y the same depending 

        int x = location.getX();
        int y = location.getY();

        int newX = 0;
        int newY = 0;

        if(c.getX() > x){
            newX = x+1;
        }
        else if(c.getX() == x){
            newX = x;
        }
        else{
            newX = x-1;
        }

        // Now find newY
        if(c.getY() > y){
            newY = y+1;
        }
        else if(c.getY() == y){
            newY = y;
        }
        else{
            newY = y-1;
        }

        Coord best = new Coord(newX, newY);
        return best;
    }


    // Define other variables and methods as you see fit.

    public String toString(){
        return unique;

    }

}

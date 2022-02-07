
import java.util.*;

public class RandomGenerator {

    private static Random rand = new Random ();

    public static double random ()
    {
	double r = rand.nextDouble ();
	return r;
	// Shorter: return rand.nextDouble();
    }

    public static int random (int a, int b)
    {
	return a + rand.nextInt (b-a+1);
    }

    public static double random (double a, double b)
    {
	// WRITE YOUR CODE HERE to use the output from random(),
	// which returns a number between 0 and 1, to pick number
	// between a and b (by scaling appropriately).
    double real = random();
    int valA = (int) a;
    int valB = (int) b;

    return real + random(valA,valB);
    }

    public static int random (int[] A)
    {
	// WRITE YOUR CODE HERE to pick a random element from A 
	// and return that element.
    int ind = random(0,A.length-1);
    return A[ind];
    }

    public static String random (String[] A)
    {
	// WRITE YOUR CODE HERE to pick a random element from A 
	// and return that element.    
    int ind = random(0,A.length-1);
    return A[ind];
    }

}

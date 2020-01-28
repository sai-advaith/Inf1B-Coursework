/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions to check the state of the game
 * board and validate board coordinates and figure positions.
 */
import java.util.*;
import java.lang.*; 
public class FoxHoundUtils {

    // ATTENTION: Changing the following given constants can 
    // negatively affect the outcome of the auto grading!

    /** Default dimension of the game board in case none is specified. */
    public static final int DEFAULT_DIM = 8;
    /** Minimum possible dimension of the game board. */
    public static final int MIN_DIM = 4;
    /** Maximum possible dimension of the game board. */
    public static final int MAX_DIM = 26;

    /** Symbol to represent a hound figure. */
    public static final char HOUND_FIELD = 'H';
    /** Symbol to represent the fox figure. */
    public static final char FOX_FIELD = 'F';
    // HINT Write your own constants here to improve code readability ...
    public static final char EMPTY_FIELD = '.';
    public static final int DEFAULT_FOX = 1;
    public static final int DEFAULT_HOUND = 4;
    public static String[] initialisePositions(int dimension) {
        if (dimension < MIN_DIM || dimension > MAX_DIM)
        {
            throw new IllegalDimensionArgument("the dimension is not valid");
        }
        else{
            int hounds = dimension/2;
            int fox= DEFAULT_FOX;
            String positions[] = new String[hounds+fox];
            int j = 0;
            for(int i = 1;i<dimension;i++)
            { // Hounds implementation
                if (i%2 == 1)
                {
                    positions[j] = String.valueOf((char)(65+i)) + "1" ;
                    j++;
                }
            }
            if (dimension%2 == 0)
                positions[positions.length-1] = String.valueOf((char)(65 + ((dimension/2)))) + String.valueOf(dimension);
            else
            {
                if (((1+dimension)/2) % 2 == 1 )
                    positions[positions.length-1] = String.valueOf((char)(65 + ((1 + dimension)/2))) + String.valueOf(dimension);
                else
                    positions[positions.length-1] = String.valueOf((char)(65 + ((dimension/2)))) + String.valueOf(dimension);
    
            }
            return positions;
        }
    
    }
    public static boolean isValidMove(int dim, String[] players, char figure,String origin,String destination)
    {
        
    }
}

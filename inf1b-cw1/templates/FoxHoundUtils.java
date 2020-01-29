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
            if (dimension <= MIN_DIM || dimension >= MAX_DIM)
                throw new IllegalArgumentException("not possible");
            int hounds = (int)Math.floor(dimension/2);
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
    public static boolean isValidMove(int dim, String[] players, char figure,String origin,String destination)
    {
        boolean hound_validity = true;
        boolean fox_validity = true;
        if (players == null)
            throw new NullPointerException("null array");
        if (figure != 'H' && figure != 'F')
            throw new IllegalArgumentException("not valid");
        if (Character.isLetter(origin.charAt(0)) && Character.isLetter(destination.charAt(0))  && Character.isDigit(destination.charAt(1)) && Character.isDigit(destination.charAt(1)))
        {
            int origin_row = (int)(origin.charAt(1)) - 49;
            int origin_column = (int)(origin.charAt(0)) - 65;
            int destination_row = (int)(destination.charAt(1)) - 49;
            int destination_column = (int)(destination.charAt(0)) - 65;
            switch(figure)
            {
                case 'H':
                    hound_validity = hound_validity && (FoxHoundUtils.occupancy(players,destination));
                    hound_validity = hound_validity && (origin_row == destination_row - 1);
                    if (origin_column == (dim - 1))
                        hound_validity = hound_validity && (destination_row == (origin_column - 1)); 
                    else if (origin_column == 0)
                        hound_validity = hound_validity && (destination_row == origin_column + 1);
                    else 
                    {
                        boolean test = (destination_column == (origin_column + 1)) || (destination_column == origin_column - 1);
                        hound_validity = hound_validity && test;
                    }
                    return hound_validity;
                case 'F':
                    fox_validity = fox_validity && (FoxHoundUtils.occupancy(players,destination));
                    if(origin_row == (dim - 1))
                    {
                        fox_validity = fox_validity && (destination_row == (origin_row - 1));
                        fox_validity = fox_validity && ((destination_column == (origin_column + 1)) || (destination_column == (origin_column - 1)));
                    }
                    else
                    {
                        fox_validity = fox_validity && ((destination_row == (origin_row + 1)) || (destination_row == (origin_row - 1)));
                        if (origin_column == (dim - 1))
                            fox_validity = fox_validity && (destination_column == (origin_column - 1));
                        else if(origin_column == 0)
                            fox_validity = fox_validity && (destination_column == (origin_column + 1));
                        else
                            fox_validity = fox_validity && ((destination_column == (origin_column + 1)) || (destination_column == (origin_column - 1)));            
                    }
                    return fox_validity;
                default:
                    return false;
            }
        }
        else
            return false;
    }
    public static boolean occupancy(String[] players, String destination)
    {
        boolean g = true;
        for(int i = 0;i<players.length;i++)
        {
            if (players[i].equals(destination))
            {
                g = false;
                break;
            }
        }
        return g;
    }
}

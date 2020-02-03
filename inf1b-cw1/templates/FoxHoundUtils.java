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
    public static String[] initialisePositions(int dimension) throws IllegalArgumentException{
            if (dimension < MIN_DIM || dimension > MAX_DIM)
                throw new IllegalArgumentException("not possible"); // handling the case where the dimension is above the max and less than the min
            int hounds = (int)Math.floor(dimension/2); // number of hounds based on dimension of the board.
            int fox= DEFAULT_FOX; // fox is always 1
            String positions[] = new String[hounds+fox]; //  creating a string array to store the positions.
            int j = 0; // index of the positions array
            for(int i = 1;i<dimension;i++)
            { // Hounds implementation
                if (i%2 == 1)
                {
                    positions[j] = String.valueOf((char)(65+i)) + "1" ; // hounnds always in the first position
                    j++;
                }
            }
            // fox positioning based on wether dimension is odd or even
            if (dimension%2 == 0)
                positions[positions.length-1] = String.valueOf((char)(65 + ((dimension/2)))) + String.valueOf(dimension); // if even dimension 
            else
            {
                if (((1+dimension)/2) % 2 == 1 )
                    positions[positions.length-1] = String.valueOf((char)(65 + ((1 + dimension)/2))) + String.valueOf(dimension);
                else
                    positions[positions.length-1] = String.valueOf((char)(65 + ((dimension/2)))) + String.valueOf(dimension);
    
            }
            return positions; // returning string array for initial position.
    }
    public static boolean isValidMove(int dim, String[] players, char figure,String origin,String destination)
    {
        boolean hound_validity = true; // figure is a hound then we will examine the validity of its positions 
        boolean fox_validity = true; // figure is a fox then we will use this varaible to examine its validity.
        if (players == null)
            throw new NullPointerException(); // null pointer exception if players array is null
        if (figure != HOUND_FIELD && figure != FOX_FIELD)
            throw new IllegalArgumentException(); // if the figure is neither fox nor hound, illegalargumentexception
        if (Character.isLetter(origin.charAt(0)) && Character.isLetter(destination.charAt(0))  && Character.isDigit(destination.charAt(1)) && Character.isDigit(destination.charAt(1)))
        {
            int origin_row = (int)(origin.charAt(1)) - 49; // row of origin
            int origin_column = (int)(origin.charAt(0)) - 65; // column of origin
            int destination_row = (int)(destination.charAt(1)) - 49; // row of destination
            int destination_column = (int)(destination.charAt(0)) - 65; // column of destination
            // System.out.println(origin_row+" "+origin_column+" "+destination_row+" "+destination_column);
            switch(figure)
            {
                case HOUND_FIELD:
                    // taking several cases of validity into consideration
                    hound_validity = hound_validity && !(origin.equals(players[players.length-1]));
                    hound_validity = hound_validity && (FoxHoundUtils.occupancy(players,destination));
                    hound_validity = hound_validity && (origin_row == destination_row - 1);
                    if (origin_column == (dim - 1))
                        hound_validity = hound_validity && (destination_column == (origin_column - 1)); 
                    else if (origin_column == 0)
                        hound_validity = hound_validity && (destination_column == origin_column + 1);
                    else 
                    {
                        boolean test = (destination_column == (origin_column + 1)) || (destination_column == origin_column - 1);
                        hound_validity = hound_validity && test;
                    }
                    return hound_validity;
                case FOX_FIELD:
                    // taking several cases of validity into consideration
                    fox_validity = fox_validity && origin.equals(players[players.length-1]);
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
                    return false; // if neither, then false
            }
        }
        else
            return false;
    }
    public static boolean occupancy(String[] players, String destination)
    { // checking if destination is already occipied using linear search
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

    public static boolean isFoxWin(String fox_pos)
    { // checking fox win by examining the row number of the fox
        return FoxHoundUI.row(fox_pos) == 0;
    }
    public static boolean isHoundWin(String[] players,int dimension) throws IllegalArgumentException
    { // checking for hound win by examining all the cases if the scenario is valid or not
            if (dimension >= MIN_DIM && dimension <= MAX_DIM) { 
                int fr = FoxHoundUI.row(players[players.length-1]); // row of fox
                int fc = FoxHoundUI.column(players[players.length-1]); // column of fox
                char board[][] = FoxHoundUI.boardArray(players,dimension); // current board alignment
                // based on different positions of the fox, we will check on the diagonal elements to see if its surrounded by hounds or not
                if(fc == 0 && fr != dimension - 1)
                    return (board[fr+1][fc+1] == 'H' && board[fr-1][fc+1] == 'H'); 

                else if (fc == dimension - 1 && fr != dimension - 1)
                    return (board[fr+1][fc-1] == 'H' && board[fr-1][fc+1] == 'H');

                else if(fr == dimension-1 && (fc != 0 && fc != dimension - 1))
                    return (board[fr-1][fc-1] == 'H' && board[fr-1][fc+1] == 'H');

                else if (fr == dimension-1 && fc == 0)
                    return (board[fr-1][fc+1] == 'H');

                else if (fr == dimension - 1 && fc == dimension - 1)
                    return (board[fr-1][fc-1] == 'H');

                else
                    return (board[fr+1][fc+1] == 'H' && board[fr+1][fc-1] == 'H' && board[fr-1][fc+1] == 'H' && board[fr-1][fc-1] == 'H');
            }
            else
                throw new IllegalArgumentException(); // exception thrown if the dimension is invalid.
    }
}

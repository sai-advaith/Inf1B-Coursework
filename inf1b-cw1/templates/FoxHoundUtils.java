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
    /** Symbol to represent an empty field*/
    public static final char EMPTY_FIELD = '.';
    /** default number of fox in the game */
    public static final int DEFAULT_FOX = 1;
    /** default number of hounds in the game */
    public static final int DEFAULT_HOUND = 4;

    /**
     *
     * @param dimension dimension of the board
     * @return a string array with the inital positions of the fox and hounds
     */
    public static String[] initialisePositions(int dimension) {
            if (dimension < MIN_DIM || dimension > MAX_DIM) {
                throw new IllegalArgumentException();
            }
            int hounds = (int)Math.floor(dimension/2);
            int fox= DEFAULT_FOX;
            String positions[] = new String[hounds+fox];
            int j = 0;
            for(int i = 1;i<dimension;i++) { // Hounds implementation
                if (i%2 == 1) {
                    positions[j] = String.valueOf((char)(65+i)) + "1" ;
                    j++;
                }
            }
            if (dimension%2 == 0) {
                positions[positions.length-1] = String.valueOf((char)(65 + ((dimension/2)))) + String.valueOf(dimension);
            }
            else {
                if (((1+dimension)/2) % 2 == 1 ) {
                    positions[positions.length - 1] = String.valueOf((char) (65 + ((1 + dimension) / 2))) + String.valueOf(dimension);
                }
                else {
                    positions[positions.length - 1] = String.valueOf((char) (65 + ((dimension / 2)))) + String.valueOf(dimension);
                }
            }
            return positions;
    }

    /**
     *
     * @param dim dimension of the board
     * @param players string array containing the positions of fox and hounds
     * @param figure character representing either fox or hound
     * @param origin string representing the origin of a piece
     * @param destination string representing the position to which the piece must be moved
     * @return checking if the move is valid
     */
    public static boolean isValidMove(int dim, String[] players, char figure,String origin,String destination) {
        boolean hound_validity = true;
        boolean fox_validity = true;
        if (players == null) {
            throw new NullPointerException();
        }
        if (figure != HOUND_FIELD && figure != FOX_FIELD) {
            throw new IllegalArgumentException();
        }
        if (Character.isLetter(origin.charAt(0)) && Character.isLetter(destination.charAt(0))  && Character.isDigit(destination.charAt(1)) && Character.isDigit(destination.charAt(1))) {
            int origin_row = (int)(origin.charAt(1)) - 49;
            int origin_column = (int)(origin.charAt(0)) - 65;
            int destination_row = (int)(destination.charAt(1)) - 49;
            int destination_column = (int)(destination.charAt(0)) - 65;
            // System.out.println(origin_row+" "+origin_column+" "+destination_row+" "+destination_column);
            switch(figure) {
                case HOUND_FIELD: {
                    hound_validity = hound_validity && !(origin.equals(players[players.length - 1]));
                    hound_validity = hound_validity && (FoxHoundUtils.isOccupied(players, destination));
                    hound_validity = hound_validity && (origin_row == destination_row - 1);
                    if (origin_column == (dim - 1)) {
                        hound_validity = hound_validity && (destination_column == (origin_column - 1));
                    } else if (origin_column == 0) {
                        hound_validity = hound_validity && (destination_column == origin_column + 1);
                    } else {
                        boolean test = (destination_column == (origin_column + 1)) || (destination_column == origin_column - 1);
                        hound_validity = hound_validity && test;
                    }
                    return hound_validity;
                }
                case FOX_FIELD: {
                    fox_validity = fox_validity && origin.equals(players[players.length - 1]);
                    fox_validity = fox_validity && (FoxHoundUtils.isOccupied(players, destination));
                    if (origin_row == (dim - 1)) {
                        fox_validity = fox_validity && (destination_row == (origin_row - 1));
                        fox_validity = fox_validity && ((destination_column == (origin_column + 1)) || (destination_column == (origin_column - 1)));
                    } else {
                        fox_validity = fox_validity && ((destination_row == (origin_row + 1)) || (destination_row == (origin_row - 1)));
                        if (origin_column == (dim - 1)) {
                            fox_validity = fox_validity && (destination_column == (origin_column - 1));
                        }
                        else if (origin_column == 0) {
                            fox_validity = fox_validity && (destination_column == (origin_column + 1));
                        }
                        else {
                            fox_validity = fox_validity && ((destination_column == (origin_column + 1)) || (destination_column == (origin_column - 1)));
                        }
                    }
                    return fox_validity;
                }
                default: {
                    return false;
                }
            }
        }
        else {
            return false;
        }
    }

    /**
     *
     * @param players containing the positions of the fox and hounds
     * @param destination the position to which the hound or fox will be moved
     * @return if the object is already occupied
     */
    public static boolean isOccupied(String[] players, String destination) {
        boolean g = true;
        for(int i = 0;i<players.length;i++) {
            if (players[i].equals(destination)) {
                g = false;
                break;
            }
        }
        return g;
    }

    /**
     *
     * @param fox_pos position of the fox in the board
     * @return if the fox has won the game
     */
    public static boolean isFoxWin(String fox_pos)
    {
        return FoxHoundUI.getRow(fox_pos) == 0;
    }

    /**
     *
     * @param players positions of fox and hounds in the board
     * @param dimension dimension of the board
     * @return if the hound has won
     * @throws IllegalArgumentException if the dimension is more than the maximum dimension or less than minimum dimension
     */
    public static boolean isHoundWin(String[] players,int dimension) throws IllegalArgumentException {
            if (dimension >= MIN_DIM && dimension <= MAX_DIM) {
                int fr = FoxHoundUI.getRow(players[players.length-1]);
                int fc = FoxHoundUI.getColumn(players[players.length-1]);
                char board[][] = FoxHoundUI.boardArray(players,dimension);
                if(fc == 0 && fr != dimension - 1) {
                    return (board[fr + 1][fc + 1] == 'H' && board[fr - 1][fc + 1] == 'H');
                }
                else if (fc == dimension - 1 && fr != dimension - 1) {
                    return (board[fr + 1][fc - 1] == 'H' && board[fr - 1][fc + 1] == 'H');
                }
                else if(fr == dimension-1 && (fc != 0 && fc != dimension - 1)) {
                    return (board[fr - 1][fc - 1] == 'H' && board[fr - 1][fc + 1] == 'H');
                }
                else if (fr == dimension-1 && fc == 0) {
                    return (board[fr - 1][fc + 1] == 'H');
                }
                else if (fr == dimension - 1 && fc == dimension - 1) {
                    return (board[fr - 1][fc - 1] == 'H');
                }
                else {
                    return (board[fr + 1][fc + 1] == 'H' && board[fr + 1][fc - 1] == 'H' && board[fr - 1][fc + 1] == 'H' && board[fr - 1][fc - 1] == 'H');
                }
            }
            else {
                throw new IllegalArgumentException();
            }
    }
}

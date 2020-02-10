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
                throw new IllegalArgumentException(); }
            int hounds = (int)Math.floor(dimension/2);
            int fox= DEFAULT_FOX;
            StringBuilder s = new StringBuilder();
            String positions[] = new String[hounds+fox];
            int j = 0;

            for(int i = 1;i<dimension;i++) { // Hounds implementation
                if (i%2 == 1) {
                    positions[j] = String.valueOf((char)(65+i)) + "1" ;
                    j++; } }

            if (dimension%2 == 0) {
                s.append(String.valueOf((char)(65 + ((dimension/2)))));
                s.append(String.valueOf(dimension));
                positions[positions.length-1] = s.toString(); }

            else {
                if (((1+dimension)/2) % 2 == 1 ) {
                    s.append(String.valueOf((char) (65 + ((1 + dimension) / 2))));
                    s.append(String.valueOf(dimension));
                    positions[positions.length - 1] = s.toString(); }
                else {
                    s.append(String.valueOf((char) (65 + ((dimension / 2)))));
                    s.append(String.valueOf(dimension));
                    positions[positions.length - 1] = s.toString(); }
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
        if (players == null) { throw new NullPointerException(); }
        if (figure != HOUND_FIELD && figure != FOX_FIELD) { throw new IllegalArgumentException(); }
        if (FoxHoundIO.isValidPosition(origin) && FoxHoundIO.isValidPosition(destination)) {
            switch(figure) {
                case HOUND_FIELD: { return isHoundValid(dim, players,'H',origin,destination); }
                case FOX_FIELD: { return isFoxValid(dim, players, 'F', origin, destination); }
                default: { return false; }
            }
        }
        else {
            return false;
        }

    }


    private static boolean isHoundValid(int dim, String[] players, char figure, String origin, String destination) {
        boolean hound = true;
        int or = FoxHoundUI.getRow(origin); //row of origin
        int oc = FoxHoundUI.getColumn(origin); // column of origin
        int dr = FoxHoundUI.getRow(destination); // row of destination
        int dc = FoxHoundUI.getColumn(destination); // column of destination

        hound = hound && !(origin.equals(players[players.length - 1]));
        hound = hound && (FoxHoundUI.boardArray(players, dim)[dr][dc] == '.');
        hound = hound && (or == dr - 1);
        // different cases based on different positions of the fox
        if (oc == (dim - 1)) { hound = hound && (dc == (oc - 1)); }
        else if (oc == 0) { hound = hound && (dc == oc + 1); }
        else {
            boolean test = (dc == (oc + 1)) || (dc == oc - 1);
            hound = hound && test;
        }

        return hound;
    }
    private static boolean isFoxValid(int dim, String[] players, char figure, String origin, String destination) {
        boolean fox = true;
        int or = FoxHoundUI.getRow(origin); // row of the origin
        int oc = FoxHoundUI.getColumn(origin); // column of the origin
        int dr = FoxHoundUI.getRow(destination); // row of the destination 
        int dc = FoxHoundUI.getColumn(destination); // column of the destination
        
        fox = fox && origin.equals(players[players.length - 1]);
        fox = fox && (FoxHoundUI.boardArray(players, dim)[dr][dc] == EMPTY_FIELD);
        // different cases for different positions
        if (or == (dim - 1)) {
            fox = fox && (dr == (or - 1));
            fox = fox && ((dc == (oc + 1)) || (dc == (oc - 1)));
        } 
        else {
            fox = fox && ((dr == (or + 1)) || (dr == (or - 1)));
            if (oc == (dim - 1)) { fox = fox && (dc == (oc - 1)); }
            else if (oc == 0) { fox = fox && (dc == (oc + 1)); }
            else { fox = fox && ((dc == (oc + 1)) || (dc == (oc - 1))); }
        }
        return fox;
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
                return fox_movement(players,dimension);
            }
            else {
                throw new IllegalArgumentException();
            }
    }
    private static boolean fox_movement(String[] players, int dimension) {
        int fr = FoxHoundUI.getRow(players[players.length-1]);
        int fc = FoxHoundUI.getColumn(players[players.length-1]);

        char board[][] = FoxHoundUI.boardArray(players,dimension);

        if(fc == 0 && fr != dimension - 1) {
            return (board[fr + 1][fc + 1] == 'H' &&
                    board[fr - 1][fc + 1] == 'H');
        }

        else if (fc == dimension - 1 && fr != dimension - 1) {
            return (board[fr + 1][fc - 1] == 'H' &&
                    board[fr - 1][fc + 1] == 'H');
        }

        else if(fr == dimension-1 && (fc != 0 && fc != dimension - 1)) {
            return (board[fr - 1][fc - 1] == 'H' &&
                    board[fr - 1][fc + 1] == 'H');
        }

        else if (fr == dimension-1 && fc == 0) {
            return (board[fr - 1][fc + 1] == 'H');
        }

        else if (fr == dimension - 1 && fc == dimension - 1) {
            return (board[fr - 1][fc - 1] == 'H');
        }

        else {
            return (board[fr + 1][fc + 1] == 'H' &&
                    board[fr + 1][fc - 1] == 'H' &&
                    board[fr - 1][fc + 1] == 'H' &&
                    board[fr - 1][fc - 1] == 'H');
        }
    }
}

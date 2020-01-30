
import javax.lang.model.util.ElementScanner6;

import java.util.*;
/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all user interface related
 * functionality such as printing menus and displaying the game board.
 */
public class FoxHoundUI {

    /** Number of main menu entries. */
    private static final int MENU_ENTRIES = 2;
    /** Main menu display string. */
    private static final String MAIN_MENU =
        "\n1. Move\n2. Exit\n\nEnter 1 - 2:";

    /** Menu entry to select a move action. */
    public static final int MENU_MOVE = 1;
    /** Menu entry to terminate the program. */
    public static final int MENU_EXIT = 2;
    public static void displayBoard(String[] players, int dimension) {
        char board[][] = boardArray(players, dimension);
        System.out.print("  ");
        for(int i = 0;i<board.length;i++)
        {
            System.out.print((char)(65+i));
        }
        System.out.println();
        for(int i = 0;i<board.length;i++)
        {
            System.out.print((i+1));
            System.out.print(' ');
            for(int j = 0;j<board.length;j++)
            {
                System.out.print(board[i][j]);
            }
            System.out.print(' ');
            System.out.print(1+i);
            System.out.println();
        }
        System.out.print("  ");
        for(int i = 0;i<board.length;i++)
        {
            System.out.print((char)(65+i));
        }
        System.out.println();
    }
    public static char[][] boardArray(String[] players,int dimension)
    {
        char [][] board= new char[dimension][dimension];
        int k = 0;
        for(int i = 0;i<dimension;i++)
        {
            for(int j = 0;j<dimension;j++)
            {
                    if (k < players.length-1 && (i == row(players[k]) && j == column(players[k])))
                    {
                        board[i][j] = FoxHoundUtils.HOUND_FIELD;
                        k++;
                    }
                    else if ((k == players.length-1 && (i == row(players[k]) && j == column(players[k]))))
                    {
                        board[i][j] = FoxHoundUtils.FOX_FIELD;
                        k++;
                    } 
                    else 
                        board[i][j] = FoxHoundUtils.EMPTY_FIELD;
            }
        }
        return board;
    }
    public static int row(String s)
    {
        return (Integer.parseInt(s.substring(1))-1);
    }
    public static int column(String s)
    {
        int k = (int)(s.charAt(0)) - 65;
        return k;
    }
    /**
     * Print the main menu and query the user for an entry selection.
     * 
     * @param figureToMove the figure type that has the next move
     * @param stdin a Scanner object to read user input from
     * @return a number representing the menu entry selected by the user
     * @throws IllegalArgumentException if the given figure type is invalid
     * @throws NullPointerException if the given Scanner is null
     */
    public static int mainMenuQuery(char figureToMove, Scanner stdin) {
        Objects.requireNonNull(stdin, "Given Scanner must not be null");
        if (figureToMove != FoxHoundUtils.FOX_FIELD 
         && figureToMove != FoxHoundUtils.HOUND_FIELD) {
            throw new IllegalArgumentException("Given figure field invalid: " + figureToMove);
        }

        String nextFigure = 
            figureToMove == FoxHoundUtils.FOX_FIELD ? "Fox" : "Hounds";

        int input = -1;
        while (input == -1) {
            System.out.println(nextFigure + " to move");
            System.out.println(MAIN_MENU);

            boolean validInput = false;
            if (stdin.hasNextInt()) {
                input = stdin.nextInt();
                validInput = input > 0 && input <= MENU_ENTRIES;
            }

            if (!validInput) {
                System.out.println("Please enter valid number.");
                input = -1; // reset input variable
            }

            stdin.nextLine(); // throw away the rest of the line
        }

        return input;
    }
    public static String[] positioinQuery(int dimension, Scanner stdIn)
    {
        String[] str = new String[2];
        boolean g = true;
        
        do
        {
            System.out.println("Provide origin and destination coordinates");
            if (stdIn == null)
            {
                g = false;
                System.out.println("ERROR: Please enter valid coordinate pair separated by space.");
                continue;
            }
            else if(dimension > FoxHoundUtils.MAX_DIM || dimension < FoxHoundUtils.MIN_DIM)
            {
                g = false;
                System.out.println("ERROR: Please enter valid coordinate pair separated by space.");
                continue;
            }
            System.out.println("Enter two positions between A1-H8");
            String a = stdIn.next();
            String b = stdIn.next();
            int c = 0;
            char row = a.charAt(0);
            char max = (char)(64+dimension);
            try{
                c = Integer.parseInt(b.substring(1));
            }
            catch(NumberFormatException e)
            {
                g = false;
                System.out.println("ERROR: Please enter valid coordinate pair separated by space.");
                continue;
            }
            if(row>max || c>dimension)
            {
                g = false;
                System.out.println("ERROR: Please enter valid coordinate pair separated by space.");
                continue;
            }
            str[0] = a;
            str[1] = b;
            g = true;
        }while(!g);
        return str;
    }
}








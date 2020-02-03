import java.util.Scanner;

/** 
 * The Main class of the fox hound program.
 * 
 * It contains the main game loop where main menu interactions
 * are processed and handler functions are called.
  */
  import java.nio.file.*;
  import java.io.*;
  import java.util.*;
public class FoxHoundGame {

    /** 
     * This scanner can be used by the program to read from
     * the standard input. 
     * 
     * Every scanner should be closed after its use, however, if you do
     * that for StdIn, it will close the underlying input stream as well
     * which makes it difficult to read from StdIn again later during the
     * program.
     * 
     * Therefore, it is advisable to create only one Scanner for StdIn 
     * over the course of a program and only close it when the program
     * exits. Additionally, it reduces complexity. 
     */
    private static final Scanner STDIN_SCAN = new Scanner(System.in);

    /**
     * Swap between fox and hounds to determine the next
     * figure to move.
     * 
     * @param currentTurn last figure to be moved
     * @return next figure to be moved
     */
    private static char swapPlayers(char currentTurn) { // to swap between hounds and foxes
        if (currentTurn == FoxHoundUtils.FOX_FIELD) {
            return FoxHoundUtils.HOUND_FIELD;
        } else {
            return FoxHoundUtils.FOX_FIELD;
        }
    }

    /**
     * The main loop of the game. Interactions with the main
     * menu are interpreted and executed here.
     * 
     * @param dim the dimension of the game board
     * @param players current position of all figures on the board in board coordinates
     */
    private static void gameLoop(int dim, String[] players) {

        // start each game with the Fox
        char turn = FoxHoundUtils.FOX_FIELD; // we always start with the fox
        boolean exit = false;
        while(!exit) {
            System.out.println(Arrays.toString(players)); //printing the players before we choose what move to make. 
            System.out.println("\n#################################");
            FoxHoundUI.displayBoard(players, dim); //displaying the board using the UI function
            int choice = FoxHoundUI.mainMenuQuery(turn, STDIN_SCAN); // giving the user with main menu options
            // handle menu choice using a switch condition
            switch(choice) {
                case FoxHoundUI.MENU_MOVE:
                    boolean g = false; // to break out of the loop if the move is not valid
                    int c = 0;
                    String[] k = new String[2]; // storing the origin and destination
                    do
                    {
                        if (c>0)
                            System.out.println("invalid input"); // since the code is giving the user multiple chances to enter coordinates, the code will give an error message once there is an issue with the coordinates
                        // System.out.println(FoxHoundUtils.isFoxWin(k[0]));
                        // System.out.println(turn == 'F');
                        String current_fox = players[players.length-1]; // the last string is the current position of the fox. 
                        if(FoxHoundUtils.isFoxWin(current_fox))
                        { // break out of the code if the fox has already won. The code need not go ahead.
                            System.out.println("Fox has won");
                            System.exit(0);
                        }
                        if(FoxHoundUtils.isHoundWin(players,dim))
                        {// break out of the code if the hound has already won. The code need not go ahead. 
                            System.out.println("Hound has won");
                            System.exit(0);
                        }
                        k = FoxHoundUI.positionQuery(dim, STDIN_SCAN); // asking the position, if no character has won the game yet
                        g = FoxHoundUtils.isValidMove(dim, players, turn, k[0], k[1]); // storing if the move valid
                        c++; // since its a do-while the code will work at least once. Hence we need to make sure we prevent that
                    }while(!g);   
                        int d = find(players,k[0]); // the find function does a linear search to find the position of the origin and replace with destination
                        players[d] = k[1]; // replacing with destination
                        turn = swapPlayers(turn); //swapping
                        System.out.println(Arrays.toString(players)); // printing after swapping.
        
                    break;
                case FoxHoundUI.SAVE_MOVE:
                    Path p = FoxHoundUI.fileQuery(STDIN_SCAN); // taking a scanner object 
                    boolean b = FoxHoundIO.saveGame(players,turn,p); // saving the players position, if true then quietly saved. else fail the function
                    if (!(b)) 
                        System.err.println("ERROR: Saving file failed."); // if theres a problem, print the error
                    break;
                case FoxHoundUI.LOAD_MOVE:
                    Path p1 = FoxHoundUI.fileQuery(STDIN_SCAN); // taking a scanner object to get the file path
                    char ch = FoxHoundIO.loadGame(players,p1); // loading the game using the file path and players
                    if (ch == '#')
                        System.err.println("ERROR: Loading from file failed."); // quietly loads it if given 'H' or 'F', else give an error
                    break;
                case FoxHoundUI.MENU_EXIT:
                    exit = true;
                    break; // break and exit the conditional
                default:
                    System.err.println("ERROR: invalid menu choice: " + choice);
            }
        }
    }
    public static int find(String[] players, String w)
    {//linear search to find the position of string object to be replaced. 
        int k = 0; 
        for(int i = 0;i<players.length;i++)
        {
            if(players[i].equals(w))
                k = i;
        }
        return k;
    }
    /**
     * Entry method for the Fox and Hound game. 
     * 
     * The dimensions of the game board can be passed in as
     * optional command line argument.
     * 
     * If no argument is passed, a default dimension of 
     * {@value FoxHoundUtils#DEFAULT_DIM} is used. 
     * 
     * Dimensions must be between {@value FoxHoundUtils#MIN_DIM} and 
     * {@value FoxHoundUtils#MAX_DIM}.
     * 
     * @param args contain the command line arguments where the first can be
     * board dimensions.
     */
    public static void main(String[] args) throws IOException {

        // main method
        int dimension = FoxHoundUtils.DEFAULT_DIM; //accessing the default dimension variable from Utils
        String[] players = FoxHoundUtils.initialisePositions(dimension); //initializing the players
        gameLoop(dimension,players); //gameLoop function
        STDIN_SCAN.close();  //closing the scanner object
    }
}

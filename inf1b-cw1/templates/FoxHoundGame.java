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
    private static char swapPlayers(char currentTurn) {
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
        char turn = FoxHoundUtils.FOX_FIELD;
        boolean exit = false;
        while(!exit) {
            System.out.println(Arrays.toString(players));
            System.out.println("\n#################################");
            FoxHoundUI.displayBoard(players, dim);
            int choice = FoxHoundUI.mainMenuQuery(turn, STDIN_SCAN);
            
            // handle menu choice
            switch(choice) {
                case FoxHoundUI.MENU_MOVE:
                    boolean g = false;
                    int c = 0;
                    String[] k = new String[2];
                    do
                    {
                        if (c>0)
                            System.out.println("invalid input");
                        // System.out.println(FoxHoundUtils.isFoxWin(k[0]));
                        // System.out.println(turn == 'F');
                        String current_fox = players[players.length-1];
                        if(FoxHoundUtils.isFoxWin(current_fox))
                        {
                            System.out.println("Fox has won");
                            System.exit(0);
                        }
                        if(FoxHoundUtils.isHoundWin(players,dim))
                        {
                            System.out.println("Hound has won");
                            System.exit(0);
                        }
                        k = FoxHoundUI.positioinQuery(dim, STDIN_SCAN);
                        g = FoxHoundUtils.isValidMove(dim, players, turn, k[0], k[1]);
                        c++;
                    }while(!g);   
                        int d = find(players,k[0]);
                        players[d] = k[1];
                        turn = swapPlayers(turn);
                        System.out.println(Arrays.toString(players));  
        
                    break;
                case FoxHoundUI.MENU_EXIT:
                    exit = true;
                    break;
                default:
                    System.err.println("ERROR: invalid menu choice: " + choice);
            }
        }
    }
    public static int find(String[] players, String w)
    {
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

        // int dimension = FoxHoundUtils.DEFAULT_DIM;
        int dimension = 5;
        String[] players = FoxHoundUtils.initialisePositions(dimension);
        // System.out.println(Arrays.toString(players));
        // System.out.println(FoxHoundUtils.isValidMove(8, players, 'H', "H1", "G2"));
        // FoxHoundUI.displayBoard(players,dimension);
        // System.out.println(Arrays.toString(players));
        // gameLoop(dimension, players);
        // FoxHoundUI.positioinQuery(8, STDIN_SCAN);
        // Close the scanner reading the standard input stream
		Path p1 = Paths.get("inf1b-cw1/FoxHoundSave.txt");
        FoxHoundIO.saveGame(players, 'H', p1);     
        STDIN_SCAN.close();
    }
}

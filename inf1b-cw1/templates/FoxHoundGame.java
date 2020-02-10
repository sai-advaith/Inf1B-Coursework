import java.util.Scanner;
import java.nio.file.*;
import java.io.*;
import java.util.*;
/**
 * The Main class of the fox hound program.
 *
 * It contains the main game loop where main menu interactions
 * are processed and handler functions are called.
 */
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
    private static final char INVALID_LOAD = '#';
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
                case FoxHoundUI.MENU_MOVE: {
                    boolean g = false;
                    int c = 0;
                    String[] k = new String[2];
                    do {
                        if (c>0) {
                            System.err.println("ERROR: invalid input");
                        }
                        String f = players[players.length-1]; // fox position
                        if(FoxHoundUtils.isFoxWin(f)) {
                            System.out.println("Fox has won");
                            System.exit(0);
                        }
                        if(FoxHoundUtils.isHoundWin(players,dim)) {
                            System.out.println("Hound has won");
                            System.exit(0);
                        }
                        k = FoxHoundUI.positionQuery(dim, STDIN_SCAN);
                        g = FoxHoundUtils.isValidMove(dim, players, turn, k[0], k[1]);
                        c++;
                    }while(!g);
                        players[findPosition(players,k[0])] = k[1];
                        turn = swapPlayers(turn);
                        System.out.println(Arrays.toString(players));
                    break;
                }
                case FoxHoundUI.SAVE_MOVE: {
                    Path p = FoxHoundUI.fileQuery(STDIN_SCAN);
                    boolean b = FoxHoundIO.saveGame(players, turn, p);
                    if (!(b)) {
                        System.err.println("ERROR: Saving file failed.");
                    }
                    break;
                }
                case FoxHoundUI.LOAD_MOVE: {
                    Path p1 = FoxHoundUI.fileQuery(STDIN_SCAN);
                    char ch = FoxHoundIO.loadGame(players, p1);
                    if (ch == INVALID_LOAD) {
                        System.err.println("ERROR: Loading from file failed.");
                    }
                    break;
                }
                case FoxHoundUI.MENU_EXIT: {
                    exit = true;
                    break;
                }
                default: {
                    System.err.println("ERROR: invalid menu choice: " + choice);
                }
             }
        }
    }/**
     * This method is to determine the index of a string in players array
     * Search using basic linear search
     * @param players string array containing the positions of pieces
     * @param w String to be searched for in the array
     * @return the index of the string in the array
     */
    private static int findPosition(String[] players, String w) {
        int k = 0;
        for(int i = 0;i<players.length;i++) {
            if(players[i].equals(w)) {
                k = i;
            }
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
    public static void main(String[] args) throws IOException,IllegalArgumentException {
        int dimension = 0;
        if (args == null || args.length > 1)
            throw new IOException();
        else
        { // command line argument
            dimension = Integer.parseInt(args[0]);
            if(dimension > FoxHoundUtils.MAX_DIM || dimension < FoxHoundUtils.MIN_DIM)
                throw new IllegalArgumentException();
        }
        String[] players = FoxHoundUtils.initialisePositions(dimension);
        gameLoop(dimension, players);
        STDIN_SCAN.close();
    }
}


    import java.nio.file.Paths;
    import java.nio.file.Path;
    import java.util.*;
    /**
     * A utility class for the fox hound program.
     *
     * It contains helper functions for all user interface related
     * functionality such as printing menus and displaying the game board.
     */
    public class FoxHoundUI {

        /** Number of main menu entries. */
        private static final int MENU_ENTRIES = 4;
        /** Main menu display string. */
        private static final String MAIN_MENU =
            "\n1. Move\n2. Save Game\n3. Load Game\n4. Exit \n\nEnter 1-4:";

        /** Menu entry to select a move action. */
        public static final int MENU_MOVE = 1;
        /** Menu entry to save the game */
        public static final int SAVE_MOVE = 2;
        /** Menu entry to load a game */
        public static final int LOAD_MOVE = 3;
        /** Menu entry to terminate the program. */
        public static final int MENU_EXIT = 4;

        /**
         *
         * @param players containing the positions of the pieces
         * @param dimension dimension of the board
         */
        public static void displayBoard(String[] players, int dimension) {
            char[][] board = boardArray(players, dimension);
            StringBuilder str = new StringBuilder();
            str.append("  ");
            for(int i = 0;i<board.length;i++) {
                str.append((char)(65+i));
            } // string builder to store the board in string form
            str.append("  ");
            str.append("\n");
            str.append("\n");
            for(int i = 0;i<board.length;i++) {
                str.append((i+1));
                str.append(' ');
                for(int j = 0;j<board.length;j++) {
                    str.append(board[i][j]);
                }
                str.append(' ');
                str.append(1+i);
                str.append("\n");
            }
            str.append("\n");
            str.append("  ");
            for(int i = 0;i<board.length;i++) {
                str.append((char)(65+i));
            }
            str.append("  ");
            System.out.println(str.toString());
        }

        /**
         *
         * @param players containing the positions of the pieces
         * @param dimension dimension of the board
         * @return a 2D array with all the positions in players occupied by respective pieces
         */
        static char[][] boardArray(String[] players,int dimension) {
            char [][] board = new char[dimension][dimension]; // 2d array
            for(int i = 0;i<players.length;i++) {
                int r = getRow(players[i]);
                int c = getColumn(players[i]);
                if (i == players.length-1) {
                    board[r][c] = FoxHoundUtils.FOX_FIELD;
                }
                else {
                    board[r][c] = FoxHoundUtils.HOUND_FIELD;
                }
            }
            for(int  i = 0;i<board.length;i++) {
                for(int  j = 0;j<board.length;j++) {
                    if (board[i][j] == '\u0000') {
                        board[i][j] = FoxHoundUtils.EMPTY_FIELD;
                    }
                }
            }
            return board;
        }

        /**
         *
         * @param s board coordinates of a piece as a string
         * @return row of the piece
         */
        static int getRow(String s) {
            return (Integer.parseInt(s.substring(1))-1);
        }

        /**
         *
         * @param s board coordinates of a piece as a string
         * @return column of the piece
         */
        static int getColumn(String s) {
            return (int)(s.charAt(0)) - 65;
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

        /**
         *
         * @param dimension dimension of the board
         * @param stdIn scanner object
         * @return a string array containing the origin and destination of the piece
         */
        public static String[] positionQuery(int dimension, Scanner stdIn) {
            String[] str = new String[2];
            boolean g = true;
            do {
                System.out.println("Provide origin and destination coordinates.");
                if (stdIn == null) {
                    g = false;
                    System.err.println("ERROR: Please enter valid coordinate pair separated by space.");
                    continue;
                }
                else if(dimension > FoxHoundUtils.MAX_DIM || dimension < FoxHoundUtils.MIN_DIM) {
                    g = false;
                    System.err.println("ERROR: Please enter valid coordinate pair separated by space.");
                    continue;
                }
                System.out.println("Enter two positions between A1-H8:\n");
                String t = stdIn.nextLine();
                int j = 0;
                StringBuilder a = new StringBuilder(); // an entire string as the input and not seperate
                StringBuilder b = new StringBuilder();
                for(int i =0;i<t.length();i++) {
                    if(t.charAt(i) == ' ') {
                        a.append(t.substring(j,i));
                        j = i+1;
                    }
                }
                b.append(t.substring(j,t.length()));
                int c = 0;
                char row = a.charAt(0);
                char max = (char)(64+dimension);
                try{
                    c = Integer.parseInt(b.substring(1));
                }
                catch(NumberFormatException e) {
                    g = false;
                    System.err.println("ERROR: Please enter valid coordinate pair separated by space.");
                    continue;
                }
                if(row>max || c>dimension) {
                    g = false;
                    System.err.println("ERROR: Please enter valid coordinate pair separated by space.");
                    continue;
                }
                str[0] = a.toString();
                str[1] = b.toString();
                g = true;
            }while(!g);
            return str;
        }

        /**
         *
         * @param stdIn scanner object
         * @return returning the file path as a path object
         */
        public static Path fileQuery(Scanner stdIn) { // taking file path
            Objects.requireNonNull(stdIn, "Given Scanner must not be null");
            System.out.println("Enter file path:");
            return Paths.get(stdIn.next());
        }
    }








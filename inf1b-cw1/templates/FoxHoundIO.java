import java.nio.file.Path;
import java.nio.file.Files;
import java.io.*;
/**
 * A utility class for the fox hound program.
 *
 * It contains helper functions for all file input / output
 * related operations such as saving and loading a game.
 */
public class FoxHoundIO {
    /**
     *
     * @param players string array storing positions of pieces
     * @param turn character of either fox or hounds turn
     * @param PATH file path object to be saved in
     * @return if the saving was successful or not
     * @throws NullPointerException if the array or path is null
     * @throws IllegalArgumentException if the dimension is not default or the turn is not a fox or hound
     */

    public static boolean saveGame(String[] players, char turn, Path PATH) throws NullPointerException, IllegalArgumentException {
        boolean g = true;
        if(players == null || PATH == null) {
            throw new NullPointerException();
        }
        if(players.length != 5) {
            throw new IllegalArgumentException();
        }
        if (turn != FoxHoundUtils.HOUND_FIELD && turn != FoxHoundUtils.FOX_FIELD) {
            throw new IllegalArgumentException();
        }
        StringBuilder str = new StringBuilder(turn + " ");
        for (String player : players) {
            str.append(player).append(" "); // current game
        }
        File file;
        try
        {
            file = new File(PATH.toUri());
            if (file.exists())
                g = false;
            else {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file)); // using bufferedwriter to write the string
                bw.write(str.toString());
                bw.close();
            }
        }
        catch(IOException e) {
            return false;
        }
        return g;
    }

    /**
     *
     * @param players array containing positions of the pieces
     * @param PATH file path of the file to be loaded
     * @return the player to play next
     * @throws IllegalArgumentException if the dimension is not default
     */
    public static char loadGame(String[] players, Path PATH) throws IllegalArgumentException {
        if(players.length != 5)
            throw new IllegalArgumentException();
        File f = new File(PATH.toUri());
        char c = '\u0000';
        if(!f.exists()) {
            return '#';
        }
        try {
            byte[] encode = Files.readAllBytes(PATH);
            String str = new String(encode);
            String[] words = str.split(" ",0);
            String d = words[0]; // reading all the bytes from a string and splitting based on a regex
            String[] cop = new String[words.length - 1];
            if (!(d.equals("H") || words[0].equals("F"))) {
                return '#';
            }
            boolean g = false;
            for(int i = 1;i<words.length;i++) {
                if (!(isValidPosition(words[i]))) {
                    g = true;
                    break;
                }
            }
            if(!g) {
                int  j = 0;
                for(int i = 1;i < words.length;i++) {
                    cop[j] = words[i]; // creating a duplicate to create a deep copy of the object
                    j ++;
                }
                for(int i = 0;i<cop.length;i++) {
                    players[i] = cop[i];
                }
            }
            if (g) {
                return '#';
            }
            else {
                if (d.equals("H")) {
                    c = FoxHoundUtils.HOUND_FIELD;
                }
                else {
                    c = FoxHoundUtils.FOX_FIELD;
                }
            }
        }
        catch(IOException e) {
            System.err.println("ERROR: Loading from file failed.");
        }
        return c;
    }

    /**
     *
     * @param pos the position of a piece to be checked for validity
     * @return if the position is valid or not
     */
    static boolean isValidPosition(String pos) {
        if(Character.isLetter(pos.charAt(0)) && (Character.isDigit(pos.charAt(1)))) {
            if (FoxHoundUI.getRow(pos)% 2 == 0) {
                return (FoxHoundUI.getRow(pos) <= 7 
                && FoxHoundUI.getColumn(pos) <= 7) 
                && (FoxHoundUI.getColumn(pos)%2 == 1);
            }
            else {
                return (FoxHoundUI.getRow(pos) <= 7 
                && FoxHoundUI.getColumn(pos) <= 7) 
                && (FoxHoundUI.getColumn(pos)%2 == 0);
            }
        }
        else {
            return false;
        }
    }
}

import java.nio.file.Path;

/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all file input / output
 * related operations such as saving and loading a game.
 */
import java.nio.file.Files;
import java.nio.file.*;
import java.io.*;
import java.lang.Object;
import java.util.*;
public class FoxHoundIO {


    public static boolean saveGame(String[] players, char turn, Path PATH) throws NullPointerException, IllegalArgumentException
    {  
        boolean g = true; // returning if saving is successful

        if(players == null)
            throw new NullPointerException(); // nullpointer if the players array is null
        if(players.length != 5)
            throw new IllegalArgumentException(); // if not valid default dimension then errors

        if (turn != FoxHoundUtils.HOUND_FIELD && turn != FoxHoundUtils.FOX_FIELD)
            throw new IllegalArgumentException();// if turn isnt hound or fox, them error

        else if (PATH == null)
            throw new NullPointerException();//if path object is null then another error. 

        StringBuilder str = new StringBuilder(turn + " ");// string builder for the string to be written to the file

        for(int  i = 0;i<players.length;i++)
        {
            str = str.append(players[i] + " ");//positions written to the file
        }
        File file; // creating a file object
        try
        {
            file = new File(PATH.toUri()); // assigning the object a value using the constructor and converting the path to a uri
            if (file.exists())
                g = false; // if the file exists, then we break out of the code
            else
            {//else write the string to a new file
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(str.toString());
                bw.close(); // close all streams
            }

        }
        catch(IOException e)
        {
            return false; // if file not found, exception thrown
        }
        return g;
    }
    public static char loadGame(String[] players, Path PATH) throws IllegalArgumentException
    {
        if(players.length != 5)
            throw new IllegalArgumentException(); // if dimension is wrong, then throw an error
        File f = new File(PATH.toUri()); // create file object
        char c = '\u0000';
        if(!f.exists())
            return '#'; // if it doesnt exist, then we cannot load anything
        try
        {
            byte[] encode;
            String str;
            String[] words;
            encode = Files.readAllBytes(PATH); // reading all bytes and storing in an array
            str = new String(encode);
            words = str.split(" ",0); // splliting the array
            String d = words[0];
            String[] cop = new String[words.length - 1];
            if (!(d.equals("H") || words[0].equals("F")))
                return '#'; // if not fox or hound, return false
            boolean g = false;
            for(int i = 1;i<words.length;i++)
            {
                if (!(isValidPosition(words[i])))
                {
                    g = true; // if the entries in the array arent valid coordinates then its false
                    break;
                }
            }
            if(!g)
            { // copy the values into players only if all the previous conditions are satisfied
                int  j = 0;
                for(int i = 1;i < words.length;i++)
                {
                    cop[j] = words[i];
                    j ++; //creating a fresh array containing the values of the new array
                }
                for(int i = 0;i<cop.length;i++)
                    players[i] = cop[i]; // storing it
            }
            if (g)
                return '#';
            else
            {
                if (d.equals("H"))
                    c = FoxHoundUtils.HOUND_FIELD;//returning the characters
                else
                    c = FoxHoundUtils.FOX_FIELD;
            }
        }
        catch(IOException e)
        {
            System.out.println("ERROR: Loading from file failed.");
        }
        return c;    
    }
    public static boolean isValidPosition(String pos)
    {//checking if the coordinate in file is valid
        if(Character.isLetter(pos.charAt(0)) && (Character.isDigit(pos.charAt(1))))
        {
            if (FoxHoundUI.row(pos) > 7 || FoxHoundUI.column(pos) > 7)  
                return false;
            else
                return true;
        }
        else
            return false;
    }
}

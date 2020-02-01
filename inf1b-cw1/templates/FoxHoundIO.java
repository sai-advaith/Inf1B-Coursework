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


    public static boolean saveGame(String[] players, char turn, Path PATH) throws IOException
    {   
        boolean g = true;

        if(players == null)
            throw new NullPointerException();

        if (turn != FoxHoundUtils.HOUND_FIELD && turn != FoxHoundUtils.FOX_FIELD)
            throw new IllegalArgumentException();

        else if (PATH == null)
            throw new NullPointerException();

        StringBuilder str = new StringBuilder(turn + " ");

        for(int  i = 0;i<players.length;i++)
        {
            str = str.append(players[i] + " ");
        }
        File file;
        try
        {
            file = new File(PATH.toUri());
            if (file.exists())
                g = false;
            else
            {
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(str.toString());
                bw.close();
            }
                
            // System.out.println(file.isFile());
            // System.out.println(file.isDirectory());
        }
        catch(IOException e)
        {
            e.printStackTrace();;
        }
        return g;
    }
    public static char loadGame(String[] players, Path PATH) throws IOException
    {
        File f = new File(PATH.toUri());
        char c = '\u0000';

        if(!f.exists())
        {
            System.out.println("e");
            return '*';
        }
        try
        {
            byte[] encode;
            String str;
            String[] words;
            encode = Files.readAllBytes(PATH);
            str = new String(encode);
            words = str.split(" ",0);
            String d = words[0];
            if (!(d.equals("H") || words[0].equals("F")))
                return '#';
            boolean g = false;
            for(int i = 1;i<words.length;i++)
            {
                if (!(isValidPosition(words[i])))
                {
                    g = true;
                    break;
                }
            }
            if (g)
                return '#';
            else
            {
                if (d.equals("H"))
                    c = FoxHoundUtils.FOX_FIELD;
                else
                    c = FoxHoundUtils.HOUND_FIELD;
            }
        }
        catch(IOException e)
        {
            System.out.println("ERROR: Loading from file failed.");
        }
        return c;    
    }
    public static boolean isValidPosition(String pos)
    {
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

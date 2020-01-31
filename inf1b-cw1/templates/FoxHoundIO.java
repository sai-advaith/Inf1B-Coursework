import java.nio.file.Path;

/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all file input / output
 * related operations such as saving and loading a game.
 */
import java.io.*;
import java.nio.*;
public class FoxHoundIO {


    public static boolean saveGame(String[] players, char turn, Path PATH) throws IOException
    {   
        boolean g = true;

        if(players == null)
            throw new NullPointerException("Null players array");

        if (turn != FoxHoundUtils.HOUND_FIELD && turn != FoxHoundUtils.FOX_FIELD)
            throw new IllegalArgumentException("Illegal turn argument");

        else if (PATH == null)
            throw new NullPointerException("Null path");

        StringBuilder str = new StringBuilder(turn + " ");
        
        for(int  i = 0;i<players.length;i++)
        {
            str = str.append(players[i] + " ");
        }
        FileOutputStream fStream = null;
        File file;
        try
        {
            file = new File(PATH.toUri());
            fStream = new FileOutputStream(file);
            if(!file.exists())
                file.createNewFile();
            else
                g = false;
            byte[] bytesArray = (str.toString()).getBytes();
            fStream.write(bytesArray);
            fStream.flush();
            System.out.println("File has been saved");
        }
        catch(IOException e)
        {
            e.printStackTrace();;
        }
        finally{
            try
            {
                if(fStream != null)
                    fStream.close();
            }
            catch(IOException e)
            {
                System.out.println("Cannot close the Stream");
            }
        }
        return g;
    }
}

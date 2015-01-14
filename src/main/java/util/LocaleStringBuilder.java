package util;

import android.content.Context;
import android.util.Log;

import com.kod.knightsofdrakonur.framework.FileIO;
import com.kod.knightsofdrakonur.framework.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import impl.AndroidFileIO;

/**
 * Created by GreenyNeko on 10.01.2015.
 */
public class LocaleStringBuilder
{
    private Locale locale;
    private String internString;
    private FileIO file;

    public LocaleStringBuilder(Game game)
    {
        this.internString = "";
        this.file = new AndroidFileIO((Context)game);
        this.locale = game.getLocale();
    }

    /* Return the fully parsed string.
     *
     * @return String - the parsed string.
     */
    public String finalizeString()
    {
        return this.internString;
    }

    /* Adds a string to the built string.
     *
     * @param String string - the string to be added.
     */
    public LocaleStringBuilder addString(String string)
    {
        this.internString += string;
        return this;
    }

    /* Adds a string from a language file to the built string.
     *
     * @param String id - the id referring to the language file string.
     */
    public LocaleStringBuilder addLocaleString(String id)
    {
        String text = id;
        try
        {
            InputStream in = file.readAsset("lang/" + this.locale.getDisplayName() + ".lang");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine()) != null)
            {
                if(line.substring(0, line.indexOf('=')).contentEquals(id))
                {
                    text = line.substring(line.indexOf('=') + 1);
                    break;
                }
            }
        }
        catch(IOException e)
        {
            // IO Error
            Log.e("KOD", "IOException occurred while trying to load language string \'" + id
                    + "\' from language file \'lang/" + locale.getDisplayName() + ".lang\'");
            text = id;
        }
        catch(NullPointerException e)
        {
            // No matching entry in the language file.
            Log.e("KOD", "Couldn't find a matching entry for \'" + id + "\' from the file\'lang/"
                    + locale.getDisplayName() + ".lang\'");
            text = id;
        }
        catch(Exception e)
        {
            // If anything goes wrong just draw the raw string.
            Log.e("KOD", "An error occurred while trying to load language string \'" + id
                    + "\' from language file \'lang/" + locale.getDisplayName() + ".lang\'");
            text = id;
        }
        this.internString += text;
        return this;
    }

    /* Replace the respective tags in the built string.
     *
     * @param String tag - the tag that will be replaced e.g. "{dmg}"
     * @param String value - the value the tag should be replaced with.
     */
    public LocaleStringBuilder replaceTags(String tag, String value)
    {
        while(this.internString.contains(tag))
        {
            this.internString = this.internString.replace(tag, value);
        }
        return this;
    }
}

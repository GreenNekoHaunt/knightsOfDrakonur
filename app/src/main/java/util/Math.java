package util;

/**
 * Created by GreenyNeko on 11.12.2014.
 */

import android.content.Context;
import android.graphics.Paint;

import com.kod.knightsofdrakonur.framework.FileIO;
import com.kod.knightsofdrakonur.framework.Input.TouchEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import impl.AndroidFileIO;

public class Math
{
    /* Returns true if the touch has been in the set rectangle.
     *
     * @param TouchEvent touchEvent - the touch event handler.
     * @param int x - the x coordinate of the collision rectangle.
     * @param int y - the y coordinate of the collision rectangle.
     * @param int width - the width of the collision rectangle.
     * @param int height - the height of the collision rectangle.
     */
    public static boolean inBoundary(TouchEvent touchEvent, int x, int y, int width, int height)
    {
        if(touchEvent.x > x && touchEvent.y > y
                && touchEvent.x < x + width - 1 && touchEvent.y < y + height -1)
        {
            return true;
        }
        return false;
    }

    /* Gets the width of the String on the screen when using locales.
     *
     * @param Context context - the adroind content context.
     * @param String id - the locale id for the language file.
     * @param Paint paint - the style of the text that gets drawn.
     *
     * TODO: Move this function into LocaleStringBuilder maybe?
     */
    public static float getLocaleStringWidth(Context context, String id, Paint paint)
    {
        Locale locale = paint.getTextLocale();
        FileIO lang = new AndroidFileIO(context);
        String text = id;

        try
        {
            InputStream in = lang.readAsset("lang/" + locale.getDisplayName() + ".lang");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine()) != null)
            {
                if(line.substring(0, line.indexOf('=')).contentEquals(id))
                {
                    text = line.substring(line.indexOf('=') + 1);
                }
            }
        }
        catch(IOException e)
        {
            text = id;
        }

        return paint.measureText(text);
    }
}

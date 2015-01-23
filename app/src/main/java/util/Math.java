package util;

/**
 * Created by GreenyNeko on 11.12.2014.
 */

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;

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

    /* Gets the width of the String on the screen.
     *
     * @param String text - the text to be measured.
     * @param float size - the text size used.
     *
     * @return int - the width of the text.
     */
    public static int getTextWidth(String text, float size)
    {
        Rect bounds = new Rect();
        Paint textPaint = new Paint();
        textPaint.setTextSize(size);
        textPaint.getTextBounds(text, 0,text.length(), bounds);
        return bounds.width();
    }
}

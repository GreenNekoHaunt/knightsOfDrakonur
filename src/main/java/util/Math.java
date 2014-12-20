package util;

/**
 * Created by GreenyNeko on 11.12.2014.
 */

import com.kod.knightsofdrakonur.framework.Input.TouchEvent;

public class Math
{
    public static boolean inBoundary(TouchEvent touchEvent, int x, int y, int width, int height)
    {
        if(touchEvent.x > x && touchEvent.y > y
                && touchEvent.x < x + width - 1 && touchEvent.y < y + height -1)
        {
            return true;
        }
        return false;
    }
}

package impl;

/**
 * Created by GreenyNeko on 05.12.2014.
 */

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import com.kod.knightsofdrakonur.framework.Input;
import com.kod.knightsofdrakonur.framework.TouchHandler;
import com.kod.knightsofdrakonur.framework.SingleTouchHandler;
import com.kod.knightsofdrakonur.framework.MultiTouchHandler;

public class AndroidInput implements Input
{
    TouchHandler touchHandler;

    public AndroidInput(Context context, View view, float scaleX, float scaleY)
    {
        if(VERSION.SDK_INT < 5)
        {
            touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
        }
        else
        {
            touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
        }
    }

    @Override
    public boolean isTouchDown(int pointer)
    {
        return touchHandler.isTouchDown(pointer);
    }

    @Override
    public int getTouchX(int pointer)
    {
        return touchHandler.getTouchX(pointer);
    }

    @Override
    public int getTouchY(int pointer)
    {
        return touchHandler.getTouchY(pointer);
    }

    @Override
    public List<TouchEvent> getTouchEvents()
    {
        return touchHandler.getTouchEvents();
    }
}

package com.kod.knightsofdrakonur.framework;

/**
 * Created by GreenyNeko on 05.12.2014.
 */

import java.util.List;

import android.view.View.OnTouchListener;

public interface TouchHandler extends OnTouchListener
{
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<Input.TouchEvent> getTouchEvents();
}

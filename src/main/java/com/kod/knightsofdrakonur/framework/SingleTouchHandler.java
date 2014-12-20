package com.kod.knightsofdrakonur.framework;

/**
 * Created by GreenyNeko on 05.12.2014.
 */

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import com.kod.knightsofdrakonur.framework.Input.TouchEvent;
import com.kod.knightsofdrakonur.framework.Pool.PoolObjectFactory;

public class SingleTouchHandler implements TouchHandler
{
    boolean isTouched;
    int touchX;
    int touchY;
    Pool<Input.TouchEvent> touchEventPool;
    List<Input.TouchEvent> touchEvents = new ArrayList<Input.TouchEvent>();
    List<Input.TouchEvent> touchEventsBuffer = new ArrayList<Input.TouchEvent>();
    float scaleX;
    float scaleY;

    public SingleTouchHandler(View view, float scaleX, float scaleY)
    {
        PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>()
        {
            @Override
            public TouchEvent createObject()
            {
                return new TouchEvent();
            }
        };
        touchEventPool = new Pool<TouchEvent>(factory, 100);
        view.setOnTouchListener(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        synchronized(this)
        {
            TouchEvent touchEvent = touchEventPool.newObject();
            switch (motionEvent.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    touchEvent.type = TouchEvent.TOUCH_DOWN;
                    isTouched = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                    isTouched = true;
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    touchEvent.type = TouchEvent.TOUCH_UP;
                    isTouched = false;
                    break;
            }

            touchEvent.x = touchX = (int)(motionEvent.getX() * scaleX);
            touchEvent.y = touchY = (int)(motionEvent.getY() * scaleY);
            touchEventsBuffer.add(touchEvent);

            return true;
        }
    }

    @Override
    public boolean isTouchDown(int pointer)
    {
        synchronized(this)
        {
            return pointer == 0 ? isTouched : false;
        }
    }

    @Override
    public int getTouchX(int pointer)
    {
        synchronized(this)
        {
            return touchX;
        }
    }

    @Override
    public int getTouchY(int pointer)
    {
        synchronized(this)
        {
            return touchY;
        }
    }

    @Override
    public List<TouchEvent> getTouchEvents()
    {
        synchronized(this)
        {
            int len = touchEvents.size();
            for(int i = 0; i < len; i++)
            {
                touchEventPool.free(touchEvents.get(i));
            }
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }
}

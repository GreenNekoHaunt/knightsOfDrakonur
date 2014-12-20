package src;

/**
 * Created by GreenyNeko on 11.12.2014.
 */

import java.util.List;
import java.util.Locale;

import android.content.res.Configuration;
import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.widget.Toast;

import com.kod.knightsofdrakonur.framework.Game;
import com.kod.knightsofdrakonur.framework.Graphics;
import com.kod.knightsofdrakonur.framework.Screen;
import com.kod.knightsofdrakonur.framework.Input.TouchEvent;

import util.Math;

public class TitleScreen extends Screen
{
    public int ticks = 0;

    public TitleScreen(Game game)
    {
        super(game);
    }

    @Override
    public void update(float deltaTime)
    {
        Graphics graphics = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for(int i = 0; i < touchEvents.size(); i++)
        {
            TouchEvent touchEvent = touchEvents.get(i);
            if(touchEvent.type == TouchEvent.TOUCH_UP)
            {
                if(Math.inBoundary(touchEvent, 0, 0, graphics.getWidth(), graphics.getHeight()))
                {
                    game.setScreen(new MenuScreen(game));
                }
            }
        }
    }

    @Override
    public void paint(float deltaTime)
    {
        int screenH = game.getGraphics().getHeight(), screenW = game.getGraphics().getWidth();
        Graphics graphics = game.getGraphics();
        graphics.clearScreen(0);

        Paint textStyle = new Paint();
        textStyle.setARGB(250, 250, 250, 0);
        textStyle.setTextAlign(Paint.Align.CENTER);
        textStyle.setTextSize(32.0f);
        textStyle.setTextLocale(Locale.ENGLISH);
        graphics.drawString(String.valueOf(ticks), 200, 50, textStyle);
        if(ticks % 100 < 50)
        {
            graphics.drawString("Tap the screen!", (int) (screenW * 0.5), (int) (screenH * 0.9), textStyle);
        }
        ticks++;
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {

    }

    @Override
    public void backButton()
    {

    }
}

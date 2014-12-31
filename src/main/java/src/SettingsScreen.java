package src;

/**
 * Created by GreenyNeko on 11.12.2014.
 */

import android.content.Context;
import android.graphics.Paint;

import com.kod.knightsofdrakonur.framework.Game;
import com.kod.knightsofdrakonur.framework.Graphics;
import com.kod.knightsofdrakonur.framework.Input.TouchEvent;
import com.kod.knightsofdrakonur.framework.Screen;

import java.util.List;
import java.util.Locale;

import util.Math;

public class SettingsScreen extends Screen
{
    public SettingsScreen(Game game)
    {
        super(game);
    }

    @Override
    public void update(float deltaTime)
    {
        int screenH = game.getGraphics().getHeight(), screenW = game.getGraphics().getWidth();
        Graphics graphics = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for(int i = 0; i < touchEvents.size(); i++)
        {
            TouchEvent touchEvent = touchEvents.get(i);
            if(touchEvent.type == TouchEvent.TOUCH_UP)
            {
                Paint paint = new Paint();
                paint.setTextSize(36.0f);
                float width = Math.getLocaleStringWidth((Context)game, "lang.settings.ui.language",
                        paint);
                if(Math.inBoundary(touchEvent, (screenW / 2) - (int)(width / 2), screenH / 2,
                        (int)width, 36))
                {
                    if (game.getLocale() == Locale.ENGLISH)
                    {
                        game.setLocale(Locale.GERMAN);
                    } else if (game.getLocale() == Locale.GERMAN)
                    {
                        game.setLocale(Locale.JAPANESE);
                    } else
                    {
                        game.setLocale(Locale.ENGLISH);
                    }
                }
                width = Math.getLocaleStringWidth((Context)game, "lang.ui.back", paint);
                if(Math.inBoundary(touchEvent, (screenW / 2) - (int)(width / 2),
                        (int)(screenH * 0.9), (int)width, 36))
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
        graphics.clearScreen(0x459AFF);

        Paint textStyle = new Paint();
        textStyle.setTextSize(36.0f);
        textStyle.setARGB(250, 250, 250, 250);
        textStyle.setTextAlign(Paint.Align.CENTER);
        textStyle.setTextLocale(game.getLocale());

        graphics.drawString((Context)game, "lang.settings.ui.language", screenW / 2,
                screenH / 2 - 52, textStyle);
        graphics.drawString((Context)game, "lang.self", screenW / 2,
                screenH / 2, textStyle);
        graphics.drawString((Context)game, "lang.ui.back",
                screenW / 2, (int)(screenH * 0.90), textStyle);
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
    public void onBackPressed()
    {
        game.setScreen(new MenuScreen(game));
    }
}

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

public class MenuScreen extends Screen
{
    public MenuScreen(Game game)
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
                if(Math.inBoundary(touchEvent, (int)(screenW * 0.5), (int)(screenH * 0.2) - 18, 360, 36))
                {
                    game.setScreen(new BattleScreen(game));
                }
                else if(Math.inBoundary(touchEvent, (int)(screenW * 0.5) - ("Character".length() / 18), (int)(screenH * 0.4) - 18, "Character".length() * 36, 36))
                {
                    game.setScreen(new CharacterScreen(game));
                }
                else if(Math.inBoundary(touchEvent, (int)(screenW * 0.5) - ("Settings".length() / 18), (int)(screenH * 0.6) - 18, "Settings".length() * 36, 36))
                {
                    game.setScreen(new SettingsScreen(game));
                }
                else if(Math.inBoundary(touchEvent, (int)(screenW * 0.5) - ("Logout".length() / 18), (int)(screenH * 0.8) - 18, "Logout".length() * 36, 36))
                {
                    game.setScreen(new TitleScreen(game));
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
        textStyle.setARGB(250, 250, 250, 250);
        textStyle.setTextAlign(Paint.Align.CENTER);
        textStyle.setTextSize(36.0f);
        textStyle.setTextLocale(game.getLocale());
        graphics.drawString((Context)game, "lang.menu.ui.adventure", (int)(screenW * 0.5), (int)(screenH * 0.2), textStyle);
        graphics.drawString((Context)game, "lang.menu.ui.character", (int)(screenW * 0.5), (int)(screenH * 0.4), textStyle);
        graphics.drawString((Context)game, "lang.menu.ui.settings", (int)(screenW * 0.5), (int)(screenH * 0.6), textStyle);
        graphics.drawString((Context)game, "lang.menu.ui.logout", (int)(screenW * 0.5), (int)(screenH * 0.8), textStyle);
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
        game.setScreen(new TitleScreen(game));
    }
}

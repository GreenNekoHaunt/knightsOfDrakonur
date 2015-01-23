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

import util.LocaleStringBuilder;
import util.Math;

public class SettingsScreen extends Screen
{
    private String labelLanguage;
    private String valueLanguage;
    private String labelBack;

    public SettingsScreen(Game game)
    {
        super(game);
        this.labelLanguage = (new LocaleStringBuilder(game))
                .addLocaleString("lang.settings.ui.language").finalizeString();
        this.valueLanguage = (new LocaleStringBuilder(game))
                .addLocaleString("lang.self").finalizeString();
        this.labelBack = (new LocaleStringBuilder(game))
                .addLocaleString("lang.ui.back").finalizeString();
    }

    @Override
    /* Called whenever the activity updates.
     *
     * @param float deltaTime - the time that has passed.
     */
    public void update(float deltaTime)
    {
        int screenH = game.getGraphics().getHeight(), screenW = game.getGraphics().getWidth();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for(int i = 0; i < touchEvents.size(); i++)
        {
            TouchEvent touchEvent = touchEvents.get(i);
            if(touchEvent.type == TouchEvent.TOUCH_UP)
            {
                float width = Math.getTextWidth(this.labelLanguage, 36.0f);
                if(Math.inBoundary(touchEvent, (screenW / 2) - (int)(width / 2), screenH / 2,
                        (int)width, 36))
                {
                    if (game.getLocale() == Locale.ENGLISH)
                    {
                        game.setLocale(Locale.GERMAN);
                    }
                    else if (game.getLocale() == Locale.GERMAN)
                    {
                        game.setLocale(Locale.JAPANESE);
                    }
                    else
                    {
                        game.setLocale(Locale.ENGLISH);
                    }
                    this.labelLanguage = (new LocaleStringBuilder(game))
                            .addLocaleString("lang.settings.ui.language").finalizeString();
                    this.valueLanguage = (new LocaleStringBuilder(game))
                            .addLocaleString("lang.self").finalizeString();
                    this.labelBack = (new LocaleStringBuilder(game))
                            .addLocaleString("lang.ui.back").finalizeString();
                }
                width = Math.getTextWidth(this.labelBack, 36.0f);
                if(Math.inBoundary(touchEvent, (screenW / 2) - (int)(width / 2),
                        (int)(screenH * 0.9), (int)width, 36))
                {
                    game.setScreen(new MenuScreen(game));
                }
            }
        }
    }

    @Override
    /* Called whenever the screen is updated.
     *
     * @param float deltaTime - the time that has passed since the last update.
     */
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

        graphics.drawString(this.labelLanguage, screenW / 2, screenH / 2 - 52, textStyle);
        graphics.drawString(this.valueLanguage, screenW / 2, screenH / 2, textStyle);
        graphics.drawString(this.labelBack, screenW / 2, (int)(screenH * 0.90), textStyle);
    }

    @Override
    /* Called when the activity is paused. */
    public void pause()
    {

    }

    @Override
    /* Called when the activity is resumed. */
    public void resume()
    {

    }

    @Override
    /* Called when the activity is disposed. */
    public void dispose()
    {

    }

    @Override
    /* When the back button of the smartphone is pressed. */
    public void onBackPressed()
    {
        game.setScreen(new MenuScreen(game));
    }
}

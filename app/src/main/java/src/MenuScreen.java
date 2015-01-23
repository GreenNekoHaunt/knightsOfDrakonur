package src;

/**
 * Created by GreenyNeko on 11.12.2014.
 */

import android.graphics.Paint;
import android.graphics.Rect;

import com.kod.knightsofdrakonur.framework.Game;
import com.kod.knightsofdrakonur.framework.Graphics;
import com.kod.knightsofdrakonur.framework.Input.TouchEvent;
import com.kod.knightsofdrakonur.framework.Screen;

import java.util.List;

import util.LocaleStringBuilder;
import util.Math;

public class MenuScreen extends Screen
{
    private String labelAdventure;
    private String labelArena;
    private String labelCharacter;
    private String labelSettings;
    private String labelLogout;

    public MenuScreen(Game game)
    {
        super(game);
        this.labelAdventure = (new LocaleStringBuilder(game))
                .addLocaleString("lang.menu.ui.adventure").finalizeString();
        this.labelArena = (new LocaleStringBuilder(game))
                .addLocaleString("lang.menu.ui.arena").finalizeString();
        this.labelCharacter = (new LocaleStringBuilder(game))
                .addLocaleString("lang.menu.ui.character").finalizeString();
        this.labelSettings = (new LocaleStringBuilder(game))
                .addLocaleString("lang.menu.ui.settings").finalizeString();
        this.labelLogout = (new LocaleStringBuilder(game))
                .addLocaleString("lang.menu.ui.logout").finalizeString();
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
                if(Math.inBoundary(touchEvent,
                        (int)(screenW * 0.5) - Math.getTextWidth(this.labelAdventure, 36.0f) / 2,
                        (int)(screenH * 0.1) - 36, Math.getTextWidth(this.labelAdventure, 36.0f),
                        36))
                {
                    game.setScreen(new BattleScreen(game, 0));
                }
                else if(Math.inBoundary(touchEvent,
                        (int)(screenW * 0.5) - Math.getTextWidth(this.labelArena, 36.0f / 2),
                        (int)(screenH * 0.3) - 36, Math.getTextWidth(this.labelArena, 36.0f), 36))
                {
                    game.setScreen(new BattleScreen(game, 1));

                }
                else if(Math.inBoundary(touchEvent,
                        (int)(screenW * 0.5) - Math.getTextWidth(this.labelCharacter, 36.0f) / 2,
                        (int)(screenH * 0.5) - 36, Math.getTextWidth(this.labelCharacter, 36.0f),
                        36))
                {
                    game.setScreen(new CharacterScreen(game, game.getCurrentPlayer()));
                }
                else if(Math.inBoundary(touchEvent,
                        (int)(screenW * 0.5) - Math.getTextWidth(this.labelSettings, 36.0f) / 2,
                        (int)(screenH * 0.7) - 36, Math.getTextWidth(this.labelSettings, 36.0f),
                        36))
                {
                    game.setScreen(new SettingsScreen(game));
                }
                else if(Math.inBoundary(touchEvent,
                        (int)(screenW * 0.5) - Math.getTextWidth(this.labelLogout, 36.0f) / 2,
                        (int)(screenH * 0.9) - 36, Math.getTextWidth(this.labelSettings, 36.0f),
                        36))
                {
                    game.setScreen(new TitleScreen(game));
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
        textStyle.setARGB(250, 250, 250, 250);
        textStyle.setTextAlign(Paint.Align.CENTER);
        textStyle.setTextSize(36.0f);
        graphics.drawString(this.labelAdventure, (int)(screenW * 0.5), (int)(screenH * 0.1),
                textStyle);
        graphics.drawString(this.labelArena, (int)(screenW * 0.5), (int)(screenH * 0.3), textStyle);
        graphics.drawString(this.labelCharacter, (int)(screenW * 0.5), (int)(screenH * 0.5),
                textStyle);
        graphics.drawString(this.labelSettings, (int)(screenW * 0.5), (int)(screenH * 0.7),
                textStyle);
        graphics.drawString(this.labelLogout, (int)(screenW * 0.5), (int)(screenH * 0.9),
                textStyle);
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
        game.setScreen(new TitleScreen(game));
    }
}

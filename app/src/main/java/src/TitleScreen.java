package src;

/**
 * Created by GreenyNeko on 11.12.2014.
 */

import java.util.List;

import android.graphics.Paint;

import com.kod.knightsofdrakonur.framework.Game;
import com.kod.knightsofdrakonur.framework.Graphics;
import com.kod.knightsofdrakonur.framework.Screen;
import com.kod.knightsofdrakonur.framework.Input.TouchEvent;

import src.entity.Player;
import src.entity.Role;
import util.LocaleStringBuilder;
import util.Math;

public class TitleScreen extends Screen
{
    public int ticks = 0;

    public TitleScreen(Game game)
    {
        super(game);
    }

    @Override
    /* Called whenever the activity updates.
     *
     * @param float deltaTime - the time that has passed.
     */
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
                    Player player = new Player(Role.WARRIOR, "Player1");
                    Player player2 = new Player(Role.WARRIOR, "Player2");
                    game.setCurrentPlayer(player);
                    game.setCurrentPlayer2(player2);
                    game.setScreen(new MenuScreen(game));
                }
            }
        }
        ticks++;
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
        textStyle.setARGB(250, 250, 250, 0);
        textStyle.setTextAlign(Paint.Align.CENTER);
        textStyle.setTextSize(32.0f);
        textStyle.setTextLocale(game.getLocale());
        if(ticks % 100 < 50)
        {
            graphics.drawString(
                    (new LocaleStringBuilder(game)).addLocaleString("lang.title.ui.tapScreen")
                            .finalizeString(), (int) (screenW * 0.5), (int) (screenH * 0.9),
                    textStyle);
        }
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
        game.finishGame();
    }
}

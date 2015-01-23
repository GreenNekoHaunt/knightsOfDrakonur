package src;

/**
 * Created by GreenyNeko on 11.12.2014.
 */

import android.graphics.Paint;

import com.kod.knightsofdrakonur.framework.Game;
import com.kod.knightsofdrakonur.framework.Graphics;
import com.kod.knightsofdrakonur.framework.Input.TouchEvent;
import com.kod.knightsofdrakonur.framework.Screen;

import java.util.List;
import java.util.Locale;

import src.entity.Player;
import util.Math;

public class CharacterScreen extends Screen
{
    private Player player;

    public CharacterScreen(Game game, Player player)
    {
        super(game);
        this.player = player;
    }

    @Override
    /* Called whenever the activity updates.
     *
     * @param float deltaTime - the time that has passed.
     */
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
                if(Math.inBoundary(touchEvent, 0, (int)(screenH * 0.12), 160, 256))
                {
                    game.setScreen(new AttributeScreen(game, this.player));
                }
                else if(Math.inBoundary(touchEvent, 0, (int)(screenH * 0.37), 160, 256))
                {
                    game.setScreen(new SkillScreen(game, this.player));
                }
                else if(Math.inBoundary(touchEvent, 0, (int)(screenH * 0.62), 160, 256))
                {
                    game.setScreen(new EquipScreen(game, this.player));
                }
                else if(Math.inBoundary(touchEvent, screenW - Assets.ui_shield.getWidth(), 0,
                        192, 256))
                {
                    if(this.player == game.getCurrentPlayer())
                    {
                        this.player = game.getCurrentPlayer2();
                    }
                    else if(this.player == game.getCurrentPlayer2())
                    {
                        this.player = game.getCurrentPlayer();
                    }
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
        Paint numberStyle = new Paint();
        numberStyle.setTextSize(60.0f);
        numberStyle.setStrokeWidth(5.0f);
        numberStyle.setARGB(255, 255, 255, 0);
        numberStyle.setTextAlign(Paint.Align.CENTER);

        graphics.drawImage(Assets.ui_shield, screenW - Assets.ui_shield.getWidth(), 0);
        if(this.player == game.getCurrentPlayer())
        {
            graphics.drawString("1", (screenW - Assets.ui_shield.getWidth() / 2), 256 / 2, numberStyle);
        }
        else if(this.player == game.getCurrentPlayer2())
        {
            graphics.drawString("2", (screenW - Assets.ui_shield.getWidth() / 2), 256 / 2, numberStyle);
        }

        graphics.drawImage(Assets.ui_shield, -32, (int)(screenH * 0.12));
        graphics.drawImage(Assets.ui_shield, -32, (int)(screenH * 0.37));
        graphics.drawImage(Assets.ui_shield, -32, (int)(screenH * 0.62));

        graphics.drawImage(Assets.ui_iconAttributes, 16, (int)(screenH * 0.12) + 48);
        graphics.drawImage(Assets.ui_iconSkills, 16, (int)(screenH * 0.37) + 48);
        graphics.drawImage(Assets.ui_iconEquip, 16, (int)(screenH * 0.62) + 48);
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

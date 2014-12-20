package src;

/**
 * Created by GreenyNeko on 11.12.2014.
 */

import java.util.List;

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
    private boolean rotation = false;
    private boolean switchActivity = false;

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
                if(Math.inBoundary(touchEvent, 0, 0, 250, 250))
                {
                    //game.setScreen(new LoginScreen(game));
                    switchActivity = true;
                }
            }
        }

        if(game.getRotation() == 0 || game.getRotation() == 270)
        {
            //graphics.switchSizes();
            rotation = true;
        }
        else
        {
            rotation = false;
        }

    }

    @Override
    public void paint(float deltaTime)
    {
        int screenH = game.getGraphics().getHeight(), screenW = game.getGraphics().getWidth();
        Graphics graphics = game.getGraphics();
        graphics.drawImage(Assets.ui_skillSlotLeft,
                (screenW / 2) - (Assets.ui_skillSlot.getWidth() * 3), (screenH - Assets.ui_skillSlot.getHeight() - 16));
        graphics.drawImage(Assets.ui_skillSlot,
                (screenW / 2) - (Assets.ui_skillSlot.getWidth() * 2), (screenH - Assets.ui_skillSlot.getHeight() - 16));
        graphics.drawImage(Assets.ui_skillSlot,
                (screenW / 2) - Assets.ui_skillSlot.getWidth(), (screenH - Assets.ui_skillSlot.getHeight() - 16));
        graphics.drawImage(Assets.ui_skillSlot,
                (screenW / 2), (screenH - Assets.ui_skillSlot.getHeight() - 16));
        graphics.drawImage(Assets.ui_skillSlot,
                (screenW / 2) + Assets.ui_skillSlot.getWidth(), (screenH - Assets.ui_skillSlot.getHeight() - 16));
        graphics.drawImage(Assets.ui_skillSlotRight,
                (screenW / 2) + (Assets.ui_skillSlot.getWidth() * 2), (screenH - Assets.ui_skillSlot.getHeight() - 16));
        graphics.drawImage(Assets.skill_fireball, (int)(screenW * 0.035), (int)(screenH * 0.875));

        Paint textColor = new Paint();
        textColor.setARGB(250, 250, 250, 0);
        if(rotation)
        {
            graphics.drawString("90° or 270° Rotate", 50, 50, textColor);
        }
        else
        {
            graphics.drawString("0° or 180° Rotate", 300, 50, textColor);
        }

        if(switchActivity)
        {
            graphics.drawString("Switching to next activity", 50, 100, textColor);
        }
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

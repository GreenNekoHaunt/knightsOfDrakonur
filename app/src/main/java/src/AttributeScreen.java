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

import src.entity.Role;
import src.mechanic.Attribute;
import util.LocaleStringBuilder;
import util.Math;

public class AttributeScreen extends Screen
{
    public int ticks = 0;
    private int roleOffset = 0;
    Attribute[] playerImportantAttr = new Attribute[5];

    public AttributeScreen(Game game)
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
        int screenH = game.getGraphics().getHeight(), screenW = game.getGraphics().getWidth();
        Graphics graphics = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        if(game.getCurrentPlayer().getRole() == Role.WARRIOR)
        {
            roleOffset = 0;
        }
        else if(game.getCurrentPlayer().getRole() == Role.SCOUT)
        {
            roleOffset = 4;
        }
        else if(game.getCurrentPlayer().getRole() == Role.MAGE)
        {
            roleOffset = 8;
        }

        playerImportantAttr[0] = Attribute.UTILITY;
        for(int i = 0; i < 4; i++)
        {
            playerImportantAttr[i + 1] = Attribute.values()[i + 4 + this.roleOffset];
        }

        for(int i = 0; i < touchEvents.size(); i++)
        {
            TouchEvent touchEvent = touchEvents.get(i);
            if(touchEvent.type == TouchEvent.TOUCH_UP)
            {
                if(Math.inBoundary(touchEvent,
                        (screenW / 2) - (int)(Assets.ui_skillSlot.getWidth() * 1.5),
                        (int)(screenH * 0.04), 128, 128))
                {
                    game.getCurrentPlayer().setRole(Role.WARRIOR);
                    this.resetPlayerAttributes();
                }
                else if(Math.inBoundary(touchEvent,
                        (screenW / 2) - (int)(Assets.ui_skillSlot.getWidth() * 0.5),
                        (int)(screenH * 0.04), 128, 128))
                {
                    game.getCurrentPlayer().setRole(Role.SCOUT);
                    this.resetPlayerAttributes();
                }
                else if(Math.inBoundary(touchEvent,
                        (screenW / 2) + (int)(Assets.ui_skillSlot.getWidth() * 0.5),
                        (int)(screenH * 0.04), 128, 128))
                {
                    game.getCurrentPlayer().setRole(Role.MAGE);
                    this.resetPlayerAttributes();
                }
                for(int j = 0; j < 5; j++)
                {
                    if (Math.inBoundary(touchEvent,
                            (screenW / 2) - Assets.ui_barRect.getWidth() / 2 - 96,
                            (int) (screenH * 0.2) + 164 * j, 96, 96))
                    {
                        Attribute attr = playerImportantAttr[j];
                        if (game.getCurrentPlayer().getAttributeStat(attr) > 0)
                        {
                            game.getCurrentPlayer().decreaseAttribute(attr, 1);
                            game.getCurrentPlayer().increaseUnusedAttributePoints(1);
                        }
                    }
                    else if (Math.inBoundary(touchEvent,
                            (screenW / 2) + Assets.ui_barRect.getWidth() / 2,
                            (int) (screenH * 0.2) + 164 * j, 96, 96))
                    {
                        Attribute attr = playerImportantAttr[j];
                        if (game.getCurrentPlayer().getUnusedAttributePoints() > 0)
                        {
                            game.getCurrentPlayer().increaseAttribute(attr, 1);
                            game.getCurrentPlayer().decreaseUnusedAttributePoints(1);
                        }
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
        Paint textStyle = new Paint();
        textStyle.setTextAlign(Paint.Align.LEFT);
        textStyle.setTextSize(36);
        textStyle.setShadowLayer(1.0f, 1.0f, 1.0f, 0xFFFFFFFF);
        textStyle.setARGB(255, 255, 255, 255);

        graphics.drawImage(Assets.ui_skillSlotLeft,
                (screenW / 2) - (int)(Assets.ui_skillSlot.getWidth() * 1.5),
                (int)(screenH * 0.04));
        graphics.drawImage(Assets.ui_skillSlot,
                (screenW / 2) - (int)(Assets.ui_skillSlot.getWidth() * 0.5),
                (int)(screenH * 0.04));
        graphics.drawImage(Assets.ui_skillSlotRight,
                (screenW / 2) + (int)(Assets.ui_skillSlot.getWidth() * 0.5),
                (int)(screenH * 0.04));

        // Create an array of attribute names according to the player.
        String[] locales = new String[6];
        locales[0] = Attribute.values()[3].getLocaleId();
        for(int i = 0; i < 4; i++)
        {
            locales[i + 1] = Attribute.values()[i + 4 + this.roleOffset].getLocaleId();
        }

        // Draw bars
        for(int i = 0; i < 5; i++)
        {
            int barPos = (screenW / 2) - Assets.ui_barRect.getWidth() / 2;
            graphics.drawScaledImage(Assets.ui_barRect,
                    barPos,
                    (int)(screenH * 0.2) + 164 * i, 500, 96, 0, 0, 500, 96);
            graphics.drawScaledImage(Assets.ui_upgradeArrows,
                    barPos - 96, (int)(screenH * 0.2) + 164 * i,
                    96, 96, 0, 48, 48, 48);
            graphics.drawScaledImage(Assets.ui_upgradeArrows,
                    barPos + Assets.ui_barRect.getWidth(),
                    (int)(screenH * 0.2) + 164 * i, 96, 96, 0, 0, 48, 48);
            String str = (new LocaleStringBuilder(game))
                    .addLocaleString(locales[i]).finalizeString();
            graphics.drawString(str, barPos, (int)(screenH * 0.2) + 164 * i - 16,
                    textStyle);
        }

        // Fill bars
        for(int i = 0; i < 5; i++)
        {
            int attrStr = game.getCurrentPlayer().getAttributeStat(playerImportantAttr[i]);
            int imagePartX = 9 + (i + this.roleOffset) * 3;
            if(i == 0)
            {
                imagePartX = 9;
            }
            graphics.drawScaledImage(Assets.ui_barFill,
                    (screenW / 2) - Assets.ui_barRect.getWidth() / 2 + 10,
                    (int)(screenH * 0.2) + 164 * i + 10,
                    attrStr, 76, imagePartX, 0, 3, 76);
        }

        Paint textStylePoints = new Paint();
        textStylePoints.setTextAlign(Paint.Align.CENTER);
        textStylePoints.setTextSize(36);
        textStylePoints.setShadowLayer(1.0f, 1.0f, 1.0f, 0xFFC8C864);
        textStylePoints.setARGB(255, 255, 255, 200);
        graphics.drawString("Unused Attribute Points",
                (screenW / 2), screenH - 132, textStylePoints);
        graphics.drawString(String.valueOf(game.getCurrentPlayer().getUnusedAttributePoints()),
                (screenW / 2), screenH - 88, textStylePoints);
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
        game.setScreen(new CharacterScreen(game));
    }

    public void resetPlayerAttributes()
    {
        int attributes = 0;
        for(int i = 0; i < Attribute.values().length - 3; i++)
        {
            Attribute currAttr = Attribute.values()[i + 3];
            int attrValue = game.getCurrentPlayer().getAttributeStat(currAttr);
            game.getCurrentPlayer().decreaseAttribute(currAttr, attrValue);
            attributes += attrValue;
        }
        game.getCurrentPlayer().increaseUnusedAttributePoints(attributes);
    }
}

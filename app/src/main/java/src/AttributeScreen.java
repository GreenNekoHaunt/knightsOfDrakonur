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

import src.entity.Player;
import src.entity.Role;
import src.mechanic.Attribute;
import src.mechanic.StatUnlock;
import util.LocaleStringBuilder;
import util.Math;

public class AttributeScreen extends Screen
{
    private int roleOffset = 0;
    Attribute[] playerImportantAttr = new Attribute[5];
    private Player player;

    public AttributeScreen(Game game, Player player)
    {
        super(game);
        this.player = player;
        if(this.player.getRole() == Role.WARRIOR)
        {
            roleOffset = 0;
        }
        else if(this.player.getRole() == Role.SCOUT)
        {
            roleOffset = 4;
        }
        else if(this.player.getRole() == Role.MAGE)
        {
            roleOffset = 8;
        }
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
                    if(!player.isSecondaryRoleLocked())
                    {
                        if(player.getRole() != Role.WARRIOR
                                && player.getSecondaryRole() != Role.WARRIOR)
                        {
                            this.resetPlayerSecondaryAttributes(this.player.getSecondaryRole());
                            player.setSecondaryRole(Role.WARRIOR);
                        }
                        roleOffset = 0;
                    }
                }
                else if(Math.inBoundary(touchEvent,
                        (screenW / 2) - (int)(Assets.ui_skillSlot.getWidth() * 0.5),
                        (int)(screenH * 0.04), 128, 128))
                {
                    if(!player.isSecondaryRoleLocked())
                    {
                        if(player.getRole() != Role.SCOUT
                                   && player.getSecondaryRole() != Role.SCOUT)
                        {
                            this.resetPlayerSecondaryAttributes(this.player.getSecondaryRole());
                            player.setSecondaryRole(Role.SCOUT);
                        }
                        roleOffset = 4;
                    }
                }
                else if(Math.inBoundary(touchEvent,
                        (screenW / 2) + (int)(Assets.ui_skillSlot.getWidth() * 0.5),
                        (int)(screenH * 0.04), 128, 128))
                {
                    if(!player.isSecondaryRoleLocked())
                    {
                        if(player.getRole() != Role.MAGE && player.getSecondaryRole() != Role.MAGE)
                        {
                            this.resetPlayerSecondaryAttributes(this.player.getSecondaryRole());
                            player.setSecondaryRole(Role.MAGE);
                        }
                        roleOffset = 8;
                    }
                }
                for(int j = 0; j < 5; j++)
                {
                    if (Math.inBoundary(touchEvent,
                            (screenW / 2) - Assets.ui_barRect.getWidth() / 2 - 96,
                            (int) (screenH * 0.2) + 164 * j, 96, 96))
                    {
                        Attribute attr = playerImportantAttr[j];
                        if (this.player.getAttributeStat(attr) > 0)
                        {
                            this.player.decreaseAttribute(attr, 1);
                            this.player.increaseUnusedAttributePoints(1);
                        }
                    }
                    else if (Math.inBoundary(touchEvent,
                            (screenW / 2) + Assets.ui_barRect.getWidth() / 2,
                            (int) (screenH * 0.2) + 164 * j, 96, 96))
                    {
                        Attribute attr = playerImportantAttr[j];
                        if (this.player.getUnusedAttributePoints() > 0)
                        {
                            this.player.increaseAttribute(attr, 1);
                            this.player.decreaseUnusedAttributePoints(1);
                        }
                    }
                }
            }
        }
        StatUnlock.updateUnlocks(game);
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

        if(!player.isSecondaryRoleLocked())
        {
            graphics.drawImage(Assets.ui_skillSlotLeft,
                    (screenW / 2) - (int) (Assets.ui_skillSlot.getWidth() * 1.5),
                    (int) (screenH * 0.04));
            graphics.drawImage(Assets.ui_skillSlot,
                    (screenW / 2) - (int) (Assets.ui_skillSlot.getWidth() * 0.5),
                    (int) (screenH * 0.04));
            graphics.drawImage(Assets.ui_skillSlotRight,
                    (screenW / 2) + (int) (Assets.ui_skillSlot.getWidth() * 0.5),
                    (int) (screenH * 0.04));
        }

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
            int attrStr = this.player.getAttributeStat(playerImportantAttr[i]);
            int imagePartX = 9 + (i + this.roleOffset) * 3;
            if(i == 0)
            {
                imagePartX = 9;
            }
            for(int j = 0; j < attrStr; j++)
            {
                graphics.drawScaledImage(Assets.ui_barFill,
                        (screenW / 2) - Assets.ui_barRect.getWidth() / 2 + 10 + j,
                        (int) (screenH * 0.2) + 164 * i + 10,
                        1, 76, imagePartX, 0, 3, 76);
            }
        }

        Paint textStylePoints = new Paint();
        textStylePoints.setTextAlign(Paint.Align.CENTER);
        textStylePoints.setTextSize(36);
        textStylePoints.setShadowLayer(1.0f, 1.0f, 1.0f, 0xFFC8C864);
        textStylePoints.setARGB(255, 255, 255, 200);
        graphics.drawString("Unused Attribute Points",
                (screenW / 2), screenH - 132, textStylePoints);
        graphics.drawString(String.valueOf(this.player.getUnusedAttributePoints()),
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
        game.setScreen(new CharacterScreen(game, this.player));
    }

    public void resetPlayerSecondaryAttributes(Role role)
    {
        int attributes = 0;
        for(int i = 0; i < Attribute.values().length - 3; i++)
        {
            Attribute currAttr = Attribute.values()[i + 3];
            if(currAttr.getReqRole() == role)
            {
                int attrValue = this.player.getAttributeStat(currAttr);
                this.player.decreaseAttribute(currAttr, attrValue);
                attributes += attrValue;
            }
        }
        this.player.increaseUnusedAttributePoints(attributes);
    }
}

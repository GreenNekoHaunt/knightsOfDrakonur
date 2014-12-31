package src;

/**
 * Created by GreenyNeko on 11.12.2014.
 */

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;

import com.kod.knightsofdrakonur.framework.Game;
import com.kod.knightsofdrakonur.framework.Graphics;
import com.kod.knightsofdrakonur.framework.Input.TouchEvent;
import com.kod.knightsofdrakonur.framework.Screen;

import java.util.List;

import src.entity.Player;
import src.skills.Skill;
import util.Math;

public class SkillScreen extends Screen
{
    private int ticks = 0;
    private int internScrollBarPos = 0;
    private int realScrollBarPos = 0;
    private int scrollBarMin = 0;
    private int scrollBarMax = (Skill.skillCount - 1) * 128;
    private Player player;
    private Skill dragSkill;
    private boolean dragging;
    private int dragX;
    private int dragY;
    private int prevSlot;

    public SkillScreen(Game game)
    {
        super(game);
    }

    @Override
    public void update(float deltaTime)
    {
        player = game.getCurrentPlayer();
        player.readySkills("");
        int screenH = game.getGraphics().getHeight(), screenW = game.getGraphics().getWidth();
        Graphics graphics = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for(int i = 0; i < touchEvents.size(); i++)
        {
            int startY = 0;
            TouchEvent touchEvent = touchEvents.get(i);
            if(Math.inBoundary(touchEvent, screenW - 48, 0, screenW,
                    screenH - 128))
            {
                if(touchEvent.y > realScrollBarPos + 64)
                {
                    internScrollBarPos += 32;
                }
                else
                {
                    internScrollBarPos -= 32;
                }
            }
            if(touchEvent.type == TouchEvent.TOUCH_DOWN)
            {
                if(Math.inBoundary(touchEvent, 0, 0, screenW - 48, 1152))
                {
                    int skillSelected = ((touchEvent.y + internScrollBarPos) / 128) + 1;
                    if(skillSelected < Skill.skillCount)
                    {
                        this.dragSkill = Skill.skills.get(skillSelected).getCopy();
                        this.dragging = true;
                        this.prevSlot = -1;
                    }
                }
                else if(Math.inBoundary(touchEvent, 0, screenH - 128, 768, 128))
                {
                    this.dragSkill = player.getSkillOnSlot(touchEvent.x / 128);
                    this.dragging = true;
                    player.setSkillSlot(touchEvent.x / 128, Skill.none);
                    this.prevSlot = (touchEvent.x / 128);
                }
            }
            if(touchEvent.type == TouchEvent.TOUCH_DRAGGED)
            {
                if(this.dragging)
                {
                    this.dragX = touchEvent.x;
                    this.dragY = touchEvent.y;
                    if(Math.inBoundary(touchEvent, 0, screenH - 128, 768, 128))
                    {
                        if(player.getSkillOnSlot(touchEvent.x / 128) != Skill.none)
                        {
                            player.setSkillSlot(this.prevSlot,
                                    player.getSkillOnSlot(touchEvent.x / 128));
                            player.setSkillSlot((touchEvent.x / 128), Skill.none);
                        }
                    }
                }
                this.prevSlot = (touchEvent.x / 128);
            }
            if(touchEvent.type == touchEvent.TOUCH_UP)
            {
                if(this.dragging)
                {
                    if(Math.inBoundary(touchEvent, 0, screenH - 128, 768, 128))
                    {
                        player.setSkillSlot((touchEvent.x / 128), this.dragSkill);
                    }
                    this.dragging = false;
                }
            }

            if(internScrollBarPos < scrollBarMin)
            {
                internScrollBarPos = scrollBarMin;
            }
            else if(internScrollBarPos > scrollBarMax)
            {
                internScrollBarPos = scrollBarMax;
            }
            realScrollBarPos = (int)(((double)internScrollBarPos / (double)scrollBarMax)
                    * (screenH - 352));
        }
    }

    @Override
    public void paint(float deltaTime)
    {
        int screenH = game.getGraphics().getHeight(), screenW = game.getGraphics().getWidth();
        Graphics graphics = game.getGraphics();
        graphics.clearScreen(0x459AFF);

        Paint titleStyle = new Paint();
        Paint shortDescStyle = new Paint();
        titleStyle.setTextAlign(Paint.Align.LEFT);
        titleStyle.setTextLocale(game.getLocale());
        titleStyle.setTextSize(36);
        titleStyle.setARGB(255, 255, 200, 150);
        shortDescStyle.setTextAlign(Paint.Align.LEFT);
        shortDescStyle.setTextLocale(game.getLocale());
        shortDescStyle.setTextSize(24);
        shortDescStyle.setARGB(255, 255, 230, 200);
        Paint iconStyle = new Paint(shortDescStyle);
        iconStyle.setTextAlign(Paint.Align.RIGHT);

        // Draw Skill list
        int x = -internScrollBarPos;
        for(int i = 1; x < screenH - 128; i++)
        {
            graphics.drawScaledImage(Assets.ui_skillBar, 0, x, screenW - 48, 128, 0, 0, 256, 128);
            // Draw Skill icons
            try
            {
                Skill skill = Skill.getSkillById(i);
                if(skill != null)
                {
                    skill.draw(game, graphics, 16, x + 8, 0);
                    graphics.drawString((Context) game, skill.getNameId(), 160, x + 52,
                            titleStyle);
                    int iconY = 168; //140 - 96 = 44
                    if(skill.getManaCost() > 0)
                    {
                        graphics.drawImage(Assets.ui_iconMana, screenW - iconY, x + 16);
                        graphics.drawRawString(String.valueOf(skill.getManaCost()),
                                screenW - (iconY - 72), x + 44, iconStyle);
                        iconY += 80;
                    }
                    if(skill.getCooldown() > 0)
                    {
                        graphics.drawImage(Assets.ui_iconCooldown, screenW - iconY, x + 16);
                        graphics.drawRawString(String.valueOf(skill.getCooldown()),
                                screenW - (iconY - 72), x + 44, iconStyle);
                    }
                    graphics.drawString((Context)game, skill.getShortDescId(), 160,
                            x + 92, shortDescStyle);
                }
            }
            catch(IndexOutOfBoundsException e)
            {
                Log.e("KOD", "Trying to access skill with the id \'" + String.valueOf(i) + "\'");
            }
            x += 128;
        }

        // Draw Scrollbar
        graphics.drawImage(Assets.ui_scrollBarArrows, screenW - 48, 0);
        graphics.drawScaledImage(Assets.ui_scrollBar, screenW - 48, 48, 48, screenH - 176, 0, 0, 48,
                224);
        graphics.drawImage(Assets.ui_scrollBarArrows, screenW - 48, screenH - 176, 0, 48, 48, 48);
        graphics.drawImage(Assets.ui_scrollBarCursor, screenW - 48, realScrollBarPos + 48);

        // Draw the skill interface
        graphics.drawImage(Assets.ui_skillSlotLeft,
                0,
                screenH - 128);
        for(int i = 0; i < 4; i++)
        {
            int offset = i + 1;
            graphics.drawImage(Assets.ui_skillSlot,
                    Assets.ui_skillSlot.getWidth() * offset,
                    screenH - 128);
        }
        graphics.drawImage(Assets.ui_skillSlotRight,
                Assets.ui_skillSlot.getWidth() * 5,
                screenH - 128);

        for(int i = 0; i < 6; i++)
        {
            player.getSkillOnSlot(i).draw(game, graphics,
                    (Assets.ui_skillSlot.getWidth() * i) + 8,
                    (int)(screenH - 128) + 8, 0);
        }

        if(this.dragging)
        {
            this.dragSkill.draw(game, graphics, this.dragX, this.dragY, 0);
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
    public void onBackPressed()
    {
        game.setScreen(new MenuScreen(game));
    }
}

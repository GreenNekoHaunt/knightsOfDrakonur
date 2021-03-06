package src;

/**
 * Created by GreenyNeko on 11.12.2014.
 */

import android.graphics.Paint;
import android.util.Log;

import com.kod.knightsofdrakonur.framework.Game;
import com.kod.knightsofdrakonur.framework.Graphics;
import com.kod.knightsofdrakonur.framework.Input.TouchEvent;
import com.kod.knightsofdrakonur.framework.Screen;

import java.util.ArrayList;
import java.util.List;

import src.entity.Player;
import src.skills.Skill;
import util.Math;

public class SkillScreen extends Screen
{
    private int ticks = 0;
    private ArrayList<Skill> unlockedSkills = new ArrayList<Skill>();
    private int internScrollBarPos = 0;
    private int realScrollBarPos = 0;
    private int scrollBarMin = 0;
    private int scrollBarMax = 0;
    private Player player;
    private Skill dragSkill;
    private boolean dragging = false;
    private boolean drawDragging = false;
    private int dragX;
    private int dragY;
    private int prevSlot;

    public SkillScreen(Game game, Player player)
    {
        super(game);
        this.player = player;
        this.scrollBarMax = this.player.getLearnedSkillsAmount() * 128;
        for(int i = 0; i < Skill.skillCount; i++)
        {
            if(player.hasLearnedSkill(Skill.getSkillById(i)))
            {
                unlockedSkills.add(Skill.getSkillById(i));
            }
        }
    }

    @Override
    /* Called whenever the activity updates.
     *
     * @param float deltaTime - the time that has passed.
     */
    public void update(float deltaTime)
    {
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
                    int skillSelected = ((touchEvent.y + internScrollBarPos) / 128);
                    if (skillSelected < player.getLearnedSkillsAmount())
                    {
                        if (player.isSkillEquipped(unlockedSkills.get(skillSelected)))
                        {
                            // Inform the player that more than one skill cannot be put on the
                            // skill bar.
                        }
                        else
                        {
                            this.dragSkill = unlockedSkills.get(skillSelected).getCopy();
                            this.dragging = true;
                            this.prevSlot = -1;
                        }
                    }
                }
                else if(Math.inBoundary(touchEvent, 0, screenH - 128, 768, 128))
                {
                    if(!player.isSlotLocked(touchEvent.x / 128))
                    {
                        this.dragSkill = player.getSkillOnSlot(touchEvent.x / 128);
                        this.dragging = true;
                        player.setSkillSlot(touchEvent.x / 128, Skill.none);
                        this.prevSlot = (touchEvent.x / 128);
                    }
                }
            }
            if(touchEvent.type == TouchEvent.TOUCH_DRAGGED)
            {
                if(this.dragging)
                {
                    this.dragX = touchEvent.x;
                    this.dragY = touchEvent.y;
                    if(!player.isSlotLocked(touchEvent.x / 128))
                    {
                        this.drawDragging = true;
                        if(Math.inBoundary(touchEvent, 0, screenH - 128, 768, 128))
                        {
                            if (player.getSkillOnSlot(touchEvent.x / 128) != Skill.none)
                            {
                                player.setSkillSlot(this.prevSlot,
                                        player.getSkillOnSlot(touchEvent.x / 128));
                                player.setSkillSlot((touchEvent.x / 128), Skill.none);
                            }
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
                        if(player.isSlotLocked(touchEvent.x / 128))
                        {
                            // Inform the player his level isn't high enough for this skill slot.
                        }
                        else
                        {
                            player.setSkillSlot((touchEvent.x / 128), this.dragSkill);
                        }
                    }
                    this.dragging = false;
                    this.drawDragging = false;
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
    /* Called whenever the screen is updated.
     *
     * @param float deltaTime - the time that has passed since the last update.
     */
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
        for(int i = 0; x < screenH - 128; i++)
        {
            graphics.drawScaledImage(Assets.ui_skillBar, 0, x, screenW - 48, 128, 0, 0, 256, 128);
            // Draw Skill icon
            try
            {
                Skill skill = unlockedSkills.get(i);
                if(skill != null)
                {
                    skill.draw(game, graphics, 16, x + 8, 0);
                    graphics.drawString(skill.getName(game), 160, x + 52,
                            titleStyle);
                    int iconY = 168;
                    if(skill.getManaCost() > 0)
                    {
                        graphics.drawImage(Assets.ui_iconMana, screenW - iconY, x + 16);
                        graphics.drawString(String.valueOf(skill.getManaCost()),
                                screenW - (iconY - 72), x + 44, iconStyle);
                        iconY += 80;
                    }
                    if(skill.getCooldown() > 0)
                    {
                        graphics.drawImage(Assets.ui_iconCooldown, screenW - iconY, x + 16);
                        graphics.drawString(String.valueOf(skill.getCooldown()),
                                screenW - (iconY - 72), x + 44, iconStyle);
                    }
                    graphics.drawString(skill.getShortDesc(game), 160,
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

        if(this.drawDragging)
        {
            this.dragSkill.draw(game, graphics, this.dragX, this.dragY, 0);
        }
    }

    @Override
    /* Called when the activity is paused. */
    public void pause() {

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
}

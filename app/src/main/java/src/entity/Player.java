package src.entity;

import android.graphics.Paint;

import com.kod.knightsofdrakonur.framework.Game;
import com.kod.knightsofdrakonur.framework.Graphics;

import java.util.ArrayList;
import java.util.HashMap;

import src.Assets;
import src.mechanic.ActiveBuff;
import src.mechanic.Attribute;
import src.mechanic.StatUnlock;
import src.skills.Skill;
import util.LocaleStringBuilder;

/**
 * Created by GreenyNeko on 23.12.2014.
 */
public class Player extends Entity
{
    private ArrayList<Skill> learnedSkills = new ArrayList<Skill>();
    private static final int reqXpRate = 27;
    private int xp;
    private int reqXp;
    private int ascensions;
    private int attributePoints;

    public Player(Role role)
    {
        super(role);
        this.xp = 0;
        this.reqXp = (int)(0.25 * this.reqXpRate * (this.getLevel() + 1));
        if(this.getRole() == Role.WARRIOR)
        {
            this.learnSkill(Skill.heavyStrike);
        }
        else if(this.getRole() == Role.SCOUT)
        {
            this.learnSkill(Skill.arrowShot);
        }
        else if(this.getRole() == Role.MAGE)
        {
            this.learnSkill(Skill.fireball);
            this.learnSkill(Skill.icicles);
            this.learnSkill(Skill.throwRocks);
            this.learnSkill(Skill.boltCharge);
        }
    }

    public Player(Role role, String name)
    {
        this(role);
        this.setName(name);
    }

    @Override
    /* This event is called whenever a game tick occured.
     *
     * @param int round - the current round of the battle.
     * @param Player enemy - the enemy of this player.
     * @param boolean hasRoundDelta - Whehter or not the round has changed since the last update.
     */
    public void onUpdate(int round, Entity enemy, boolean hasRoundDelta)
    {
        super.onUpdate(round, enemy, hasRoundDelta);
        if(this.xp >= this.reqXp)
        {
            onLevelUp();
        }
    }

    /* Called whenever the player has a level up.
     * It handles what happens when the player has a level up.
     */
    public void onLevelUp()
    {
        this.xp -= this.reqXp;
        this.setLevel(this.getLevel() + 1);
        this.reqXp = (int)(0.25 * this.reqXpRate * (this.getLevel() + 1));

        this.increaseAttribute(Attribute.RESOURCE, 1);
        this.increaseAttribute(Attribute.VITALITY, 1);
        int resource = this.getAttributeStat(Attribute.RESOURCE) + this.baseResource;
        int vitality = this.getAttributeStat(Attribute.VITALITY) + this.baseVitality;
        this.setMaxMana(resource * (this.getLevel() + 1) * this.getRole().getManaFactor());
        this.setMaxHealth(vitality * (this.getLevel() + 1) * this.getRole().getVitalityFactor());
        this.revive();
        this.attributePoints += 6;
    }

    /* Returns the current experience.
     *
     * @return int - the players experience
     */
    public int getXp()
    {
        return this.xp;
    }

    /* Returns the experience required for the next level up.
     *
     * @return int - the required experience.
     */
    public int getReqXp()
    {
        return this.reqXp;
    }

    /* Returns the number of ascensions.
     *
     * @return int - the number of ascensions.
     *
     * NOTE: This will be removed when more dungeons are added.
     */
    public int getAscensions()
    {
        return this.ascensions;
    }

    /* Returns the amount of attribute points open to set.
     *
     * @return int - the amount of attributes.
     */
    public int getUnusedAttributePoints()
    {
        return this.attributePoints;
    }

    public int getLearnedSkillsAmount()
    {
        return this.learnedSkills.size();
    }

    /* Returns whether or not the player has learned the skill.
     *
     * @param Skill skill - the skill to be checked.
     */
    public boolean hasLearnedSkill(Skill skill)
    {
        for(int i = 0; i < learnedSkills.size(); i++)
        {
            if(skill.getId() == learnedSkills.get(i).getId())
            {
                return true;
            }
        }
        return false;
    }

    /* Sets the number of ascension.
     *
     * NOTE: This will be removed when more dungeons are added.
     */
    public void setAscensions(int amount)
    {
        this.ascensions = amount;
    }

    /* Decreases the amount of attribute points open to set.
     *
     * @param int amount - the amount of points.
     */
    public void decreaseUnusedAttributePoints(int amount)
    {
        this.attributePoints -= amount;
    }

    /* Increases the amount of attribute points open to set.
     *
     * @param int amount - the amount of points.
     */
    public void increaseUnusedAttributePoints(int amount)
    {
        this.attributePoints += amount;
    }

    /* Gives the player experience points.
     *
     * @param int amount - the amount of experience that should be given.
     */
    public void receiveXp(int amount)
    {
        this.xp += amount;
    }

    /* Teaches the player a new skill.
     *
     * @param Skill skill - the skill that should be taught.
     */
    public void learnSkill(Skill skill)
    {
        this.learnedSkills.add(skill);
    }

    /* Makes the player forget a learned skill.
     *
     * @param Skill skill - the skill the player should forget.
     */
    public void forgetSkill(Skill skill)
    {
        this.learnedSkills.remove(skill);
    }

    public void drawAsPlayer(Game game, Graphics graphics, int currentRound)
    {
        int screenH = game.getGraphics().getHeight(), screenW = game.getGraphics().getWidth();
        Paint textStyle = new Paint();
        textStyle.setARGB(250, 0, 0, 0);
        textStyle.setTextAlign(Paint.Align.CENTER);
        textStyle.setTextSize(36.0f);
        // Health Bar
        graphics.drawScaledImage(Assets.ui_barKnife,
                (int)(screenW * 0.02), (int)(screenH * 0.83) - 84,
                400, 48, 0, 0, 500, 96);
        // Mana Bar
        graphics.drawScaledImage(Assets.ui_barRect,
                (int)(screenW * 0.02), (int)(screenH * 0.83) - 36,
                400, 36, 0, 0, 500, 96);
        // Fill Player Health
        for (int i = 0;
             i < (int)(((double)this.getHealth() / (double)this.getMaxHealth()) * 395);
             i++)
        {
            int height = 36;
            int mod = 0;
            if(i > 300)
            {
                height -= (i - 300) * 0.4;
                mod += (i - 300) * 0.4;
            }
            graphics.drawScaledImage(Assets.ui_barFill,
                    (int)(screenW * 0.02) + 8 + i, (int)(screenH * 0.83) - 78 + mod,
                    1, height, 6, 0, 3, 76);
        }
        // Fill Mana Bar
        for(int i = 0;
            i < (int)(((double)this.getMana() / (double)this.getMaxMana()) * 380); i++)
        {
            graphics.drawScaledImage(Assets.ui_barFill,
                    (int)(screenW * 0.02) + 12 + i, (int)(screenH * 0.83) - 30,
                    1, 24, 1, 0, 3, 76);
        }

        textStyle.setTextSize(24);
        graphics.drawString(
                String.valueOf(this.getMana()) + "/" + String.valueOf(this.getMaxMana()),
                (int) (screenW * 0.02) + 12 + 200, (int) (screenH * 0.83) - 12, textStyle);
        textStyle.setTextSize(36);
        graphics.drawString(
                String.valueOf(this.getHealth()) + "/" + String.valueOf(this.getMaxHealth()),
                (int)(screenW * 0.02) + (Assets.ui_barKnife.getWidth() / 2) - 64,
                (int)(screenH * 0.83) - 48, textStyle);
        textStyle.setTextSize(36);
        textStyle.setTextAlign(Paint.Align.LEFT);
        graphics.drawString(this.getName(), (int)(screenW * 0.03), (int)(screenH * 0.72),
                textStyle);
        String labelLevel = (new LocaleStringBuilder(game))
                .addLocaleString("lang.battle.ui.level")
                .addString(" " + String.valueOf(this.getLevel())).finalizeString();
        graphics.drawString(labelLevel, (int) (screenW * 0.03),
                (int) (screenH * 0.75), textStyle);

        // Skill Interface
        graphics.drawImage(Assets.ui_skillSlotLeft,
                (screenW / 2) - Assets.ui_skillSlot.getWidth() * 3,
                (int)(screenH * 0.87));
        for(int i = 0; i < 4; i++)
        {
            int offset = i - 2;
            graphics.drawImage(Assets.ui_skillSlot,
                    (screenW / 2) + Assets.ui_skillSlot.getWidth() * offset,
                    (int)(screenH * 0.87));
        }
        graphics.drawImage(Assets.ui_skillSlotRight,
                (screenW / 2) + Assets.ui_skillSlot.getWidth() * 2,
                (int)(screenH * 0.87));

        for(int i = 0; i < 6; i++)
        {
            int offset = i - 3;
            this.getSkillOnSlot(i).draw(game, graphics,
                    (screenW / 2) + Assets.ui_skillSlot.getWidth() * offset + 8,
                    (int)(screenH * 0.87) + 8, currentRound);
        }

        graphics.drawScaledImage(Assets.ui_barRect,
                (int) (screenW * 0.02), (int) (screenH * 0.83),
                500, 27, 0, 0, 500, 96);
        for (int i = 0;
             i < (int) (((double) this.getXp() / (double) this.getReqXp()) * 480); i++)
        {
            graphics.drawScaledImage(Assets.ui_barFill,
                    (int) (screenW * 0.02) + 10 + i, (int) (screenH * 0.83) + 6,
                    1, 15, 3, 0, 1, 76);
        }
        textStyle.setTextSize(15);
        graphics.drawString(
                String.valueOf(this.getXp()) + "/"
                        + String.valueOf(this.getReqXp()) + "XP",
                (int) (screenW * 0.02 + Assets.ui_barRect.getWidth() / 2),
                (int) (screenH * 0.83) + 20, textStyle);

        // Draw buffs and debuffs
        ArrayList<ActiveBuff> filteredBuffs = new ArrayList<ActiveBuff>();
        for(int i = 0; i < this.buffs.size(); i++)
        {
            if(!filteredBuffs.contains(this.buffs.get(i)))
            {
                filteredBuffs.add(this.buffs.get(i));
            }
        }
        for(int i = 0; i < 5; i++)
        {
            if(i < filteredBuffs.size())
            {
                filteredBuffs.get(i).getBuff().draw(game, graphics,
                        (int)(screenW * 0.02) + 408 + i * 72,
                        (int)(screenH * 0.83) - 72, this.buffs.get(i).getTTL(),
                        this.getActiveBuffStackSize(this.buffs.get(i).getBuff()));
            }
        }
    }
}

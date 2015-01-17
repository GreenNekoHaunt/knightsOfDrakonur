package src.entity;

import java.util.ArrayList;
import java.util.HashMap;

import src.mechanic.ActiveBuff;
import src.mechanic.Attribute;
import src.mechanic.Buff;
import src.skills.Skill;

/**
 * Created by GreenyNeko on 23.12.2014.
 */
public class Player extends Entity
{
    protected HashMap<Attribute, Integer> attributes = new HashMap<Attribute, Integer>();
    private int xp;
    private int reqXp;
    private int ascensions;
    private int attributePoints;

    public Player(Role role)
    {
        super(role);
        this.xp = 0;
        this.reqXp = (int)(0.25 * 27 * (this.getLevel() + 1));
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

        this.reqXp = (int)(0.25 * 27 * (this.getLevel() + 1));
        this.setMaxMana(6 * (this.getLevel() + 1) * this.getRole().getManaFactor());
        this.gainMana(this.getMaxMana());
        this.setMaxHealth(56 * (this.getLevel() + 1) * this.getRole().getVitalityFactor());
        this.heal(this.getMaxHealth());
        this.attributePoints += 6;
        this.reqXp = (int)(0.25 * 27 * (this.getLevel() + 1));
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

    /* Sets the number of ascension.
     *
     * NOTE: This will be removed when more dungeons are added.
     */
    public void setAscensions(int amount)
    {
        this.ascensions = amount;
    }

    /* Gives the player experience points.
     *
     * @param int amount - the amount of experience that should be given.
     */
    public void receiveXp(int amount)
    {
        this.xp += amount;
    }
}

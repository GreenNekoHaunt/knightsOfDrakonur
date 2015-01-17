package src.entity;

import android.util.Log;

import java.util.Random;

import src.mechanic.Attribute;

/**
 * Created by GreenyNeko on 23.12.2014.
 */
public class ComputerPlayer extends Entity
{
    private String nameId;
    private boolean boss;

    public ComputerPlayer(String nameId, int level, Role role)
    {
        super(role);
        this.nameId = nameId;
        this.setLevel(level);
        attributes.put(Attribute.ARMOR, level);
        attributes.put(Attribute.VITALITY, level);
        attributes.put(Attribute.RESOURCE, level);
        attributes.put(Attribute.UTILITY, level);
        if(role == Role.WARRIOR)
        {
            attributes.put(Attribute.STRENGTH, level * 2);
            attributes.put(Attribute.ENDURANCE, level);
            attributes.put(Attribute.STANCE, level);
            attributes.put(Attribute.ARMS, level);
        }
        else if(role == Role.SCOUT)
        {
            attributes.put(Attribute.PRECISION, level * 2);
            attributes.put(Attribute.DEXTERTY, level);
            attributes.put(Attribute.SURVIVAL, level);
            attributes.put(Attribute.INSTINCTS, level);
        }
        else if(role == Role.MAGE)
        {
            attributes.put(Attribute.AIR, level * 2);
            attributes.put(Attribute.WATER, level);
            attributes.put(Attribute.FIRE, level);
            attributes.put(Attribute.EARTH, level);
        }
    }

    public ComputerPlayer(String nameId, int level, Role role, int armor, int vitality,
                          int resource, int utility, int attr1, int attr2, int attr3, int attr4)
    {
        this(nameId, level, role);
        attributes.put(Attribute.ARMOR, armor);
        attributes.put(Attribute.VITALITY, vitality);
        attributes.put(Attribute.RESOURCE, resource);
        attributes.put(Attribute.UTILITY, utility);
        if(role == Role.WARRIOR)
        {
            attributes.put(Attribute.STRENGTH, attr1);
            attributes.put(Attribute.ENDURANCE, attr2);
            attributes.put(Attribute.STANCE, attr3);
            attributes.put(Attribute.ARMS, attr4);
        }
        else if(role == Role.SCOUT)
        {
            attributes.put(Attribute.PRECISION, attr1);
            attributes.put(Attribute.DEXTERTY, attr2);
            attributes.put(Attribute.SURVIVAL, attr3);
            attributes.put(Attribute.INSTINCTS, attr4);
        }
        else if(role == Role.MAGE)
        {
            attributes.put(Attribute.AIR, attr1);
            attributes.put(Attribute.WATER, attr2);
            attributes.put(Attribute.FIRE, attr3);
            attributes.put(Attribute.EARTH, attr4);
        }
    }

    @Override
    /* Called whenever the players health reaches 0.
     *
     * @param Entity enemy - the opponent who has killed this entity.
     */
    public void onDeath(Entity entity)
    {
        super.onDeath(entity);
        if(entity instanceof Player)
        {
            ((Player)entity).receiveXp(((this.getLevel() + 1) * 7) / (entity.getLevel() + 1));
        }
    }

    /* Returns the id for the language file .
     *
     * @return String - the id for the language file.
     */
    public String getNameId()
    {
        return this.nameId;
    }

    /* Returns if this NPC is a boss.
     *
     * @return boolean - whether or not this NPC is a boss.
     */
    public boolean isBoss()
    {
        return this.boss;
    }

    /* Sets this NPC as a boss. */
    public ComputerPlayer setBoss()
    {
        this.boss = true;
        return this;
    }

    /* Takes care of the AI thinking choosing the right skill to activate from it's 6.
     *
     * @param int round - the current round.
     * @param Player enemy - the enemy against which the skill might be used.
     */
    public void chooseSkill(int round, Player enemy)
    {
        // This is where the AI is handled.

        Random random = new Random();
        boolean done = false;
        while(!done)
        {
            int skillId = random.nextInt(6);
            if(this.getSkillOnSlot(skillId).isReady())
            {
                if(!this.isSlotLocked(skillId))
                {
                    if (this.getSkillOnSlot(skillId).getManaCost() <= this.getMana())
                    {
                        this.cast(skillId, round, enemy);
                        done = true;
                    }
                }
            }
        }
    }
}

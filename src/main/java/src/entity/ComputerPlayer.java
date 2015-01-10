package src.entity;

import android.util.Log;

import java.util.Random;

import src.mechanic.Attribute;

/**
 * Created by GreenyNeko on 23.12.2014.
 */
public class ComputerPlayer extends Player
{
    private String nameId;
    private int level;
    private int totalHealth;
    private int totalMana;
    private int health;
    private int mana;

    public ComputerPlayer(String nameId, int level, Role role)
    {
        super(role);
        this.nameId = nameId;
        this.level = level;
    }

    public ComputerPlayer(String nameId, int level, Role role, int armor, int vitality,
                          int resource, int attr1, int attr2, int attr3, int attr4)
    {
        this(nameId, level, role);
        attributes.put(Attribute.ARMOR, 1);
        attributes.put(Attribute.VITALITY, 1);
        attributes.put(Attribute.RESOURCE, 1);
        if(role == Role.WARRIOR)
        {
            attributes.put(Attribute.STRENGTH, 1);
            attributes.put(Attribute.ENDURANCE, 1);
            attributes.put(Attribute.STANCE, 1);
            attributes.put(Attribute.ARMS, 1);
        }
        else if(role == Role.SCOUT)
        {
            attributes.put(Attribute.PRECISION, 1);
            attributes.put(Attribute.DEXTERTY, 1);
            attributes.put(Attribute.SURVIVAL, 1);
            attributes.put(Attribute.INSTINCTS, 1);
        }
        else if(role == Role.MAGE)
        {
            attributes.put(Attribute.AIR, 1);
            attributes.put(Attribute.WATER, 1);
            attributes.put(Attribute.FIRE, 1);
            attributes.put(Attribute.EARTH, 1);
        }
    }

    @Override
    public void onDeath(Player player)
    {
        super.onDeath(player);
        player.receiveXp(((this.getLevel() + 1) * 7) / (player.getLevel() + 1));
    }

    public String getNameId()
    {
        return this.nameId;
    }

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

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
public class Player
{
    protected HashMap<Attribute, Integer> attributes = new HashMap<Attribute, Integer>();
    private Role role;
    private int level;
    private int xp;
    private int reqXp;
    private int totalHealth;
    private int totalMana;
    private int health;
    private int mana;
    private Skill slots[] = new Skill[6];
    private boolean dead;
    private boolean stunned;
    private ArrayList<ActiveBuff> buffs = new ArrayList<ActiveBuff>();

    public Player(Role role)
    {
        this.level = 1;
        this.xp = 0;
        this.reqXp = (int)(0.25 * 27 * (this.level + 1));
        this.totalMana = 6 * (this.level + 1) * role.getManaFactor();
        this.mana = this.totalMana;
        this.totalHealth = 56 * (this.level + 1) * role.getVitalityFactor();
        this.health = this.totalHealth;
        this.role = role;
        for(int i = 0; i < 6; i++)
        {
            if(this.isSlotLocked(i))
            {
                this.setSkillSlot(i, Skill.lockedSkillSlot.getCopy());
            }
            else
            {
                this.setSkillSlot(i, Skill.none.getCopy());
            }
        }

        attributes.put(Attribute.ARMOR, 0);
        attributes.put(Attribute.VITALITY, 0);
        attributes.put(Attribute.RESOURCE, 0);
        attributes.put(Attribute.STRENGTH, 0);
        attributes.put(Attribute.ENDURANCE, 0);
        attributes.put(Attribute.STANCE, 0);
        attributes.put(Attribute.ARMS, 0);
        attributes.put(Attribute.PRECISION, 0);
        attributes.put(Attribute.DEXTERTY, 0);
        attributes.put(Attribute.SURVIVAL, 0);
        attributes.put(Attribute.INSTINCTS, 0);
        attributes.put(Attribute.AIR, 0);
        attributes.put(Attribute.WATER, 0);
        attributes.put(Attribute.FIRE, 0);
        attributes.put(Attribute.EARTH, 0);
    }

    public void onUpdate(int round, Player enemy, boolean hasRoundDelta)
    {
        // Whenever the game updates
        for(int i = 0; i < 6; i++)
        {
            if(!this.isSlotLocked(i) && this.slots[i].getId() == Skill.lockedSkillSlot.getId())
            {
                this.slots[i] = Skill.none.getCopy();
            }
            this.slots[i].onUpdate(round, this);
        }
        if(health <= 0)
        {
            onDeath(enemy);
        }
        else if(this.health > this.totalHealth)
        {
            this.health = this.totalHealth;
        }
        if(this.mana > this.totalMana)
        {
            this.mana = this.totalMana;
        }
        if(this.isStunned())
        {
            onStunned();
        }
        if(hasRoundDelta)
        {
            //has the current round changed since last update
            for (int i = 0; i < this.buffs.size(); i++) {
                this.buffs.get(i).tick();
            }
        }
    }

    public void onDeath(Player enemy)
    {
        this.dead = true;
    }

    public void onLevelUp()
    {
        this.xp -= this.reqXp;
        this.level++;

        this.reqXp = (int)(0.25 * 27 * (this.level + 1));
        this.totalMana = 6 * (this.level + 1) * role.getManaFactor();
        this.mana = this.totalMana;
        this.totalHealth = 56 * (this.level + 1) * role.getVitalityFactor();
        this.health = this.totalHealth;
        // hardcoded
        this.reqXp = (int)(0.25 * 27 * (this.level + 1));
    }

    public void onStunned() { }

    public Role getRole()
    {
        return this.role;
    }

    public int getLevel()
    {
        return this.level;
    }

    public int getXp()
    {
        return this.xp;
    }

    public int getReqXp()
    {
        return this.reqXp;
    }

    public int getMana()
    {
        return this.mana;
    }

    public int getTotalMana()
    {
        return this.totalMana;
    }

    public int getHealth()
    {
        return this.health;
    }

    public int getTotalHealth()
    {
        return this.totalHealth;
    }

    public boolean isDead()
    {
        return this.dead;
    }

    public boolean isStunned() { return this.stunned; }

    public Skill getSkillOnSlot(int slot)
    {
        return this.slots[slot];
    }

    public void setSkillSlot(int slot, Skill skill)
    {
        this.slots[slot] = skill;
    }

    public void setStunned(Player who, int duration)
    {
        this.stunned = true;
        this.applyBuff(who, Buff.stun, duration);
    }

    public void revive()
    {
        this.health = this.totalHealth;
        this.mana = this.totalMana;
        this.readySkills("");
        this.buffs.clear();
        this.dead = false;
    }

    public void applyBuff(Player who, Buff buff, int duration)
    {
        buff.setCaster(who);
        this.buffs.add(new ActiveBuff(this, buff.getCopy(), duration));
    }

    public void purgeBuff(ActiveBuff buff)
    {
        if(buff.getBuff() == Buff.stun)
        {
            this.stunned = false;
        }
        if(buff.isActive())
        {
            buff.getBuff().onPurge(this);
        }
        this.buffs.remove(buff);
    }

    public void readySkills(String except)
    {

        for(int i = 0; i < 6; i++)
        {
            if(!except.contains(String.valueOf(i)))
            {
                this.slots[i].setReady(true);
            }
        }
    }

    public boolean hasSkillEquipped(Skill skill)
    {
        for(int i = 0; i < this.slots.length; i++)
        {
            if(this.slots[i].getId() == skill.getId())
            {
                return true;
            }
        }
        return false;
    }

    public boolean isSlotLocked(int slot)
    {
        switch(slot)
        {
            case 5:
                return this.level < 10;
            case 4:
                return this.level < 8;
            case 3:
                return this.level < 5;
            case 2:
                return this.level < 3;
            case 1:
                return this.level < 2;
            default:
                return false;


        }
    }

    public void receiveXp(int amount)
    {
        this.xp += amount;
        if(this.xp > this.reqXp)
        {
            onLevelUp();
        }
    }

    public void takeDamage(Player who, int amount)
    {
        this.health -= amount;
    }

    public void heal(int amount)
    {
        this.health += amount;
    }

    public void takeMana(int amount)
    {
        this.mana -= amount;
    }

    public void gainMana(int amount) { this.mana += amount; }

    public boolean hasEnoughManaFor(Skill skill)
    {
        return this.getMana() > skill.getManaCost();
    }

    public void increaseAttribute(Attribute attr, int value)
    {
        this.attributes.put(attr, this.attributes.get(attr) + value);
    }

    public void decreaseAttribute(Attribute attr, int value)
    {
        this.attributes.put(attr, this.attributes.get(attr) - value);
    }

    public int getAttributeStat(Attribute attr)
    {
        return this.attributes.get(attr);
    }

    public void cast(int slot, int round, Player enemy)
    {
        if(!this.isStunned() && !this.isSlotLocked(slot))
        {
            this.getSkillOnSlot(slot).onTap(round, this, enemy);
        }
    }
}

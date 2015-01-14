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
        attributes.put(Attribute.UTILITY, 0);
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

    /* This event is called whenever a game tick occured.
     *
     * @param int round - the current round of the battle.
     * @param Player enemy - the enemy of this player.
     * @param boolean hasRoundDelta - Whehter or not the round has changed since the last update.
     */
    public void onUpdate(int round, Player enemy, boolean hasRoundDelta)
    {
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
            for (int i = 0; i < this.buffs.size(); i++) {
                this.buffs.get(i).tick();
            }
        }
    }

    /* Called whenever the players health reaches 0.
     *
     * @param Player enemy - the opponent who has killed this player.
     */
    public void onDeath(Player enemy)
    {
        this.dead = true;
    }

    /* Called whenever the player has a level up.
     * It handles what happens when the player has a level up.
     */
    public void onLevelUp()
    {
        this.xp -= this.reqXp;
        this.level++;

        this.reqXp = (int)(0.25 * 27 * (this.level + 1));
        this.totalMana = 6 * (this.level + 1) * role.getManaFactor();
        this.mana = this.totalMana;
        this.totalHealth = 56 * (this.level + 1) * role.getVitalityFactor();
        this.health = this.totalHealth;
        // increase attributes as long attribute screen isn't done yet.
        this.increaseAttribute(Attribute.ARMOR, 1);
        this.increaseAttribute(Attribute.VITALITY, 1);
        this.increaseAttribute(Attribute.RESOURCE, 1);
        this.increaseAttribute(Attribute.UTILITY, 1);
        // player attributes
        // increase as long as you can't choose your role.
        //if(this.getRole() == Role.WARRIOR)
        //{
            this.increaseAttribute(Attribute.STRENGTH, 1);
            this.increaseAttribute(Attribute.ENDURANCE, 1);
            this.increaseAttribute(Attribute.STANCE, 1);
            this.increaseAttribute(Attribute.ARMS, 1);
        //}
        //else if(this.getRole() == Role.SCOUT)
        //{
            this.increaseAttribute(Attribute.PRECISION, 1);
            this.increaseAttribute(Attribute.DEXTERTY, 1);
            this.increaseAttribute(Attribute.SURVIVAL, 1);
            this.increaseAttribute(Attribute.INSTINCTS, 1);
        //}
        //else if(this.getRole() == Role.MAGE)
        //{
            this.increaseAttribute(Attribute.AIR, 1);
            this.increaseAttribute(Attribute.WATER, 1);
            this.increaseAttribute(Attribute.FIRE, 1);
            this.increaseAttribute(Attribute.EARTH, 1);
        //}
        // hardcoded
        this.reqXp = (int)(0.25 * 27 * (this.level + 1));
    }

    /* Called when the player is stunned. */
    public void onStunned() { }

    /* Returns the players role or class.
     *
     * @return Role - the players role.
     */
    public Role getRole()
    {
        return this.role;
    }

    /* Returns the players current level.
     *
     * @return int - the players current level.
     */
    public int getLevel()
    {
        return this.level;
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

    /* Returns the players current mana.
     *
     * @return int - the current mana.
     */
    public int getMana()
    {
        return this.mana;
    }

    /* Return the max value of the players mana.
     *
     * @returns int - the max value.
     */
    public int getMaxMana()
    {
        return this.totalMana;
    }

    /* Returns the current health of the player.
     *
     * @return int - the current health.
     */
    public int getHealth()
    {
        return this.health;
    }

    /* Returns the max value of the players health.
     *
     * @return int - the max value.
     */
    public int getMaxHealth()
    {
        return this.totalHealth;
    }

    /* Returns the Skill on the said slot.
     *
     * @param int slot - the slot that contains the skill.
      *
     * @return Skill - the skill on the said slot.
     */
    public Skill getSkillOnSlot(int slot)
    {
        return this.slots[slot];
    }

    /* Return the strength of the players attribute.
     *
     * @param Attribute attr - the attribute of which the strength should be returned.
     */
    public int getAttributeStat(Attribute attr)
    {
        return this.attributes.get(attr);
    }

    /* Returns true if the player is dead.
     *
     * @return boolean - whether or not the player is dead.
     */
    public boolean isDead()
    {
        return this.dead;
    }

    /* Returns true if the player is stunned.
     *
     * @return boolean - whether or not the player is stunned.
     */
    public boolean isStunned() { return this.stunned; }

    /* Sets the skill contained in the slot.
     *
     * @param int slot - the slot of which the skill should be replaced.
     * @param Skill skill - the skill that should be replace the old one.
     */
    public void setSkillSlot(int slot, Skill skill)
    {
        this.slots[slot] = skill;
    }

    /* Sets whether or not the player should be stunned.
     *
     * @param Player who - the actor who stunned this player.
     * @param int duration - how many rounds the effect should stay.
     */
    public void setStunned(Player who, int duration)
    {
        this.stunned = true;
        this.applyBuff(who, Buff.stun, duration);
    }

    /* Revives the player filling his health, mana and readying his skills. */
    public void revive()
    {
        this.health = this.totalHealth;
        this.mana = this.totalMana;
        this.readySkills("");
        this.buffs.clear();
        this.dead = false;
    }

    /* Applies a buff on the player.
     *
     * @param Player who - the Player who applies the buff.
     * @param Buff buff - the buff that should be applied.
     * @param int duration - the amount of rounds the buff is active.
     */
    public void applyBuff(Player who, Buff buff, int duration)
    {
        buff.setCaster(who);
        this.buffs.add(new ActiveBuff(this, buff.getCopy(), duration));
    }

    /* Purges a buff from the player.
     *
     * @param ActiveBuff buff - the active buff on the player.
     */
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

    /* Makes sure all skills that are not mentioned in the except are ready.
     *
     * @param String except - can contain numbers from 0 to 5 naming which skills should be ignored.
     */
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

    /* Checks whether or not the player has the skill on in his slots.
     *
     * @param Skill skill - the skill which should be searched for.
     *
     * @return boolean - whether or not the skill is on the players skill slots.
     */
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

    /* Checks whether or not the slot is still locked.
     *
     * @param int slot - the slot which should be checked.
     *
     * @return boolean - whether or not the slot is locked.
     */
    public boolean isSlotLocked(int slot)
    {
        switch(slot)
        {
            case 5:
                // TODO: Use constants for the unlock levels.
                return this.level < 10;
            case 4:
                return this.level < 8;
            case 3:
                return this.level < 6;
            case 2:
                return this.level < 3;
            case 1:
                return this.level < 2;
            default:
                return false;


        }
    }

    /* Gives the player experience points.
     *
     * @param int amount - the amount of experience that should be given.
     */
    public void receiveXp(int amount)
    {
        this.xp += amount;
        if(this.xp >= this.reqXp)
        {
            onLevelUp();
        }
    }

    /* Deals damage to the player.
     *
     * @param Player who - the player who deals the damage.
     * @param int amount - the amount of the damage done.
     */
    public void takeDamage(Player who, int amount)
    {
        this.health -= amount;
    }

    /* Heals the player.
     *
     * @param int amount - the amount that should be healed.
     */
    public void heal(int amount)
    {
        this.health += amount;
    }

    /* Takes mana from the player.
     *
     * @param int amount - the amount of mana that should be taken.
     */
    public void takeMana(int amount)
    {
        this.mana -= amount;
    }

    /* Gives the player mana.
     *
     * @param int amount - the amount of mana gained.
     */
    public void gainMana(int amount) { this.mana += amount; }

    /* Checks if the player has enough mana to use this skill.
     *
     * @param Skill skill - the skill that should be used in the calculation
     *
     * @return boolean - whether or not hte player has enough mana.
     */
    public boolean hasEnoughManaFor(Skill skill)
    {
        return this.getMana() >= skill.getManaCost();
    }

    /* Increases the players attribute.
     *
     * @param Attribute attr - the attribute to be increased
     * @param int value - the amount by which the attribute should be increased.
     */
    public void increaseAttribute(Attribute attr, int value)
    {
        this.attributes.put(attr, this.attributes.get(attr) + value);
    }

    /* Decreases the players attribute.
     *
     * @param Attribute attr - the attribute to be decreased.
     * @param int vlaue - the amount by which the attribute should be decreased.
     */
    public void decreaseAttribute(Attribute attr, int value)
    {
        this.attributes.put(attr, this.attributes.get(attr) - value);
    }

    /* Activates the skill on the slot.
     *
     * @param int slot - which skill in the players slots should be activated.
     * @param int round - in which round the skill is activated.
     * @param Player enemy - against who the skill might be used.
     */
    public void cast(int slot, int round, Player enemy)
    {
        Skill skill = this.getSkillOnSlot(slot);
        if(!this.isStunned() && !this.isSlotLocked(slot)) // skill instanceOf SkillStunBreak
        {
            skill.onTap(round, this, enemy);
        }
    }
}

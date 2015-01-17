package src.entity;

import java.util.ArrayList;
import java.util.HashMap;

import src.mechanic.ActiveBuff;
import src.mechanic.Attribute;
import src.mechanic.Buff;
import src.mechanic.BuffStun;
import src.skills.Skill;

/**
 * Created by GreenyNeko on 15.01.2015.
 */
public class Entity
{
    protected HashMap<Attribute, Integer> attributes = new HashMap<Attribute, Integer>();
    private Role role;
    private int level;
    private int totalHealth;
    private int totalMana;
    private int health;
    private int mana;
    private Skill slots[] = new Skill[6];
    private boolean dead;
    private boolean stunned;
    private ArrayList<ActiveBuff> buffs = new ArrayList<ActiveBuff>();

    public Entity(Role role)
    {
        this.role = role;
        this.level = 1;
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
        attributes.put(Attribute.UTILITY, 1);
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

        if(role == Role.WARRIOR)
        {
            attributes.put(Attribute.STRENGTH, 2);
            attributes.put(Attribute.ENDURANCE, 1);
            attributes.put(Attribute.STANCE, 1);
            attributes.put(Attribute.ARMS, 1);
        }
        else if(role == Role.SCOUT)
        {
            attributes.put(Attribute.PRECISION, 2);
            attributes.put(Attribute.DEXTERTY, 1);
            attributes.put(Attribute.SURVIVAL, 1);
            attributes.put(Attribute.INSTINCTS, 1);
        }
        else if(role == Role.MAGE)
        {
            attributes.put(Attribute.AIR, 2);
            attributes.put(Attribute.WATER, 1);
            attributes.put(Attribute.FIRE, 1);
            attributes.put(Attribute.EARTH, 1);
        }
    }

    /* Sets the max mana this entity can have.
     *
     * @param int mana - the max value
     */
    protected void setMaxMana(int mana)
    {
        this.totalMana = mana;
    }

    /* Sets the max health this entity can have.
     *
     * @param int health - the max value
     */
    protected void setMaxHealth(int health)
    {
        this.totalHealth = health;
    }

    protected void setLevel(int level)
    {
        this.level = level;
    }

    /* This event is called whenever a game tick occured.
     *
     * @param int round - the current round of the battle.
     * @param Entity enemy - the enemy of this entity.
     * @param boolean hasRoundDelta - Whehter or not the round has changed since the last update.
     */
    public void onUpdate(int round, Entity enemy, boolean hasRoundDelta)
    {
        for(int i = 0; i < 6; i++)
        {
            if(!this.isSlotLocked(i)
                    && this.getSkillOnSlot(i).getId() == Skill.lockedSkillSlot.getId())
            {
                this.setSkillSlot(i, Skill.none.getCopy());
            }
            this.getSkillOnSlot(i).onUpdate(round, this);
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

    /* Called whenever the entity health reaches 0.
     *
     * @param Entity enemy - the opponent who has killed this entity.
     */
    public void onDeath(Entity enemy)
    {
        this.dead = true;
    }

    /* Called when the entity is stunned. */
    public void onStunned() { }

    /* Returns the entities role or class.
     *
     * @return Role - the entities role.
     */
    public Role getRole()
    {
        return this.role;
    }

    /* Returns the entities current level.
     *
     * @return int - the entities current level.
     */
    public int getLevel()
    {
        return this.level;
    }

    /* Returns the entities current mana.
 *
 * @return int - the current mana.
 */
    public int getMana()
    {
        return this.mana;
    }

    /* Return the max value of the entities mana.
     *
     * @returns int - the max value.
     */
    public int getMaxMana()
    {
        return this.totalMana;
    }

    /* Returns the current health of the entity.
     *
     * @return int - the current health.
     */
    public int getHealth()
    {
        return this.health;
    }

    /* Returns the max value of the entities health.
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

    /* Return the strength of the entities attribute.
     *
     * @param Attribute attr - the attribute of which the strength should be returned.
     */
    public int getAttributeStat(Attribute attr)
    {
        return this.attributes.get(attr);
    }

    /* Returns true if the entity is dead.
     *
     * @return boolean - whether or not the entity is dead.
     */
    public boolean isDead()
    {
        return this.dead;
    }

    /* Returns true if the entity is stunned.
     *
     * @return boolean - whether or not the entity is stunned.
     */
    public boolean isStunned() { return this.stunned; }

    /* Sets the role of the entity afterwards.
     *
     * @param Role role - the new role/class assigned to the player.
     */
    public void setRole(Role role)
    {
        this.role = role;
    }

    /* Sets the skill contained in the slot.
     *
     * @param int slot - the slot of which the skill should be replaced.
     * @param Skill skill - the skill that should be replace the old one.
     */
    public void setSkillSlot(int slot, Skill skill)
    {
        this.slots[slot] = skill;
    }

    /* Sets whether or not the entity should be stunned.
     *
     * @param Entity who - the actor who stunned this entity.
     * @param int duration - how many rounds the effect should stay.
     */
    public void setStunned(Entity who, int duration)
    {
        this.stunned = true;
        this.applyBuff(who, Buff.stun, duration);
    }

    /* Revives the entity filling his health, mana and readying his skills. */
    public void revive()
    {
        this.health = this.totalHealth;
        this.mana = this.totalMana;
        this.readySkills("");
        this.buffs.clear();
        this.dead = false;
    }

    /* Applies a buff on the entity.
     *
     * @param Entity who - the Entity who applies the buff.
     * @param Buff buff - the buff that should be applied.
     * @param int duration - the amount of rounds the buff is active.
     */
    public void applyBuff(Entity who, Buff buff, int duration)
    {
        buff.setCaster(who);
        this.buffs.add(new ActiveBuff(this, buff.getCopy(), duration));
    }

    /* Purges a buff from the entity.
     *
     * @param ActiveBuff buff - the active buff on the entity.
     */
    public void purgeBuff(ActiveBuff buff)
    {
        if(buff.getBuff() instanceof BuffStun)
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

    /* Checks whether or not the entity has the skill on in his slots.
     *
     * @param Skill skill - the skill which should be searched for.
     *
     * @return boolean - whether or not the skill is on the entities skill slots.
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

    /* Deals damage to the entity.
     *
     * @param Entity who - the entity who deals the damage.
     * @param int amount - the amount of the damage done.
     */
    public void takeDamage(Entity who, int amount)
    {
        this.health -= amount;
    }

    /* Heals the entity.
     *
     * @param int amount - the amount that should be healed.
     */
    public void heal(int amount)
    {
        this.health += amount;
    }

    /* Takes mana from the entity.
     *
     * @param int amount - the amount of mana that should be taken.
     */
    public void takeMana(int amount)
    {
        this.mana -= amount;
    }

    /* Gives the entity mana.
     *
     * @param int amount - the amount of mana gained.
     */
    public void gainMana(int amount) { this.mana += amount; }

    /* Checks if the entity has enough mana to use this skill.
     *
     * @param Skill skill - the skill that should be used in the calculation
     *
     * @return boolean - whether or not hte entity has enough mana.
     */
    public boolean hasEnoughManaFor(Skill skill)
    {
        return this.getMana() >= skill.getManaCost();
    }

    /* Increases the entities attribute.
     *
     * @param Attribute attr - the attribute to be increased
     * @param int value - the amount by which the attribute should be increased.
     */
    public void increaseAttribute(Attribute attr, int value)
    {
        this.attributes.put(attr, this.attributes.get(attr) + value);
    }

    /* Decreases the entities attribute.
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
     * @param int slot - which skill in the entities slots should be activated.
     * @param int round - in which round the skill is activated.
     * @param Entity enemy - against who the skill might be used.
     */
    public void cast(int slot, int round, Entity enemy)
    {
        Skill skill = this.getSkillOnSlot(slot);
        if(!this.isStunned() && !this.isSlotLocked(slot)) // skill instanceOf SkillStunBreak
        {
            skill.onTap(round, this, enemy);
        }
    }
}

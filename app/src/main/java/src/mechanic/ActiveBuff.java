package src.mechanic;

import src.entity.Entity;
import src.entity.Role;

/**
 * Created by GreenyNeko on 30.12.2014.
 */
public class ActiveBuff
{
    private int timeToLive;
    private Buff buff;
    private Entity target;
    private boolean active;

    public ActiveBuff()
    {
        this.timeToLive = 0;
        this.buff = Buff.none;
        this.target = new Entity(Role.ANY);
    }

    public ActiveBuff(Entity target, Buff buff, int duration)
    {
        this.target = target;
        this.buff = buff;
        this.timeToLive = duration;
        this.active = true;
        buff.onApply(target);
    }

    /* Returns the buff that is being handled.
     *
     * @return Buff - the buff that is being handled.
     */
    public Buff getBuff()
    {
        return this.buff;
    }

    /* Returns in how many rounds this buff will wear off.
     *
     * @return int - the number of rounds.
     */
    public int getTTL()
    {
        return this.timeToLive;
    }

    /* Returns whether or not the buff is active.
     *
     * @return boolean - whether or not the buff is active.
     */
    public boolean isActive() { return this.active; }

    /* Makes the buff tick if active. */
    public void tick()
    {
        this.timeToLive--;
        this.buff.onTick(this.target);
        if(this.timeToLive < 0)
        {
            this.getBuff().onWearOff(this.target);
            this.active = false;
            target.purgeBuff(this);
        }
    }
}

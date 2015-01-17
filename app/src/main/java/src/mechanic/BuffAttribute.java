package src.mechanic;

import src.entity.Entity;

/**
 * Created by GreenyNeko on 30.12.2014.
 */
public class BuffAttribute extends Buff
{
    private Attribute attr;

    public BuffAttribute()
    {
        super();
    }

    public BuffAttribute(String nameId, String descId, String shortDescId, Attribute attr,
                         int damage)
    {
        super(nameId, descId, shortDescId);
        this.setStrength(damage);
        this.attr = attr;
    }

    @Override
    /* Called when the buff is applied to a player.
     *
     * @param Entity target - the player the buff is applied to.
     */
    public void onApply(Entity target)
    {
        target.increaseAttribute(this.attr, 3);
    }

    @Override
    /* Called when the buff effect should be done for this round.
     *
     * @param Entity target - the player the buff is active on.
     */
    public void onTick(Entity target)
    {

    }

    @Override
    /* Called when the buff is forcefully removed from the player.
     *
     * @param Entity target - the player the buff was active on.
     */
    public void onPurge(Entity target)
    {
        target.decreaseAttribute(this.attr, 3);
    }

    @Override
    /* Called when the buff wears off.
     *
     * @param Entity target - the player the buff was active on.
     */
    public void onWearOff(Entity target)
    {
        target.decreaseAttribute(this.attr, 3);
    }
}

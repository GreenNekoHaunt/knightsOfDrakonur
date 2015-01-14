package src.mechanic;

import src.entity.Player;

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
     * @param Player target - the player the buff is applied to.
     */
    public void onApply(Player target)
    {
        target.increaseAttribute(this.attr, 3);
    }

    @Override
    /* Called when the buff effect should be done for this round.
     *
     * @param Player target - the player the buff is active on.
     */
    public void onTick(Player target)
    {

    }

    @Override
    /* Called when the buff is forcefully removed from the player.
     *
     * @param Player target - the player the buff was active on.
     */
    public void onPurge(Player target)
    {
        target.decreaseAttribute(this.attr, 3);
    }

    @Override
    /* Called when the buff wears off.
     *
     * @param Player target - the player the buff was active on.
     */
    public void onWearOff(Player target)
    {
        target.decreaseAttribute(this.attr, 3);
    }
}

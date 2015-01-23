package src.mechanic;

import src.entity.Entity;

/**
 * Created by GreenyNeko on 30.12.2014.
 */
public class BuffDot extends Buff
{

    public BuffDot()
    {
        super();
    }

    public BuffDot(String nameId, String descId, String shortDescId, int damage)
    {
        super(nameId, descId, shortDescId);
        this.setStrength(damage);
    }

    @Override
    /* Called when the buff is applied to a player.
     *
     * @param Entity target - the player the buff is applied to.
     */
    public void onApply(Entity target)
    {

    }

    @Override
    /* Called when the buff effect should be done for this round.
     *
     * @param Entity target - the player the buff is active on.
     *
     * TODO: Replace hardcoded damage type according to the skill.
     */
    public void onTick(Entity target)
    {
        target.takeDamage(this.getCaster(), this.getStrength(), DamageType.DIRECT);
    }

    @Override
    /* Called when the buff is forcefully removed from the player.
     *
     * @param Entity target - the player the buff was active on.
     */
    public void onPurge(Entity target)
    {

    }

    @Override
    /* Called when the buff wears off.
     *
     * @param Entity target - the player the buff was active on.
     */
    public void onWearOff(Entity target)
    {

    }
}

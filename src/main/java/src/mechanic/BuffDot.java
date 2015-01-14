package src.mechanic;

import src.entity.Player;
import src.entity.Role;

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
     * @param Player target - the player the buff is applied to.
     */
    public void onApply(Player target)
    {

    }

    @Override
    /* Called when the buff effect should be done for this round.
     *
     * @param Player target - the player the buff is active on.
     */
    public void onTick(Player target)
    {
        target.takeDamage(this.getCaster(), this.getStrength());
    }

    @Override
    /* Called when the buff is forcefully removed from the player.
     *
     * @param Player target - the player the buff was active on.
     */
    public void onPurge(Player target)
    {

    }

    @Override
    /* Called when the buff wears off.
     *
     * @param Player target - the player the buff was active on.
     */
    public void onWearOff(Player target)
    {

    }
}

package src.mechanic;

import com.kod.knightsofdrakonur.framework.Image;

import java.util.ArrayList;

import src.entity.Entity;
import src.entity.Player;

/**
 * Created by GreenyNeko on 30.12.2014.
 */
public class BuffStun extends Buff
{
    public BuffStun()
    {
        super();
    }

    public BuffStun(String nameId, String descId, String shortDescId)
    {
        super(nameId, descId, shortDescId);
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
     * @param Player target - the player the buff was active on.
     */
    public void onWearOff(Entity target)
    {

    }
}

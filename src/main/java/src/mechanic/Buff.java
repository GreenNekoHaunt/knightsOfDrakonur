package src.mechanic;

import android.util.Log;

import com.kod.knightsofdrakonur.framework.Image;

import java.util.ArrayList;

import src.entity.Player;

/**
 * Created by GreenyNeko on 30.12.2014.
 */
public class Buff implements Cloneable
{
    public static int skillCount = 0;
    public static ArrayList<Buff> buffs = new ArrayList<Buff>();
    private int id;
    private Image icon;
    private String nameId;
    private String shortDescId;
    private String descId;
    private int[] iconPos = new int[2];
    private int damage;
    private Player caster;
    private String assetPath;

    public static Buff none = new Buff();
    public static Buff stun = (new BuffStun("lang.buff.stun.name", "lang.buff.stun.desc",
            "lang.buff.stun.desc.short"));
    public static Buff bleeding = (new BuffDot("lang.buff.bleeding.name", "lang.buff.bleeding.desc",
            "lang.buff.bleeding.desc.short", 3));
    public static Buff fireBuff = (new BuffAttribute("lang.buff.firebuff.name",
            "lang.buff.firebuff.desc", "lang.buff.firebuff.desc.short", Attribute.FIRE, 3));
    public static Buff waterBuff = (new BuffAttribute("lang.buff.waterbuff.name",
            "lang.buff.waterbuff.desc", "lang.buff.waterbuff.desc.short", Attribute.WATER, 3));
    public static Buff airBuff = (new BuffAttribute("lang.buff.airbuff.name",
            "lang.buff.airbuff.desc", "lang.buff.airbuff.desc.short", Attribute.AIR, 3));

    public Buff()
    {
        this.id = skillCount++;
        buffs.add(this);
    }

    public Buff(String nameId, String descId, String shortDescId)
    {
        super();
        this.nameId = nameId;
        this.descId = descId;
        this.shortDescId = shortDescId;
    }

    /* Called when the buff is applied to a player.
     *
     * @param Player target - the player the buff is applied to.
     */
    public void onApply(Player target)
    {

    }

    /* Called when the buff effect should be done for this round.
     *
     * @param Player target - the player the buff is active on.
     */
    public void onTick(Player target)
    {

    }

    /* Called when the buff is forcefully removed from the player.
     *
     * @param Player target - the player the buff was active on.
     */
    public void onPurge(Player target)
    {

    }

    /* Called when the buff wears off.
     *
     * @param Player target - the player the buff was active on.
     */
    public void onWearOff(Player target)
    {

    }

    /* Sets the player who has applied the buff.
     *
     * @param Player caster - the player who did.
     */
    public Buff setCaster(Player caster)
    {
        this.caster = caster;
        return this;
    }

    /* Sets the strength of the effect.
     *
     * @param int strength - the strength of the effect.
     */
    public Buff setStrength(int strength)
    {
        this.damage = strength;
        return this;
    }

    /* Returns the player who has applied the buff.
     *
     * @return Player - the player who has applied the buff.
     */
    public Player getCaster()
    {
        return this.caster;
    }

    /* Returns the strength of the buff.
     *
     * @return int - the strength of the buff.
     */
    public int getStrength()
    {
        return this.damage;
    }

    /* Return a copy of this buff.
     *
     * @return Buff - a copy of this buff.
     */
    public Buff getCopy()
    {
        try
        {
            return (Buff)super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            Log.e("KOD", "Clone not supported exception");
            return null;
        }
    }
}

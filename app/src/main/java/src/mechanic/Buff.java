package src.mechanic;

import android.graphics.Paint;
import android.util.Log;

import com.kod.knightsofdrakonur.framework.Game;
import com.kod.knightsofdrakonur.framework.Graphics;
import com.kod.knightsofdrakonur.framework.Image;

import java.util.ArrayList;

import src.entity.Entity;

/**
 * Created by GreenyNeko on 30.12.2014.
 */
public class Buff implements Cloneable
{
    public static int buffCount = 0;
    public static ArrayList<Buff> buffs = new ArrayList<Buff>();
    private int id;
    private Image icon;
    private String nameId;
    private String shortDescId;
    private String descId;
    private int[] iconPos = new int[2];
    private int damage;
    private Entity caster;
    private String assetPath;

    public static Buff none = new Buff();
    public static Buff stun = (new BuffStun("lang.buff.stun.name", "lang.buff.stun.desc",
            "lang.buff.stun.desc.short"));
    public static Buff bleeding = (new BuffDot("lang.buff.bleeding.name", "lang.buff.bleeding.desc",
            "lang.buff.bleeding.desc.short", 3));
    public static Buff fireBuff = (new BuffAttribute("lang.buff.firebuff.name",
            "lang.buff.firebuff.desc", "lang.buff.firebuff.desc.short", Attribute.FIRE, 3))
            .setIconPath("gfx/buffs/buffScrollFire.png");
    public static Buff waterBuff = (new BuffAttribute("lang.buff.waterbuff.name",
            "lang.buff.waterbuff.desc", "lang.buff.waterbuff.desc.short", Attribute.WATER, 3))
            .setIconPath("gfx/buffs/buffScrollWater.png");
    public static Buff airBuff = (new BuffAttribute("lang.buff.airbuff.name",
            "lang.buff.airbuff.desc", "lang.buff.airbuff.desc.short", Attribute.AIR, 3))
            .setIconPath("gfx/buffs/buffScrollAir.png");
    public static Buff earthBuff = (new BuffAttribute("lang.buff.earthbuff.name",
            "lang.buff.earthbuff.desc", "lang.buff.earthbuff.desc.short", Attribute.EARTH, 3))
            .setIconPath("gfx/buffs/buffScrollEarth.png");

    public Buff()
    {
        this.id = buffCount++;
        this.assetPath = "gfx/buffs/none.png";
        buffs.add(this);
    }

    public Buff(String nameId, String descId, String shortDescId)
    {
        this();
        this.nameId = nameId;
        this.descId = descId;
        this.shortDescId = shortDescId;
    }

    /* Initializes the graphics for all created skills.
     *
     * @param Graphics graphics - an graphics object.
     */
    public static void initializeGraphics(Graphics graphics)
    {
        for(int i = 0; i < buffCount; i++)
        {
            Buff buff = buffs.get(i);
            buff.setIcon(graphics);
            buffs.set(i, buff);
        }
    }

    /* Called when the buff is applied to a player.
     *
     * @param Entity target - the player the buff is applied to.
     */
    public void onApply(Entity target)
    {

    }

    /* Called when the buff effect should be done for this round.
     *
     * @param Entity target - the player the buff is active on.
     */
    public void onTick(Entity target)
    {

    }

    /* Called when the buff is forcefully removed from the player.
     *
     * @param Entity target - the player the buff was active on.
     */
    public void onPurge(Entity target)
    {

    }

    /* Called when the buff wears off.
     *
     * @param Player target - the player the buff was active on.
     */
    public void onWearOff(Entity target)
    {

    }

    /* Sets the player who has applied the buff.
     *
     * @param Player caster - the player who did.
     */
    public Buff setCaster(Entity caster)
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

    /* Set the icon coordinate when using a spritesheet.
     *
     * @param int x - the x coordinate of the icon.
     * @param int y - the y coordinate of the icon.
     */
    public Buff setIconCoord(int x, int y)
    {
        this.iconPos[0] = x;
        this.iconPos[1] = y;
        return this;
    }

    /* Set the path to the icon.
     *
     * @param String assetPath - the path to the asset file.
     */
    public Buff setIconPath(String assetPath)
    {
        this.assetPath = assetPath;
        return this;
    }

    /* Set the icon of the skill.
     *
     * @param Graphics graphics - the graphics handler.
     */
    public Buff setIcon(Graphics graphics)
    {
        // Sets the icon.
        try
        {
            this.icon = graphics.newImage(this.assetPath, Graphics.ImageFormat.RGB565);
        }
        catch(NullPointerException e)
        {
            Log.e("KOD", "Could not load buff icon for \'" + this.nameId
                                 + "\' at \'" + this.assetPath + "\'");
        }
        catch(RuntimeException e)
        {
            Log.e("KOD", "Could not load buff icon for \'" + this.nameId
                    + "\' at \'" + this.assetPath + "\'");
        }
        return this;
    }

    /* Returns the id of the buff.
     *
     * @return int - the id.
     */
    public int getId()
    {
        return this.id;
    }

    /* Returns the player who has applied the buff.
     *
     * @return Player - the player who has applied the buff.
     */
    public Entity getCaster()
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
            return null;
        }
    }

    /* Draws the buff icon.
     *
     * @param Game game - the game handler
     * @param Graphics graphics - the graphics handler
     * @param int x - the x position
     * @param int y - the y position
     * @param int duration - how many rounds the buff will be active still
     * @param int stacks - the amount of this buff being on the entity.
     */
    public void draw(Game game, Graphics graphics, int x, int y, int duration, int stacks)
    {
        if(this.icon != null)
        {
            Paint numberStyle = new Paint();
            numberStyle.setTextSize(40.0f);
            numberStyle.setARGB(255, 255, 255, 255);
            graphics.drawImage(this.icon, x, y);
            graphics.drawString(String.valueOf(duration), x, y + 16, numberStyle);
            graphics.drawString(String.valueOf(stacks), x + this.icon.getWidth() - 16,
                    y + this.icon.getHeight(), numberStyle);
        }
    }
}

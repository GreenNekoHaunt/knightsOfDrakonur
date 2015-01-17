package src.skills;

import android.graphics.Paint;
import android.util.Log;

import com.kod.knightsofdrakonur.framework.Game;
import com.kod.knightsofdrakonur.framework.Graphics;
import com.kod.knightsofdrakonur.framework.Image;

import java.util.ArrayList;

import src.entity.Entity;
import src.mechanic.Attribute;
import src.mechanic.Buff;
import util.LocaleStringBuilder;

/**
 * Created by GreenyNeko on 23.12.2014.
 */
public class Skill implements Cloneable
{
    public static int skillCount = 0;
    public static ArrayList<Skill> skills = new ArrayList<Skill>();
    private int id;
    private Image icon;
    private String nameId;
    private String shortDescId;
    private String descId;
    private boolean ready;
    private int roundLastCastedIn;
    private int[] iconPos = new int[2];
    private int manaCost;
    private int cooldown;
    private Attribute attr;
    private int damage;
    private String assetPath;

    public static final Skill none = new Skill();
    // Skills for mechanics.
    public static final Skill lockedSkillSlot = new Skill()
            .setIconPath("gfx/ui/skillSlotLocked.png");
    // Actual in-game skills
    public static Skill fireball = (new SkillStandardAttack("lang.skills.fireball.name",
            "lang.skills.fireball.desc", "lang.skills.fireball.desc.short", Attribute.FIRE, 14))
            .setDamageFactor(1.25).setIconPath("gfx/skills/fireball.png").setManaCost(6);
    public static Skill scrollOfFire = (new SkillBuff("lang.skills.scrolloffire.name",
            "lang.skills.scrolloffire.desc", "lang.skills.scrolloffire.desc.short", Attribute.FIRE,
            Buff.fireBuff, 9)).setEffectiveFactor(0).setIconPath("gfx/skills/scrollOfFire.png")
            .setCooldown(45);
    public static final Skill boltCharge = (new SkillStandardAttack("lang.skills.boltcharge.name",
            "lang.skills.boltcharge.desc", "lang.skills.boltcharge.desc.short", Attribute.AIR, 25))
            .setDamageFactor(1.25).setIconPath("gfx/skills/boltCharge.png").setManaCost(10)
            .setCooldown(6);
    public static final Skill scrollOfAir = (new SkillBuff("lang.skills.scrollofair.name",
            "lang.skills.scrollofair.desc", "lang.skills.scrollofair.desc.short", Attribute.AIR,
            Buff.airBuff, 9)).setEffectiveFactor(0).setIconPath("gfx/skills/scrollOfAir.png")
            .setCooldown(45);
    public static final Skill clearMind = (new SkillRefresh("lang.skills.clearmind.name",
            "lang.skills.clearmind.desc", "lang.skills.clearmind.desc.short", Attribute.WATER,
            12)).setIconPath("gfx/skills/clearMind.png").setCooldown(6);
    public static final Skill icicles = (new SkillMultiAttack("lang.skills.icicles.name",
            "lang.skills.icicles.desc", "lang.skills.icicles.desc.short", Attribute.WATER,
            4, 4)).setDamageFactor(0.25).setIconPath("gfx/skills/icicles.png").setManaCost(4);
    public static final Skill scrollOfWater = (new SkillBuff("lang.skills.scrollofwater.name",
            "lang.skills.scrollofwater.desc", "lang.skills.scrollofwater.desc.short",
            Attribute.WATER, Buff.waterBuff, 9)).setEffectiveFactor(0).setCooldown(45)
            .setIconPath("gfx/skills/scrollOfWater.png");
    public static final Skill heavyStrike = (new SkillStandardAttack("lang.skills.heavystrike.name",
            "lang.skills.heavystrike.desc", "lang.skills.heavystrike.desc.short", Attribute.ARMS,
            9)).setDamageFactor(1.25).setIconPath("gfx/skills/heavyStrike.png");
    public static final Skill arrowShot = (new SkillStandardAttack("lang.skills.arrowshot.name",
            "lang.skills.arrowshot.desc", "lang.skills.arrowshot.desc.short", Attribute.PRECISION,
            6)).setDamageFactor(1.25).setIconPath("gfx/skills/arrowShot.png");
    public static final Skill aimedShot = (new SkillStandardAttack("lang.skills.aimedshot.name",
            "lang.skills.aimedshot.desc", "lang.skills.aimedshot.desc.short", Attribute.PRECISION,
            12)).setDamageFactor(1.25).setIconPath("gfx/skills/aimedShot.png").setCooldown(5);
    public static final Skill tripleShot = (new SkillMultiAttack("lang.skills.tripleshot.name",
            "lang.skills.tripleshot.desc", "lang.skills.tripleshot.desc.short", Attribute.DEXTERTY,
            6, 3).setDamageFactor(0.33)).setDiminishingReturns(0.33)
            .setIconPath("gfx/skills/tripleShot.png").setCooldown(3);
    public static final Skill entanglement = (new SkillStun("lang.skills.entanglement.name",
            "lang.skills.entanglement.desc", "lang.skills.entanglement.desc.short",
            Attribute.SURVIVAL, 4)).setEffectiveFactor(0.25)
            .setIconPath("gfx/skills/entanglement.png").setCooldown(6);
    public static final Skill scratch = (new SkillStandardAttack("lang.skills.scratch.name",
            "lang.skills.scratch.desc", "lang.skills.scratch.desc.short", Attribute.INSTINCTS,
            3)).setDamageFactor(1.25).setIconPath("gfx/skills/scratch.png");
    public static final Skill bite = (new SkillStandardAttack("lang.skills.bite.name",
            "lang.skills.bite.desc", "lang.skills.bite.desc.short", Attribute.INSTINCTS, 8))
            .setDamageFactor(1.25).setIconPath("gfx/skills/bite.png");
    public static final Skill sandwich = (new SkillHeal("lang.skills.sandwich.name",
            "lang.skills.sandwich.desc", "lang.skills.sandwich.desc.short", Attribute.UTILITY,
            15)).setEffectiveFactor(1.25).setIconPath("gfx/skills/sandwichTest.png")
            .setCooldown(30);

    public Skill()
    {
        this.id = skillCount++;
        this.ready = true;
        skills.add(this);
    }

    public Skill(String nameId)
    {
        this();
        this.nameId = nameId;
    }

    /* Initializes the graphics for all created skills.
     *
     * @param Graphics graphics - an graphics object.
     */
    public static void initializeGraphics(Graphics graphics)
    {
        for(int i = 0; i < skillCount; i++)
        {
            Skill skill = skills.get(i);
            skill.setIcon(graphics);
            skills.set(i, skill);
        }
    }

    /* Returns the skill matching the id.
     *
     * @param int id - the id of the skill searched.
     *
     * @return Skill - the skill matching the id.
     */
    public static Skill getSkillById(int id)
    {
        // Get a skill with the help of an id.
        return Skill.skills.get(id);
    }

    /* Called when the skill has been tapped.
     *
     * @param int round - the current round of the match.
     * @param Entity player - the player the skill belongs to.
     * @param Entity enemy - the opponent of the player.
     */
    public void onTap(int round, Entity player, Entity enemy)
    {
        // Called whenever this skill has been tapped.
        this.onActivation(round, player, enemy);
    }

    /* Called when the skill gets activated.
     *
     * @param int round - the current round of the match.
     * @param Entity player - the player the skill belongs to.
     * @param Entity enemy - the opponent of the player.
     */
    public void onActivation(int round, Entity player, Entity enemy)
    {
        // Called when the skill was ready
        this.roundLastCastedIn = round;
        this.setReady(false);
    }

    /* Called when the skill is on cooldown.
     *
     * @param int round - the current round of the match.
     * @param Entity player - the player the skill belongs to.
     */
    public void onCooldown(int round, Entity player)
    {
        // Called when the skill has been activated
        // gets called until the skill is ready again.

        if((round - this.roundLastCastedIn) >= this.cooldown)
        {
            this.setReady(true);
        }
    }

    /* Called whenever a game tick has passed.
     *
     * @param int round - the current round of the match.
     * @param Entity player - the player the skill belongs to.
     */
    public void onUpdate(int round, Entity player)
    {
        // Called whenever an update is called.
        if(!this.isReady())
        {
            this.onCooldown(round, player);
        }

    }

    /* Returns the id of this skill.
     *
     * @return int - the id.
     */
    public int getId() { return this.id; }

    /* Returns the language id for the name.
     *
     * @return String - the name id for the locale.
     */
    public String getNameId()
    {
        return this.nameId;
    }

    /* Returns the language id for the short description.
     *
     * @return String - the short description id for the locale.
     */
    public String getShortDescId()
    {
        return this.shortDescId;
    }

    /* Returns the language id for the description.
     *
     * @return String - the description id for the locale.
     */
    public String getDescId()
    {
        return this.descId;
    }

    /* Returns the name of the skill using the locales.
     *
     * @param Game game - the game handler.
     *
     * @return String - the name of the skill.
     */
    public String getName(Game game)
    {
        return (new LocaleStringBuilder(game)).addLocaleString(this.nameId).finalizeString();
    }

    /* Returns the short description of the skill.
     *
     * @param Game game - the game handler.
     *
     * @return String - the short description of the skill.
     */
    public String getShortDesc(Game game)
    {
        return (new LocaleStringBuilder(game)).addLocaleString(this.shortDescId)
                .finalizeString();
    }

    /* Returns the description of the skill.
     *
     * @param Game game - the game handler.
     *
     * @return String - the description of hte skill.
     */
    public String getDesc(Game game)
    {
        return (new LocaleStringBuilder(game)).addLocaleString(this.descId).finalizeString();
    }

    /* Returns whether or not the skill is ready.
     *
     * @return boolean - whether or not the skill is ready.
     */
    public boolean isReady()
    {
        return this.ready;
    }

    /* Returns the damage the skill does or heals.
     *
     * @return int - the damage does or heals.
     */
    public int getDamage()
    {
        return this.damage;
    }

    /* Returns the amount of mana the skill costs.
     *
     * @return int - how much mana this skill costs
     */
    public int getManaCost() { return this.manaCost; }

    /* Returns the amount of rounds the skill has cooldown.
     *
     * @return int - the cooldown in rounds.
     */
    public int getCooldown() { return this.cooldown; }

    /* Return the attribute this skill belongs to.
     *
     * @return Attribute - the attribute.
     */
    public Attribute getAttribute() { return this.attr; }

    /* Returns a copy of this skill.
     *
     * @return Skill - the copy of this skill.
     */
    public Skill getCopy()
    {
        try
        {
            return (Skill)super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            Log.e("KOD", "Clone not supported exception");
            return null;
        }
    }

    /* Sets the name id for the locale.
     *
     * @param String nameId - the name id that should be set.
     */
    public Skill setNameId(String nameId)
    {
        // Sets the name of the skill
        this.nameId = nameId;
        return this;
    }

    /* Sets the short description id for the locale.
     *
     * @param String shortDescId - the short description id that should be set.
     */
    public Skill setShortDesc(String descId)
    {
        // Sets the short description
        this.shortDescId = descId;
        return this;
    }

    /* Sets the description id for the locale.
     *
     * @param String descId - the description id that should be set.
     */
    public Skill setDesc(String descId)
    {
        // Set the description
        this.descId = descId;
        return this;
    }

    /* Set the skill whether or not it's ready or not.
     *
     */
    public Skill setReady(boolean ready)
    {
        // Sets the skill ready to be casted or not.
        this.ready = ready;
        return this;
    }

    /* Sets the damage of the skill.
     *
     * @param int damage - the damage of the skill.
     */
    public Skill setDamage(int damage)
    {
        this.damage = damage;
        return this;
    }

    /* Set the icon coordinate when using a spritesheet.
     *
     * @param int x - the x coordinate of the icon.
     * @param int y - the y coordinate of the icon.
     */
    public Skill setIconCoord(int x, int y)
    {
        // Sets the coordinates of the icon in a skillsheet.
        this.iconPos[0] = x;
        this.iconPos[1] = y;
        return this;
    }

    /* Set the path to the icon.
     *
     * @param String assetPath - the path to the asset file.
     */
    public Skill setIconPath(String assetPath)
    {
        this.assetPath = assetPath;
        return this;
    }

    /* Set the icon of the skill.
     *
     * @param Graphics graphics - the graphics handler.
     */
    public Skill setIcon(Graphics graphics)
    {
        // Sets the icon.
        try
        {
            this.icon = graphics.newImage(assetPath, Graphics.ImageFormat.RGB565);
        }
        catch(NullPointerException e)
        {
            Log.e("KOD", "Could not load skill icon for \'" + this.nameId + "\' at \'" + assetPath + "\'");
        }
        return this;
    }

    /* Sets the cost of mana for this skill.
     *
     * @param int cost - how much the skill should cost.
     */
    public Skill setManaCost(int cost)
    {
        // Sets how much mana the skill activation costs.
        this.manaCost = cost;
        return this;
    }

    /* Sets the cooldown of the skill.
     *
     * @param int cooldown - the cooldown of the skill.
     */
    public Skill setCooldown(int cooldown)
    {
        // Sets the cooldown of the ability (in rounds).
        this.cooldown = cooldown;
        return this;
    }

    /* Set the attribute the skill should be assigned to.
     *
     * @param Attribute attr - the attribute of the skill
     */
    public Skill setAttribute(Attribute attr)
    {
        // Set the skills attribute
        this.attr = attr;
        return this;
    }

    /* Draw the icon of the skill.
     *
     * @param Game game - the game handler.
     * @param Graphics graphics - the graphics handler.
     * @param int x - the x coordinate of the skill icon.
     * @param int y - the y coordinate of the skill icon.
     * @param int currentRound - the current round.
     */
    public void draw(Game game, Graphics graphics, int x, int y, int currentRound)
    {
        if(this.icon != null)
        {
            if (!this.isReady()) {
                Paint textStyle = new Paint();
                int restCooldown = this.cooldown - (currentRound - this.roundLastCastedIn);
                textStyle.setTextSize(40);
                textStyle.setTextAlign(Paint.Align.CENTER);
                textStyle.setARGB(255, 255, 255, 255);
                graphics.drawImage((Image) icon, x, y, iconPos[0] * 112, iconPos[1] * 112, 112, 112);
                graphics.drawRect(x, y, 112, 112, 0xAA000000);
                graphics.drawString(String.valueOf(restCooldown), x + (112 / 2),
                        y + (112 / 2), textStyle);
            } else {
                graphics.drawImage((Image) icon, x, y, iconPos[0] * 112, iconPos[1] * 112, 112, 112);
            }
        }
    }
}

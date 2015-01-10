package src.skills;

import android.graphics.Paint;
import android.util.Log;

import com.kod.knightsofdrakonur.framework.Game;
import com.kod.knightsofdrakonur.framework.Graphics;
import com.kod.knightsofdrakonur.framework.Image;

import java.util.ArrayList;

import src.entity.Player;
import src.mechanic.Attribute;
import src.mechanic.Buff;

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
            Buff.fireBuff, 9)).setIconPath("gfx/skills/scrollOfFire.png").setCooldown(45);
    public static final Skill boltCharge = (new SkillStandardAttack("lang.skills.boltcharge.name",
            "lang.skills.boltcharge.desc", "lang.skills.boltcharge.desc.short", Attribute.AIR, 25))
            .setDamageFactor(1.25).setIconPath("gfx/skills/boltCharge.png").setManaCost(10)
            .setCooldown(6);
    public static final Skill scrollOfAir = (new SkillBuff("lang.skills.scrollofair.name",
            "lang.skills.scrollofair.desc", "lang.skills.scrollofair.desc.short", Attribute.AIR,
            Buff.airBuff, 9)).setIconPath("gfx/skills/scrollOfAir.png").setCooldown(45);
    public static final Skill clearMind = (new SkillRefresh("lang.skills.clearmind.name",
            "lang.skills.clearmind.desc", "lang.skills.clearmind.desc.short", Attribute.WATER,
            12)).setEffectiveFactor(1.25).setIconPath("gfx/skills/clearMind.png").setCooldown(6);
    public static final Skill icicles = (new SkillMultiAttack("lang.skills.icicles.name",
            "lang.skills.icicles.desc", "lang.skills.icicles.desc.short", Attribute.WATER,
            4, 4)).setDamageFactor(1.25).setIconPath("gfx/skills/icicles.png").setManaCost(4);
    public static final Skill scrollOfWater = (new SkillBuff("lang.skills.scrollofwater.name",
            "lang.skills.scrollofwater.desc", "lang.skills.scrollofwater.desc.short",
            Attribute.WATER, Buff.waterBuff, 9)).setCooldown(45)
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
            6, 3).setDamageFactor(1.25)).setDimishingReturns(0.33)
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
            15)).setEffectiveFactor(1.25).setIconPath("gfx/skills/sandwichTest.png").setCooldown(30);

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

    public static void initializeGraphics(Graphics graphics)
    {
        for(int i = 0; i < skillCount; i++)
        {
            Skill skill = skills.get(i);
            skill.setIcon(graphics);
            skills.set(i, skill);
        }
    }

    public static Skill getSkillById(int id)
    {
        // Get a skill with the help of an id.
        return Skill.skills.get(id);
    }

    public void onTap(int round, Player player, Player enemy)
    {
        // Called whenever this skill has been tapped.
        this.onActivation(round, player, enemy);
    }

    public void onActivation(int round, Player player, Player enemy)
    {
        // Called when the skill was ready
        this.roundLastCastedIn = round;
        this.setReady(false);
    }

    public void onCooldown(int round, Player player)
    {
        // Called when the skill has been activated
        // gets called until the skill is ready again.

        if((round - this.roundLastCastedIn) >= this.cooldown)
        {
            this.setReady(true);
        }
    }

    public void onUpdate(int round, Player player)
    {
        // Called whenever an update is called.
        if(!this.isReady())
        {
            this.onCooldown(round, player);
        }

    }

    public int getId() { return this.id; }

    public String getNameId()
    {
        return this.nameId;
    }

    public String getShortDescId()
    {
        return this.shortDescId;
    }

    public String getDescId()
    {
        return this.descId;
    }

    public boolean isReady()
    {
        return this.ready;
    }

    public int getDamage()
    {
        return this.damage;
    }

    public int getManaCost() { return this.manaCost; }

    public int getCooldown() { return this.cooldown; }

    public Attribute getAttribute() { return this.attr; }

    public Skill setNameId(String nameId)
    {
        // Sets the name of the skill
        this.nameId = nameId;
        return this;
    }

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

    public Skill setShortDesc(String descId)
    {
        // Sets the short description
        this.shortDescId = descId;
        return this;
    }

    public Skill setDesc(String descId)
    {
        // Set the description
        this.descId = descId;
        return this;
    }

    public Skill setReady(boolean ready)
    {
        // Sets the skill ready to be casted or not.
        this.ready = ready;
        return this;
    }

    public Skill setDamage(int damage)
    {
        this.damage = damage;
        return this;
    }

    public Skill setIconCoord(int x, int y)
    {
        // Sets the coordinates of the icon in a skillsheet.
        this.iconPos[0] = x;
        this.iconPos[1] = y;
        return this;
    }

    public Skill setIconPath(String assetPath)
    {
        this.assetPath = assetPath;
        return this;
    }

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

    public Skill setManaCost(int cost)
    {
        // Sets how much mana the skill activation costs.
        this.manaCost = cost;
        return this;
    }

    public Skill setCooldown(int cooldown)
    {
        // Sets the cooldown of the ability (in rounds).
        this.cooldown = cooldown;
        return this;
    }

    public Skill setAttribute(Attribute attr)
    {
        // Set the skills attribute
        this.attr = attr;
        return this;
    }

    public void draw(Game game, Graphics graphics, int x, int y, int currentRound)
    {
        // Draws the Skill icon whenever required.
        if(this.icon != null)
        {
            if (!this.isReady()) {
                Paint textStyle = new Paint();
                int restCooldown = this.cooldown - (currentRound - this.roundLastCastedIn);
                textStyle.setTextSize(40);
                textStyle.setTextLocale(game.getLocale());
                textStyle.setTextAlign(Paint.Align.CENTER);
                textStyle.setARGB(255, 255, 255, 255);
                graphics.drawImage((Image) icon, x, y, iconPos[0] * 112, iconPos[1] * 112, 112, 112);
                graphics.drawRect(x, y, 112, 112, 0xAA000000);
                graphics.drawRawString(String.valueOf(restCooldown), x + (112 / 2),
                        y + (112 / 2), textStyle);
            } else {
                graphics.drawImage((Image) icon, x, y, iconPos[0] * 112, iconPos[1] * 112, 112, 112);
            }
        }
    }
}

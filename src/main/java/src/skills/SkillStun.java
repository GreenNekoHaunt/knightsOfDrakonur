package src.skills;

import src.entity.Player;
import src.mechanic.Attribute;

/**
 * Created by GreenyNeko on 23.12.2014.
 */
public class SkillStun extends Skill
{
    private int duration = 0;
    private double effectiveFactor = 0;

    SkillStun(String name, String descId, String shortDescId, Attribute attr, int duration)
    {
        super(name);
        this.duration = duration;
        this.setDesc(descId);
        this.setShortDesc(shortDescId);
        this.setAttribute(attr);
    }

    public SkillStun setEffectiveFactor(double factor)
    {
        this.effectiveFactor = factor;
        return this;
    }

    @Override
    public void onTap(int round, Player player, Player enemy)
    {
        this.onActivation(round, player, enemy);
    }

    @Override
    public void onActivation(int round, Player player, Player enemy)
    {
        // getPlayerInfluence(player);
        super.onActivation(round, player, enemy);
        player.gainMana(this.getDamage());
        int duration = this.getDuration()
                + (int)Math.round(this.effectiveFactor
                * player.getAttributeStat(this.getAttribute()));
        enemy.setStunned(player, duration);
    }

    public int getDuration()
    {
        return this.duration;
    }
}

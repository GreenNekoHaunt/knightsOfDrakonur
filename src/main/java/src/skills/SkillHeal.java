package src.skills;

import android.util.Log;

import src.entity.Player;
import src.mechanic.Attribute;

/**
 * Created by GreenyNeko on 23.12.2014.
 */
public class SkillHeal extends Skill
{
    private double effectiveFactor = 0;

    SkillHeal(String name, String descId, String shortDescId, Attribute attr, int heal)
    {
        super(name);
        this.setDamage(heal);
        this.setDesc(descId);
        this.setShortDesc(shortDescId);
        this.setAttribute(attr);
    }

    public SkillHeal setEffectiveFactor(double factor)
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
        int amount = this.getDamage() + (int)Math.round(this.effectiveFactor
                * player.getAttributeStat(this.getAttribute()));
        player.heal(amount);
        player.takeMana(this.getManaCost());
    }
}

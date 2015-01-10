package src.skills;

import src.entity.Player;
import src.mechanic.Attribute;

/**
 * Created by GreenyNeko on 23.12.2014.
 */
public class SkillRefresh extends Skill
{
    private double effectiveFactor;

    SkillRefresh(String name, String descId, String shortDescId, Attribute attr, int manaGain)
    {
        super(name);
        this.setDamage(manaGain);
        this.setDesc(descId);
        this.setShortDesc(shortDescId);
        this.setAttribute(attr);
    }

    public SkillRefresh setEffectiveFactor(double factor)
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
        int effectiveMana = this.getDamage()
                + (int)Math.round(this.effectiveFactor
                * player.getAttributeStat(this.getAttribute()));
        player.gainMana(effectiveMana);
    }
}

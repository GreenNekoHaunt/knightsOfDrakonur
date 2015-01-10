package src.skills;

import src.entity.Player;
import src.mechanic.Attribute;

/**
 * Created by GreenyNeko on 23.12.2014.
 */
public class SkillMultiAttack extends Skill
{
    private int attacks = 0;
    private double dimishingReturns = 1;
    private double damageFactor;

    SkillMultiAttack(String name, String descId, String shortDescId, Attribute attr, int attacks,
                     int damage)
    {
        super(name);
        this.setDamage(damage);
        this.setDesc(descId);
        this.setShortDesc(shortDescId);
        this.attacks = attacks;
        this.setAttribute(attr);
    }

    public SkillMultiAttack setDamageFactor(double factor)
    {
        this.damageFactor = factor;
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
        int damage = this.getDamage() +
                (int)Math.round(this.damageFactor * player.getAttributeStat(this.getAttribute()));
        for(int i = 0; i < this.attacks; i++)
        {
            if(dimishingReturns == 1)
            {
                damage *= (1/this.attacks);
                enemy.takeDamage(player, damage);
            }
            else
            {
                damage *= this.dimishingReturns;
                enemy.takeDamage(player, damage);
            }

        }
        player.takeMana(this.getManaCost());
    }

    public SkillMultiAttack setDimishingReturns(double rate)
    {
        this.dimishingReturns = rate;
        return this;
    }
}

package src.skills;

import src.entity.Player;
import src.mechanic.Attribute;

/**
 * Created by GreenyNeko on 23.12.2014.
 */
public class SkillStandardAttack extends Skill
{
    private double damageFactor;

    SkillStandardAttack(String name, String descId, String shortDescId, Attribute attr, int baseDamage)
    {
        super(name);
        this.setDamage(baseDamage);
        this.setDesc(descId);
        this.setShortDesc(shortDescId);
        this.setAttribute(attr);
    }

    public SkillStandardAttack setDamageFactor(double factor)
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
        super.onActivation(round, player, enemy);
        int damage = this.getDamage()
                + (int)Math.round(this.damageFactor * player.getAttributeStat(this.getAttribute()));
        enemy.takeDamage(player, damage);
        player.takeMana(this.getManaCost());
    }
}

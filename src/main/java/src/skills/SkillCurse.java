package src.skills;

import src.entity.Player;
import src.mechanic.Attribute;
import src.mechanic.Buff;

/**
 * Created by GreenyNeko on 23.12.2014.
 */
public class SkillCurse extends Skill
{
    private int duration = 0;
    private Buff buff;

    public SkillCurse(String name, String descId, String shortDescId, Attribute attr, Buff curse,
               int duration)
    {
        super(name);
        this.duration = duration;
        this.setDesc(descId);
        this.setShortDesc(shortDescId);
        this.setAttribute(attr);
        this.buff = curse;
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
        int duration = this.duration;
        player.gainMana(this.getDamage());
        enemy.applyBuff(player, this.buff, this.duration);
    }

    public int getDuration()
    {
        return this.duration;
    }
}

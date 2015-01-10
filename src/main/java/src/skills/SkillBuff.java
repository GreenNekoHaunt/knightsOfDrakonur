package src.skills;

import src.entity.Player;
import src.mechanic.Attribute;
import src.mechanic.Buff;

/**
 * Created by GreenyNeko on 23.12.2014.
 */
public class SkillBuff extends Skill
{
    private int duration = 0;
    private Buff buff;

    SkillBuff(String name, String descId, String shortDescId, Attribute attr, Buff buff,
              int duration)
    {
        super(name);
        this.duration = duration;
        this.setDesc(descId);
        this.setShortDesc(shortDescId);
        this.setAttribute(attr);
        this.buff = buff;
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
        player.applyBuff(player, this.buff, this.duration);
    }

    public int getDuration()
    {
        return this.duration;
    }
}

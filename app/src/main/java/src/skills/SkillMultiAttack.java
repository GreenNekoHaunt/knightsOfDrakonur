package src.skills;

import com.kod.knightsofdrakonur.framework.Game;

import src.entity.Entity;
import src.mechanic.Attribute;
import src.mechanic.DamageType;
import util.LocaleStringBuilder;

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

    @Override
    /* Called when the skill has been tapped.
     *
     * @param int round - the current round of the match.
     * @param Entity player - the player the skill belongs to.
     * @param Entity enemy - the opponent of the player.
     */
    public void onTap(int round, Entity player, Entity enemy)
    {
        this.onActivation(round, player, enemy);
    }

    @Override
    /* Called when the skill gets activated.
     *
     * @param int round - the current round of the match.
     * @param Entity player - the player the skill belongs to.
     * @param Entity enemy - the opponent of the player.
     *
     * TODO: Replace hardcoded damagetype according to the skill.
     */
    public void onActivation(int round, Entity player, Entity enemy)
    {
        super.onActivation(round, player, enemy);
        int damage = this.getDamage() +
                (int)Math.round(this.damageFactor * player.getAttributeStat(this.getAttribute()));
        for(int i = 0; i < this.attacks; i++)
        {
            if(dimishingReturns == 1)
            {
                enemy.takeDamage(player, damage, DamageType.DIRECT);
            }
            else
            {
                damage *= this.dimishingReturns;
                enemy.takeDamage(player, damage, DamageType.DIRECT);
            }

        }
        player.takeMana(this.getManaCost());
    }

    @Override
    /* Returns the short description of the skill.
     *
     * @param Game game - the game handler.
     *
     * @return String - the short description of the skill.
     */
    public String getShortDesc(Game game)
    {
        int damage = this.getDamage()
                + (int)Math.round(this.damageFactor
                * game.getCurrentPlayer().getAttributeStat(this.getAttribute()));
        int[] damageValues = new int[this.attacks];
        if(dimishingReturns == 1)
        {
            for(int i = 0; i < damageValues.length; i++)
            {
                damageValues[i] = damage;
            }
        }
        else
        {
            damageValues[0] = damage;
            for(int i = 1; i < this.attacks; i++)
            {
                damageValues[i] = (int)Math.round(damageValues[0] * this.dimishingReturns);
            }
        }

        LocaleStringBuilder localeStringBuilder = (new LocaleStringBuilder(game))
               .addLocaleString(this.getShortDescId())
               .replaceTags("{hits}", String.valueOf(this.attacks))
               .replaceTags("{mana}", String.valueOf(this.getManaCost()))
               .replaceTags("{cd}", String.valueOf(this.getCooldown()))
               .replaceTags("{attr}", this.getAttribute().name());

        localeStringBuilder
                = localeStringBuilder.replaceTags("{dmg}", String.valueOf(damageValues[0]));
        for(int i = 1; i < this.attacks; i++)
        {
            localeStringBuilder =
                    localeStringBuilder.replaceTags("{dmg" + String.valueOf(i + 1) + "}",
                            String.valueOf(damageValues[i]));
        }
        return localeStringBuilder.finalizeString();
    }

    @Override
    /* Returns the description of the skill.
     *
     * @param Game game - the game handler.
     *
     * @return String - the description of hte skill.
     */
    public String getDesc(Game game)
    {
        int damage = this.getDamage()
                + (int)Math.round(this.damageFactor
                * game.getCurrentPlayer().getAttributeStat(this.getAttribute()));
        int[] damageValues = new int[this.attacks];
        if(dimishingReturns == 1)
        {
            for(int i = 0; i < damageValues.length; i++)
            {
                damageValues[i] = damage;
            }
        }
        else
        {
            damageValues[0] = damage;
            for(int i = 1; i < this.attacks; i++)
            {
                damageValues[i] = (int)Math.round(damageValues[0] * this.dimishingReturns);
            }
        }

        LocaleStringBuilder localeStringBuilder = (new LocaleStringBuilder(game))
                .addLocaleString(this.getDescId())
                .replaceTags("{hits}", String.valueOf(this.attacks))
                .replaceTags("{mana}", String.valueOf(this.getManaCost()))
                .replaceTags("{cd}", String.valueOf(this.getCooldown()))
                .replaceTags("{attr}", this.getAttribute().name());

        localeStringBuilder
                = localeStringBuilder.replaceTags("{dmg}", String.valueOf(damageValues[0]));
        for(int i = 1; i < this.attacks; i++)
        {
            localeStringBuilder =
                    localeStringBuilder.replaceTags("{dmg" + String.valueOf(i + 1) + "}",
                            String.valueOf(damageValues[i]));
        }
        return localeStringBuilder.finalizeString();
    }

    /* Sets the diminishing returns rate.
     * This rate will be used to decrease the damage for each re-occuring hit.
     *
     * @param double rate - the rate how strong the diminishing returns should work.
     */
    public SkillMultiAttack setDiminishingReturns(double rate)
    {
        this.dimishingReturns = rate;
        return this;
    }

    /* Set the factor on how the damage scales.
     *
     * @param double factor - the scaling factor.
     */
    public SkillMultiAttack setDamageFactor(double factor)
    {
        this.damageFactor = factor;
        return this;
    }
}

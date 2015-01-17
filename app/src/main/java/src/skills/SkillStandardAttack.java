package src.skills;

import com.kod.knightsofdrakonur.framework.Game;

import src.entity.Entity;
import src.mechanic.Attribute;
import util.LocaleStringBuilder;

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
     * @param Player player - the player the skill belongs to.
     * @param Player enemy - the opponent of the player.
     */
    public void onActivation(int round, Entity player, Entity enemy)
    {
        super.onActivation(round, player, enemy);
        int damage = this.getDamage()
                + (int)Math.round(this.damageFactor * player.getAttributeStat(this.getAttribute()));
        enemy.takeDamage(player, damage);
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
        return (new LocaleStringBuilder(game)).addLocaleString(this.getShortDescId())
                .replaceTags("{dmg}", String.valueOf(damage))
                .replaceTags("{mana}", String.valueOf(this.getManaCost()))
                .replaceTags("{cd}", String.valueOf(this.getCooldown()))
                .replaceTags("{attr}", this.getAttribute().name())
                .finalizeString();
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
        return (new LocaleStringBuilder(game)).addLocaleString(this.getDescId())
                .replaceTags("{dmg}", String.valueOf(damage))
                .replaceTags("{mana}", String.valueOf(this.getManaCost()))
                .replaceTags("{cd}", String.valueOf(this.getCooldown()))
                .replaceTags("{attr}", this.getAttribute().name())
                .finalizeString();
    }

    /* Set the factor on how the damage scales.
     *
     * @param double factor - the scaling factor.
     */
    public SkillStandardAttack setDamageFactor(double factor)
    {
        this.damageFactor = factor;
        return this;
    }
}

package src.skills;

import com.kod.knightsofdrakonur.framework.Game;

import src.entity.Entity;
import src.mechanic.Attribute;
import util.LocaleStringBuilder;

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
        // getPlayerInfluence(player);
        super.onActivation(round, player, enemy);
        int amount = this.getDamage() + (int)Math.round(this.effectiveFactor
                * player.getAttributeStat(this.getAttribute()));
        player.heal(amount);
        player.takeMana(this.getManaCost());
    }

    @Override
    /* Returns the name of the skill using the locales.
     *
     * @param Game game - the game handler.
     *
     * @return String - the name of the skill.
     */
    public String getName(Game game)
    {
        return (new LocaleStringBuilder(game)).addLocaleString(this.getNameId()).finalizeString();
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
                + (int)Math.round(this.effectiveFactor
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
                + (int)Math.round(this.effectiveFactor
                * game.getCurrentPlayer().getAttributeStat(this.getAttribute()));
        return (new LocaleStringBuilder(game)).addLocaleString(this.getDescId())
                .replaceTags("{dmg}", String.valueOf(damage))
                .replaceTags("{mana}", String.valueOf(this.getManaCost()))
                .replaceTags("{cd}", String.valueOf(this.getCooldown()))
                .replaceTags("{attr}", this.getAttribute().name())
                .finalizeString();
    }

    /* Set the factor on how the effect scales.
     *
     * @param double factor - the scaling factor.
     */
    public SkillHeal setEffectiveFactor(double factor)
    {
        this.effectiveFactor = factor;
        return this;
    }
}

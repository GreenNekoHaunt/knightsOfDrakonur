package src.skills;

import com.kod.knightsofdrakonur.framework.Game;

import src.entity.Player;
import src.mechanic.Attribute;
import util.LocaleStringBuilder;

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

    @Override
    /* Called when the skill has been tapped.
     *
     * @param int round - the current round of the match.
     * @param Player player - the player the skill belongs to.
     * @param Player enemy - the opponent of the player.
     */
    public void onTap(int round, Player player, Player enemy)
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
    public void onActivation(int round, Player player, Player enemy)
    {
        // getPlayerInfluence(player);
        super.onActivation(round, player, enemy);
        int effectiveMana = this.getDamage()
                + (int)Math.round(this.effectiveFactor
                * player.getAttributeStat(this.getAttribute()));
        player.gainMana(effectiveMana);
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
        int mana = this.getDamage()
                + (int)Math.round(this.effectiveFactor
                * game.getCurrentPlayer().getAttributeStat(this.getAttribute()));
        return (new LocaleStringBuilder(game)).addLocaleString(this.getShortDescId())
                .replaceTags("{str}", String.valueOf(mana))
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
        int mana = this.getDamage()
                + (int) Math.round(this.effectiveFactor
                * game.getCurrentPlayer().getAttributeStat(this.getAttribute()));
        return (new LocaleStringBuilder(game)).addLocaleString(this.getDescId())
                .replaceTags("{str}", String.valueOf(mana))
                .replaceTags("{cd}", String.valueOf(this.getCooldown()))
                .replaceTags("{attr}", this.getAttribute().name())
                .finalizeString();
    }

    /* Set the factor on how the effect scales.
     *
     * @param double factor - the scaling factor.
     */
    public SkillRefresh setEffectiveFactor(double factor)
    {
        this.effectiveFactor = factor;
        return this;
    }
}

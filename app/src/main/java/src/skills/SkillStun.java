package src.skills;

import com.kod.knightsofdrakonur.framework.Game;

import src.entity.Entity;
import src.mechanic.Attribute;
import util.LocaleStringBuilder;

/**
 * Created by GreenyNeko on 23.12.2014.
 */
public class SkillStun extends Skill
{
    private int duration = 0;
    private double effectiveFactor = 0;

    public SkillStun(String name, String descId, String shortDescId, Attribute attr, int duration)
    {
        super(name);
        this.duration = duration;
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
        player.gainMana(this.getDamage());
        int duration = this.getDuration()
                + (int)Math.round(this.effectiveFactor
                * player.getAttributeStat(this.getAttribute()));
        enemy.setStunned(player, duration);
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
        int duration = this.getDuration()
                + (int)Math.round(this.effectiveFactor
                * game.getCurrentPlayer().getAttributeStat(this.getAttribute()));
        return (new LocaleStringBuilder(game)).addLocaleString(this.getShortDescId())
                .replaceTags("{dur}", String.valueOf(duration))
                .replaceTags("{mana}", String.valueOf(this.getManaCost()))
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
        int duration = this.getDuration()
                + (int)Math.round(this.effectiveFactor
                * game.getCurrentPlayer().getAttributeStat(this.getAttribute()));
        return (new LocaleStringBuilder(game)).addLocaleString(this.getDescId())
                .replaceTags("{dur}", String.valueOf(duration))
                .finalizeString();
    }

    /* Returns the duration of the buff.
     *
     * @return int - the duration
     */
    public int getDuration()
    {
        return this.duration;
    }

    /* Set the factor on how the effect scales.
     *
     * @param double factor - the scaling factor.
     */
    public SkillStun setEffectiveFactor(double factor)
    {
        this.effectiveFactor = factor;
        return this;
    }

}

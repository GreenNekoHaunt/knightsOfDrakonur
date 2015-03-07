package src.skills;

import com.kod.knightsofdrakonur.framework.Game;

import src.entity.Entity;
import src.mechanic.Attribute;
import src.mechanic.Buff;
import util.LocaleStringBuilder;

/**
 * Created by GreenyNeko on 23.12.2014.
 */
public class SkillCurse extends Skill
{
    private int duration = 0;
    private double effectiveFactor = 0;
    private Buff curse;

    public SkillCurse(String name, String descId, String shortDescId, Attribute attr, Buff curse,
               int duration)
    {
        super(name);
        this.duration = duration;
        this.setDesc(descId);
        this.setShortDesc(shortDescId);
        this.setAttribute(attr);
        this.curse = curse;
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
        player.takeMana(this.getManaCost());
        player.applyBuff(enemy, this.curse, this.duration);
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
        int damage = this.curse.getStrength()
                + (int)Math.round(this.effectiveFactor
                * game.getCurrentPlayer().getAttributeStat(this.getAttribute()));
        return (new LocaleStringBuilder(game)).addLocaleString(this.getShortDescId())
                .replaceTags("{str}", String.valueOf(damage))
                .replaceTags("{dur}", String.valueOf(this.getDuration()))
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
                .replaceTags("{str}", String.valueOf(damage))
                .replaceTags("{dur}", String.valueOf(this.getDuration()))
                .replaceTags("{mana}", String.valueOf(this.getManaCost()))
                .replaceTags("{cd}", String.valueOf(this.getCooldown()))
                .replaceTags("{attr}", this.getAttribute().name())
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
    public SkillCurse setEffectiveFactor(double factor)
    {
        this.effectiveFactor = factor;
        return this;
    }
}

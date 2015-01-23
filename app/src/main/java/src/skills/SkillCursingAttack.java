package src.skills;

import com.kod.knightsofdrakonur.framework.Game;

import src.entity.Entity;
import src.mechanic.Attribute;
import src.mechanic.Buff;
import src.mechanic.DamageType;
import util.LocaleStringBuilder;

/**
 * Created by GreenyNeko on 23.01.2015.
 */
public class SkillCursingAttack extends Skill
{
    private double damageFactor;
    private double effectFactor;
    private double durationFactor;
    private Buff curse;
    private int baseDuration;

    SkillCursingAttack(String name, String descId, String shortDescId, Attribute attr, int baseDamage, Buff curse, int baseDuration)
    {
        super(name);
        this.setDamage(baseDamage);
        this.setDesc(descId);
        this.setShortDesc(shortDescId);
        this.setAttribute(attr);
        this.curse = curse;
        this.baseDuration = baseDuration;
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
     *
     * TODO: Change hardcoded damage type according to the skill.
     */
    public void onActivation(int round, Entity player, Entity enemy)
    {
        super.onActivation(round, player, enemy);
        int damage = this.getDamage()
                + (int)Math.round(this.damageFactor * player.getAttributeStat(this.getAttribute()));
        int strength = this.curse.getStrength() +
                (int)Math.round(this.effectFactor * player.getAttributeStat(this.getAttribute()));
        int duration = this.baseDuration +
                (int)Math.round(this.durationFactor * player.getAttributeStat(this.getAttribute()));
        enemy.takeDamage(player, damage, DamageType.DIRECT);
        player.takeMana(this.getManaCost());
        Buff curse = this.curse.getCopy().setStrength(strength);
        enemy.applyBuff(player, curse, duration);
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
        int strength = this.curse.getStrength()
                + (int)Math.round(this.effectFactor
                        * game.getCurrentPlayer().getAttributeStat(this.getAttribute()));
        int duration = this.baseDuration
                + (int)Math.round(this.durationFactor
                        * game.getCurrentPlayer().getAttributeStat(this.getAttribute()));
        return (new LocaleStringBuilder(game)).addLocaleString(this.getShortDescId())
                .replaceTags("{dmg}", String.valueOf(damage))
                .replaceTags("{dmg2}", String.valueOf(strength))
                .replaceTags("{dur}", String.valueOf(duration))
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
        int strength = this.curse.getStrength()
                + (int)Math.round(this.effectFactor
                * game.getCurrentPlayer().getAttributeStat(this.getAttribute()));
        int duration = this.baseDuration
                + (int)Math.round(this.durationFactor
                * game.getCurrentPlayer().getAttributeStat(this.getAttribute()));
        return (new LocaleStringBuilder(game)).addLocaleString(this.getShortDescId())
                .replaceTags("{dmg}", String.valueOf(damage))
                .replaceTags("{dmg2}", String.valueOf(strength))
                .replaceTags("{dur}", String.valueOf(duration))
                .replaceTags("{mana}", String.valueOf(this.getManaCost()))
                .replaceTags("{cd}", String.valueOf(this.getCooldown()))
                .replaceTags("{attr}", this.getAttribute().name())
                .finalizeString();
    }

    public SkillCursingAttack setDurationFactor(double factor)
    {
        this.durationFactor = factor;
        return this;
    }

    /* Set the factor on how the damage scales.
     *
     * @param double factor - the scaling factor.
     */
    public SkillCursingAttack setDamageFactor(double factor)
    {
        this.damageFactor = factor;
        return this;
    }

    public SkillCursingAttack setEffectFactor(double factor)
    {
        this.effectFactor = factor;
        return this;
    }
}

package src.mechanic;

import java.security.KeyPair;

import src.entity.Role;

/**
 * Created by GreenyNeko on 23.12.2014.
 */
public enum Attribute
{
    ARMOR(0, 255, "Armor", "lang.attr.armor", false, Role.ANY),
    VITALITY(1, 255, "Vitality", "lang.attr.vitality", false, Role.ANY),
    RESOURCE(2, 255, "Resource", "lang.attr.mana", false, Role.ANY),
    UTILITY(3, 255, "Utility", "lang.attr.utility", true, Role.ANY),
    STRENGTH(4, 255, "Strength", "lang.attr.strength", true, Role.WARRIOR),
    ENDURANCE(5, 255, "Endurance", "lang.attr.endurance", true, Role.WARRIOR),
    STANCE(6, 255, "Stance", "lang.attr.stance", true, Role.WARRIOR),
    ARMS(7, 255, "Arms", "lang.attr.arms", true, Role.WARRIOR),
    PRECISION(8, 255, "Precision", "lang.attr.precision", true, Role.SCOUT),
    DEXTERTY(9, 255, "Dexterty", "lang.attr.dexterty", true, Role.SCOUT),
    SURVIVAL(10, 255, "Survival", "lang.attr.survival", true, Role.SCOUT),
    INSTINCTS(11, 255, "Instincts", "lang.attr.instincts", true, Role.SCOUT),
    FIRE(12, 255, "Fire", "lang.attr.fire", true, Role.MAGE),
    WATER(13, 255, "Water", "lang.attr.water", true, Role.MAGE),
    AIR(14, 255, "Air", "lang.attr.air", true, Role.MAGE),
    EARTH(15, 255, "Earth", "lang.attr.earth", true, Role.MAGE);

    private int id;
    private int cap;
    private String name;
    private String localeId;
    private boolean skillable;
    private Role reqRole;

    Attribute(int id, int cap, String name, String localeId, boolean skillable, Role role)
    {
        this.id = id;
        this.cap = cap;
        this.name = name;
        this.localeId = localeId;
        this.skillable = skillable;
        this.reqRole = role;
    }

    /* Return the locale id to retrieve the name according to the selected language.
     *
     * @return String - the locale id.
     */
    public String getLocaleId()
    {
        return this.localeId;
    }
}

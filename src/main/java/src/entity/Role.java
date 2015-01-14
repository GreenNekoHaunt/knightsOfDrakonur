package src.entity;

/**
 * Created by GreenyNeko on 23.12.2014.
 */
public enum Role
{
    ANY(0, "Any", "lang.classes.any", 0, 0, 0),
    WARRIOR(1, "Warrior", "lang.classes.warrior", 12, 6, 3),
    SCOUT(2, "Scout", "lang.classes.scout", 8, 4, 5),
    MAGE(3, "Mage", "lang.classes.mage", 4, 3, 9);

    private int id;
    private String name;
    private String localeId;
    private int armorFactor;
    private int vitalityFactor;
    private int manaFactor;

    Role(int id, String name, String localeId, int armorFactor, int vitalityFactor, int manaFactor)
    {
        this.id = id;
        this.name = name;
        this.localeId = localeId;
        this.armorFactor = armorFactor;
        this.vitalityFactor = vitalityFactor;
        this.manaFactor = manaFactor;
    }

    /* Returns the id of the class.
     *
     * @return int - the class id.
     */
    public int getId()
    {
        return this.id;
    }

    /* Returns the name of the class.
     *
     * @return String - the name of the class.
     */
    public String getName()
    {
        return this.name;
    }

    /* Return the id for the locale.
     *
     * @return String - id for the locale.
     */
    public String getLocaleId()
    {
        return this.localeId;
    }

    /* Return the factor how armor scales for this class.
     *
     * @return int - the factor how armor scales.
     */
    public int getArmorFactor()
    {
        return this.armorFactor;
    }

    /* Returns the factor how health scales for this class.
     *
     * @return int - the factor how health scales.
     */
    public int getVitalityFactor()
    {
        return this.vitalityFactor;
    }

    /* returns the factor how mana scales for this class.
     *
     * @return int - the factor how mana scales.
     */
    public int getManaFactor()
    {
        return this.manaFactor;
    }
}

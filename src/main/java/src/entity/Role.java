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

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getLocaleId()
    {
        return this.localeId;
    }

    public int getArmorFactor()
    {
        return this.armorFactor;
    }

    public int getVitalityFactor()
    {
        return this.vitalityFactor;
    }

    public int getManaFactor()
    {
        return this.manaFactor;
    }


}

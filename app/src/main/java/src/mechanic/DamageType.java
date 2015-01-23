package src.mechanic;

/**
 * Created by GreenyNeko on 20.01.2015.
 */
public enum DamageType
{
    DIRECT(0, "Direct", 1.0f),
    PHYSICAL(1, "Physical", 0.0f),
    MAGICAL(2, "Magical", 0.33f);

    private int id;
    private String name;
    private float armorPierceRate;

    DamageType(int id, String name, float armorPierceRate)
    {
        this.id = id;
        this.name = name;
        this.armorPierceRate = armorPierceRate;
    }
}

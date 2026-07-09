// file determines species abilities
package oc.wastelands.species;

public class Species
{
    public final boolean canFly;
    public final boolean nightVision;

    // slime abilities
    public final boolean noFallDamage;
    public final boolean slowHunger;
    public float sizeModifier;

    public final double damageMultiplier;
    public final double speedMultiplier;
    public final double jumpMultiplier;

    public Species
    (
        boolean canFly,
        boolean nightVision,
        boolean noFallDamage,
        boolean slowHunger,
        float sizeModifier,
        double damageMultiplier,
        double speedMultiplier,
        double jumpMultiplier
    )
    {
        this.canFly = canFly;
        this.nightVision = nightVision;
        this.noFallDamage = noFallDamage;
        this.slowHunger = slowHunger;
        this.sizeModifier = sizeModifier;
        this.damageMultiplier = damageMultiplier;
        this.speedMultiplier = speedMultiplier;
        this.jumpMultiplier = jumpMultiplier;
    }
}

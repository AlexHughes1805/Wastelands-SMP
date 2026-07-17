package oc.wastelands.augments;

public class Augment
{
    public final boolean nightVision;
    public final boolean noFallDamage;
    public final boolean slowHunger;

    public final double damageMultiplier;
    public final double speedMultiplier;
    public final double jumpMultiplier;

    public Augment
    (
        boolean nightVision,
        boolean noFallDamage,
        boolean slowHunger,
        double damageMultiplier,
        double speedMultiplier,
        double jumpMultiplier
    )
    {
        this.nightVision = nightVision;
        this.noFallDamage = noFallDamage;
        this.slowHunger = slowHunger;
        this.damageMultiplier = damageMultiplier;
        this.speedMultiplier = speedMultiplier;
        this.jumpMultiplier = jumpMultiplier;
    }
}

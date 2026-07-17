package oc.wastelands.augments;

import java.util.HashMap;
import java.util.Map;

public class AugmentRegistry
{
    public static final Map<String, Augment> AUGMENT = new HashMap<>();

    static
    {
        AUGMENT.put("none", new Augment(
            false,
            false,
            false,
            1.0,
            1.0,
            1.0
        ));

        AUGMENT.put("nightVision", new Augment(
            true,
            false,
            false,
            1.0,
            1.0,
            1.0
        ));

        AUGMENT.put("noFallDamage", new Augment(
            false,
            false,
            false,
            1.0,
            1.0,
            1.0
        ));

        AUGMENT.put("slowHunger", new Augment(
            false,
            false,
            false,
            1.0,
            1.0,
            1.0
        ));

        AUGMENT.put("reducedDamge", new Augment(
            false,
            false,
            false,
            0.75,
            1.0,
            1.0
        ));

        AUGMENT.put("fast", new Augment(
            false,
            false,
            false,
            1.0,
            1.5,
            1.0
        ));

        AUGMENT.put("highJump", new Augment(
            false,
            false,
            false,
            1.0,
            1.0,
            1.5
        ));
    }

    public static boolean exists(String name)
    {
        return AUGMENT.containsKey(name);
    }

    public static Augment get(String name)
    {
        return AUGMENT.getOrDefault(name, AUGMENT.get("none"));
    }
}

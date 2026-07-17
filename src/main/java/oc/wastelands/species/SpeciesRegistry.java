package oc.wastelands.species;

import java.util.HashMap;
import java.util.Map;

//defines player species
public class SpeciesRegistry
{
    public static final Map<String, Species> SPECIES = new HashMap<>();

    static
    {
        SPECIES.put("human", new Species(
            false,
            false,
            false,
            1.0F,
            1.0,
            1.0,
            1.0
        ));

        SPECIES.put("elytrian", new Species(
            false,
            true,
            false,
            1.0F,
            1.0,
            1.0,
            1.0
        ));

        SPECIES.put("slimekind", new Species(
            false,
            true,
            true,
            1.0F,
            1.25,
            1.0,
            2.0
        ));

        SPECIES.put("mammalfolk", new Species(
            false,
            false,
            false,
            1.0F,
            1.0,
            1.0,
            1.0
        ));
    }

    public static boolean exists(String name)
    {
        return SPECIES.containsKey(name);
    }

    public static Species get(String name)
    {
        return SPECIES.getOrDefault(name, SPECIES.get("human"));
    }

}

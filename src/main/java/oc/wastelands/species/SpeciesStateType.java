package oc.wastelands.species;

import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.world.PersistentStateType;

public class SpeciesStateType
{
    public static final PersistentStateType<SpeciesState> TYPE =
        new PersistentStateType<>(
            "wastelands_smp_species_state",
            SpeciesState::new,
            SpeciesState.CODEC,
            DataFixTypes.LEVEL
        );
}


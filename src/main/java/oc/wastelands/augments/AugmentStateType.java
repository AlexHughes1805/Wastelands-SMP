package oc.wastelands.augments;

import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.world.PersistentStateType;
import oc.wastelands.augments.AugmentState;

public class AugmentStateType
{
    public static final PersistentStateType<AugmentState> TYPE =
        new PersistentStateType<>(
            "wastelands_smp_species_state",
            AugmentState::new,
            AugmentState.CODEC,
            DataFixTypes.LEVEL
        );
}

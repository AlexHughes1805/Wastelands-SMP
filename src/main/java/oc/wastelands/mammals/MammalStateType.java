package oc.wastelands.mammals;

import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.world.PersistentStateType;

public class MammalStateType
{
    public static final PersistentStateType<MammalState> TYPE =
        new PersistentStateType<>(
            "wastelands_smp_mammal_state",
            MammalState::new,
            MammalState.CODEC,
            DataFixTypes.LEVEL
        );
}

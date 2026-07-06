package oc.wastelands.factions;

import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.world.PersistentStateType;

public class FactionStateType
{
    public static final PersistentStateType<FactionState> TYPE =
        new PersistentStateType<>(
            "wastelands_smp_faction_state",
            FactionState::new,
            FactionState.CODEC,
            DataFixTypes.LEVEL
        );
}

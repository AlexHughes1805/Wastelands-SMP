package oc.wastelands.factions;

import net.minecraft.world.PersistentStateType;
import net.minecraft.nbt.NbtCompound;

public class FactionStateType
{
    public static final PersistentStateType<FactionState> TYPE =
        new PersistentStateType<>(
            FactionState::new,
            FactionState::fromNbt,
            FactionState::writeNbt
        );
}

package oc.wastelands.augments;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;

public class AugmentState extends PersistentState
{
    public static final Codec<AugmentState> CODEC = Codec.unboundedMap(Codec.STRING, Codec.STRING).xmap(
        map -> fromMap(map),
        state -> state.toMap()
    );

    private final Map<UUID, String> augments = new HashMap<>();

    public void augmentSet(UUID uuid, String augment)
    {
        augments.put(uuid, augment);
        markDirty();
    }

    public void augmentRemove(UUID uuid)
    {
        augments.remove(uuid);
        markDirty();
    }

    public String augmentGet(UUID uuid)
    {
        return augments.getOrDefault(uuid, "none");
    }

    private static AugmentState fromMap(Map<String, String> map)
    {
        AugmentState state = new AugmentState();

        for (Map.Entry<String, String> entry : map.entrySet())
        {
            state.augments.put(UUID.fromString(entry.getKey()), entry.getValue());
        }

        return state;
    }

    private Map<String, String> toMap()
    {
        return augments.entrySet().stream().collect(Collectors.toMap(
            entry -> entry.getKey().toString(),
            entry -> entry.getValue()
        ));
    }

    // save to MC nbt
    public NbtCompound writeNbt(NbtCompound nbt)
    {
        NbtCompound map = new NbtCompound();

        for(Map.Entry<UUID, String> entry : augments.entrySet())
        {
            map.putString(entry.getKey().toString(), entry.getValue());
        }

        nbt.put("augments", map);
        return nbt;
    }

    // load from MC nbt
    public static AugmentState fromNbt(NbtCompound nbt)
    {
        AugmentState state = new AugmentState();
        NbtCompound map = nbt.getCompound("augments").orElse(new NbtCompound());

        for(String key : map.getKeys())
        {
            UUID uuid = UUID.fromString(key);
            String augment = map.getString(key).orElse("none");
            state.augments.put(uuid, augment);
        }
        return state;
    }
}


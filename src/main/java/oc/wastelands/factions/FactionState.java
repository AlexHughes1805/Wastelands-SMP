package oc.wastelands.factions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;

public class FactionState extends PersistentState
{
    public static final Codec<FactionState> CODEC = Codec.unboundedMap(Codec.STRING, Codec.STRING).xmap(
        map -> fromMap(map),
        state -> state.toMap()
    );

    private final Map<UUID, String> factions = new HashMap<>();

    public void factionSet(UUID uuid, String faction)
    {
        factions.put(uuid,faction);
        markDirty();
    }

    public void factionRemove(UUID uuid)
    {
        factions.remove(uuid);
        markDirty();
    }

    public String factionGet(UUID uuid)
    {
        return factions.getOrDefault(uuid, "none");
    }

    private static FactionState fromMap(Map<String, String> map)
    {
        FactionState state = new FactionState();

        for (Map.Entry<String, String> entry : map.entrySet())
        {
            state.factions.put(UUID.fromString(entry.getKey()), entry.getValue());
        }

        return state;
    }

    private Map<String, String> toMap()
    {
        return factions.entrySet().stream().collect(Collectors.toMap(
            entry -> entry.getKey().toString(),
            entry -> entry.getValue()
        ));
    }

    // save to MC nbt
    public NbtCompound writeNbt(NbtCompound nbt)
    {
        NbtCompound map = new NbtCompound();

        for(Map.Entry<UUID, String> entry : factions.entrySet())
        {
            map.putString(entry.getKey().toString(), entry.getValue());
        }

        nbt.put("factions", map);
        return nbt;
    }

    // load from MC nbt
    public static FactionState fromNbt(NbtCompound nbt)
    {
        FactionState state = new FactionState();
        NbtCompound map = nbt.getCompound("factions").orElse(new NbtCompound());

        for(String key : map.getKeys())
        {
            UUID uuid = UUID.fromString(key);
            String faction = map.getString(key).orElse("none");
            state.factions.put(uuid, faction);
        }
        return state;
    }
}

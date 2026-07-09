package oc.wastelands.mammals;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;

public class MammalState extends PersistentState
{
    public static final Codec<MammalState> CODEC = Codec.unboundedMap(Codec.STRING, Codec.STRING).xmap(
        map -> fromMap(map),
        state -> state.toMap()
    );

    private final Map<UUID, String> mammals = new HashMap<>();

    public void mammalSet(UUID uuid, String mammalName)
    {
        mammals.put(uuid, mammalName);
        markDirty();
    }

    public void mammalRemove(UUID uuid)
    {
        mammals.remove(uuid);
        markDirty();
    }

    public String mammalGet(UUID uuid)
    {
        return mammals.getOrDefault(uuid, "none");
    }

    private static MammalState fromMap(Map<String, String> map)
    {
        MammalState state = new MammalState();

        for(Map.Entry<String, String> entry : map.entrySet())
        {
            state.mammals.put(UUID.fromString(entry.getKey()), entry.getValue());
        }

        return state;
    }

    private Map<String, String> toMap()
    {
        return mammals.entrySet().stream().collect(Collectors.toMap(
            entry -> entry.getKey().toString(),
            entry -> entry.getValue()
        ));
    }

    //save to nbt
    public NbtCompound writeNbt(NbtCompound nbt)
    {
        NbtCompound map = new NbtCompound();

        for(Map.Entry<UUID, String> entry : mammals.entrySet())
        {
            map.putString(entry.getKey().toString(), entry.getValue());
        }

        nbt.put("mammals", map);
        return nbt;
    }

    // load from MC nbt
    public static MammalState fromNbt(NbtCompound nbt)
    {
        MammalState state = new MammalState();
        NbtCompound map = nbt.getCompound("mammals").orElse(new NbtCompound());

        for (String key : map.getKeys())
        {
            UUID uuid = UUID.fromString(key);
            String mammalName = map.getString(key).orElse("none");
            state.mammals.put(uuid, mammalName);
        }

        return state;
    }
}

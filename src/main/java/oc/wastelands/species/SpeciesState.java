package oc.wastelands.species;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.PersistentState;

public class SpeciesState extends PersistentState
{   
    public static final Codec<SpeciesState> CODEC = Codec.unboundedMap(Codec.STRING, Codec.STRING).xmap(
        map -> fromMap(map),
        state -> state.toMap()
    );

    private final Map<UUID, String> species = new HashMap<>();

    public void speciesSet(UUID uuid, String speciesName)
    {
        species.put(uuid, speciesName);
        markDirty();
    }

    public void speciesRemove(UUID uuid)
    {
        species.remove(uuid);
        markDirty();
    }

    public String speciesGet(UUID uuid)
    {
        return species.getOrDefault(uuid, "human");
    }

    private static SpeciesState fromMap(Map<String, String> map)
    {
        SpeciesState state = new SpeciesState();

        for(Map.Entry<String, String> entry : map.entrySet())
        {
            state.species.put(UUID.fromString(entry.getKey()), entry.getValue());
        }

        return state;
    }

    private Map<String, String> toMap()
    {
        return species.entrySet().stream().collect(Collectors.toMap(
            entry -> entry.getKey().toString(),
            entry -> entry.getValue()
        ));
    }

    //save to nbt
    public NbtCompound writeNbt(NbtCompound nbt)
    {
        NbtCompound map = new NbtCompound();

        for(Map.Entry<UUID, String> entry : species.entrySet())
        {
            map.putString(entry.getKey().toString(), entry.getValue());
        }

        nbt.put("species", map);
        return nbt;
    }

    // load from MC nbt
    public static SpeciesState fromNbt(NbtCompound nbt)
    {
        SpeciesState state = new SpeciesState();
        NbtCompound map = nbt.getCompound("species").orElse(new NbtCompound());

        for (String key : map.getKeys())
        {
            UUID uuid = UUID.fromString(key);
            String speciesName = map.getString(key).orElse("human");
            state.species.put(uuid, speciesName);
        }

        return state;
    }

    private static final Map<UUID, Float> PLAYER_SIZE = new HashMap<>();

    public static void setSize(ServerPlayerEntity player, float size)
    {
        PLAYER_SIZE.put(player.getUuid(), size);
    }

    public static float getSize(PlayerEntity player)
    {
        UUID uuid = player.getUuid();
        return PLAYER_SIZE.getOrDefault(uuid, 1.0F);
    }


}

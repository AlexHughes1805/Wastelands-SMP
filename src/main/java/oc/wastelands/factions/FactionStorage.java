package oc.wastelands.factions;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class FactionStorage
{
    public static FactionState get(MinecraftServer server)
    {
        return server.getWorld(World.OVERWORLD).getPersistentStateManager().getOrCreate(
            FactionStateType.TYPE
        );

    }

    public static void setFaction(ServerPlayerEntity player, String faction)
    {
        MinecraftServer server = player.getCommandSource().getServer();
        FactionState state = get(server);
        state.factionSet(player.getUuid(), faction);
    }

    public static void factionRemove(ServerPlayerEntity player)
    {
        MinecraftServer server = player.getCommandSource().getServer();
        FactionState state = get(server);
        state.factionRemove(player.getUuid());
    }

    public static String factionGet(ServerPlayerEntity player)
    {
        MinecraftServer server = player.getCommandSource().getServer();
        FactionState state = get(server);
        return state.factionGet(player.getUuid());
    }
}

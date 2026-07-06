package oc.wastelands.factions;

import java.util.Objects;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.world.World;

public class FactionStorage
{
    public static FactionState get(MinecraftServer server)
    {
        return Objects.requireNonNull(server.getWorld(World.OVERWORLD)).getPersistentStateManager().getOrCreate(
            FactionStateType.TYPE
        );

    }

    // setter function
    public static void setFaction(ServerPlayerEntity player, String faction)
    {
        MinecraftServer server = player.getCommandSource().getServer();
        FactionState state = get(server);
        state.factionSet(player.getUuid(), faction);
    }

    // remove function
    public static void factionRemove(ServerPlayerEntity player)
    {
        MinecraftServer server = player.getCommandSource().getServer();
        FactionState state = get(server);
        state.factionRemove(player.getUuid());
    }

    // getter function, returns the players faction as a string
    public static String factionGet(ServerPlayerEntity player)
    {
        MinecraftServer server = player.getCommandSource().getServer();
        FactionState state = get(server);
        return state.factionGet(player.getUuid());
    }

    // function to call player list refresh
    public static void refreshPlayerListName(MinecraftServer server, ServerPlayerEntity player)
    {
        server.getPlayerManager().sendToAll(
            new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_DISPLAY_NAME, player)
        );
    }
}

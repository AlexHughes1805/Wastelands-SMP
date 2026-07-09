package oc.wastelands.augments;

import java.util.Objects;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.world.World;

public class AugmentStorage
{
    public static AugmentState get(MinecraftServer server)
    {
        return Objects.requireNonNull(server.getWorld(World.OVERWORLD)).getPersistentStateManager().getOrCreate(
            AugmentStateType.TYPE
        );

    }

    // setter function
    public static void setFaction(ServerPlayerEntity player, String faction)
    {
        MinecraftServer server = player.getCommandSource().getServer();
        AugmentState state = get(server);
        state.augmentSet(player.getUuid(), faction);
    }

    // remove function
    public static void factionRemove(ServerPlayerEntity player)
    {
        MinecraftServer server = player.getCommandSource().getServer();
        AugmentState state = get(server);
        state.augmentRemove(player.getUuid());
    }

    // getter function, returns the players faction as a string
    public static String augmentGet(ServerPlayerEntity player)
    {
        MinecraftServer server = player.getCommandSource().getServer();
        AugmentState state = get(server);
        return state.augmentGet(player.getUuid());
    }

    // function to call player list refresh
    public static void refreshPlayerListName(MinecraftServer server, ServerPlayerEntity player)
    {
        server.getPlayerManager().sendToAll(
            new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_DISPLAY_NAME, player)
        );
    }
}

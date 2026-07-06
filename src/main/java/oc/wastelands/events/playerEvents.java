package oc.wastelands.events;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import oc.wastelands.factions.FactionStorage;

public class playerEvents {

    public static void register()
    {

        // when player joins the server the player list (accessed via tab) will refresh to show player factions
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
        {

            ServerPlayerEntity player = handler.getPlayer();

        
            FactionStorage.refreshPlayerListName(server, player);
        });
    }
}

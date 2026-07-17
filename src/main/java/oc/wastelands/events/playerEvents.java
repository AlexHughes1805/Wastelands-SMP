package oc.wastelands.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;

import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;

import oc.wastelands.augments.Augment;
import oc.wastelands.augments.AugmentStorage;
import oc.wastelands.factions.FactionStorage;
import oc.wastelands.species.Species;
import oc.wastelands.species.SpeciesStorage;

public class playerEvents {

    public static void register()
    {

        // when player joins the server
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
        {

            ServerPlayerEntity player = handler.getPlayer();

        
            FactionStorage.refreshPlayerListName(server, player); // refresh player list to show colours based on the faction
            SpeciesStorage.abilities(handler.player); // apply abilities based on player species
        });

        // reapply abilities when the player respawns
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) ->
        {
            SpeciesStorage.abilities(newPlayer);
        });

        // Fall damage immunity
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (!(entity instanceof ServerPlayerEntity player)) return true;

            Species species = SpeciesStorage.getSpeciesObj(player);
            Augment augment = AugmentStorage.getAugmentObj(player);

            if ((species.noFallDamage || augment.noFallDamage) && source.isIn(DamageTypeTags.IS_FALL))
            {
                return false;
            }

            return true;
        });

    }
}

package oc.wastelands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import oc.wastelands.factions.FactionStorage;
import oc.wastelands.species.SpeciesStorage;

public class playerCmd
{
     public static void attach(LiteralArgumentBuilder<ServerCommandSource> root)
     {
        root.then(CommandManager.literal("viewFaction")
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .executes(ctx -> 
                    {
                        var source = ctx.getSource();
                        ServerPlayerEntity executor = source.getEntity() instanceof ServerPlayerEntity player ? player : null;
                        ServerPlayerEntity target = EntityArgumentType.getPlayer(ctx, "player");

                        var storage = FactionStorage.get(source.getServer());
                        String currentFaction = storage.factionGet(target.getUuid());

                        if(executor == null) // if run from the server console
                        {
                                source.sendFeedback(() -> Text.literal("This command must be run by a player"), false);
                            return 0;
                        }

                        if("none".equals(currentFaction)) // if the player is not in a faction
                        {
                            executor.sendMessage(
                                Text.literal(target.getName().getString() + " is not in a faction")
                            );
                            return 1;
                        }

                        if(target == executor) // if the player checks their own faction
                        {
                            if("Zephyr".equalsIgnoreCase(currentFaction))
                            {
                                executor.sendMessage(
                                    (Text.literal("You are in faction ").formatted(Formatting.WHITE))
                                    .append(Text.literal("Zephyr").formatted(Formatting.AQUA, Formatting.UNDERLINE))
                                );
                                return 1;
                            }
                            else if("Terra".equalsIgnoreCase(currentFaction))
                            {
                                executor.sendMessage(
                                    (Text.literal("You are in faction: ").formatted(Formatting.WHITE))
                                    .append(Text.literal("Terra").formatted(Formatting.DARK_GREEN, Formatting.UNDERLINE))
                                );
                                return 1;
                            }
                        }

                        if("Zephyr".equalsIgnoreCase(currentFaction))
                        {
                            executor.sendMessage(
                                (Text.literal(target.getName().getString()).formatted(Formatting.GOLD))
                                .append(Text.literal("You are in faction: ").formatted(Formatting.WHITE))
                                .append(Text.literal("Zephyr").formatted(Formatting.AQUA, Formatting.UNDERLINE))
                            );
                            return 1;
                        }
                        else if("Terra".equalsIgnoreCase(currentFaction))
                        {
                            executor.sendMessage(
                                (Text.literal(target.getName().getString()).formatted(Formatting.GOLD))
                                .append(Text.literal(" is in faction: ").formatted(Formatting.WHITE))
                                .append(Text.literal("Terra").formatted(Formatting.DARK_GREEN, Formatting.UNDERLINE))
                            );
                            return 1;
                        }

                        return 1;
                    }
                )
            )
        )
        .then(CommandManager.literal("speciesSet")
            .then(CommandManager.literal("human")
                .executes(ctx ->
                    {
                        var source = ctx.getSource();
                        ServerPlayerEntity executor = source.getEntity() instanceof ServerPlayerEntity player ? player : null;
                        var storage = SpeciesStorage.get(ctx.getSource().getServer());
                        String currentSpecies = storage.speciesGet(executor.getUuid());


                        if("human".equalsIgnoreCase(currentSpecies)) // human is the default species
                        {
                            source.sendError(
                                Text.literal("You are already human")
                            );
                            return 0;
                        }

                        storage.speciesSet(executor.getUuid(), "human");
                        SpeciesStorage.abilities(executor); // apply abilities
                        executor.sendMessage(
                            (Text.literal("You have selected the species: ").formatted(Formatting.WHITE))
                            .append(Text.literal("human").formatted(Formatting.UNDERLINE, Formatting.GOLD))
                        );

                        return 1;
                    }
                )
            )
        .then(CommandManager.literal("slimekind")
            .executes(ctx ->
                    {
                        var source = ctx.getSource();
                        ServerPlayerEntity executor = source.getEntity() instanceof ServerPlayerEntity player ? player : null;
                        var storage = SpeciesStorage.get(ctx.getSource().getServer());
                        String currentSpecies = storage.speciesGet(executor.getUuid());

                        if("slimekind".equalsIgnoreCase(currentSpecies))
                        {
                            source.sendError(
                                Text.literal("You are already slimekind")
                            );
                            return 0;
                        }

                        storage.speciesSet(executor.getUuid(), "slimekind");
                        SpeciesStorage.abilities(executor);
                        executor.sendMessage(
                            (Text.literal("You have selected the species: ").formatted(Formatting.WHITE))
                            .append(Text.literal("slimekind").formatted(Formatting.UNDERLINE, Formatting.GREEN))
                        );

                        return 1;
                    }
                )
            )
        .then(CommandManager.literal("elytrian")
            .executes(ctx ->
                    {
                        var source = ctx.getSource();
                        ServerPlayerEntity executor = source.getEntity() instanceof ServerPlayerEntity player ? player : null;
                        var storage = SpeciesStorage.get(ctx.getSource().getServer());
                        String currentSpecies = storage.speciesGet(executor.getUuid());

                        if("elytrian".equalsIgnoreCase(currentSpecies))
                        {
                            source.sendError(
                                Text.literal("You are already elytrian")
                            );
                            return 0;
                        }

                        storage.speciesSet(executor.getUuid(), "elytrian");
                        SpeciesStorage.abilities(executor);
                        executor.sendMessage(
                            (Text.literal("You have selected the species: ").formatted(Formatting.WHITE))
                            .append(Text.literal("elytrian").formatted(Formatting.UNDERLINE, Formatting.BLUE))
                        );

                        return 1;
                    }
                )
            )
        .then(CommandManager.literal("mammalfolk")
            .executes(ctx ->
                    {
                        var source = ctx.getSource();
                        ServerPlayerEntity executor = source.getEntity() instanceof ServerPlayerEntity player ? player : null;
                        var storage = SpeciesStorage.get(ctx.getSource().getServer());
                        String currentSpecies = storage.speciesGet(executor.getUuid());

                        if("mammalfolk".equalsIgnoreCase(currentSpecies))
                        {
                            source.sendError(
                                Text.literal("You are already mammalfolk")
                            );
                            return 0;
                        }

                        storage.speciesSet(executor.getUuid(), "mammalfolk");
                        SpeciesStorage.abilities(executor);
                        executor.sendMessage(
                            (Text.literal("You have selected the species: ").formatted(Formatting.WHITE))
                            .append(Text.literal("mammalfolk").formatted(Formatting.UNDERLINE, Formatting.LIGHT_PURPLE))
                        );

                        return 1;
                    }
                )
            )
        );
     }
}

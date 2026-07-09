package oc.wastelands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import oc.wastelands.factions.FactionStorage;

public class adminCmd
{
    public static void attach(LiteralArgumentBuilder<ServerCommandSource> root)
    {
        root.then(CommandManager.literal("admin")
        .requires(CommandManager.requirePermissionLevel(CommandManager.OWNERS_CHECK))
        .then(CommandManager.literal("factionSet")
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .then(CommandManager.literal("zephyr")
                .executes(ctx -> 
                    {
                        ServerPlayerEntity target = EntityArgumentType.getPlayer(ctx, "player");
                        var storage = FactionStorage.get(ctx.getSource().getServer());
                        String currentFaction = storage.factionGet(target.getUuid());

                        if(!"none".equals(currentFaction)) // if the player is already in a faction, send an error
                        {
                            ctx.getSource().sendError(
                                Text.literal(target.getName().getString() + " is already in faction: " + currentFaction)
                            );
                            return 0;
                        }

                        storage.factionSet(target.getUuid(), "Zephyr");

                        ctx.getSource().getServer().getPlayerManager().broadcast((Text.literal(target.getName().getString()).formatted(Formatting.GOLD))
                            .append((Text.literal(" joined the faction: ")).formatted(Formatting.WHITE))
                            .append(Text.literal("Zephyr").formatted(Formatting.AQUA, Formatting.UNDERLINE)),
                            false);
                        FactionStorage.refreshPlayerListName(ctx.getSource().getServer(), target);
                        return 1;
                    }
                ))
            .then(CommandManager.literal("terra")
                .executes(ctx ->
                    {
                        ServerPlayerEntity target = EntityArgumentType.getPlayer(ctx, "player");
                        var storage = FactionStorage.get(ctx.getSource().getServer());
                        String currentFaction = storage.factionGet(target.getUuid());

                        if(!"none".equals(currentFaction)) // if the player is already in a faction, send an error
                        {
                            ctx.getSource().sendError(
                                Text.literal(target.getName().getString() + " is already in faction: " + currentFaction)
                            );
                            return 0;
                        }

                        storage.factionSet(target.getUuid(), "Terra");

                        ctx.getSource().getServer().getPlayerManager().broadcast((Text.literal(target.getName().getString()).formatted(Formatting.GOLD))
                            .append((Text.literal(" joined the faction: ")).formatted(Formatting.WHITE))
                            .append(Text.literal("Terra").formatted(Formatting.DARK_GREEN, Formatting.UNDERLINE)),
                        false);

                        FactionStorage.refreshPlayerListName(ctx.getSource().getServer(), target);
                        return 1;
                    }
                )))
            )

        .then(CommandManager.literal("factionRemove")
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .executes(ctx -> 
                    {   
                        ServerPlayerEntity target = EntityArgumentType.getPlayer(ctx, "player");

                        var storage = FactionStorage.get(ctx.getSource().getServer());
                        String currentFaction = storage.factionGet(target.getUuid());

                        if("none".equals(currentFaction)) // if the target isn't in a faction send an error
                        {
                            ctx.getSource().sendError(
                                Text.literal(target.getName().getString() + " is not in a faction")
                            );
                            return 0;
                        }

                        storage.factionRemove(target.getUuid());

                        ctx.getSource().getServer().getPlayerManager().broadcast((Text.literal(target.getName().getString()).formatted(Formatting.AQUA))
                            .append(Text.literal(" has been removed from the faction: ").formatted(Formatting.WHITE))
                            .append(Text.literal(currentFaction).formatted(Formatting.RED, Formatting.UNDERLINE)),
                            false);

                        FactionStorage.refreshPlayerListName(ctx.getSource().getServer(), target);
                        return 1;
                    }
                ))
            )

        );
    }
}
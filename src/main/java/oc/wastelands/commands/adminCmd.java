package oc.wastelands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

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
                        ctx.getSource().getServer().getPlayerManager().broadcast((Text.literal(target.getName().getString()).formatted(Formatting.GOLD))
                            .append((Text.literal(" joined the faction: ")).formatted(Formatting.WHITE))
                            .append(Text.literal("Zephyr").formatted(Formatting.AQUA)),
                            false);
                        return 1;
                    }
                ))
            .then(CommandManager.literal("terra")
                .executes(ctx ->
                    {
                        ServerPlayerEntity target = EntityArgumentType.getPlayer(ctx, "player");
                        ctx.getSource().getServer().getPlayerManager().broadcast((Text.literal(target.getName().getString()).formatted(Formatting.GOLD))
                            .append((Text.literal(" joined the faction: ")).formatted(Formatting.WHITE))
                            .append(Text.literal("Terra").formatted(Formatting.DARK_GREEN)),
                        false);
                        return 1;
                    }
                )))
            )

        .then(CommandManager.literal("factionRemove")
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .executes(ctx -> 
                    {   
                        ServerPlayerEntity target = EntityArgumentType.getPlayer(ctx, "player");
                        ctx.getSource().getServer().getPlayerManager().broadcast((Text.literal(target.getName().getString()).formatted(Formatting.RED))
                            .append((Text.literal(" has been removed from their faction")).formatted(Formatting.WHITE)),
                            false);
                        return 1;
                    }
                )
                )
            )

        );
    }
}
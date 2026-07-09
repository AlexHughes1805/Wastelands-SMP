package oc.wastelands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;


import oc.wastelands.species.SpeciesStorage;

public class humanCmd
{
    public static void attach(LiteralArgumentBuilder<ServerCommandSource> root)
    {
        root.then(CommandManager.literal("human")
            .then(CommandManager.literal("setAugment")
                .then(CommandManager.literal("")
                    .executes(ctx ->
                        {
                            var source = ctx.getSource();
                            ServerPlayerEntity executor = source.getEntity() instanceof ServerPlayerEntity player ? player : null;
                            var storage = SpeciesStorage.get(ctx.getSource().getServer());
                            String currentSpecies = storage.speciesGet(executor.getUuid());

                            if(!"human".equalsIgnoreCase(currentSpecies))
                            {
                                source.sendError(
                                    Text.literal("You are not human!")
                                );
                                return 0;
                            }

                            return 1;
                        }
                    )
                )
            )
        );
    }
}

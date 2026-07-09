package oc.wastelands.commands;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import oc.wastelands.species.SpeciesState;
import oc.wastelands.species.SpeciesStorage;

public class slimeCmd
{
    public static void attach(LiteralArgumentBuilder<ServerCommandSource> root)
    {
        root.then(CommandManager.literal("slime")
            .then(CommandManager.literal("changeSize")
                .then(CommandManager.argument("value", FloatArgumentType.floatArg(0.1F, 3.0F))
                    .executes(ctx ->
                    {
                        var source = ctx.getSource();
                        ServerPlayerEntity executor = source.getEntity() instanceof ServerPlayerEntity player ? player : null;
                        var storage = SpeciesStorage.get(ctx.getSource().getServer());
                        String currentSpecies = storage.speciesGet(executor.getUuid());
                        float size = FloatArgumentType.getFloat(ctx, "value");

                        if(!"slimekind".equalsIgnoreCase(currentSpecies))
                        {
                            source.sendError(
                                Text.literal("You are not slimekind!")
                            );
                            return 0;
                        }

                        SpeciesState.setSize(executor, size);
                        executor.calculateDimensions();
                        executor.requestTeleport(executor.getX(), executor.getY(), executor.getZ());
                        
                        executor.sendMessage(
                            Text.literal("Size set to " + size)
                        );
                        return 1;
                    }
                )
                )
            )
        );
    }
}

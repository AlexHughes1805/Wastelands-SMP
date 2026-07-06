package oc.wastelands.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class commandRoot
{
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        var root = CommandManager.literal("oc"); // requires /oc to initiate any commands in this mod

        adminCmd.attach(root);
        playerCmd.attach(root);

        dispatcher.register(root);
    }
}

package oc.wastelands.augments;

import java.util.Objects;


import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;



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

    public static Augment getAugmentObj(ServerPlayerEntity player)
    {
        String augmentName = augmentGet(player);
        return AugmentRegistry.get(augmentName);
    }

    public static void abilities(ServerPlayerEntity player)
    {
        Augment augment = getAugmentObj(player);

        if(augment.nightVision)
        {
            player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.NIGHT_VISION, 999999, 0, true, false
            ));
        }
        else
        {
            player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }

        EntityAttributeInstance speed = player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        if(speed != null)
        {
            speed.setBaseValue(0.1 * augment.speedMultiplier);
        }

        if(augment.jumpMultiplier > 1.0)
        {
            int multiplier = (int)((augment.jumpMultiplier - 1.0) * 2.0);
            player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.JUMP_BOOST, 999999, multiplier, true, false
            ));
        }
        else{
            player.removeStatusEffect(StatusEffects.JUMP_BOOST);
        }
    }
}

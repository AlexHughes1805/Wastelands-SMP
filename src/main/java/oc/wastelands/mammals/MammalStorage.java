package oc.wastelands.mammals;

import java.util.Objects;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

/*
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import oc.wastelands.mammal.Mammal;
import oc.wastelands.mammal.MammalRegistry;
import oc.wastelands.mammal.MammalState;
*/



public class MammalStorage
{
    public static MammalState get(MinecraftServer server)
    {
        return Objects.requireNonNull(server.getWorld(World.OVERWORLD))
            .getPersistentStateManager()
            .getOrCreate(MammalStateType.TYPE);
    }

    // setter function
    public static void mammalSet(ServerPlayerEntity player, String mammal)
    {
        MinecraftServer server = player.getCommandSource().getServer();
        MammalState state = get(server);
        state.mammalSet(player.getUuid(), mammal);
    }

    // remove function
    public static void mammalRemove(ServerPlayerEntity player)
    {
        MinecraftServer server = player.getCommandSource().getServer();
        MammalState state = get(server);
        state.mammalRemove(player.getUuid());
    }

    // getter function, returns the players faction as a string
    public static String mammalGet(ServerPlayerEntity player)
    {
        MinecraftServer server = player.getCommandSource().getServer();
        MammalState state = get(server);
        return state.mammalGet(player.getUuid());
    }

    /* 
    public static Mammal getMammalObj(ServerPlayerEntity player)
    {
        String mammalName = mammalGet(player);
        return MammalRegistry.get(mammalName);
    }

    public static void abilities(ServerPlayerEntity player)
    {
        Mammal mammal = getMammalObj(player);
        
        // flying
        player.getAbilities().allowFlying = mammal.canFly;
        player.sendAbilitiesUpdate();

        //nightvision
        if(mammal.nightVision)
        {
            player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.NIGHT_VISION, 999999, 0, true, false
            ));
        }
        else
        {
            player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }

        // speed multiplier
        EntityAttributeInstance speed = player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        if (speed != null)
        {
            speed.setBaseValue(0.1 * mammal.speedMultiplier);
        }

        //jump multiplier
        if(mammal.jumpMultiplier > 1.0)
        {
            int multiplier = (int)((mammal.jumpMultiplier - 1.0) * 2);
            player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.JUMP_BOOST, 999999, multiplier, true, false
            ));
        }
        else
        {
            player.removeStatusEffect(StatusEffects.JUMP_BOOST);
        }
    }
    */
}

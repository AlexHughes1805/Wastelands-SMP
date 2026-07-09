package oc.wastelands.species;

import java.util.Objects;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;


public class SpeciesStorage
{
    public static SpeciesState get(MinecraftServer server)
    {
        return Objects.requireNonNull(server.getWorld(World.OVERWORLD))
            .getPersistentStateManager()
            .getOrCreate(SpeciesStateType.TYPE);
    }

    // setter function
    public static void speciesSet(ServerPlayerEntity player, String species)
    {
        MinecraftServer server = player.getCommandSource().getServer();
        SpeciesState state = get(server);
        state.speciesSet(player.getUuid(), species);
    }

    // remove function
    public static void speciesRemove(ServerPlayerEntity player)
    {
        MinecraftServer server = player.getCommandSource().getServer();
        SpeciesState state = get(server);
        state.speciesRemove(player.getUuid());
    }

    // getter function, returns the players faction as a string
    public static String speciesGet(ServerPlayerEntity player)
    {
        MinecraftServer server = player.getCommandSource().getServer();
        SpeciesState state = get(server);
        return state.speciesGet(player.getUuid());
    }

    public static Species getSpeciesObj(ServerPlayerEntity player)
    {
        String speciesName = speciesGet(player);
        return SpeciesRegistry.get(speciesName);
    }

    public static void abilities(ServerPlayerEntity player)
    {
        Species species = getSpeciesObj(player);
        
        // flying
        player.getAbilities().allowFlying = species.canFly;
        player.sendAbilitiesUpdate();

        //nightvision
        if(species.nightVision)
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
            speed.setBaseValue(0.1 * species.speedMultiplier);
        }

        //jump multiplier
        if(species.jumpMultiplier > 1.0)
        {
            int multiplier = (int)((species.jumpMultiplier - 1.0) * 2);
            player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.JUMP_BOOST, 999999, multiplier, true, false
            ));
        }
        else
        {
            player.removeStatusEffect(StatusEffects.JUMP_BOOST);
        }
    }
}

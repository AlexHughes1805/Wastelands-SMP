package oc.wastelands.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import oc.wastelands.species.Species;
import oc.wastelands.species.SpeciesStorage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin
{

    @Inject(method = "addExhaustion", at = @At("HEAD"), cancellable = true)
    private void wastelands$slowExhaustion(float exhaustionAmount, CallbackInfo ci)
    {
        PlayerEntity self = (PlayerEntity)(Object)this;

        if (!(self instanceof ServerPlayerEntity player))
        {
            return;
        }

        Species species = SpeciesStorage.getSpeciesObj(player);

        if (species.slowHunger)
        {
            float slowed = exhaustionAmount * 0.5F;
            player.getHungerManager().addExhaustion(slowed);
            ci.cancel();
        }
    }
}


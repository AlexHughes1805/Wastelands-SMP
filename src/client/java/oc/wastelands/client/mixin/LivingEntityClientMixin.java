package oc.wastelands.client.mixin;

import oc.wastelands.species.SpeciesState;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.player.PlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityClientMixin
{

    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void wastelands$scaleDimensionsClient(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;

        // Client player class is PlayerEntity, not ServerPlayerEntity
        if (!(entity instanceof PlayerEntity player)) {
            return;
        }

        float scale = SpeciesState.getSize(player);

        if (scale != 1.0F) {
            EntityDimensions base = cir.getReturnValue();
            cir.setReturnValue(base.scaled(scale));
        }
    }
}

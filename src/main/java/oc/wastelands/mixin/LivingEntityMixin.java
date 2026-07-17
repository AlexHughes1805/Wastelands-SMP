package oc.wastelands.mixin;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import oc.wastelands.augments.Augment;
import oc.wastelands.augments.AugmentStorage;
import oc.wastelands.species.Species;
import oc.wastelands.species.SpeciesState;
import oc.wastelands.species.SpeciesStorage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin
{

    @Inject(
        method = "getDimensions",
        at = @At("RETURN"),
        cancellable = true
    )
    private void wastelands$scaleDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir)
    {
        LivingEntity entity = (LivingEntity)(Object)this;

        // Only scale players
        if (!(entity instanceof ServerPlayerEntity player))
        {
            return;
        }

        float scale = SpeciesState.getSize(player);

        if (scale != 1.0F)
        {
            EntityDimensions base = cir.getReturnValue();
            cir.setReturnValue(base.scaled(scale));
        }
    }

    @ModifyArg(
        method = "applyDamage",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;setHealth(F)V"
        ),
        index = 0
    )
    private float wastelands$modifyDamage(float originalDamage)
    {
        LivingEntity entity = (LivingEntity)(Object)this;

        if (!(entity instanceof ServerPlayerEntity player)) {
            return originalDamage;
        }

        Species species = SpeciesStorage.getSpeciesObj(player);
        Augment augment = AugmentStorage.getAugmentObj(player);
        String playerSpecies = SpeciesStorage.speciesGet(player);
        String playerAugment = AugmentStorage.augmentGet(player);

        float damage = originalDamage * (float)species.damageMultiplier;

        if(playerSpecies == "human" && playerAugment == "reducedDamage")
        {
            damage *= augment.damageMultiplier;
        }

        return damage;
    }
}

package oc.wastelands.client.mixin;

import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.PlayerLikeEntity;
import net.minecraft.entity.player.PlayerEntity;

import oc.wastelands.client.accessor.PlayerEntityRenderStateAccessor;
import oc.wastelands.species.SpeciesState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin
{

    @Inject(method = "updateRenderState", at = @At("TAIL"))
    private void wastelands$storeScale(PlayerLikeEntity player, PlayerEntityRenderState state, float tickProgress, CallbackInfo ci)
    {
        ((PlayerEntityRenderStateAccessor) (Object) state).wastelands$setScale(SpeciesState.getSize((PlayerEntity) player));
    }

    @Inject(method = "scale", at = @At("HEAD"))
    private void wastelands$scalePlayerModel(PlayerEntityRenderState state, MatrixStack matrices, CallbackInfo ci)
    {
        float scale = ((PlayerEntityRenderStateAccessor) (Object) state).wastelands$getScale();
        if (scale != 1.0F)
        {
            matrices.scale(scale, scale, scale);
        }
    }
}
package oc.wastelands.client.mixin;

import net.minecraft.client.render.entity.state.PlayerEntityRenderState;

import oc.wastelands.client.accessor.PlayerEntityRenderStateAccessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public class PlayerEntityRenderStateMixin implements PlayerEntityRenderStateAccessor
{
    @Unique
    private float wastelands$scale = 1.0F;

    @Override
    public float wastelands$getScale()
    {
        return wastelands$scale;
    }

    @Override
    public void wastelands$setScale(float scale)
    {
        wastelands$scale = scale;
    }
}
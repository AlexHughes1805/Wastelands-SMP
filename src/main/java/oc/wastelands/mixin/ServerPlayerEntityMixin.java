package oc.wastelands.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import oc.wastelands.factions.FactionStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin
{
    @Inject(method = "getPlayerListName", at = @At("HEAD"), cancellable = true)
    private void wastelands$playerListName(CallbackInfoReturnable<Text> cir)
    {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        String faction = FactionStorage.factionGet(player);

        Formatting formatting = Formatting.WHITE;
        if ("Zephyr".equalsIgnoreCase(faction))
        {
            formatting = Formatting.AQUA;
        }
        else if ("Terra".equalsIgnoreCase(faction))
        {
            formatting = Formatting.DARK_GREEN;
        }

        cir.setReturnValue(Text.literal(player.getName().getString()).formatted(formatting));
    }
}
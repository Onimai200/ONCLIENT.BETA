package ru.onnmy.freelook.mixin;

import ru.onnmy.freelook.OnnmyFreelookClient;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraMixin {
    @Inject(method = "setup", at = @At("TAIL"))
    private void onnmy$freelook(Minecraft client, net.minecraft.world.level.BlockGetter area,
                                net.minecraft.world.entity.Entity focusedEntity, boolean thirdPerson,
                                boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (!OnnmyFreelookClient.enabled || !(focusedEntity instanceof LocalPlayer)) return;

        Camera camera = (Camera)(Object)this;
        // Camera's final rotation is controlled through the player view values.
        // The mixin keeps the feature client-side; mouse rotation is handled by CameraMixin2.
    }
}
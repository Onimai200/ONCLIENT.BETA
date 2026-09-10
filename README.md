package net.example.zoommod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ZoomModClient implements ClientModInitializer {
    public static KeyBinding zoomKeyBinding;
    public static boolean isZooming = false;

    @Override
    public void onInitializeClient() {
        // Регистрируем клавишу "Z" для зума
        zoomKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.zoommod.zoom", // Название в настройках игры
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z, // Клавиша по умолчанию
                "category.zoommod.general" // Категория в настройках
        ));
    }
}
package net.example.zoommod.mixin;

import net.example.zoommod.ZoomModClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    // Текущий коэффициент приближения для плавной анимации
    private float zoomLevel = 1.0f;

    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void injectZoom(CallbackInfoReturnable<Double> info) {
        boolean isKeyPressed = ZoomModClient.zoomKeyBinding.isPressed();
        
        // Плавное приближение и отдаление
        if (isKeyPressed) {
            // Уменьшаем FOV (приближаем). 4.0f — это сила зума
            zoomLevel = Math.max(0.25f, zoomLevel - 0.05f); 
        } else {
            // Возвращаем FOV в исходное состояние
            zoomLevel = Math.min(1.0f, zoomLevel + 0.05f);
        }

        if (zoomLevel < 1.0f) {
            double originalFov = info.getReturnValue();
            // Умножаем стандартный FOV на коэффициент зума
            info.setReturnValue(originalFov * zoomLevel);
        }
    }
}

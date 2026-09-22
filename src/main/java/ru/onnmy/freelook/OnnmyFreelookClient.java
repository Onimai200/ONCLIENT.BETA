package ru.onnmy.freelook;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public final class OnnmyFreelookClient implements ClientModInitializer {
    public static boolean enabled = false;
    public static float yaw;
    public static float pitch;

    private static KeyMapping key;

    @Override
    public void onInitializeClient() {
        key = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.onnmy_freelook.toggle",
                GLFW.GLFW_KEY_F5,
                "category.onnmy_freelook"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (key.consumeClick()) {
                enabled = !enabled;
                if (client.player != null) {
                    if (enabled) {
                        yaw = client.player.getYRot();
                        pitch = client.player.getXRot();
                    } else {
                        client.player.setYRot(yaw);
                        client.player.setXRot(pitch);
                    }
                }
            }
        });
    }
}
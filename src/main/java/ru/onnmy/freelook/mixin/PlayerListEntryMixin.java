package ru.onnmy.freelook.mixin;

import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerTabOverlay.class)
public class PlayerListEntryMixin {
    // Reserved for custom TAB rendering. The vanilla TAB already exposes the player's
    // ping through its network info; this project keeps the hook isolated for future styling.
}
package com.hotbarfade;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

/**
 * Hotbar Fade on Zoom
 *
 * Watches the player's effective field-of-view every client tick.
 * When it detects the FOV has been reduced below the configured base
 * FOV (which is what Zoomify / any zoom mod does while zooming), it
 * smoothly fades the hotbar's opacity down to 0. When FOV returns to
 * normal, it fades the hotbar back to full opacity.
 *
 * This mod does not depend on Zoomify's internal code at all - it
 * only reacts to the FOV value, so it should work with Zoomify,
 * Cinematic Zoom, or any other zoom mod.
 */
public class HotbarFadeMod implements ClientModInitializer {

    // How quickly the fade happens. 0.0 = never moves, 1.0 = instant.
    // 0.15 gives a smooth ~0.5 second fade at 20 ticks/second.
    private static final float FADE_SPEED = 0.15f;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.options == null) {
                return;
            }

            float baseFov = client.options.getFov().getValue();
            float currentFov = FovState.currentFov;

            // If we haven't captured a real FOV yet, don't do anything.
            if (currentFov <= 0f) {
                return;
            }

            // Consider "zoomed" if the rendered FOV is noticeably
            // smaller than the configured base FOV.
            boolean zooming = currentFov < (baseFov - 1.0f);

            float target = zooming ? 0.0f : 1.0f;
            FovState.hotbarAlpha += (target - FovState.hotbarAlpha) * FADE_SPEED;

            // Snap to exact values when very close, to avoid endless
            // tiny floating point drift.
            if (Math.abs(FovState.hotbarAlpha - target) < 0.01f) {
                FovState.hotbarAlpha = target;
            }
        });
    }
}

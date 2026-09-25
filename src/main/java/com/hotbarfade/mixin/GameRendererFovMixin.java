package com.hotbarfade.mixin;

import com.hotbarfade.FovState;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Captures the final FOV value the game is about to render with,
 * AFTER any zoom mod (Zoomify, Cinematic Zoom, etc.) has already
 * modified it. This is the standard injection point used by most
 * zoom-related mods.
 *
 * NOTE: If the build fails complaining this method doesn't exist,
 * the method name/signature may differ slightly for your exact
 * Minecraft version - that's an easy one-line fix once we see the
 * error.
 */
@Mixin(GameRenderer.class)
public class GameRendererFovMixin {

    @Inject(method = "getFov", at = @At("RETURN"))
    private void hotbarfade$captureFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        FovState.currentFov = cir.getReturnValue().floatValue();
    }
}

package com.hotbarfade.mixin;

import com.hotbarfade.FovState;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Wraps the hotbar render call with a shader color that includes our
 * fade alpha, then resets the color afterwards so nothing else on
 * screen is affected.
 *
 * NOTE: If the build fails complaining "renderHotbar" doesn't exist
 * with this signature, Mojang may have renamed/reshaped it slightly
 * for your version - paste the build error back and it's a quick fix.
 */
@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "renderHotbar", at = @At("HEAD"))
    private void hotbarfade$beforeHotbar(float tickDelta, DrawContext context, CallbackInfo ci) {
        float a = FovState.hotbarAlpha;
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, a);
    }

    @Inject(method = "renderHotbar", at = @At("RETURN"))
    private void hotbarfade$afterHotbar(float tickDelta, DrawContext context, CallbackInfo ci) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}

package com.hotbarfade;

/**
 * Simple shared holder for the current FOV (captured from the game
 * renderer each frame) and the current hotbar opacity (updated once
 * per tick in HotbarFadeMod).
 */
public class FovState {
    public static volatile float currentFov = 0f;
    public static volatile float hotbarAlpha = 1.0f;
}

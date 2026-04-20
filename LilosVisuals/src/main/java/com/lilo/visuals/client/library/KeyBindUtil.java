package com.lilo.visuals.client.library;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.glfw.GLFW;

/**
 * Modern key binding library for Lilos Visuals
 * Provides easy key binding management and input handling
 */
public class KeyBindUtil {
    private static final Minecraft mc = Minecraft.getInstance();
    
    // Main ClickGUI key
    public static KeyBinding openClickGuiKey;
    
    // HUD toggle keys
    public static KeyBinding toggleHudKey;
    public static KeyBinding toggleFPSKey;
    public static KeyBinding toggleCoordsKey;
    
    /**
     * Initialize all key bindings
     */
    public static void init() {
        openClickGuiKey = new KeyBinding(
            "key.lilosvisuals.openClickGui",
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "Lilo's Visuals"
        );
        
        toggleHudKey = new KeyBinding(
            "key.lilosvisuals.toggleHud",
            GLFW.GLFW_KEY_H,
            "Lilo's Visuals"
        );
        
        toggleFPSKey = new KeyBinding(
            "key.lilosvisuals.toggleFPS",
            GLFW.GLFW_KEY_F,
            "Lilo's Visuals"
        );
        
        toggleCoordsKey = new KeyBinding(
            "key.lilosvisuals.toggleCoords",
            GLFW.GLFW_KEY_C,
            "Lilo's Visuals"
        );
    }
    
    /**
     * Register all key bindings with Forge
     */
    public static void register() {
        if (openClickGuiKey != null) {
            net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding(openClickGuiKey);
        }
        if (toggleHudKey != null) {
            net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding(toggleHudKey);
        }
        if (toggleFPSKey != null) {
            net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding(toggleFPSKey);
        }
        if (toggleCoordsKey != null) {
            net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding(toggleCoordsKey);
        }
    }
    
    /**
     * Check if a key is currently pressed
     */
    public static boolean isKeyDown(KeyBinding keyBinding) {
        return keyBinding.isKeyDown();
    }
    
    /**
     * Check if a specific GLFW key code is pressed
     */
    public static boolean isKeyPressed(int keyCode) {
        long handle = mc.getMainWindow().getHandle();
        return GLFW.glfwGetKey(handle, keyCode) == GLFW.GLFW_PRESS;
    }
    
    /**
     * Get the display name for a key code
     */
    public static String getKeyName(int keyCode) {
        return GLFW.glfwGetKeyName(keyCode, 0);
    }
    
    /**
     * Handle key input for ClickGUI toggle
     */
    public static void handleInput() {
        if (mc.player == null) return;
        
        // Toggle ClickGUI
        while (openClickGuiKey.isPressed()) {
            if (mc.currentScreen == null) {
                mc.displayGuiScreen(com.lilo.visuals.LilosVisuals.getClickGui());
                com.lilo.visuals.LilosVisuals.setClickGuiOpen(true);
            } else if (mc.currentScreen instanceof com.lilo.visuals.client.gui.clickgui.ClickGui) {
                mc.currentScreen.onClose();
            }
        }
        
        // Toggle HUD elements
        while (toggleHudKey.isPressed()) {
            var hud = com.lilo.visuals.LilosVisuals.getHudRenderer();
            hud.setShowWatermark(!hud.isShowWatermark());
        }
        
        while (toggleFPSKey.isPressed()) {
            var hud = com.lilo.visuals.LilosVisuals.getHudRenderer();
            hud.setShowFPS(!hud.isShowFPS());
        }
        
        while (toggleCoordsKey.isPressed()) {
            var hud = com.lilo.visuals.LilosVisuals.getHudRenderer();
            hud.setShowCoords(!hud.isShowCoords());
        }
    }
}

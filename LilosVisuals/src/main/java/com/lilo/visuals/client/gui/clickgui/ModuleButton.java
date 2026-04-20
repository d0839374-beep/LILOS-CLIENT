package com.lilo.visuals.client.gui.clickgui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single module button in the ClickGUI
 */
public class ModuleButton {
    private static final Minecraft mc = Minecraft.getInstance();
    
    private String name;
    private ModulePanel panel;
    private boolean enabled;
    private Map<String, Object> settings;
    
    public ModuleButton(String name, ModulePanel panel) {
        this.name = name;
        this.panel = panel;
        this.enabled = false;
        this.settings = new HashMap<>();
    }
    
    public void render(MatrixStack matrixStack, int x, int y, int width, int mouseX, int mouseY) {
        // Determine colors based on state
        int bgColor = enabled ? 0x666c5ce7 : 0x44000000; // Purple if enabled, dark if disabled
        int hoverColor = 0x886c5ce7;
        
        // Check if mouse is hovering
        boolean isHovered = mouseX >= x && mouseX <= x + width &&
                           mouseY >= y && mouseY <= y + 18;
        
        // Render background with hover effect
        if (isHovered) {
            fill(matrixStack, x, y, x + width, y + 18, hoverColor);
        } else {
            fill(matrixStack, x, y, x + width, y + 18, bgColor);
        }
        
        // Draw module name
        String displayName = enabled ? "§a" + name : "§7" + name;
        mc.font.draw(matrixStack, displayName, x + 5, y + 5, ClickGui.TEXT_COLOR);
        
        // Draw toggle indicator
        String status = enabled ? "§a[ON]" : "§7[OFF]";
        mc.font.draw(matrixStack, status, x + width - 40, y + 5, ClickGui.TEXT_COLOR);
    }
    
    public void toggle() {
        enabled = !enabled;
        onToggle(enabled);
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public String getName() {
        return name;
    }
    
    private void onToggle(boolean enabled) {
        // Placeholder for actual module logic
        // In a real implementation, this would enable/disable the actual cheat
        if (enabled) {
            System.out.println("[LilosVisuals] Enabled: " + name);
        } else {
            System.out.println("[LilosVisuals] Disabled: " + name);
        }
    }
    
    private void fill(MatrixStack matrixStack, int left, int top, int right, int bottom, int color) {
        net.minecraft.client.gui.AbstractGui.fill(matrixStack, left, top, right, bottom, color);
    }
}

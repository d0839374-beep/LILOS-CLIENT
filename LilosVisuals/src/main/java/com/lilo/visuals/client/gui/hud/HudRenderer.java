package com.lilo.visuals.client.gui.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;

/**
 * Modern HUD renderer for Lilos Visuals
 * Displays information like FPS, CPS, coordinates, and enabled modules
 */
public class HudRenderer {
    private static final Minecraft mc = Minecraft.getInstance();
    
    // HUD Colors
    public static final int HUD_BACKGROUND = 0x80000000;
    public static final int HUD_TEXT_COLOR = 0xFFFFFFFF;
    public static final int HUD_ACCENT = 0xFF6c5ce7;
    
    private boolean showFPS = true;
    private boolean showCPS = true;
    private boolean showCoords = true;
    private boolean showModuleList = true;
    private boolean showWatermark = true;
    
    public void render(MatrixStack matrixStack) {
        if (mc.player == null || mc.world == null) return;
        
        int yOffset = 5;
        int xOffset = 5;
        
        // Render watermark
        if (showWatermark) {
            renderWatermark(matrixStack, xOffset, yOffset);
            yOffset += 20;
        }
        
        // Render FPS
        if (showFPS) {
            renderInfoLine(matrixStack, "§7FPS: §f" + getFPS(), xOffset, yOffset);
            yOffset += 12;
        }
        
        // Render CPS
        if (showCPS) {
            renderInfoLine(matrixStack, "§7CPS: §f" + getCPS(), xOffset, yOffset);
            yOffset += 12;
        }
        
        // Render Coordinates
        if (showCoords) {
            int x = (int) Math.floor(mc.player.getPosX());
            int y = (int) Math.floor(mc.player.getPosY());
            int z = (int) Math.floor(mc.player.getPosZ());
            renderInfoLine(matrixStack, String.format("§7XYZ: §f%d %d %d", x, y, z), xOffset, yOffset);
            yOffset += 12;
            
            // Render dimension
            String dimension = getDimensionName();
            renderInfoLine(matrixStack, "§7Dimension: §f" + dimension, xOffset, yOffset);
            yOffset += 12;
        }
        
        // Render module list
        if (showModuleList) {
            renderModuleList(matrixStack);
        }
    }
    
    private void renderWatermark(MatrixStack matrixStack, int x, int y) {
        String watermark = "§l§6Lilo's Visuals §r§7| §fv1.0";
        
        // Background
        int textWidth = mc.font.getStringWidth(StringTextComponent.func_240661_a_(watermark).getString());
        fill(matrixStack, x - 2, y - 2, x + textWidth + 4, y + 12, HUD_BACKGROUND);
        
        // Accent line
        fill(matrixStack, x - 2, y - 2, x + textWidth + 4, y - 1, HUD_ACCENT);
        
        // Text
        mc.font.draw(matrixStack, watermark, x, y, HUD_TEXT_COLOR);
    }
    
    private void renderInfoLine(MatrixStack matrixStack, String text, int x, int y) {
        // Background
        int textWidth = mc.font.getStringWidth(StringTextComponent.func_240661_a_(text).getString());
        fill(matrixStack, x - 1, y - 1, x + textWidth + 2, y + 10, HUD_BACKGROUND);
        
        // Text
        mc.font.draw(matrixStack, text, x, y, HUD_TEXT_COLOR);
    }
    
    private void renderModuleList(MatrixStack matrixStack) {
        // This would render active modules on the right side
        // For now, it's a placeholder
        int x = mc.getMainWindow().getScaledWidth() - 150;
        int y = 5;
        
        String title = "§l§6Active Modules";
        mc.font.draw(matrixStack, title, x, y, HUD_ACCENT);
        
        // Module list would go here
        y += 12;
        mc.font.draw(matrixStack, "§7No active modules", x, y, 0x888888);
    }
    
    private int getFPS() {
        return Minecraft.debugFPS;
    }
    
    private int getCPS() {
        // Placeholder CPS calculation
        // In a real implementation, this would track actual clicks
        return 0;
    }
    
    private String getDimensionName() {
        if (mc.world == null) return "Unknown";
        
        if (mc.world.getDimensionKey() == net.minecraft.world.dimension.DimensionType.OVERWORLD) {
            return "Overworld";
        } else if (mc.world.getDimensionKey() == net.minecraft.world.dimension.DimensionType.NETHER) {
            return "Nether";
        } else if (mc.world.getDimensionKey() == net.minecraft.world.dimension.DimensionType.THE_END) {
            return "The End";
        }
        return "Unknown";
    }
    
    private void fill(MatrixStack matrixStack, int left, int top, int right, int bottom, int color) {
        net.minecraft.client.gui.AbstractGui.fill(matrixStack, left, top, right, bottom, color);
    }
    
    // Getters and setters
    public boolean isShowFPS() { return showFPS; }
    public void setShowFPS(boolean showFPS) { this.showFPS = showFPS; }
    
    public boolean isShowCPS() { return showCPS; }
    public void setShowCPS(boolean showCPS) { this.showCPS = showCPS; }
    
    public boolean isShowCoords() { return showCoords; }
    public void setShowCoords(boolean showCoords) { this.showCoords = showCoords; }
    
    public boolean isShowModuleList() { return showModuleList; }
    public void setShowModuleList(boolean showModuleList) { this.showModuleList = showModuleList; }
    
    public boolean isShowWatermark() { return showWatermark; }
    public void setShowWatermark(boolean showWatermark) { this.showWatermark = showWatermark; }
}

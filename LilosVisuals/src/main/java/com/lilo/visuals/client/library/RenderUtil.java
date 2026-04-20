package com.lilo.visuals.client.library;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import org.lwjgl.opengl.GL11;

/**
 * Modern rendering library for Lilos Visuals
 * Provides utility methods for smooth, modern UI rendering
 */
public class RenderUtil {
    private static final Minecraft mc = Minecraft.getInstance();
    
    /**
     * Draws a gradient rectangle with smooth colors
     */
    public static void drawGradientRect(MatrixStack matrixStack, int left, int top, int right, int bottom, 
                                        int colorStart, int colorEnd) {
        float alphaStart = (float)(colorStart >> 24 & 255) / 255.0F;
        float redStart = (float)(colorStart >> 16 & 255) / 255.0F;
        float greenStart = (float)(colorStart >> 8 & 255) / 255.0F;
        float blueStart = (float)(colorStart & 255) / 255.0F;
        
        float alphaEnd = (float)(colorEnd >> 24 & 255) / 255.0F;
        float redEnd = (float)(colorEnd >> 16 & 255) / 255.0F;
        float greenEnd = (float)(colorEnd >> 8 & 255) / 255.0F;
        float blueEnd = (float)(colorEnd & 255) / 255.0F;
        
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        
        net.minecraft.client.renderer.BufferBuilder buffer = net.minecraft.client.renderer.Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(matrixStack.getLast().getModel(), right, top, 0).color(redStart, greenStart, blueStart, alphaStart).endVertex();
        buffer.pos(matrixStack.getLast().getModel(), left, top, 0).color(redStart, greenStart, blueStart, alphaStart).endVertex();
        buffer.pos(matrixStack.getLast().getModel(), left, bottom, 0).color(redEnd, greenEnd, blueEnd, alphaEnd).endVertex();
        buffer.pos(matrixStack.getLast().getModel(), right, bottom, 0).color(redEnd, greenEnd, blueEnd, alphaEnd).endVertex();
        net.minecraft.client.renderer.Tessellator.getInstance().draw();
        
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
    
    /**
     * Draws a rounded rectangle (simulated with multiple fills)
     */
    public static void drawRoundedRect(MatrixStack matrixStack, int x, int y, int width, int height, 
                                       int radius, int color) {
        // Main body
        fill(matrixStack, x + radius, y, x + width - radius, y + height, color);
        fill(matrixStack, x, y + radius, x + width, y + height - radius, color);
        
        // Corners (simplified - would need proper circle drawing for true rounded corners)
        fill(matrixStack, x + radius, y + radius, x + width - radius, y + height - radius, color);
    }
    
    /**
     * Draws a filled rectangle
     */
    public static void fill(MatrixStack matrixStack, int left, int top, int right, int bottom, int color) {
        net.minecraft.client.gui.AbstractGui.fill(matrixStack, left, top, right, bottom, color);
    }
    
    /**
     * Draws an outlined rectangle
     */
    public static void drawOutline(MatrixStack matrixStack, int left, int top, int right, int bottom, 
                                   int color, int thickness) {
        fill(matrixStack, left, top, right, top + thickness, color); // Top
        fill(matrixStack, left, bottom - thickness, right, bottom, color); // Bottom
        fill(matrixStack, left, top, left + thickness, bottom, color); // Left
        fill(matrixStack, right - thickness, top, right, bottom, color); // Right
    }
    
    /**
     * Draws text with shadow
     */
    public static void drawTextWithShadow(MatrixStack matrixStack, String text, int x, int y, int color) {
        mc.font.drawString(matrixStack, text, x + 1, y + 1, 0x000000); // Shadow
        mc.font.drawString(matrixStack, text, x, y, color);
    }
    
    /**
     * Draws centered text with shadow
     */
    public static void drawCenteredTextWithShadow(MatrixStack matrixStack, String text, int x, int y, int color) {
        int textWidth = mc.font.getStringWidth(text);
        drawTextWithShadow(matrixStack, text, x - textWidth / 2, y, color);
    }
    
    /**
     * Enables scissor test for clipping
     */
    public static void enableScissor(int x, int y, int width, int height) {
        double scale = mc.getMainWindow().getScaleFactor();
        int scaledX = (int)(x * scale);
        int scaledY = (int)((mc.getMainWindow().getScaledHeight() - y - height) * scale);
        int scaledWidth = (int)(width * scale);
        int scaledHeight = (int)(height * scale);
        
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(scaledX, scaledY, scaledWidth, scaledHeight);
    }
    
    /**
     * Disables scissor test
     */
    public static void disableScissor() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
    
    /**
     * Draws a horizontal line
     */
    public static void drawHorizontalLine(MatrixStack matrixStack, int startX, int endX, int y, int color) {
        if (endX < startX) {
            int temp = startX;
            startX = endX;
            endX = temp;
        }
        fill(matrixStack, startX, y, endX + 1, y + 1, color);
    }
    
    /**
     * Draws a vertical line
     */
    public static void drawVerticalLine(MatrixStack matrixStack, int x, int startY, int endY, int color) {
        if (endY < startY) {
            int temp = startY;
            startY = endY;
            endY = temp;
        }
        fill(matrixStack, x, startY, x + 1, endY + 1, color);
    }
    
    /**
     * Interpolates between two colors
     */
    public static int interpolateColor(int color1, int color2, float factor) {
        factor = Math.max(0.0F, Math.min(1.0F, factor));
        
        int alpha1 = (color1 >> 24) & 0xFF;
        int red1 = (color1 >> 16) & 0xFF;
        int green1 = (color1 >> 8) & 0xFF;
        int blue1 = color1 & 0xFF;
        
        int alpha2 = (color2 >> 24) & 0xFF;
        int red2 = (color2 >> 16) & 0xFF;
        int green2 = (color2 >> 8) & 0xFF;
        int blue2 = color2 & 0xFF;
        
        int alpha = (int)(alpha1 + (alpha2 - alpha1) * factor);
        int red = (int)(red1 + (red2 - red1) * factor);
        int green = (int)(green1 + (green2 - green1) * factor);
        int blue = (int)(blue1 + (blue2 - blue1) * factor);
        
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
}

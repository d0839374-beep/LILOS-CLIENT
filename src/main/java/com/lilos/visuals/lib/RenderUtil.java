package com.lilos.visuals.lib;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
    private static final Minecraft mc = Minecraft.getInstance();
    
    public static void drawRect(float x, float y, float width, float height, int color) {
        float a = (color >> 24 & 0xFF) / 255.0F;
        float r = (color >> 16 & 0xFF) / 255.0F;
        float g = (color >> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(x, y + height, 0).color(r, g, b, a).endVertex();
        bufferBuilder.pos(x + width, y + height, 0).color(r, g, b, a).endVertex();
        bufferBuilder.pos(x + width, y, 0).color(r, g, b, a).endVertex();
        bufferBuilder.pos(x, y, 0).color(r, g, b, a).endVertex();
        
        tessellator.draw();
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    public static void drawGradientRect(float x, float y, float width, float height, int topColor, int bottomColor) {
        float a1 = (topColor >> 24 & 0xFF) / 255.0F;
        float r1 = (topColor >> 16 & 0xFF) / 255.0F;
        float g1 = (topColor >> 8 & 0xFF) / 255.0F;
        float b1 = (topColor & 0xFF) / 255.0F;
        
        float a2 = (bottomColor >> 24 & 0xFF) / 255.0F;
        float r2 = (bottomColor >> 16 & 0xFF) / 255.0F;
        float g2 = (bottomColor >> 8 & 0xFF) / 255.0F;
        float b2 = (bottomColor & 0xFF) / 255.0F;
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(x, y + height, 0).color(r1, g1, b1, a1).endVertex();
        bufferBuilder.pos(x + width, y + height, 0).color(r2, g2, b2, a2).endVertex();
        bufferBuilder.pos(x + width, y, 0).color(r2, g2, b2, a2).endVertex();
        bufferBuilder.pos(x, y, 0).color(r1, g1, b1, a1).endVertex();
        
        tessellator.draw();
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    public static void drawHorizontalGradient(float x, float y, float width, float height, int leftColor, int rightColor) {
        float a1 = (leftColor >> 24 & 0xFF) / 255.0F;
        float r1 = (leftColor >> 16 & 0xFF) / 255.0F;
        float g1 = (leftColor >> 8 & 0xFF) / 255.0F;
        float b1 = (leftColor & 0xFF) / 255.0F;
        
        float a2 = (rightColor >> 24 & 0xFF) / 255.0F;
        float r2 = (rightColor >> 16 & 0xFF) / 255.0F;
        float g2 = (rightColor >> 8 & 0xFF) / 255.0F;
        float b2 = (rightColor & 0xFF) / 255.0F;
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(x, y + height, 0).color(r1, g1, b1, a1).endVertex();
        bufferBuilder.pos(x + width, y + height, 0).color(r2, g2, b2, a2).endVertex();
        bufferBuilder.pos(x + width, y, 0).color(r2, g2, b2, a2).endVertex();
        bufferBuilder.pos(x, y, 0).color(r1, g1, b1, a1).endVertex();
        
        tessellator.draw();
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    public static void drawRoundedRect(float x, float y, float width, float height, float radius, int color) {
        if (width < radius * 2) radius = width / 2;
        if (height < radius * 2) radius = height / 2;
        
        int segments = 16;
        float a = (color >> 24 & 0xFF) / 255.0F;
        float r = (color >> 16 & 0xFF) / 255.0F;
        float g = (color >> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        // Main rectangle
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(x + radius, y + height, 0).color(r, g, b, a).endVertex();
        bufferBuilder.pos(x + width - radius, y + height, 0).color(r, g, b, a).endVertex();
        bufferBuilder.pos(x + width - radius, y, 0).color(r, g, b, a).endVertex();
        bufferBuilder.pos(x + radius, y, 0).color(r, g, b, a).endVertex();
        tessellator.draw();
        
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(x, y + radius, 0).color(r, g, b, a).endVertex();
        bufferBuilder.pos(x + radius, y + radius, 0).color(r, g, b, a).endVertex();
        bufferBuilder.pos(x + radius, y + height - radius, 0).color(r, g, b, a).endVertex();
        bufferBuilder.pos(x, y + height - radius, 0).color(r, g, b, a).endVertex();
        tessellator.draw();
        
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(x + width - radius, y + radius, 0).color(r, g, b, a).endVertex();
        bufferBuilder.pos(x + width, y + radius, 0).color(r, g, b, a).endVertex();
        bufferBuilder.pos(x + width, y + height - radius, 0).color(r, g, b, a).endVertex();
        bufferBuilder.pos(x + width - radius, y + height - radius, 0).color(r, g, b, a).endVertex();
        tessellator.draw();
        
        // Corners
        for (int i = 0; i < segments; i++) {
            float angle1 = (float) Math.toRadians(90 + i * (90.0 / segments));
            float angle2 = (float) Math.toRadians(90 + (i + 1) * (90.0 / segments));
            
            // Top-left corner
            float x1 = x + radius + (float) Math.cos(angle1) * radius;
            float y1 = y + radius + (float) Math.sin(angle1) * radius;
            float x2 = x + radius + (float) Math.cos(angle2) * radius;
            float y2 = y + radius + (float) Math.sin(angle2) * radius;
            
            bufferBuilder.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(x + radius, y + radius, 0).color(r, g, b, a).endVertex();
            bufferBuilder.pos(x1, y1, 0).color(r, g, b, a).endVertex();
            bufferBuilder.pos(x2, y2, 0).color(r, g, b, a).endVertex();
            tessellator.draw();
            
            // Top-right corner
            angle1 = (float) Math.toRadians(0 + i * (90.0 / segments));
            angle2 = (float) Math.toRadians(0 + (i + 1) * (90.0 / segments));
            
            x1 = x + width - radius + (float) Math.cos(angle1) * radius;
            y1 = y + radius + (float) Math.sin(angle1) * radius;
            x2 = x + width - radius + (float) Math.cos(angle2) * radius;
            y2 = y + radius + (float) Math.sin(angle2) * radius;
            
            bufferBuilder.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(x + width - radius, y + radius, 0).color(r, g, b, a).endVertex();
            bufferBuilder.pos(x1, y1, 0).color(r, g, b, a).endVertex();
            bufferBuilder.pos(x2, y2, 0).color(r, g, b, a).endVertex();
            tessellator.draw();
            
            // Bottom-right corner
            angle1 = (float) Math.toRadians(270 + i * (90.0 / segments));
            angle2 = (float) Math.toRadians(270 + (i + 1) * (90.0 / segments));
            
            x1 = x + width - radius + (float) Math.cos(angle1) * radius;
            y1 = y + height - radius + (float) Math.sin(angle1) * radius;
            x2 = x + width - radius + (float) Math.cos(angle2) * radius;
            y2 = y + height - radius + (float) Math.sin(angle2) * radius;
            
            bufferBuilder.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(x + width - radius, y + height - radius, 0).color(r, g, b, a).endVertex();
            bufferBuilder.pos(x1, y1, 0).color(r, g, b, a).endVertex();
            bufferBuilder.pos(x2, y2, 0).color(r, g, b, a).endVertex();
            tessellator.draw();
            
            // Bottom-left corner
            angle1 = (float) Math.toRadians(180 + i * (90.0 / segments));
            angle2 = (float) Math.toRadians(180 + (i + 1) * (90.0 / segments));
            
            x1 = x + radius + (float) Math.cos(angle1) * radius;
            y1 = y + height - radius + (float) Math.sin(angle1) * radius;
            x2 = x + radius + (float) Math.cos(angle2) * radius;
            y2 = y + height - radius + (float) Math.sin(angle2) * radius;
            
            bufferBuilder.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(x + radius, y + height - radius, 0).color(r, g, b, a).endVertex();
            bufferBuilder.pos(x1, y1, 0).color(r, g, b, a).endVertex();
            bufferBuilder.pos(x2, y2, 0).color(r, g, b, a).endVertex();
            tessellator.draw();
        }
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    public static void drawRoundedGradientRect(float x, float y, float width, float height, float radius, int topColor, int bottomColor) {
        if (width < radius * 2) radius = width / 2;
        if (height < radius * 2) radius = height / 2;
        
        int segments = 16;
        
        float a1 = (topColor >> 24 & 0xFF) / 255.0F;
        float r1 = (topColor >> 16 & 0xFF) / 255.0F;
        float g1 = (topColor >> 8 & 0xFF) / 255.0F;
        float b1 = (topColor & 0xFF) / 255.0F;
        
        float a2 = (bottomColor >> 24 & 0xFF) / 255.0F;
        float r2 = (bottomColor >> 16 & 0xFF) / 255.0F;
        float g2 = (bottomColor >> 8 & 0xFF) / 255.0F;
        float b2 = (bottomColor & 0xFF) / 255.0F;
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        // Main rectangle with gradient
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(x + radius, y + height, 0).color(r2, g2, b2, a2).endVertex();
        bufferBuilder.pos(x + width - radius, y + height, 0).color(r2, g2, b2, a2).endVertex();
        bufferBuilder.pos(x + width - radius, y, 0).color(r1, g1, b1, a1).endVertex();
        bufferBuilder.pos(x + radius, y, 0).color(r1, g1, b1, a1).endVertex();
        tessellator.draw();
        
        // Corners with interpolation
        for (int i = 0; i < segments; i++) {
            float t = (float)i / segments;
            float ca = a1 + (a2 - a1) * t;
            float cr = r1 + (r2 - r1) * t;
            float cg = g1 + (g2 - g1) * t;
            float cb = b1 + (b2 - b1) * t;
            
            float angle1 = (float) Math.toRadians(90 + i * (90.0 / segments));
            float angle2 = (float) Math.toRadians(90 + (i + 1) * (90.0 / segments));
            
            // Top-left corner
            float x1 = x + radius + (float) Math.cos(angle1) * radius;
            float y1 = y + radius + (float) Math.sin(angle1) * radius;
            float x2 = x + radius + (float) Math.cos(angle2) * radius;
            float y2 = y + radius + (float) Math.sin(angle2) * radius;
            
            bufferBuilder.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(x + radius, y + radius, 0).color(r1, g1, b1, a1).endVertex();
            bufferBuilder.pos(x1, y1, 0).color(cr, cg, cb, ca).endVertex();
            bufferBuilder.pos(x2, y2, 0).color(cr, cg, cb, ca).endVertex();
            tessellator.draw();
            
            // Top-right corner
            angle1 = (float) Math.toRadians(0 + i * (90.0 / segments));
            angle2 = (float) Math.toRadians(0 + (i + 1) * (90.0 / segments));
            
            x1 = x + width - radius + (float) Math.cos(angle1) * radius;
            y1 = y + radius + (float) Math.sin(angle1) * radius;
            x2 = x + width - radius + (float) Math.cos(angle2) * radius;
            y2 = y + radius + (float) Math.sin(angle2) * radius;
            
            bufferBuilder.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(x + width - radius, y + radius, 0).color(r1, g1, b1, a1).endVertex();
            bufferBuilder.pos(x1, y1, 0).color(cr, cg, cb, ca).endVertex();
            bufferBuilder.pos(x2, y2, 0).color(cr, cg, cb, ca).endVertex();
            tessellator.draw();
            
            // Bottom-right corner
            angle1 = (float) Math.toRadians(270 + i * (90.0 / segments));
            angle2 = (float) Math.toRadians(270 + (i + 1) * (90.0 / segments));
            
            x1 = x + width - radius + (float) Math.cos(angle1) * radius;
            y1 = y + height - radius + (float) Math.sin(angle1) * radius;
            x2 = x + width - radius + (float) Math.cos(angle2) * radius;
            y2 = y + height - radius + (float) Math.sin(angle2) * radius;
            
            bufferBuilder.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(x + width - radius, y + height - radius, 0).color(r2, g2, b2, a2).endVertex();
            bufferBuilder.pos(x1, y1, 0).color(cr, cg, cb, ca).endVertex();
            bufferBuilder.pos(x2, y2, 0).color(cr, cg, cb, ca).endVertex();
            tessellator.draw();
            
            // Bottom-left corner
            angle1 = (float) Math.toRadians(180 + i * (90.0 / segments));
            angle2 = (float) Math.toRadians(180 + (i + 1) * (90.0 / segments));
            
            x1 = x + radius + (float) Math.cos(angle1) * radius;
            y1 = y + height - radius + (float) Math.sin(angle1) * radius;
            x2 = x + radius + (float) Math.cos(angle2) * radius;
            y2 = y + height - radius + (float) Math.sin(angle2) * radius;
            
            bufferBuilder.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(x + radius, y + height - radius, 0).color(r2, g2, b2, a2).endVertex();
            bufferBuilder.pos(x1, y1, 0).color(cr, cg, cb, ca).endVertex();
            bufferBuilder.pos(x2, y2, 0).color(cr, cg, cb, ca).endVertex();
            tessellator.draw();
        }
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    public static void drawString(String text, float x, float y, int color, boolean shadow) {
        FontRenderer fontRenderer = mc.fontRenderer;
        if (shadow) {
            fontRenderer.drawStringWithShadow(text, x, y, color);
        } else {
            fontRenderer.drawString(text, x, y, color);
        }
    }
    
    public static void drawStringCentered(String text, float x, float y, int color, boolean shadow) {
        FontRenderer fontRenderer = mc.fontRenderer;
        float width = fontRenderer.getStringWidth(text);
        if (shadow) {
            fontRenderer.drawStringWithShadow(text, x - width / 2, y, color);
        } else {
            fontRenderer.drawString(text, x - width / 2, y, color);
        }
    }
    
    public static int getStringWidth(String text) {
        return mc.fontRenderer.getStringWidth(text);
    }
    
    public static int getColorWithAlpha(int color, int alpha) {
        return (alpha << 24) | (color & 0xFFFFFF);
    }
    
    public static int interpolateColor(int color1, int color2, float progress) {
        int a1 = (color1 >> 24) & 0xFF;
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;
        
        int a2 = (color2 >> 24) & 0xFF;
        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;
        
        int a = (int)(a1 + (a2 - a1) * progress);
        int r = (int)(r1 + (r2 - r1) * progress);
        int g = (int)(g1 + (g2 - g1) * progress);
        int b = (int)(b1 + (b2 - b1) * progress);
        
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
    
    public static void drawOutline(float x, float y, float width, float height, float lineWidth, int color) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(lineWidth);
        
        float a = (color >> 24 & 0xFF) / 255.0F;
        float r = (color >> 16 & 0xFF) / 255.0F;
        float g = (color >> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        
        bufferBuilder.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(x, y, 0).color(r, g, b, a).endVertex();
        bufferBuilder.pos(x, y + height, 0).color(r, g, b, a).endVertex();
        bufferBuilder.pos(x + width, y + height, 0).color(r, g, b, a).endVertex();
        bufferBuilder.pos(x + width, y, 0).color(r, g, b, a).endVertex();
        tessellator.draw();
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
}

package com.lilo.visuals.client.gui.clickgui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single panel in the ClickGUI containing modules
 */
public class ModulePanel {
    private static final Minecraft mc = Minecraft.getInstance();
    
    public int x, y, width;
    private String title;
    private List<ModuleButton> modules;
    private boolean extended = true;
    private int headerHeight = 20;
    private int moduleHeight = 18;
    
    public ModulePanel(String title, int x, int y, int width) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.modules = new ArrayList<>();
    }
    
    public void addModule(String moduleName) {
        modules.add(new ModuleButton(moduleName, this));
    }
    
    public void render(MatrixStack matrixStack, int mouseX, int mouseY) {
        // Render panel background
        fill(matrixStack, x, y, x + width, y + getTotalHeight(), ClickGui.PANEL_COLOR);
        
        // Render header
        fill(matrixStack, x, y, x + width, y + headerHeight, ClickGui.HEADER_COLOR);
        
        // Draw header title with modern styling
        String displayTitle = "§l" + title + (extended ? " §r§7▼" : " §r§7▶");
        mc.font.draw(matrixStack, displayTitle, x + 5, y + 6, ClickGui.TEXT_COLOR);
        
        // Render modules if extended
        if (extended) {
            int currentY = y + headerHeight;
            for (ModuleButton module : modules) {
                module.render(matrixStack, x, currentY, width, mouseX, mouseY);
                currentY += moduleHeight;
            }
        }
        
        // Render border
        drawBorder(matrixStack, x, y, width, getTotalHeight());
    }
    
    private int getTotalHeight() {
        return headerHeight + (extended ? modules.size() * moduleHeight : 0);
    }
    
    public boolean isMouseOverHeader(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width &&
               mouseY >= y && mouseY <= y + headerHeight;
    }
    
    public void handleClick(int mouseX, int mouseY) {
        if (isMouseOverHeader(mouseX, mouseY)) {
            extended = !extended;
            return;
        }
        
        if (extended) {
            int currentY = y + headerHeight;
            for (ModuleButton module : modules) {
                if (mouseX >= x && mouseX <= x + width &&
                    mouseY >= currentY && mouseY <= currentY + moduleHeight) {
                    module.toggle();
                    return;
                }
                currentY += moduleHeight;
            }
        }
    }
    
    private void fill(MatrixStack matrixStack, int left, int top, int right, int bottom, int color) {
        net.minecraft.client.gui.AbstractGui.fill(matrixStack, left, top, right, bottom, color);
    }
    
    private void drawBorder(MatrixStack matrixStack, int x, int y, int width, int height) {
        // Top border
        fill(matrixStack, x, y, x + width, y + 1, ClickGui.ACCENT_COLOR);
        // Bottom border
        fill(matrixStack, x, y + height - 1, x + width, y + height, 0xFF1a1a2e);
        // Left border
        fill(matrixStack, x, y, x + 1, y + height, 0xFF1a1a2e);
        // Right border
        fill(matrixStack, x + width - 1, y, x + width, y + height, 0xFF1a1a2e);
    }
}

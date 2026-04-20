package com.lilo.visuals.client.gui.clickgui;

import com.lilo.visuals.LilosVisuals;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Modern ClickGUI for Lilos Visuals mod
 * Features a sleek, modern design with smooth animations
 */
public class ClickGui extends Screen {
    private static final Minecraft mc = Minecraft.getInstance();
    
    // Modern color scheme
    public static final int BACKGROUND_COLOR = 0x991a1a2e; // Dark blue with transparency
    public static final int PANEL_COLOR = 0xAA2d2d44;
    public static final int HEADER_COLOR = 0xFF3d3d5c;
    public static final int TEXT_COLOR = 0xFFFFFFFF;
    public static final int ACCENT_COLOR = 0xFF6c5ce7; // Purple accent
    
    private List<ModulePanel> panels;
    private int draggedPanelIndex = -1;
    private int dragOffsetX, dragOffsetY;
    
    public ClickGui() {
        super(new StringTextComponent("ClickGUI"));
        this.panels = new ArrayList<>();
        initializePanels();
    }
    
    private void initializePanels() {
        int x = 50;
        int y = 50;
        int panelWidth = 180;
        
        // Combat category
        ModulePanel combatPanel = new ModulePanel("Combat", x, y, panelWidth);
        combatPanel.addModule("KillAura");
        combatPanel.addModule("Velocity");
        combatPanel.addModule("Criticals");
        combatPanel.addModule("AutoArmor");
        panels.add(combatPanel);
        
        // Movement category
        x += panelWidth + 20;
        ModulePanel movementPanel = new ModulePanel("Movement", x, y, panelWidth);
        movementPanel.addModule("Fly");
        movementPanel.addModule("Speed");
        movementPanel.addModule("Jesus");
        movementPanel.addModule("NoFall");
        panels.add(movementPanel);
        
        // Render category
        x += panelWidth + 20;
        ModulePanel renderPanel = new ModulePanel("Render", x, y, panelWidth);
        renderPanel.addModule("ESP");
        renderPanel.addModule("Fullbright");
        renderPanel.addModule("Tracers");
        renderPanel.addModule("HUD");
        panels.add(renderPanel);
        
        // Player category
        x = 50;
        y += 200;
        ModulePanel playerPanel = new ModulePanel("Player", x, y, panelWidth);
        playerPanel.addModule("AutoEat");
        playerPanel.addModule("FastPlace");
        playerPanel.addModule("Scaffold");
        playerPanel.addModule("Timer");
        panels.add(playerPanel);
        
        // World category
        x += panelWidth + 20;
        ModulePanel worldPanel = new ModulePanel("World", x, y, panelWidth);
        worldPanel.addModule("Nuker");
        worldPanel.addModule("FastBreak");
        worldPanel.addModule("LiquidPlace");
        panels.add(worldPanel);
    }
    
    @Override
    protected void init() {
        super.init();
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // Render background
        fill(matrixStack, 0, 0, this.width, this.height, BACKGROUND_COLOR);
        
        // Draw title
        drawCenteredString(matrixStack, this.font, "§l§6Lilo's Visuals §r§7- ClickGUI", 
                          this.width / 2, 20, ACCENT_COLOR);
        
        // Render all panels
        for (ModulePanel panel : panels) {
            panel.render(matrixStack, mouseX, mouseY);
        }
        
        // Render instructions
        String instructions = "§7Right-click header to drag | Click module to toggle | Scroll to change values";
        drawCenteredString(matrixStack, this.font, instructions, 
                          this.width / 2, this.height - 30, 0x888888);
        
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // Check if any panel header was clicked
        for (int i = 0; i < panels.size(); i++) {
            ModulePanel panel = panels.get(i);
            if (panel.isMouseOverHeader((int)mouseX, (int)mouseY)) {
                if (button == 1) { // Right click to drag
                    draggedPanelIndex = i;
                    dragOffsetX = (int)mouseX - panel.x;
                    dragOffsetY = (int)mouseY - panel.y;
                    return true;
                } else if (button == 0) { // Left click on modules
                    panel.handleClick((int)mouseX, (int)mouseY);
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 1 && draggedPanelIndex != -1) {
            draggedPanelIndex = -1;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (draggedPanelIndex != -1 && button == 1) {
            ModulePanel panel = panels.get(draggedPanelIndex);
            panel.x = (int)mouseX - dragOffsetX;
            panel.y = (int)mouseY - dragOffsetY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }
    
    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
    
    @Override
    public void onClose() {
        LilosVisuals.setClickGuiOpen(false);
        super.onClose();
    }
}

package com.lilos.visuals.gui;

import com.lilos.visuals.lib.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class ClickGui extends Screen {
    private static final Minecraft mc = Minecraft.getInstance();
    
    private List<ModuleButton> moduleButtons;
    private int scrollOffset = 0;
    private boolean isDragging = false;
    private int dragStartY = 0;
    
    private static final int BACKGROUND_COLOR = 0x991a1a1a;
    private static final int BUTTON_COLOR = 0x882d2d2d;
    private static final int BUTTON_HOVER_COLOR = 0x993d3d3d;
    private static final int ENABLED_COLOR = 0xFF4CAF50;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    
    public ClickGui() {
        super(new StringTextComponent("ClickGUI"));
        this.moduleButtons = new ArrayList<>();
        initModules();
    }
    
    private void initModules() {
        moduleButtons.add(new ModuleButton("ESP", 20, 60, true));
        moduleButtons.add(new ModuleButton("Fullbright", 20, 100, false));
        moduleButtons.add(new ModuleButton("Tracers", 20, 140, false));
        moduleButtons.add(new ModuleButton("ArmorHUD", 20, 180, true));
        moduleButtons.add(new ModuleButton("Coordinates", 20, 220, true));
        moduleButtons.add(new ModuleButton("FPS", 20, 260, true));
        moduleButtons.add(new ModuleButton("Ping", 20, 300, false));
        moduleButtons.add(new ModuleButton("Speedometer", 20, 340, false));
    }
    
    @Override
    protected void init() {
        super.init();
    }
    
    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground();
        
        // Draw main panel
        RenderUtil.drawRoundedRect(10, 10, 200, 380, 8, BACKGROUND_COLOR);
        RenderUtil.drawString("Lilos Visuals", 25, 20, TEXT_COLOR, true);
        RenderUtil.drawString("Press RSHIFT to close", 25, 35, 0xFFAAAAAA, false);
        
        // Draw module buttons
        for (ModuleButton button : moduleButtons) {
            button.render(mouseX, mouseY);
        }
        
        super.render(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (ModuleButton mb : moduleButtons) {
            if (mb.isMouseOver((int)mouseX, (int)mouseY)) {
                if (button == 0) { // Left click
                    mb.toggle();
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        isDragging = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 54) { // Right Shift
            mc.displayGuiScreen(null);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public boolean shouldPause() {
        return false;
    }
    
    public static class ModuleButton {
        private String name;
        private int x, y;
        private int width = 170;
        private int height = 30;
        private boolean enabled;
        
        public ModuleButton(String name, int x, int y, boolean enabled) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.enabled = enabled;
        }
        
        public void render(int mouseX, int mouseY) {
            int color = isMouseOver(mouseX, mouseY) ? BUTTON_HOVER_COLOR : BUTTON_COLOR;
            
            RenderUtil.drawRoundedRect(x, y, width, height, 4, color);
            
            // Draw status indicator
            if (enabled) {
                RenderUtil.drawRoundedRect(x + width - 25, y + 5, 20, 20, 3, ENABLED_COLOR);
            } else {
                RenderUtil.drawRoundedRect(x + width - 25, y + 5, 20, 20, 3, 0xFF555555);
            }
            
            RenderUtil.drawString(name, x + 10, y + 11, TEXT_COLOR, false);
        }
        
        public boolean isMouseOver(int mouseX, int mouseY) {
            return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        }
        
        public void toggle() {
            enabled = !enabled;
        }
        
        public boolean isEnabled() {
            return enabled;
        }
        
        public String getName() {
            return name;
        }
    }
}

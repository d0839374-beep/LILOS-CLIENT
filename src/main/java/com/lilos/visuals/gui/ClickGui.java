package com.lilos.visuals.gui;

import com.lilos.visuals.lib.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClickGui extends Screen {
    private static final Minecraft mc = Minecraft.getInstance();
    
    private List<Category> categories;
    private Category selectedCategory;
    private int scrollOffset = 0;
    private boolean isDragging = false;
    private int dragStartX, dragStartY;
    private int guiX = 50, guiY = 50;
    private int panelWidth = 280, panelHeight = 350;
    private int categoryButtonHeight = 25;
    
    // Ultra modern colors
    private static final int BACKGROUND_GRADIENT_TOP = 0xFF1a1a2e;
    private static final int BACKGROUND_GRADIENT_BOTTOM = 0xFF0f0f1a;
    private static final int PANEL_COLOR = 0xAA2d2d44;
    private static final int PANEL_HOVER = 0xBB3d3d5c;
    private static final int CATEGORY_SELECTED = 0xFF6C63FF;
    private static final int CATEGORY_NORMAL = 0x884a4a6a;
    private static final int MODULE_ENABLED = 0xFF4CAF50;
    private static final int MODULE_DISABLED = 0xFF555555;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    private static final int TEXT_GRAY = 0xFFAAAAAA;
    private static final int ACCENT_COLOR = 0xFF6C63FF;
    
    public ClickGui() {
        super(new StringTextComponent("ClickGUI"));
        this.categories = new ArrayList<>();
        initCategories();
        this.selectedCategory = categories.get(0);
    }
    
    private void initCategories() {
        // Combat Category
        Category combat = new Category("Combat", 0);
        combat.addModule(new Module("KillAura", "Automatically attacks entities", true));
        combat.addModule(new Module("AutoCrystal", "Places and activates crystals", false));
        combat.addModule(new Module("Criticals", "Always deals critical hits", false));
        combat.addModule(new Module("Velocity", "Reduces knockback", true));
        categories.add(combat);
        
        // Visual Category
        Category visual = new Category("Visual", 1);
        visual.addModule(new Module("ESP", "Renders entities through walls", true));
        visual.addModule(new Module("Fullbright", "Maximum brightness", false));
        visual.addModule(new Module("Tracers", "Lines to entities", false));
        visual.addModule(new Module("Chams", "Renders players with custom texture", false));
        visual.addModule(new Module("Nametags", "Enhanced name tags", true));
        categories.add(visual);
        
        // Movement Category
        Category movement = new Category("Movement", 2);
        movement.addModule(new Module("Speed", "Increases movement speed", false));
        movement.addModule(new Module("Fly", "Allows flying", false));
        movement.addModule(new Module("Jesus", "Walk on water", false));
        movement.addModule(new Module("NoFall", "Prevents fall damage", true));
        categories.add(movement);
        
        // Render Category
        Category render = new Category("Render", 3);
        render.addModule(new Module("HUD", "Custom HUD elements", true));
        render.addModule(new Module("Animations", "Item use animations", true));
        render.addModule(new Module("Particles", "Custom particles", false));
        render.addModule(new Module("Trajectories", "Show projectile path", true));
        categories.add(render);
        
        // Player Category
        Category player = new Category("Player", 4);
        player.addModule(new Module("AutoEat", "Automatically eats food", true));
        player.addModule(new Module("FastPlace", "Faster block placement", false));
        player.addModule(new Module("Scaffold", "Auto places blocks", false));
        player.addModule(new Module("Timer", "Game speed modifier", false));
        categories.add(player);
    }
    
    @Override
    protected void init() {
        super.init();
        guiX = (width - panelWidth * 2) / 2;
        guiY = (height - panelHeight) / 2;
    }
    
    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        // Background gradient
        RenderUtil.drawGradientRect(0, 0, width, height, BACKGROUND_GRADIENT_TOP, BACKGROUND_GRADIENT_BOTTOM);
        
        // Main panel
        RenderUtil.drawRoundedGradientRect(guiX, guiY, panelWidth, panelHeight, 12, PANEL_COLOR, 0xAA1a1a2e);
        
        // Header
        RenderUtil.drawRoundedRect(guiX + 10, guiY + 10, panelWidth - 20, 40, 8, 0x883d3d5c);
        RenderUtil.drawStringCentered("Lilo's Visuals", guiX + panelWidth / 2, guiY + 20, TEXT_COLOR, true);
        RenderUtil.drawStringCentered("ULTRA EDITION", guiX + panelWidth / 2, guiY + 35, ACCENT_COLOR, false);
        
        // Category buttons
        int catY = guiY + 60;
        for (Category cat : categories) {
            int catColor = (selectedCategory == cat) ? CATEGORY_SELECTED : CATEGORY_NORMAL;
            if (isMouseOver(mouseX, mouseY, guiX + 10, catY, panelWidth - 20, categoryButtonHeight)) {
                catColor = RenderUtil.interpolateColor(catColor, PANEL_HOVER, 0.5f);
            }
            
            RenderUtil.drawRoundedRect(guiX + 10, catY, panelWidth - 20, categoryButtonHeight, 6, catColor);
            RenderUtil.drawString(cat.name, guiX + 20, catY + 8, TEXT_COLOR, false);
            
            // Small indicator
            if (selectedCategory == cat) {
                RenderUtil.drawRect(guiX + panelWidth - 25, catY + 8, 4, 10, ACCENT_COLOR);
            }
            
            catY += categoryButtonHeight + 5;
        }
        
        // Modules panel
        int modulesPanelX = guiX + panelWidth + 20;
        int modulesPanelY = guiY;
        int modulesPanelWidth = 260;
        
        RenderUtil.drawRoundedGradientRect(modulesPanelX, modulesPanelY, modulesPanelWidth, panelHeight, 12, PANEL_COLOR, 0xAA1a1a2e);
        
        // Selected category title
        RenderUtil.drawStringCentered(selectedCategory.name + " Modules", modulesPanelX + modulesPanelWidth / 2, modulesPanelY + 15, ACCENT_COLOR, true);
        RenderUtil.drawRect(modulesPanelX + 20, modulesPanelY + 30, modulesPanelWidth - 40, 1, ACCENT_COLOR);
        
        // Modules list
        int moduleY = modulesPanelY + 45;
        for (Module module : selectedCategory.modules) {
            int moduleHeight = 40;
            
            // Module background
            int moduleColor = module.enabled ? 0x882d4a2d : 0x882d2d2d;
            if (isMouseOver(mouseX, mouseY, modulesPanelX + 10, moduleY, modulesPanelWidth - 20, moduleHeight)) {
                moduleColor = RenderUtil.interpolateColor(moduleColor, 0xAA3d3d5c, 0.6f);
            }
            
            RenderUtil.drawRoundedRect(modulesPanelX + 10, moduleY, modulesPanelWidth - 20, moduleHeight, 6, moduleColor);
            
            // Module name
            RenderUtil.drawString(module.name, modulesPanelX + 20, moduleY + 8, TEXT_COLOR, true);
            
            // Module description
            RenderUtil.drawString(module.description, modulesPanelX + 20, moduleY + 24, TEXT_GRAY, false);
            
            // Toggle switch
            int switchX = modulesPanelX + modulesPanelWidth - 50;
            int switchY = moduleY + 10;
            int switchWidth = 35;
            int switchHeight = 18;
            
            RenderUtil.drawRoundedRect(switchX, switchY, switchWidth, switchHeight, 9, 
                module.enabled ? MODULE_ENABLED : MODULE_DISABLED);
            
            // Switch circle
            int circleX = module.enabled ? switchX + switchWidth - 11 : switchX + 2;
            RenderUtil.drawRoundedRect(circleX, switchY + 2, 14, 14, 7, TEXT_COLOR);
            
            moduleY += moduleHeight + 5;
        }
        
        // Footer info
        RenderUtil.drawString("Press RSHIFT to close", guiX + 10, guiY + panelHeight - 20, TEXT_GRAY, false);
        RenderUtil.drawString("Scroll to navigate", modulesPanelX + 10, modulesPanelY + panelHeight - 20, TEXT_GRAY, false);
        
        super.render(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // Category selection
        int catY = guiY + 60;
        for (Category cat : categories) {
            if (isMouseOver((int)mouseX, (int)mouseY, guiX + 10, catY, panelWidth - 20, categoryButtonHeight)) {
                selectedCategory = cat;
                return true;
            }
            catY += categoryButtonHeight + 5;
        }
        
        // Module toggling
        int modulesPanelX = guiX + panelWidth + 20;
        int modulesPanelY = guiY;
        int moduleY = modulesPanelY + 45;
        
        for (Module module : selectedCategory.modules) {
            int moduleHeight = 40;
            if (isMouseOver((int)mouseX, (int)mouseY, modulesPanelX + 10, moduleY, modulesPanelX + 260 - 20, moduleHeight)) {
                if (button == 0) {
                    module.enabled = !module.enabled;
                }
                return true;
            }
            moduleY += moduleHeight + 5;
        }
        
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT) {
            mc.displayGuiScreen(null);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public boolean shouldPause() {
        return false;
    }
    
    private boolean isMouseOver(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
    
    // Category class
    public static class Category {
        String name;
        int id;
        List<Module> modules;
        
        public Category(String name, int id) {
            this.name = name;
            this.id = id;
            this.modules = new ArrayList<>();
        }
        
        public void addModule(Module module) {
            modules.add(module);
        }
    }
    
    // Module class
    public static class Module {
        String name;
        String description;
        boolean enabled;
        
        public Module(String name, String description, boolean enabled) {
            this.name = name;
            this.description = description;
            this.enabled = enabled;
        }
    }
}

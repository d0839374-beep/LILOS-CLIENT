package com.lilo.visuals.client.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.gui.ChatScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Modern custom main menu screen with sleek design
 */
public class CustomMainMenu extends Screen {
    private static final Minecraft mc = Minecraft.getInstance();
    
    // Modern color scheme
    private static final int BACKGROUND_COLOR = 0xFF1a1a2e;
    private static final int BUTTON_COLOR = 0xAA3d3d5c;
    private static final int BUTTON_HOVER = 0xAA6c5ce7;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    private static final int ACCENT_COLOR = 0xFF6c5ce7;
    
    public CustomMainMenu() {
        super(new StringTextComponent("Main Menu"));
    }
    
    @Override
    protected void init() {
        super.init();
        
        int buttonWidth = 200;
        int buttonHeight = 20;
        int centerX = this.width / 2;
        int startY = this.height / 4 + 96;
        
        // Singleplayer button
        this.addButton(new ModernButton(
            centerX - buttonWidth / 2, startY,
            buttonWidth, buttonHeight,
            new StringTextComponent("Singleplayer"),
            btn -> mc.displayGuiScreen(new net.minecraft.client.gui.screen.MultiplayerScreen(this))
        ));
        
        // Multiplayer button
        this.addButton(new ModernButton(
            centerX - buttonWidth / 2, startY + 24,
            buttonWidth, buttonHeight,
            new StringTextComponent("Multiplayer"),
            btn -> mc.displayGuiScreen(new net.minecraft.client.gui.screen.MultiplayerScreen(this))
        ));
        
        // Mods button
        this.addButton(new ModernButton(
            centerX - buttonWidth / 2, startY + 48,
            buttonWidth, buttonHeight,
            new StringTextComponent("Mods"),
            btn -> mc.displayGuiScreen(new net.minecraftforge.fml.client.gui.modlist.ModListScreen(this))
        ));
        
        // Options button
        this.addButton(new ModernButton(
            centerX - buttonWidth / 2, startY + 72,
            buttonWidth, buttonHeight,
            new StringTextComponent("Options"),
            btn -> mc.displayGuiScreen(new net.minecraft.client.gui.screen.OptionsScreen(this, mc.gameSettings))
        ));
        
        // Quit button
        this.addButton(new ModernButton(
            centerX - buttonWidth / 2, startY + 120,
            buttonWidth, buttonHeight,
            new StringTextComponent("Quit Game"),
            btn -> mc.shutdown()
        ));
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // Render gradient background
        this.renderBackground(matrixStack);
        
        // Draw title
        String title = "§l§6Lilo's Visuals";
        String subtitle = "§7Modern Minecraft Experience";
        
        int titleY = this.height / 4 - 60;
        drawCenteredString(matrixStack, this.font, title, this.width / 2, titleY, ACCENT_COLOR);
        drawCenteredString(matrixStack, this.font, subtitle, this.width / 2, titleY + 15, 0x888888);
        
        // Render version
        String version = "§8v1.0.0 | §7Forge 1.16.5";
        drawString(matrixStack, this.font, version, 5, this.height - 15, 0x666666);
        
        // Render copyright
        String copyright = "§8© 2024 Lilo's Visuals";
        drawString(matrixStack, this.font, copyright, this.width - this.font.getStringWidth(copyright) - 5, 
                  this.height - 15, 0x666666);
        
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
    
    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
    
    // Modern button class with smooth hover effects
    public static class ModernButton extends Button {
        public ModernButton(int x, int y, int width, int height, IPressable onPress) {
            super(x, y, width, height, new StringTextComponent(""), onPress);
        }
        
        public ModernButton(int x, int y, int width, int height, StringTextComponent text, IPressable onPress) {
            super(x, y, width, height, text, onPress);
        }
        
        @Override
        public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            Minecraft mc = Minecraft.getInstance();
            
            // Determine colors
            int bgColor = this.isHovered() ? BUTTON_HOVER : BUTTON_COLOR;
            int textColor = this.active ? TEXT_COLOR : 0x888888;
            
            // Draw button background with rounded corners effect
            fill(matrixStack, this.x, this.y, this.x + this.width, this.y + this.height, bgColor);
            
            // Draw accent line on top
            if (this.isHovered()) {
                fill(matrixStack, this.x, this.y, this.x + this.width, this.y + 2, ACCENT_COLOR);
            }
            
            // Draw button text
            drawCenteredString(matrixStack, mc.font, this.getMessage().getString(), 
                             this.x + this.width / 2, this.y + (this.height - 8) / 2, textColor);
        }
    }
}

package com.lilo.visuals.client.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.PauseScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

/**
 * Modern custom pause menu screen with sleek design
 */
public class CustomPauseMenu extends PauseScreen {
    private static final Minecraft mc = Minecraft.getInstance();
    
    // Modern color scheme
    private static final int BUTTON_COLOR = 0xAA3d3d5c;
    private static final int BUTTON_HOVER = 0xAA6c5ce7;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    private static final int ACCENT_COLOR = 0xFF6c5ce7;
    
    public CustomPauseMenu() {
        super(new StringTextComponent("Game Menu"));
    }
    
    @Override
    protected void init() {
        super.init();
        
        int buttonWidth = 200;
        int buttonHeight = 20;
        int centerX = this.width / 2;
        int startY = this.height / 4 + 72;
        
        // Clear existing buttons
        this.buttons.clear();
        
        // Back to Game button
        this.addButton(new ModernButton(
            centerX - buttonWidth / 2, startY,
            buttonWidth, buttonHeight,
            new StringTextComponent("Back to Game"),
            btn -> this.mc.displayGuiScreen(null)
        ));
        
        // Advancements button
        this.addButton(new ModernButton(
            centerX - buttonWidth / 2, startY + 24,
            buttonWidth, buttonHeight,
            new StringTextComponent("Advancements"),
            btn -> this.mc.displayGuiScreen(new net.minecraft.client.gui.screen.AdvancementsScreen(
                this.mc.player.connection.getPlayerAdvancementManager()))
        ));
        
        // Statistics button
        this.addButton(new ModernButton(
            centerX - buttonWidth / 2, startY + 48,
            buttonWidth, buttonHeight,
            new StringTextComponent("Statistics"),
            btn -> this.mc.displayGuiScreen(new net.minecraft.client.gui.screen.StatsScreen(this))
        ));
        
        // Options button
        this.addButton(new ModernButton(
            centerX - buttonWidth / 2, startY + 72,
            buttonWidth, buttonHeight,
            new StringTextComponent("Options"),
            btn -> this.mc.displayGuiScreen(new net.minecraft.client.gui.screen.OptionsScreen(
                this, this.mc.gameSettings))
        ));
        
        // Report Bugs button
        this.addButton(new ModernButton(
            centerX - buttonWidth / 2, startY + 96,
            buttonWidth, buttonHeight,
            new StringTextComponent("Report Bugs"),
            btn -> {
                // Open bug report URL (placeholder)
                System.out.println("Opening bug report...");
            }
        ));
        
        // Disconnect/Save and Quit button
        StringTextComponent disconnectText;
        if (this.mc.isIntegratedServerRunning()) {
            disconnectText = new StringTextComponent("Save and Quit to Title");
        } else {
            disconnectText = new StringTextComponent("Disconnect");
        }
        
        this.addButton(new ModernButton(
            centerX - buttonWidth / 2, startY + 120,
            buttonWidth, buttonHeight,
            disconnectText,
            btn -> this.mc.world.sendQuittingDisconnectingPacket()
        ));
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // Render background
        this.renderBackground(matrixStack);
        
        // Draw title
        String title = "§l§6Game Menu";
        drawCenteredString(matrixStack, this.font, title, this.width / 2, this.height / 4 - 20, ACCENT_COLOR);
        
        // Draw subtitle
        String subtitle = "§7Lilo's Visuals Custom Menu";
        drawCenteredString(matrixStack, this.font, subtitle, this.width / 2, this.height / 4 - 5, 0x888888);
        
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
    
    // Modern button class with smooth hover effects
    public static class ModernButton extends Button {
        public ModernButton(int x, int y, int width, int height, StringTextComponent text, IPressable onPress) {
            super(x, y, width, height, text, onPress);
        }
        
        @Override
        public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            Minecraft mc = Minecraft.getInstance();
            
            // Determine colors
            int bgColor = this.isHovered() ? BUTTON_HOVER : BUTTON_COLOR;
            int textColor = this.active ? TEXT_COLOR : 0x888888;
            
            // Draw button background
            fill(matrixStack, this.x, this.y, this.x + this.width, this.y + this.height, bgColor);
            
            // Draw accent line on top if hovered
            if (this.isHovered()) {
                fill(matrixStack, this.x, this.y, this.x + this.width, this.y + 2, ACCENT_COLOR);
            }
            
            // Draw button text
            drawCenteredString(matrixStack, mc.font, this.getMessage().getString(), 
                             this.x + this.width / 2, this.y + (this.height - 8) / 2, textColor);
        }
    }
}

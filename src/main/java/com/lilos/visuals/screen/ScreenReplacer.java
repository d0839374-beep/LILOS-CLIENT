package com.lilos.visuals.screen;

import com.lilos.visuals.gui.ClickGui;
import com.lilos.visuals.lib.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.MainMenuScreen;
import net.minecraft.client.gui.screen.PauseScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ScreenReplacer {
    private static final Minecraft mc = Minecraft.getInstance();
    
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (!com.lilos.visuals.ConfigSpec.ENABLE_CUSTOM_MENU.get()) return;
        
        if (event.getGui() instanceof MainMenuScreen) {
            event.setGui(new CustomMainMenu());
        } else if (event.getGui() instanceof PauseScreen) {
            event.setGui(new CustomPauseMenu());
        }
    }
    
    public static class CustomMainMenu extends Screen {
        private static final int BACKGROUND_COLOR = 0xFF1a1a2e;
        private static final int BUTTON_COLOR = 0x883d3d5c;
        private static final int BUTTON_HOVER_COLOR = 0xAA4d4d6c;
        private static final int TEXT_COLOR = 0xFFFFFFFF;
        
        public CustomMainMenu() {
            super(new StringTextComponent("Main Menu"));
        }
        
        @Override
        protected void init() {
            super.init();
            
            int buttonWidth = 200;
            int buttonHeight = 20;
            int centerX = width / 2;
            int startY = height / 4 + 96;
            
            this.addButton(new Button(centerX - buttonWidth / 2, startY, buttonWidth, buttonHeight, 
                    new StringTextComponent("Singleplayer"), b -> {
                mc.displayGuiScreen(null);
            }));
            
            this.addButton(new Button(centerX - buttonWidth / 2, startY + 24, buttonWidth, buttonHeight, 
                    new StringTextComponent("Multiplayer"), b -> {
                mc.displayGuiScreen(null);
            }));
            
            this.addButton(new Button(centerX - buttonWidth / 2, startY + 48, buttonWidth, buttonHeight, 
                    new StringTextComponent("Mods"), b -> {
                mc.displayGuiScreen(new net.minecraft.client.gui.screen.ModsScreen(this));
            }));
            
            this.addButton(new Button(centerX - buttonWidth / 2, startY + 72, buttonWidth, buttonHeight, 
                    new StringTextComponent("Options"), b -> {
                mc.displayGuiScreen(new net.minecraft.client.gui.screen.OptionsScreen(this, mc.gameSettings));
            }));
            
            this.addButton(new Button(centerX - buttonWidth / 2, startY + 120, buttonWidth / 2 - 2, buttonHeight, 
                    new StringTextComponent("Quit"), b -> {
                mc.shutdown();
            }));
            
            this.addButton(new Button(centerX + 2, startY + 120, buttonWidth / 2 - 2, buttonHeight, 
                    new StringTextComponent("ClickGUI"), b -> {
                mc.displayGuiScreen(new ClickGui());
            }));
        }
        
        @Override
        public void render(int mouseX, int mouseY, float partialTicks) {
            RenderUtil.drawGradientRect(0, 0, width, height, BACKGROUND_COLOR, 0xFF0f0f1a);
            RenderUtil.drawString("Lilos Visuals", width / 2 - 50, height / 4 - 20, TEXT_COLOR, true);
            RenderUtil.drawString("Modern Client for 1.16.5", width / 2 - 40, height / 4, 0xFFAAAAAA, false);
            RenderUtil.drawRoundedRect(0, height - 30, width, 30, 0, 0x882d2d4a);
            RenderUtil.drawString("Press RSHIFT for ClickGUI", 10, height - 22, 0xFF888888, false);
            super.render(mouseX, mouseY, partialTicks);
        }
        
        @Override
        public boolean shouldPause() {
            return false;
        }
    }
    
    public static class CustomPauseMenu extends Screen {
        private static final int BACKGROUND_COLOR = 0xCC1a1a2e;
        private static final int TEXT_COLOR = 0xFFFFFFFF;
        
        public CustomPauseMenu() {
            super(new StringTextComponent("Game Menu"));
        }
        
        @Override
        protected void init() {
            super.init();
            
            int buttonWidth = 200;
            int buttonHeight = 20;
            int centerX = width / 2;
            int startY = height / 4 + 72;
            
            this.addButton(new Button(centerX - buttonWidth / 2, startY, buttonWidth, buttonHeight, 
                    new StringTextComponent("Back to Game"), b -> {
                mc.displayGuiScreen(null);
                mc.mouseHelper.grabMouse();
            }));
            
            this.addButton(new Button(centerX - buttonWidth / 2, startY + 24, buttonWidth, buttonHeight, 
                    new StringTextComponent("Options"), b -> {
                mc.displayGuiScreen(new net.minecraft.client.gui.screen.OptionsScreen(this, mc.gameSettings));
            }));
            
            this.addButton(new Button(centerX - buttonWidth / 2, startY + 48, buttonWidth, buttonHeight, 
                    new StringTextComponent("ClickGUI"), b -> {
                mc.displayGuiScreen(new ClickGui());
            }));
            
            this.addButton(new Button(centerX - buttonWidth / 2, startY + 72, buttonWidth, buttonHeight, 
                    new StringTextComponent("Disconnect"), b -> {
                if (mc.world != null) {
                    mc.world.sendQuittingDisconnectingPacket();
                }
                mc.displayGuiScreen(new CustomMainMenu());
                mc.mouseHelper.grabMouse();
            }));
        }
        
        @Override
        public void render(int mouseX, int mouseY, float partialTicks) {
            RenderUtil.drawRect(0, 0, width, height, BACKGROUND_COLOR);
            RenderUtil.drawRoundedRect(width / 2 - 110, height / 4 - 20, 220, 140, 8, 0xAA2d2d4a);
            RenderUtil.drawString("Game Paused", width / 2 - 35, height / 4 - 10, TEXT_COLOR, true);
            super.render(mouseX, mouseY, partialTicks);
        }
        
        @Override
        public boolean shouldPause() {
            return true;
        }
    }
}

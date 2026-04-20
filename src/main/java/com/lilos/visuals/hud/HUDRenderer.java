package com.lilos.visuals.hud;

import com.lilos.visuals.lib.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;

public class HUDRenderer {
    private static final Minecraft mc = Minecraft.getInstance();
    
    private static final int BACKGROUND_COLOR = 0x881a1a1a;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    private static final int ACCENT_COLOR = 0xFF4CAF50;
    
    public static void renderHUD() {
        if (mc.player == null || mc.world == null) return;
        
        int yOffset = 10;
        int xOffset = 10;
        
        // Render FPS
        renderModule("FPS: " + Minecraft.getDebugFPS(), xOffset, yOffset);
        yOffset += 25;
        
        // Render Coordinates
        int x = MathHelper.floor(mc.player.posX);
        int y = MathHelper.floor(mc.player.posY);
        int z = MathHelper.floor(mc.player.posZ);
        String coordStr = String.format("XYZ: %d %d %d", x, y, z);
        renderModule(coordStr, xOffset, yOffset);
        yOffset += 25;
        
        // Render Ping
        int ping = mc.getConnection().getPlayerPackets().getPing();
        renderModule("Ping: " + ping + "ms", xOffset, yOffset);
        yOffset += 25;
        
        // Render Speed
        double speed = Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ) * 20;
        renderModule(String.format("Speed: %.2f m/s", speed), xOffset, yOffset);
        yOffset += 25;
        
        // Render Armor Status
        if (mc.player.inventory.armorInventory.get(3).isEmpty() == false) {
            String armorStr = "Armor: " + getArmorPercent() + "%";
            renderModule(armorStr, xOffset, yOffset);
        }
        
        // Render module list (simulated)
        renderModuleList(xOffset + 250, 10);
    }
    
    private static void renderModule(String text, int x, int y) {
        int width = RenderUtil.getStringWidth(text) + 16;
        int height = 20;
        
        RenderUtil.drawRoundedRect(x - 8, y - 4, width, height, 6, BACKGROUND_COLOR);
        RenderUtil.drawString(text, x, y, TEXT_COLOR, true);
        
        // Accent line
        RenderUtil.drawRect(x - 8, y - 4, 3, height, ACCENT_COLOR);
    }
    
    private static void renderModuleList(int x, int y) {
        String[] modules = {"ESP", "Fullbright", "Tracers"};
        int yOffset = y;
        
        for (String module : modules) {
            int width = RenderUtil.getStringWidth(module) + 16;
            RenderUtil.drawRoundedRect(x, yOffset, width, 18, 4, BACKGROUND_COLOR);
            RenderUtil.drawString(module, x + 8, yOffset + 5, TEXT_COLOR, true);
            
            // Small indicator
            RenderUtil.drawRect(x + width - 6, yOffset + 7, 4, 4, ACCENT_COLOR);
            
            yOffset += 22;
        }
    }
    
    private static int getArmorPercent() {
        if (mc.player == null) return 0;
        
        int totalDurability = 0;
        int currentDurability = 0;
        
        for (int i = 0; i < 4; i++) {
            var stack = mc.player.inventory.armorInventory.get(i);
            if (!stack.isEmpty()) {
                totalDurability += stack.getMaxDamage();
                currentDurability += stack.getMaxDamage() - stack.getDamage();
            }
        }
        
        if (totalDurability == 0) return 100;
        return (currentDurability * 100) / totalDurability;
    }
}

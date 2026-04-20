package com.lilos.visuals;

import com.lilos.visuals.gui.ClickGui;
import com.lilos.visuals.hud.HUDRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = LilosVisuals.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    
    private static boolean keyReleased = true;
    
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            var mc = net.minecraft.client.Minecraft.getInstance();
            if (mc.player != null && mc.currentScreen == null) {
                long handle = mc.getMainWindow().getHandle();
                int keyState = GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_RIGHT_SHIFT);
                
                if (keyState == GLFW.GLFW_PRESS && keyReleased) {
                    mc.displayGuiScreen(new ClickGui());
                    keyReleased = false;
                } else if (keyState == GLFW.GLFW_RELEASE) {
                    keyReleased = true;
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            if (ConfigSpec.ENABLE_HUD.get()) {
                HUDRenderer.renderHUD();
            }
        }
    }
}

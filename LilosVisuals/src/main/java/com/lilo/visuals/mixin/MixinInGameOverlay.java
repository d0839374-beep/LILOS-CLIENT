package com.lilo.visuals.mixin;

import com.lilo.visuals.LilosVisuals;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to render HUD overlay on top of the in-game GUI
 */
@Mixin(IngameGui.class)
public class MixinInGameOverlay {
    
    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(MatrixStack matrixStack, float partialTicks, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        
        // Don't render HUD when a screen is open (except for ClickGUI)
        if (mc.currentScreen != null && !(mc.currentScreen instanceof com.lilo.visuals.client.gui.clickgui.ClickGui)) {
            return;
        }
        
        // Render our custom HUD
        if (LilosVisuals.getHudRenderer() != null) {
            LilosVisuals.getHudRenderer().render(matrixStack);
        }
    }
}

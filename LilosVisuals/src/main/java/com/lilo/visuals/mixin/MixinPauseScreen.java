package com.lilo.visuals.mixin;

import com.lilo.visuals.client.gui.screens.CustomPauseMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.PauseScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to replace the vanilla pause menu with our custom one
 */
@Mixin(PauseScreen.class)
public class MixinPauseScreen {
    
    @Inject(method = "<init>", at = @At("RETURN"), cancellable = true)
    private void onInit(CallbackInfo ci) {
        // Redirect to our custom pause menu
        Minecraft mc = Minecraft.getInstance();
        if (mc.currentScreen instanceof PauseScreen && !(mc.currentScreen instanceof CustomPauseMenu)) {
            mc.displayGuiScreen(new CustomPauseMenu());
        }
    }
}

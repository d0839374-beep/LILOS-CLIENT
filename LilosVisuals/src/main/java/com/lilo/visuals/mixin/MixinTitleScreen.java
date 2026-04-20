package com.lilo.visuals.mixin;

import com.lilo.visuals.client.gui.screens.CustomMainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to replace the vanilla main menu with our custom one
 */
@Mixin(MainMenuScreen.class)
public class MixinTitleScreen {
    
    @Inject(method = "<init>", at = @At("RETURN"), cancellable = true)
    private void onInit(CallbackInfo ci) {
        // This will be called when the title screen is created
        // We redirect to our custom main menu instead
        Minecraft mc = Minecraft.getInstance();
        if (mc.currentScreen instanceof MainMenuScreen) {
            mc.displayGuiScreen(new CustomMainMenu());
        }
    }
}

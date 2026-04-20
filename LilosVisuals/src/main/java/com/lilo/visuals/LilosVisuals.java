package com.lilo.visuals;

import com.lilo.visuals.client.gui.clickgui.ClickGui;
import com.lilo.visuals.client.gui.hud.HudRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("lilosvisuals")
public class LilosVisuals {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "lilosvisuals";
    
    private static ClickGui clickGui;
    private static HudRenderer hudRenderer;
    private static boolean isClickGuiOpen = false;

    public LilosVisuals() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("Lilo's Visuals initialized!");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        clickGui = new ClickGui();
        hudRenderer = new HudRenderer();
        LOGGER.info("Client setup complete!");
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.type == TickEvent.Type.CLIENT) {
            // Key binding will be handled properly in the ClickGui class
        }
    }

    public static ClickGui getClickGui() {
        return clickGui;
    }

    public static HudRenderer getHudRenderer() {
        return hudRenderer;
    }

    public static boolean isClickGuiOpen() {
        return isClickGuiOpen;
    }

    public static void setClickGuiOpen(boolean open) {
        isClickGuiOpen = open;
    }
}

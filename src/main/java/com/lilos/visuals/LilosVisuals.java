package com.lilos.visuals;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("lilosvisuals")
public class LilosVisuals {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "lilosvisuals";

    public LilosVisuals() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        modEventBus.addListener(this::clientSetup);
        
        MinecraftForge.EVENT_BUS.register(this);
        
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigSpec.SPEC);
        
        LOGGER.info("Lilos Visuals initialized!");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Client setup complete");
    }
}

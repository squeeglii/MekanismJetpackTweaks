package me.squeeglii.mod.mekatweaks.client;

import me.squeeglii.mod.mekatweaks.MekanismTweaks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MekanismTweaks.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MekanismTweaksClient {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        //ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () ->
        //        new ConfigGuiHandler.ConfigGuiFactory((client, parent) -> MekanismTweaks.CONFIG));


    }

}

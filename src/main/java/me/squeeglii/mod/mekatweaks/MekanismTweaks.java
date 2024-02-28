package me.squeeglii.mod.mekatweaks;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod("mekatweaks")
public class MekanismTweaks {

    public static final String MOD_ID = "mekatweaks";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final ForgeConfigSpec CONFIG = new ForgeConfigSpec.Builder()
            .push("jetpack")
                .comment("How fast can you move forwards? 0.0 = vanilla speed")
                .defineInRange("forward_speed_boost", 2.0d, 0.0d, 15.0d).next()
                .comment("How fast can you move sideways / backwards? 0.0 = vanilla speed")
                .defineInRange("strafe_speed_boost", 0.0d, 0.0d, 15.0d).next()
                .comment("How fast can you move upwards? 0.15 = default mekanism")
                .defineInRange("vertical_speed", 0.15d, 0.0d, 5.0d).next()
                .comment("What is the top speed when moving upwards in NORMAL mode? 0.5 = default mekanism")
                .defineInRange("max_vertical_speed_normal", 0.5d, 0.0d, 100.0d).next()
                .comment("What is the top speed when moving up & down in HOVER mode? 0.2 = default mekanism")
                .defineInRange("max_vertical_speed_hover", 0.2d, 0.0d, 100.0d).next()
            .pop()
            .build();

    public MekanismTweaks() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG);
        MinecraftForge.EVENT_BUS.register(this);
    }


}

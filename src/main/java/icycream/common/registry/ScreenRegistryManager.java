package icycream.common.registry;

import icycream.IcyCream;
import icycream.client.gui.GuiMixerScreen;
import icycream.client.gui.GuiRefrigeratorScreen;
import icycream.common.gui.MixerContainer;
import icycream.common.gui.RefrigeratorContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = IcyCream.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ScreenRegistryManager {
    @SubscribeEvent
    public static void registerScreens(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(MixerContainer.type, GuiMixerScreen::new);
        ScreenManager.registerFactory(RefrigeratorContainer.type, GuiRefrigeratorScreen::new);
    }
}

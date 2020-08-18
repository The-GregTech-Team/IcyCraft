package icycream.common.registry;

import icycream.IcyCream;
import icycream.client.gui.GuiMixerScreen;
import icycream.common.gui.MixerContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = IcyCream.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ScreenRegistryManager {
    public static void registerScreens(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(MixerContainer.type, GuiMixerScreen::new);
    }
}

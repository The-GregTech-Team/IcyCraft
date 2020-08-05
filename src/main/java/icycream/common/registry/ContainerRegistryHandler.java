package icycream.common.registry;

import icycream.client.gui.GuiMixerScreen;
import icycream.common.gui.MixerContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author lyt
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContainerRegistryHandler {
    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(MixerContainer.type);
        ScreenManager.registerFactory(MixerContainer.type, GuiMixerScreen::new);
    }
}
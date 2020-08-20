package icycream.common.registry;

import icycream.common.gui.ExtractorContainer;
import icycream.common.gui.MaceratorContainer;
import icycream.common.gui.MixerContainer;
import icycream.common.gui.RefrigeratorContainer;
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
        event.getRegistry().registerAll(MixerContainer.TYPE, RefrigeratorContainer.TYPE, ExtractorContainer.TYPE, MaceratorContainer.TYPE);
    }
}

package icycream.common.registry;

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
        event.getRegistry().register(MixerContainer.type);
        event.getRegistry().register(RefrigeratorContainer.type);
    }
}

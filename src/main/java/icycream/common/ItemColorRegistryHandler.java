package icycream.common;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("icycream")
@Mod.EventBusSubscriber(modid="icycream", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemColorRegistryHandler {
    public static final Item ice_cream_complex = null;
    @SubscribeEvent
    public static void itemColors(ColorHandlerEvent.Item event) {
        event.getItemColors().register((a, b) -> {
            return ((ItemIceCream)ice_cream_complex).getColor(b);
        }, ice_cream_complex);
    }
}
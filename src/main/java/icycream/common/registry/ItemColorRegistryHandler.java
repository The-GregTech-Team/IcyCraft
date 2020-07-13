package icycream.common.registry;

import icycream.common.item.ItemBucket;
import icycream.common.item.ItemIceCream;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("icycream")
@Mod.EventBusSubscriber(modid="icycream", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemColorRegistryHandler {
    public static final Item ice_cream_complex = null;
    public static final Item bucket = null;
    @SubscribeEvent
    public static void itemColors(ColorHandlerEvent.Item event) {
        event.getItemColors().register((a, b) -> {
            if(ice_cream_complex != null) {
                return ((ItemIceCream)ice_cream_complex).getColor(b, a);
            } else {
                return 0xFFFFFF;
            }
        }, ice_cream_complex);
        event.getItemColors().register(
                (a, b) -> {
                    if(bucket != null) {
                      return ((ItemBucket)bucket).getColor(b, a);
                    } else {
                        return 0xFFFFFF;
                    }
                }
        );
    }
}

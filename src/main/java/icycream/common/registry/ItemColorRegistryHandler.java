package icycream.common.registry;

import icycream.IcyCream;
import icycream.common.item.ItemIceCream;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(IcyCream.MODID)
@Mod.EventBusSubscriber(modid=IcyCream.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ItemColorRegistryHandler {
    private static final Item ice_cream_complex = null;
    @SubscribeEvent
    public static void itemColors(ColorHandlerEvent.Item event) {
        event.getItemColors().register((a, b) -> {
            if(ice_cream_complex != null) {
                return ((ItemIceCream)ice_cream_complex).getColor(b, a);
            } else {
                return 0xFFFFFF;
            }
        }, ice_cream_complex);
    }
}

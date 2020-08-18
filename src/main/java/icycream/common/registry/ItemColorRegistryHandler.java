package icycream.common.registry;

import icycream.IcyCream;
import icycream.common.item.ItemIceCream;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid=IcyCream.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ItemColorRegistryHandler {
    private static Item ice_cream_complex = null;
    @SubscribeEvent
    public static void itemColors(ColorHandlerEvent.Item event) {
        ice_cream_complex = Registry.ITEM.getOrDefault(new ResourceLocation(IcyCream.MODID, "ice_cream_complex"));
        event.getItemColors().register((a, b) -> {
            if(ice_cream_complex != null) {
                return ((ItemIceCream)ice_cream_complex).getColor(b, a);
            } else {
                return 0xFFFFFF;
            }
        }, ice_cream_complex);
    }
}

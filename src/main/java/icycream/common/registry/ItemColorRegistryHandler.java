package icycream.common.registry;

import icycream.IcyCream;
import icycream.common.item.ItemIceCream;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.*;

@Mod.EventBusSubscriber(modid=IcyCream.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ItemColorRegistryHandler {
    public static Item ice_cream_complex = null;
    public static Item cocoa_powder = null;
    private static Color cocoa_powder_color = new Color(0x731919);

    private static IItemColor itemColor = (a, b) -> {
        if(a.getItem() == ice_cream_complex) {
            return ((ItemIceCream)ice_cream_complex).getColor(b, a);
        } else if(a.getItem() == cocoa_powder){
            return cocoa_powder_color.getRGB();
        }
        else {
            return 0xFFFFFF;
        }
    };
    @SubscribeEvent
    public static void itemColors(ColorHandlerEvent.Item event) {
        ice_cream_complex = ForgeRegistries.ITEMS.getValue(new ResourceLocation(IcyCream.MODID, "ice_cream_complex"));
        cocoa_powder = ForgeRegistries.ITEMS.getValue(new ResourceLocation(IcyCream.MODID, "cocoa_powder"));
        event.getItemColors().register(itemColor, ice_cream_complex);
        event.getItemColors().register(itemColor, cocoa_powder);
    }
}

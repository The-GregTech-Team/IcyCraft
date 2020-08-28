package icycream.common.registry;

import icycream.IcyCream;
import icycream.client.color.IcecreamBucketTinter;
import icycream.common.block.BlockIcecreamBucket;
import icycream.common.item.Ingredient;
import icycream.common.item.ItemIceCream;
import net.minecraft.block.Block;
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
public class ColorRegistryHandler {
    public static Item ice_cream_complex = null;
    public static Item cocoa_powder = null;
    public static Block bucket_ice_cream;
    public static Item item_bucket_ice_cream;
    public static Item item_cream_ball;
    public static Item item_icecream_handle;
    private static Color cocoa_powder_color = new Color(0x731919);
    @SubscribeEvent
    public static void itemColors(ColorHandlerEvent.Item event) {
        ice_cream_complex = ForgeRegistries.ITEMS.getValue(new ResourceLocation(IcyCream.MODID, "ice_cream_complex"));
        cocoa_powder = ForgeRegistries.ITEMS.getValue(new ResourceLocation(IcyCream.MODID, "cocoa_powder"));
        item_bucket_ice_cream = ForgeRegistries.ITEMS.getValue(new ResourceLocation(IcyCream.MODID, "icecream_bucket"));
        item_cream_ball = ForgeRegistries.ITEMS.getValue(new ResourceLocation(IcyCream.MODID, "icecream_ball"));
        item_icecream_handle = ForgeRegistries.ITEMS.getValue(new ResourceLocation(IcyCream.MODID, "icecream_handle"));

        event.getItemColors().register((itemStack, tintIndex) -> ((ItemIceCream)ice_cream_complex).getColor(tintIndex, itemStack), ice_cream_complex);
        event.getItemColors().register((itemStack, tintIndex) -> cocoa_powder_color.getRGB(), cocoa_powder);
        event.getItemColors().register((itemStack, tintIndex) -> {
            if(tintIndex == 1)
                return itemStack.getTag() == null ? Ingredient.DEFAULT.getColor().getRGB() : Ingredient.valueOf(itemStack.getTag().getString("ingredient")).getColor().getRGB();
            else
                return Ingredient.DEFAULT.getColor().getRGB();
        }, item_bucket_ice_cream);
        event.getItemColors().register(
                (itemStack, tintIndex) ->
                        itemStack.getTag() == null ? Ingredient.DEFAULT.getColor().getRGB() : Ingredient.valueOf(itemStack.getTag().getString("ingredient")).getColor().getRGB(),
                item_cream_ball, item_icecream_handle);
    }

    @SubscribeEvent
    public static void blockColors(ColorHandlerEvent.Block event) {
        bucket_ice_cream = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(IcyCream.MODID, "icecream_bucket"));
        event.getBlockColors().register(new IcecreamBucketTinter(), bucket_ice_cream);
    }
}

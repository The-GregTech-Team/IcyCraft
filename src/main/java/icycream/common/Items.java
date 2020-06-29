package icycream.common;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("icycraft")
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Items {
    public static final Item ice_cream_basic = null;
    private static ItemGroup itemGroup = new ItemGroup("icyCraft") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ice_cream_basic);
        }
    };
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(
        new Item(
                new Item.Properties()
                .maxStackSize(64)
                .food(new Food.Builder().hunger(3).build())
                .group(itemGroup)
            ).setRegistryName("icycraft", "ice_cream_basic")
        );

    }
}

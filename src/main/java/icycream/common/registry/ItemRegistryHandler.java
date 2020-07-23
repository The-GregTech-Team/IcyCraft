package icycream.common.registry;

import icycream.IcyCream;
import icycream.common.item.ItemIceCream;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ObjectHolder(IcyCream.MODID)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistryHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Item ice_cream_basic = null;
    public static ItemGroup itemGroup = new ItemGroup(IcyCream.MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ice_cream_basic);
        }
    };
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        LOGGER.info("registering items");
        event.getRegistry().register(
        new ItemIceCream(
                new Item.Properties()
                .maxStackSize(64)
                .food(new Food.Builder().hunger(1).setAlwaysEdible().build())
                .group(itemGroup)
            ).setRegistryName(IcyCream.MODID, "ice_cream_basic")
        );
        event.getRegistry().register(
                new ItemIceCream(
                        new Item.Properties()
                                .maxStackSize(64)
                                .food(new Food.Builder().hunger(1).setAlwaysEdible().build())
                                .group(itemGroup)
                ).setRegistryName(IcyCream.MODID, "ice_cream_complex")
        );
    }

    private static void registerLiquidBucket() {

    }
}

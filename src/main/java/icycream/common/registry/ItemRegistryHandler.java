package icycream.common.registry;

import icycream.IcyCream;
import icycream.common.item.ItemIceCream;
import icycream.common.util.RecipeManagerHelper;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author lyt
 */
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
        // 食材
        // 可可粉
        event.getRegistry().register(
                new Item(new Item.Properties().food(new Food.Builder().hunger(1).fastToEat().build()).group(itemGroup))
                .setRegistryName(IcyCream.MODID, "cocoa_powder")
        );
        RecipeManagerHelper.loadRecipes();
    }

    private static void registerLiquidBucket() {

    }
}

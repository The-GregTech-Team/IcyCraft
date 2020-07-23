package icycream.common.registry;

import icycream.IcyCream;
import icycream.common.fluid.FluidIngredient;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

import static net.minecraft.item.Items.BUCKET;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class FluidRegistryHandler {
    @SubscribeEvent
    public static void registerFluids(RegistryEvent.Register<Fluid> event) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
       registerFluid("fluid_egg", new Color(0xffb800));
    }

    /**
     * 老子不做桶了，做nmd桶去，sb
     * @param name
     * @param color
     */
    private static void registerFluid(String name, Color color) {
        FluidIngredient.Source source = new FluidIngredient.Source(color, name);
        FluidIngredient.Flowing flowing = new FluidIngredient.Flowing(color, name);
        Registry.FLUID.register(new ResourceLocation(IcyCream.MODID, name), source);
        Registry.FLUID.register(new ResourceLocation(IcyCream.MODID, name + "_flowing"), flowing);
        /**
         *
        BucketItem bucketItem = new BucketItem(source, (new Item.Properties()).containerItem(BUCKET).maxStackSize(1).group(ItemGroup.MISC));
        Util.unfreezeRegistry(Registry.BLOCK);
        FlowingFluidBlock sourceFluidBlock = new FlowingFluidBlock(() -> source, Block.Properties.create(Material.WATER).
                doesNotBlockMovement().
                hardnessAndResistance(100.0F).
                noDrops());
        Registry.BLOCK.register(new ResourceLocation(IcyCream.MODID, "block_liquid_" + name), sourceFluidBlock);
        Util.freezeRegistry(Registry.BLOCK);
        Util.unfreezeRegistry(Registry.ITEM);
        Registry.ITEM.register(new ResourceLocation(IcyCream.MODID,name + "_bucket"), bucketItem);
        Util.freezeRegistry(Registry.ITEM);
         */

    }
}

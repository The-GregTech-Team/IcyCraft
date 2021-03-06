package icycream.common.registry;

import icycream.IcyCream;
import icycream.common.fluid.FluidIngredient;
import icycream.common.item.Ingredient;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class FluidRegistryHandler {
    @SubscribeEvent
    public static void registerFluids(RegistryEvent.Register<Fluid> event) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
       registerFluid("fluid_egg", new Color(0xffb800));
       registerFluid("fluid_oil", new Color(0xb5d000));
       registerFluid("fluid_sweet_berries_juice", Ingredient.BERRY);
       registerFluid("fluid_apple_juice", Ingredient.APPLE);
       registerFluid("fluid_carrot_juice", Ingredient.CARROT);
       registerFluid("fluid_pumpkin_juice", Ingredient.PUMPKIN);
       registerFluid("fluid_melon_juice", Ingredient.MELON);

       registerFluid("fluid_raw_mixture", new Color(0xddefdd));
       registerFluid("fluid_honey_mixture", Ingredient.HONEY);
       registerFluid("fluid_sweet_berries_mixture", Ingredient.BERRY);
       registerFluid("fluid_apple_mixture", Ingredient.APPLE);
       registerFluid("fluid_carrot_mixture", Ingredient.CARROT);
       registerFluid("fluid_pumpkin_mixture", Ingredient.PUMPKIN);
       registerFluid("fluid_melon_mixture", Ingredient.MELON);
       registerFluid("fluid_vanilla_mixture", Ingredient.VANILLA);
       registerFluid("fluid_coco_mixture", Ingredient.COCO);
       registerFluid("fluid_purpur_mixture", Ingredient.PURPUR);
       registerFluid("fluid_puffer_mixture", Ingredient.PUFFER_FISH);
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

    private static void registerFluid(String name, Ingredient ingredient) {
        FluidIngredient.Source source = new FluidIngredient.Source(ingredient, name);
        FluidIngredient.Flowing flowing = new FluidIngredient.Flowing(ingredient, name);
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

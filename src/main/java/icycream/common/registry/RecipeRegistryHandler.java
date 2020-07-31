package icycream.common.registry;

import com.google.common.collect.Lists;
import icycream.IcyCream;
import icycream.common.recipes.IngredientItem;
import icycream.common.recipes.MachineRecipe;
import icycream.common.tile.TileEntityMixer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class RecipeRegistryHandler {
    @SubscribeEvent
    public static void registerRecipes(FMLServerStartedEvent event) {
        TileEntityMixer.MIXER_RECIPES.add(
                new MachineRecipe(Lists.newArrayList(new IngredientItem("minecraft","egg",1)),
                        null,
                        new FluidStack(Registry.FLUID.getOrDefault(new ResourceLocation(IcyCream.MODID, "fluid_egg")),
                                100), 200));
    }
}

package icycream.common.registry;

import icycream.common.fluid.FluidIngredient;
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
    public static void registerFluids(RegistryEvent.Register<Fluid> event) {
       registerFluid("fluid_egg", new Color(0xffb800));
    }

    private static void registerFluid(String name, Color color) {
        Registry.FLUID.register(new ResourceLocation("icycream", name), new FluidIngredient.Source(color, name));
        Registry.FLUID.register(new ResourceLocation("icycream", name + "_flowing"), new FluidIngredient.Flowing(color, name));

    }
}

package icycream.common.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidStackSerializer {
    public static FluidStack getFluidStack(JsonObject json) {
        if (json == null || json.isJsonNull())
            throw new JsonSyntaxException("Json cannot be null");

        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(json.get("fluid").getAsString()));
        int amount = json.get("amount").getAsInt();

        if (fluid == null)
            throw new JsonSyntaxException("Cannot lookup fluid " + json.get("fluid").getAsString());
        return new FluidStack(fluid, amount);
    }
}

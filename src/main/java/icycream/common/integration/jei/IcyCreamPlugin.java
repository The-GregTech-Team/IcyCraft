package icycream.common.integration.jei;

import icycream.IcyCream;
import icycream.common.recipes.IcyCreamRecipeUid;
import icycream.common.recipes.RecipeTypes;
import icycream.common.registry.ServerHandler;
import icycream.common.util.RecipeManagerHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class IcyCreamPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(IcyCream.MODID, "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(RecipeManagerHelper.getRecipes(RecipeTypes.EXTRACTING, ServerHandler.getServerInstance().getRecipeManager()).values(), IcyCreamRecipeUid.EXTRACTING);
        registration.addRecipes(RecipeManagerHelper.getRecipes(RecipeTypes.FREEZING, ServerHandler.getServerInstance().getRecipeManager()).values(), IcyCreamRecipeUid.FREEZING);
        registration.addRecipes(RecipeManagerHelper.getRecipes(RecipeTypes.MACERATING, ServerHandler.getServerInstance().getRecipeManager()).values(), IcyCreamRecipeUid.MACERATING);
        registration.addRecipes(RecipeManagerHelper.getRecipes(RecipeTypes.MIXING, ServerHandler.getServerInstance().getRecipeManager()).values(), IcyCreamRecipeUid.MIXING);
    }
}

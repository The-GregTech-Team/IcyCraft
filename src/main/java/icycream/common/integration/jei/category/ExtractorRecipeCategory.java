package icycream.common.integration.jei.category;

import icycream.common.recipes.ExtractorRecipe;
import icycream.common.recipes.ShapelessFluidRecipeSerializer;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class ExtractorRecipeCategory implements IRecipeCategory<ExtractorRecipe> {
    private final String localizedName = I18n.format("tile.icycream.extractor.name");

    @Override
    public ResourceLocation getUid() {
        return ShapelessFluidRecipeSerializer.EXTRACTING.getRegistryName();
    }

    @Override
    public Class<? extends ExtractorRecipe> getRecipeClass() {
        return ExtractorRecipe.class;
    }

    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return null;
    }

    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public void setIngredients(ExtractorRecipe extractorRecipe, IIngredients iIngredients) {

    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, ExtractorRecipe extractorRecipe, IIngredients iIngredients) {

    }
}

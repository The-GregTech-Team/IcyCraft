package icycream.common.block.entity;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityExtractor extends TileEntityFluidRecipeMachine<TileEntityExtractor> {
    public TileEntityExtractor(TileEntityType<TileEntityExtractor> tileEntityTypeIn, int slots) {
        super(tileEntityTypeIn, slots);
    }

    @Override
    public IRecipe<TileEntityExtractor> findRecipe() {
        return null;
    }
}

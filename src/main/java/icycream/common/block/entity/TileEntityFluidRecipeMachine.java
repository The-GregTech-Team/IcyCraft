package icycream.common.block.entity;

import net.minecraft.tileentity.TileEntityType;

public abstract class TileEntityFluidRecipeMachine<T extends TileEntityRecipeMachine<T>> extends TileEntityRecipeMachine<T> {
    public TileEntityFluidRecipeMachine(TileEntityType<T> tileEntityTypeIn, int slots) {
        super(tileEntityTypeIn, slots);
    }
}

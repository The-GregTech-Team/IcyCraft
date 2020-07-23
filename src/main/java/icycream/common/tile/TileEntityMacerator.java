package icycream.common.tile;

import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityMacerator  extends TileEntity implements ITickableTileEntity {
    public TileEntityMacerator(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick() {

    }
}

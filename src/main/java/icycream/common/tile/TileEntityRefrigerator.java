package icycream.common.tile;

import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityRefrigerator  extends TileEntity implements ITickableTileEntity {

    public TileEntityRefrigerator(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick() {

    }
}

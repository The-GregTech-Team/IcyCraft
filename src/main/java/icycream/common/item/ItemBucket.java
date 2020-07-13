package icycream.common.item;

import icycream.common.fluid.FluidIngredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemBucket extends Item {
    public ItemBucket(Properties properties) {
        super(properties);
    }
    private int getColorFromNBT(ItemStack itemStack) {
        CompoundNBT tag = itemStack.getTag();
        if(tag == null) {
            return 0x000000;
        }
        String liquid = tag.getString("liquid");
        FluidIngredient fluid = (FluidIngredient) Registry.FLUID.getOrDefault(new ResourceLocation("icycream", liquid));
        if(fluid != null) {
            return fluid.getColor().getRGB();
        } else {
            return 0x000000;
        }
    }
    @OnlyIn(Dist.CLIENT)
    public int getColor(int tintIndex, ItemStack itemStack) {
        if(tintIndex == 0) {
            return 0xFFFFFF;
        } else {
            return getColorFromNBT(itemStack);
        }
    }
}

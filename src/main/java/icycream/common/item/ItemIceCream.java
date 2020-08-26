package icycream.common.item;

import com.google.common.collect.Lists;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ItemIceCream extends Item {

    protected Logger logger = LogManager.getLogger();

    public ItemIceCream(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    /**
     * 根据nbt(原料配比)获取药水(buff)
     * 存储在ingredients nbt里面
     * 格式是dict
     * test command:
     * give Dev icycream:ice_cream_complex{"ingredient":"COCO,APPLE,HONEY,PURPUR"} 1
     * @param itemStack
     * @return
     */
    private List<EffectInstance> getEffectFromNBT(ItemStack itemStack) {
        String ingredients = itemStack.getTag().getString("ingredients");
        ArrayList<EffectInstance> effectInstances = Lists.newArrayList();
        for (String ingredient : ingredients.split(",")) {
            Ingredient ingredientEnum = Ingredient.valueOf(ingredient);
            if(ingredient != null) {
                for (Effect effect : ingredientEnum.getPotionEffect()) {
                    /**
                     * 初版，直接按照里面的数值来确定效果持续时间
                     */
                    effectInstances.add(new EffectInstance(effect, ingredientEnum.getDurationTicks()));
                }
            }
        }
        return effectInstances;
    }

    private int getColorFromNBT(int tintIndex, ItemStack itemStack) {
        CompoundNBT tag = itemStack.getTag();
        if(tag == null) {
            return 0xFFFFFF;
        }
        CompoundNBT ingredients = tag.getCompound("ingredients");
        String handle = tag.getString("handle");
        if(ingredients == null || ingredients.keySet().isEmpty()) {
            logger.debug("eating an empty icecream");
            return 0xFFFFFF;
        }
        Set<String> ingredientSet = ingredients.keySet();
        List<String> ingredientList = Lists.newArrayList(ingredientSet);
        if(tintIndex == 0) {
            return Ingredient.valueOf(handle).getColor().getRGB();
        } else if (tintIndex == 5) {
            return 0x000000;
        }
        else {
            return Ingredient.valueOf(ingredientList.get(tintIndex - 1)).getColor().getRGB();
        }
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     *
     * @param itemStack
     * @param world
     * @param world
     */
    @Override
    public ItemStack onItemUseFinish(ItemStack itemStack, World world, LivingEntity livingEntity) {
        //复制了一段原版代码mmp
        //为了把根据nbt加药水的效果做出来
        //先做功能，目前这个地方就这样吧
        Item item = itemStack.getItem();
        if (item.isFood()) {
            world.playSound(null, livingEntity.getPosX(), livingEntity.getPosY(), livingEntity.getPosZ(), livingEntity.getEatSound(itemStack), SoundCategory.NEUTRAL, 1.0F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);
            for (EffectInstance effectInstance : getEffectFromNBT(itemStack)) {
                livingEntity.addPotionEffect(effectInstance);
            }
            if (!(livingEntity instanceof PlayerEntity) || !((PlayerEntity)livingEntity).abilities.isCreativeMode) {
                itemStack.shrink(1);
            }
        }
        return itemStack;
    }

    @OnlyIn(Dist.CLIENT)
    public int getColor(int tintIndex, ItemStack itemStack) {
        return getColorFromNBT(tintIndex, itemStack);
    }
}

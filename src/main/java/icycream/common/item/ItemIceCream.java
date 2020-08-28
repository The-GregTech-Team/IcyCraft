package icycream.common.item;

import com.google.common.collect.Lists;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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
     * give Dev icycream:ice_cream_complex{"handle":"COCO","ingredients":"COCO,APPLE,HONEY,PURPUR"} 1
     * @param itemStack
     * @return
     */
    private List<EffectInstance> getEffectFromNBT(ItemStack itemStack) {
        String ingredients = itemStack.getTag() != null ? itemStack.getTag().getString("ingredients") : "DEFAULT";
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

    @OnlyIn(Dist.CLIENT)
    private int getColorFromNBT(int tintIndex, ItemStack itemStack) {
        CompoundNBT tag = itemStack.getTag();
        if(tag == null) {
            return 0xFFFFFF;
        }
        String ingredients = tag.getString("ingredients");
        String handle = tag.getString("handle");
        if(ingredients.length() <= 0) {
            logger.debug("eating an empty icecream");
            return 0xFFFFFF;
        }
        String[] ingredientList = ingredients.split(",");
        if(tintIndex == 0) {
            return Ingredient.valueOf(handle).getColor().getRGB();
        } else if (tintIndex == 5) {
            return 0x000000;
        }
        else {
            return Ingredient.valueOf(ingredientList[tintIndex - 1]).getColor().getRGB();
        }
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     *
     * @param stack
     * @param worldIn
     * @param tooltip
     * @param flagIn
     */
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        String ingredients = null;
        if (stack.getTag() != null) {
            ingredients = stack.getTag().getString("ingredients");
        }
        else {
            ingredients = "DEFAULT";
        }
        tooltip.add(new TranslationTextComponent("tooltip.foodlist"));
        for (String ingredient : ingredients.split(",")) {
            Ingredient ingredientEnum = Ingredient.valueOf(ingredient);
            tooltip.add(new TranslationTextComponent(ingredientEnum.getEffectName()));
            for (Effect effect : ingredientEnum.getPotionEffect()) {
                tooltip.add(new StringTextComponent(effect.getDisplayName().getFormattedText() + ":" + ingredientEnum.getDurationTicks() / 20.0 + "s"));
            }
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

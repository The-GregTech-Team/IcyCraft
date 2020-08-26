package icycream.common.item;

import net.minecraft.potion.*;
import java.awt.Color;

/**
 * 配料表
 * 每份配料占一个球
 */
public enum Ingredient {
    BODY_YELLOW("牛奶蛋筒", "egg_milk_noeffect", new Color(0xf0e493), 0, new Effect[]{}),
    BODY_BROWN("可可蛋筒", "egg_milk_coco_noeffect", new Color(0xcf8b4e),0,  new Effect[]{}),
    COCO("可可", "coco_nightvision", new Color(0x86360c), 300, new Effect[]{Effects.NIGHT_VISION}),
    VANILLA("香草", "vanilla_luck", new Color(0x9dcd00), 150, new Effect[]{Effects.LUCK}),
    HONEY("蜂蜜", "honey_strength", new Color(0xffeb00), 270, new Effect[]{Effects.STRENGTH}),
    APPLE("苹果", "apple_luck", new Color(0xff9f82), 225, new Effect[]{Effects.LUCK}),
    MELON("西瓜", "melon_haste", new Color(0x00a75b), 450, new Effect[]{Effects.HASTE}),
    PUMPKIN("南瓜", "pumpkin_regen", new Color(0xff8c00), 600, new Effect[]{Effects.REGENERATION}),
    CARROT("胡萝卜 ", "carrot_regen", new Color(0xffaf00), 120, new Effect[]{Effects.REGENERATION}),
    BERRY("树莓", "berry_luck", new Color(0xf22fa1), 120, new Effect[]{Effects.LUCK}),
    PURPUR("紫松", "purpur_slow_fall", new Color(0xca5eb1), 300,  new Effect[]{Effects.SLOW_FALLING}),
    //以下是老八秘制小汉堡
    PUFFER_FISH("河豚","puffer_fish_poison", new Color(0x1a6a04), 1200, new Effect[]{Effects.POISON, Effects.WATER_BREATHING})
    ;
    private final int durationTicks;
    private String name;
    private String effectName;
    private Effect[] potionEffect;
    private Color color;
    public String getName() {
        return name;
    }
    public int getDurationTicks() {
        return durationTicks;
    }
    public String getEffectName() {
        return effectName;
    }
    public Effect[] getPotionEffect() {
        return potionEffect;
    }
    public Color getColor() {
        return color;
    }
    Ingredient(String name, String effectName, Color color, int durationTicks, Effect[] potionEffect) {
        this.potionEffect = potionEffect;
        this.name = name;
        this.effectName = effectName;
        this.color = color;
        this.durationTicks = durationTicks;
    }
}

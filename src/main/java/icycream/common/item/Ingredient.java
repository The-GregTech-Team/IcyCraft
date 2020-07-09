package icycream.common.item;

import net.minecraft.potion.*;
import java.awt.Color;

public enum Ingredient {
    BODY_YELLOW("牛奶蛋筒", "egg_milk_noeffect", new Color(0xf0e493), new Effect[]{}),
    BODY_BROWN("可可蛋筒", "egg_milk_coco_noeffect", new Color(0xcf8b4e), new Effect[]{}),
    COCO("可可", "coco_nightvision", new Color(0x86360c), new Effect[]{Effects.NIGHT_VISION}),
    MILK("牛奶", "milk_saturation", new Color(0xfafafa),new Effect[]{Effects.SATURATION}),
    VANILLA("香草", "vanilla_luck", new Color(0x9dcd00), new Effect[]{Effects.LUCK}),
    HONEY("蜂蜜", "honey_strength", new Color(0xffeb00), new Effect[]{Effects.STRENGTH}),
    APPLE("苹果", "apple_luck", new Color(0xff9f82), new Effect[]{Effects.LUCK}),
    MELON("西瓜", "melon_haste", new Color(0x00a75b), new Effect[]{Effects.HASTE}),
    PURPUR("紫松", "purpur_slow_fall", new Color(0xca5eb1), new Effect[]{Effects.SLOW_FALLING}),
    //以下是老八秘制小汉堡
    ;
    private String name;
    private String effectName;
    private Effect[] potionEffect;
    private Color color;
    public String getName() {
        return name;
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
    Ingredient(String name, String effectName, Color color, Effect[] potionEffect) {
        this.potionEffect = potionEffect;
        this.name = name;
        this.effectName = effectName;
        this.color = color;
    }
}

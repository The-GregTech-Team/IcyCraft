package icycream.common;

import com.google.gson.Gson;
import net.minecraft.potion.*;

public enum Ingredient {

    COCO("可可", "coco_coffine", new Effect[]{Effects.NIGHT_VISION}),
    MILK("牛奶", "milk_saturation", new Effect[]{Effects.SATURATION}),
    VANILLA("香草", "vanilla_saturation", new Effect[]{Effects.SATURATION}),
    SUGAR("砂糖", "sugar_saturation", new Effect[]{Effects.SATURATION});
    private String name;
    private String effectName;
    private Effect[] potionEffect;
    public String getName() {
        return name;
    }
    public String getEffectName() {
        return effectName;
    }
    public Effect[] getPotionEffect() {
        return potionEffect;
    }
    Ingredient(String name, String effectName, Effect[] potionEffect) {
        this.potionEffect = potionEffect;
        this.name = name;
        this.effectName = effectName;
    }
}

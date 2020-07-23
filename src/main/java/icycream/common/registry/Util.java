package icycream.common.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraftforge.registries.ForgeRegistry;

import java.lang.reflect.Field;

public class Util {
    public static void unfreezeRegistry(DefaultedRegistry registry) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Field delegate = Class.forName("net.minecraftforge.registries.NamespacedDefaultedWrapper").getDeclaredField("delegate");
        delegate.setAccessible(true);
        ForgeRegistry reg = (ForgeRegistry) delegate.get(registry);
        reg.unfreeze();
    }
    public static void freezeRegistry(DefaultedRegistry registry) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Field delegate = Class.forName("net.minecraftforge.registries.NamespacedDefaultedWrapper").getDeclaredField("delegate");
        delegate.setAccessible(true);
        ForgeRegistry reg = (ForgeRegistry) delegate.get(registry);
        reg.freeze();
    }
    public static void unfreezeRegistry(SimpleRegistry registry) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Field delegate = Class.forName("net.minecraftforge.registries.NamespacedWrapper").getDeclaredField("delegate");
        delegate.setAccessible(true);
        ForgeRegistry reg = (ForgeRegistry) delegate.get(registry);
        reg.unfreeze();
    }
    public static void freezeRegistry(SimpleRegistry registry) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Field delegate = Class.forName("net.minecraftforge.registries.NamespacedWrapper").getDeclaredField("delegate");
        delegate.setAccessible(true);
        ForgeRegistry reg = (ForgeRegistry) delegate.get(registry);
        reg.freeze();
    }
    public boolean itemStackEquals(ItemStack A, ItemStack B) {
        if(A == null && B == null)
        {
            return true;
        } else if(A != null && B != null) {
          return A.equals(B, true);
        } else {
            return false;
        }
    }
}

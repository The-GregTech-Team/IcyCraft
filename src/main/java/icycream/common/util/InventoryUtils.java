package icycream.common.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class InventoryUtils {
    public static List<ItemStack> toList(IInventory inventory) {
        List<ItemStack> list = new ArrayList<>(inventory.getSizeInventory());
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            list.set(i, inventory.getStackInSlot(i));
        }
        return list;
    }

    public static ListIterator<ItemStack> toIterator(IInventory inventory) {
        return new ListIterator<ItemStack>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return inventory.getSizeInventory() >= i;
            }

            @Override
            public ItemStack next() {
                return inventory.getStackInSlot(i++);
            }

            @Override
            public boolean hasPrevious() {
                return i > 0;
            }

            @Override
            public ItemStack previous() {
                return inventory.getStackInSlot(--i);
            }

            @Override
            public int nextIndex() {
                return i + 1;
            }

            @Override
            public int previousIndex() {
                return i - 1;
            }

            @Override
            public void remove() {
            }

            @Override
            public void set(ItemStack stack) {
                inventory.setInventorySlotContents(i, stack);
            }

            @Override
            public void add(ItemStack stack) {
            }
        };
    }

    public static List<ItemStack> take(IInventory inventory, List<Ingredient> ingredients) {
        List<ItemStack> taken = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            for (ItemStack itemStack : toList(inventory)) {
                if (ingredient.test(itemStack)) {
                    itemStack.shrink(1);
                    ItemStack copied = itemStack.copy();
                    copied.setCount(1);
                    taken.add(copied);
                    break;
                }
            }
        }
        return taken;
    }
}

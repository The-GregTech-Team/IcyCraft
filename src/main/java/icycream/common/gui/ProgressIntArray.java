package icycream.common.gui;

import net.minecraft.util.IIntArray;

public class ProgressIntArray implements IIntArray {
    int[] arr = new int[2];
    @Override
    public int get(int index) {
        return arr[index];
    }

    @Override
    public void set(int index, int value) {
        arr[index] = value;
    }

    @Override
    public int size() {
        return 2;
    }
}

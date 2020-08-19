package icycream.common.gui;

import net.minecraft.util.IIntArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 可伸缩的intarray
 */
public class ScalableIntArray implements IIntArray {
    private List<Integer> integerList = new ArrayList<>();
    @Override
    public int get(int index) {
        return integerList.get(index);
    }

    @Override
    public void set(int index, int value) {
        if(index == integerList.size()) {
            integerList.add(value);
        } else {
            integerList.set(index, value);
        }
    }

    @Override
    public int size() {
        return integerList.size();
    }

    public void removeAt(int index) {
        integerList.remove(index);
    }

    public int[] toIntArray() {
        int[] ints = new int[size()];
        for (int i = 0; i < integerList.size(); i++) {
            ints[i] = integerList.get(i);
        }
        return ints;
    }
}

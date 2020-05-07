package jopengui.utils;

import java.util.List;

public class Utils {
    public static float[] floatListToArray(List<Float> list) {
        float[] array = new float[list.size()];
        for(int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}

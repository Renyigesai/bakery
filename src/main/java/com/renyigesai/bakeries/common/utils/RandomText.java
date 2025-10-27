package com.renyigesai.bakeries.common.utils;

import java.util.Random;

public class RandomText {

    public static String getFlourSieveRandomText(){
        return "player.bakeries.tips.flour_sieve." + getRandom();
    }

    public static String getToasterRandomText(){
        return "player.bakeries.tips.toaster." + getRandom();
    }

    public static int getRandom(){
        return random(3,1);
    }

    /**
     * 生成一个在指定范围内的随机整数。
     *
     * @param max 范围的最大值（包含）。
     * @param min 范围的最小值（包含）。
     * @return 在 [min, max] 范围内的一个随机整数。
     */

    public static Integer random(int max, int min) {
        Random rand = new Random();
        int value = 0;
        for (int i = 0; i < max; i++) {
            value = rand.nextInt(max - min + 1) + min;
        }
        return value;
    }
}

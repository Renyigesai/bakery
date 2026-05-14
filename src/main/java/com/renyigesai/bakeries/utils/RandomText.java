package com.renyigesai.bakeries.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomText {
    private RandomText() {
    }

    public static String pick(List<String> values) {
        if (values == null || values.isEmpty()) {
            return "";
        }
        return values.get(ThreadLocalRandom.current().nextInt(values.size()));
    }
}

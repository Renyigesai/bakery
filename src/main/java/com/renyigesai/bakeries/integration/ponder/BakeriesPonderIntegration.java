package com.renyigesai.bakeries.integration.ponder;

public class BakeriesPonderIntegration {
    public static void register() {
        net.createmod.ponder.foundation.PonderIndex.addPlugin(
                new BakeriesPonderPlugin()
        );
    }
}
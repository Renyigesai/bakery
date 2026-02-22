package com.renyigesai.bakeries.jade;

import com.renyigesai.bakeries.block.baysalt_frame.BaysaltFrameBlock;
import com.renyigesai.bakeries.block.baysalt_frame.BaysaltFrameBlockEntity;
import com.renyigesai.bakeries.block.oven.OvenBlock;
import com.renyigesai.bakeries.block.oven.OvenBlockEntity;
import com.renyigesai.bakeries.jade.provider.BaysaltFrameComponentProvider;
import com.renyigesai.bakeries.jade.provider.OvenComponentProvider;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class BakeriesWailaPlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(BaysaltFrameComponentProvider.INSTANCE, BaysaltFrameBlockEntity.class);
//        registration.registerBlockDataProvider(OvenComponentProvider.INSTANCE, OvenBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(BaysaltFrameComponentProvider.INSTANCE, BaysaltFrameBlock.class);
//        registration.registerBlockComponent(OvenComponentProvider.INSTANCE, OvenBlock.class);
    }
}
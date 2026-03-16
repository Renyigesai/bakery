//package com.renyigesai.bakeries.jade.provider;
//
//import com.renyigesai.bakeries.block.baysalt_frame.BaysaltFrameBlockEntity;
//import com.renyigesai.bakeries.jade.Identifiers;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraftforge.fluids.FluidStack;
//import snownee.jade.api.BlockAccessor;
//import snownee.jade.api.IBlockComponentProvider;
//import snownee.jade.api.IServerDataProvider;
//import snownee.jade.api.ITooltip;
//import snownee.jade.api.config.IPluginConfig;
//import snownee.jade.api.fluid.JadeFluidObject;
//import snownee.jade.api.theme.IThemeHelper;
//import snownee.jade.api.ui.BoxStyle;
//import snownee.jade.api.ui.IElementHelper;
//import snownee.jade.api.ui.IProgressStyle;
//import snownee.jade.api.view.FluidView;
//import snownee.jade.util.FluidTextHelper;
//
//public enum BaysaltFrameComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
//    INSTANCE;
//    /**
//     * 向工具提示中添加信息
//     * 该方法覆写自接口，用于在工具提示中添加有关方块的附加信息
//     *
//     * @param tooltip 工具提示对象，用于添加信息
//     * @param accessor 方块访问器，提供方块的相关数据
//     * @param config 插件配置对象，允许根据配置调整行为
//     */
//    @Override
//    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
//        // 检查方块数据中是否包含FluidTanks信息，并且信息是CompoundTag类型
//        if (accessor.getServerData().get("FluidTanks") instanceof CompoundTag compoundTag) {
//            // 从CompoundTag中加载流体信息
//            FluidStack fluid = FluidStack.loadFluidStackFromNBT(compoundTag);
//            // 获取流体的数值
//            int fuel = fluid.getAmount();
//            // 获取流体的描述信息
//            Component fluidComponent = fluid.getFluid().getFluidType().getDescription();
//            // 获取元素助手对象
//            IElementHelper helper = IElementHelper.get();
//
//            // 如果流体信息不为空，则向工具提示中添加流体相关信息
//            if(!fluid.isEmpty()){
//                tooltip.add(Component.literal(" "));
//                tooltip.append(IThemeHelper.get().info(Component.translatable("bakeries.fluid")));
//                tooltip.append(IThemeHelper.get().info(fluidComponent));
//            }
//
//            // 创建流体视图对象，并设置当前和最大流体量以及比例
//            FluidView fluidView = new FluidView(helper.fluid(JadeFluidObject.of(fluid.getFluid())));
//            fluidView.current = FluidTextHelper.getUnicodeMillibuckets(fluid.getAmount(), true);
//            fluidView.max = FluidTextHelper.getUnicodeMillibuckets(2000, true);
//            fluidView.ratio = (float)((double)fluid.getAmount() / (double)2000);
//            // 设置进度样式，并向工具提示中添加流体进度信息
//            IProgressStyle progressStyle = helper.progressStyle().overlay(fluidView.overlay);
//            tooltip.add(Component.literal(" "));
//            tooltip.append(helper.progress(fluidView.ratio, fluid.getFluid().getFluidType().getDescription().copy().append(": "+fuel +"mB"), progressStyle, BoxStyle.DEFAULT, true));
//        }
//
//        // 检查方块数据中是否包含进度信息，并向工具提示中添加进度百分比
//        if (accessor.getServerData().contains("progress") && accessor.getServerData().contains("maxProgress")) {
//            int progress = accessor.getServerData().getInt("progress");
//            int maxProgress = accessor.getServerData().getInt("maxProgress");
//            int p = (int) (progress * 100 / maxProgress);
//            tooltip.add(Component.literal(" "));
//            tooltip.append(IThemeHelper.get().info(Component.translatable("bakeries.progress")));
//            tooltip.append(IThemeHelper.get().info(p));
//            tooltip.append(IThemeHelper.get().info("%"));
//        }
//
//        // 检查方块数据中是否包含盐分信息，并向工具提示中添加盐分量
//        if (accessor.getServerData().contains("salts")) {
//            int salts = accessor.getServerData().getInt("salts");
//            int maxSalts = accessor.getServerData().getInt("max_salts");
//            tooltip.add(Component.literal(" "));
//            tooltip.append(IThemeHelper.get().info(Component.translatable("bakeries.salts")));
//            tooltip.append(IThemeHelper.get().info(salts));
//            tooltip.append(IThemeHelper.get().info("/"));
//            tooltip.append(IThemeHelper.get().info(maxSalts));
//        }
//    }
//    /**
//     * 将服务器端数据附加到指定的CompoundTag中
//     * 此方法用于保存服务器端特定于区块的数据，以便在需要时恢复区块的状态
//     *
//     * @param tag CompoundTag对象，用于存储数据
//     * @param accessor BlockAccessor对象，提供对区块数据的访问
//     */
//    @Override
//    public void appendServerData(CompoundTag tag, BlockAccessor accessor) {
//        // 获取方块实体
//        BaysaltFrameBlockEntity brewingStand = (BaysaltFrameBlockEntity)accessor.getBlockEntity();
//
//        // 创建一个新的CompoundTag对象用于存储流体罐数据
//        CompoundTag compound = new CompoundTag();
//
//        // 将流体罐的数据写入CompoundTag中，并将其存储在"FluidTanks"键下
//        tag.put("FluidTanks", brewingStand.getFluidTank().writeToNBT(compound));
//
//        // 将当前盐的数量和最大盐的数量存储在各自的键下
//        tag.putInt("salts", brewingStand.salts);
//        tag.putInt("max_salts", brewingStand.max_salts);
//
//        // 将进度和最大进度存储在各自的键下
//        tag.putInt("progress", brewingStand.progress);
//        tag.putInt("maxProgress", brewingStand.maxProgress);
//    }
//
//    @Override
//    public ResourceLocation getUid() {
//        return Identifiers.BAYSALT_FRAME;
//    }
//
//}
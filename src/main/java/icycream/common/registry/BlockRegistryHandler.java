package icycream.common.registry;

import icycream.common.block.BlockExtractor;
import icycream.common.block.BlockMacerator;
import icycream.common.block.BlockMixer;
import icycream.common.block.BlockRefridgerator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistryHandler {
    /**
     * qnmd deprecation
     * 我就tmd要用，sb forge你妈死了
     * @param event
     */
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        registerBlocksWithItem(new BlockExtractor(Block.Properties.create(Material.IRON).hardnessAndResistance(6, 30)), "icycream", "extractor");
        registerBlocksWithItem(new BlockMixer(Block.Properties.create(Material.IRON).hardnessAndResistance(6, 30)),"icycream", "mixer");
        registerBlocksWithItem(new BlockRefridgerator(Block.Properties.create(Material.IRON).hardnessAndResistance(6, 30)), "icycream", "refrigerator");
        registerBlocksWithItem(new BlockMacerator(Block.Properties.create(Material.IRON).hardnessAndResistance(6, 30)), "icycream", "macerator");
    }

    public static void registerBlocksWithItem(Block block, String namespace, String name) {
        Registry.register(Registry.BLOCK, new ResourceLocation(namespace, name), block);
        Registry.register(Registry.ITEM, new ResourceLocation(namespace, name), new BlockItem(block, new Item.Properties()));
    }
}

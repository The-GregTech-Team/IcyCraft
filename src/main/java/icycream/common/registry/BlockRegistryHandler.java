package icycream.common.registry;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import icycream.IcyCream;
import icycream.common.block.BlockExtractor;
import icycream.common.block.BlockMacerator;
import icycream.common.block.BlockMixer;
import icycream.common.block.BlockRefridgerator;
import icycream.common.tile.TileEntityExtractor;
import icycream.common.tile.TileEntityMacerator;
import icycream.common.tile.TileEntityMixer;
import icycream.common.tile.TileEntityRefrigerator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistryHandler {

    public static TileEntityType<TileEntityMixer> mixer;

    public static TileEntityType<TileEntityExtractor> extracter;

    public static TileEntityType<TileEntityMacerator> macerator;

    public static TileEntityType<TileEntityRefrigerator> refrigerator;



    /**
     * qnmd deprecation
     * 我就tmd要用，sb forge你妈死了
     * @param event
     */
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        Block block = registerBlocksWithItem(new BlockExtractor(Block.Properties.create(Material.IRON).hardnessAndResistance(6, 30)), IcyCream.MODID, "extractor");
        extracter = registerTile("extractor", block, TileEntityExtractor.class);
        block = registerBlocksWithItem(new BlockMixer(Block.Properties.create(Material.IRON).hardnessAndResistance(6, 30)), IcyCream.MODID, "mixer");
        mixer = registerTile("mixer", block, TileEntityMixer.class);;
        block = registerBlocksWithItem(new BlockRefridgerator(Block.Properties.create(Material.IRON).hardnessAndResistance(6, 30)), IcyCream.MODID, "refrigerator");
        refrigerator =  registerTile("refridgerator", block, TileEntityRefrigerator.class);
        block = registerBlocksWithItem(new BlockMacerator(Block.Properties.create(Material.IRON).hardnessAndResistance(6, 30)), IcyCream.MODID, "macerator");
        macerator = registerTile("macerator", block, TileEntityMacerator.class);
    }

    public static Block registerBlocksWithItem(Block block, String namespace, String name) {
        Registry.register(Registry.BLOCK, new ResourceLocation(namespace, name), block);
        BlockItem blockItem = new BlockItem(block, new Item.Properties().maxStackSize(64).group(ItemRegistryHandler.itemGroup));
        Registry.register(Registry.ITEM, new ResourceLocation(namespace, name), blockItem);
        return block;
    }

    public static <T extends TileEntity> TileEntityType<T> registerTile(String name, Block block, Class<T> tileClass) {
        TileEntityType<T> tileEntityType = TileEntityType.Builder.create(() -> {
            try {
                return tileClass.getConstructor().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }, block).build(null);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, "icycream:" + name, tileEntityType);
        return tileEntityType;
    }
}

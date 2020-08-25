package icycream.common.registry;

import icycream.IcyCream;
import icycream.common.block.BlockExtractor;
import icycream.common.block.BlockMacerator;
import icycream.common.block.BlockMixer;
import icycream.common.block.BlockRefrigerator;
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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.InvocationTargetException;

public class BlockRegistryHandler {

    public static TileEntityType<TileEntityMixer> mixer;

    public static TileEntityType<TileEntityExtractor> extracter;

    public static TileEntityType<TileEntityMacerator> macerator;

    public static TileEntityType<TileEntityRefrigerator> refrigerator;

    private static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, IcyCream.MODID);
    private static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, IcyCream.MODID);
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, IcyCream.MODID);

    public BlockRegistryHandler(IEventBus forgeEventBus) {
        registerBlocks();

        BLOCKS.register(forgeEventBus);
        ITEMS.register(forgeEventBus);
        TILE_ENTITIES.register(forgeEventBus);
    }

    public void registerBlocks() {
        Block block = registerBlocksWithItem(new BlockExtractor(Block.Properties.create(Material.IRON).hardnessAndResistance(6, 30)), "extractor");
        extracter = registerTile("extractor", block, TileEntityExtractor.class);
        block = registerBlocksWithItem(new BlockMixer(Block.Properties.create(Material.IRON).hardnessAndResistance(6, 30)),  "mixer");
        mixer = registerTile("mixer", block, TileEntityMixer.class);
        block = registerBlocksWithItem(new BlockRefrigerator(Block.Properties.create(Material.IRON).hardnessAndResistance(6, 30)), "refrigerator");
        refrigerator =  registerTile("refridgerator", block, TileEntityRefrigerator.class);
        block = registerBlocksWithItem(new BlockMacerator(Block.Properties.create(Material.IRON).hardnessAndResistance(6, 30)), "macerator");
        macerator = registerTile("macerator", block, TileEntityMacerator.class);
        registerBlocksWithItem(new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(6, 30)), "icecream_bucket");
    }

    public static Block registerBlocksWithItem(Block block, String name) {
        BLOCKS.register(name, () -> block);
        BlockItem blockItem = new BlockItem(block, new Item.Properties().maxStackSize(64).group(ItemRegistryHandler.itemGroup));
        ITEMS.register(name, () -> blockItem);
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
        TILE_ENTITIES.register(name, () -> tileEntityType);
        return tileEntityType;
    }
}

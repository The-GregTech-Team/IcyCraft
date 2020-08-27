package icycream.common;

import net.minecraft.block.Blocks;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import org.junit.Before;

import java.util.concurrent.Semaphore;


public class TestIngredientAndRecipe {

    @Before
    public void register() {
        Registry.FLUID.register(new ResourceLocation("minecraft", "water"), new WaterFluid.Source());
        Registry.FLUID.register(new ResourceLocation("minecraft", "lava"), new LavaFluid.Source());
        Registry.ITEM.register(new ResourceLocation("minecraft", "grass"), new BlockItem(Blocks.GRASS, new Item.Properties()));
    }
//    @Test
    /*
    public void testCrafting() {
        List<IIngredient> ingredients = Lists.newArrayList();
        ingredients.add(new IngredientItem("minecraft","sand",5));
        ingredients.add(new IngredientItem("minecraft","stone", 5));
        ingredients.add(new IngredientFluid("minecraft","water", 5));
        ItemStack resultItem = new ItemStack(Items.GRASS, 1);
        FluidStack resultFluid = new FluidStack(Fluids.LAVA, 50);
        MixerRecipe machineRecipe = new MixerRecipe(new ResourceLocation("icycream:test_recipe"), ingredients, Collections.emptyList(), resultItem, resultFluid, 100);
        Inventory inventory = new Inventory(3);
        FluidInventory fluidInventory = new FluidInventory(3, 8000);
        inventory.setInventorySlotContents(0, new ItemStack(Items.DIRT, 1));
        inventory.setInventorySlotContents(1, new ItemStack(Items.SAND, 5));
        inventory.setInventorySlotContents(2, new ItemStack(Items.STONE, 5));
        fluidInventory.addFluidAt(0, new FluidStack(Fluids.WATER, 10));
        Assert.assertTrue(machineRecipe.canAccept(inventory, fluidInventory));
        machineRecipe.consume(inventory, fluidInventory);
        Assert.assertEquals(inventory.getStackInSlot(0).getCount(), 1);
        Assert.assertEquals(inventory.getStackInSlot(1).getCount(), 0);
        Assert.assertEquals(inventory.getStackInSlot(2).getCount(), 0);
        Assert.assertEquals(fluidInventory.getFluidInTank(0).getAmount(), 5);

    }*/
}

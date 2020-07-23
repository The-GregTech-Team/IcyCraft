package icycream.common;

import com.google.common.collect.Lists;
import icycream.common.fluid.FluidInventory;
import icycream.common.recipes.IIngredient;
import icycream.common.recipes.IngredientFluid;
import icycream.common.recipes.IngredientItem;
import icycream.common.recipes.MachineRecipe;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fluids.FluidStack;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class TestIngredientAndRecipe {

    @Before
    public void register() {
        Registry.FLUID.register(new ResourceLocation("minecraft", "water"), new WaterFluid.Source());
        Registry.FLUID.register(new ResourceLocation("minecraft", "lava"), new LavaFluid.Source());
        Registry.ITEM.register(new ResourceLocation("minecraft", "grass"), new BlockItem(Blocks.GRASS, new Item.Properties()));
    }
    @Test
    public void testFluid() {
        IngredientFluid ingredientFluid = new IngredientFluid("minecraft", "water", 1000);
        CompoundNBT tag = new CompoundNBT();
        ingredientFluid.writeToNBT(tag);
        System.out.println(tag);
        IngredientFluid ingredientFluid1 = new IngredientFluid();
        ingredientFluid1.readFromNBT(tag);
        CompoundNBT tag1 = new CompoundNBT();
        ingredientFluid1.writeToNBT(tag1);
        System.out.println(tag1);
        System.out.println(tag);
        Assert.assertEquals(tag.toString(), tag1.toString());

    }

    @Test
    public void testItem() {
        IngredientItem ingredientItem = new IngredientItem("minecraft", "grass", 64);
        CompoundNBT tag = new CompoundNBT();
        ingredientItem.writeToNBT(tag);
        IngredientItem ingredientItem1 = new IngredientItem();
        ingredientItem1.readFromNBT(tag);
        CompoundNBT tag1 = new CompoundNBT();
        ingredientItem1.writeToNBT(tag1);
        System.out.println(tag1);
        System.out.println(tag);
        Assert.assertEquals(tag.toString(), tag1.toString());

    }
    @Test
    public void testRecipeSerialize() {
        List<IIngredient> ingredients = Lists.newArrayList();
        ingredients.add(new IngredientItem("minecraft","sand",5));
        ingredients.add(new IngredientItem("minecraft","stone", 5));
        ingredients.add(new IngredientFluid("minecraft","water", 5));
        ItemStack resultItem = new ItemStack(Items.GRASS, 1);
        FluidStack resultFluid = new FluidStack(Fluids.LAVA, 50);
        MachineRecipe machineRecipe = new MachineRecipe(ingredients, resultItem, resultFluid, 100);
        CompoundNBT nbt = new CompoundNBT();
        machineRecipe.writeToNBT(nbt);
        CompoundNBT nbt1 = new CompoundNBT();
        MachineRecipe machineRecipe1 = MachineRecipe.readFromNBT(nbt);
        machineRecipe1.writeToNBT(nbt1);
        System.out.println(nbt1);
        System.out.println(nbt);
        Assert.assertEquals(nbt.toString(), nbt1.toString());
    }

    @Test
    public void testCrafting() {
        List<IIngredient> ingredients = Lists.newArrayList();
        ingredients.add(new IngredientItem("minecraft","sand",5));
        ingredients.add(new IngredientItem("minecraft","stone", 5));
        ingredients.add(new IngredientFluid("minecraft","water", 5));
        ItemStack resultItem = new ItemStack(Items.GRASS, 1);
        FluidStack resultFluid = new FluidStack(Fluids.LAVA, 50);
        MachineRecipe machineRecipe = new MachineRecipe(ingredients, resultItem, resultFluid, 100);
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
        Assert.assertEquals(fluidInventory.getFluidStackAt(0).getAmount(), 5);

    }
}

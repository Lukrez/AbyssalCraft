/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityEnergyPedestal;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class BlockEnergyPedestal extends BlockContainer {

	public BlockEnergyPedestal() {
		super(Material.ROCK);
		setUnlocalizedName("energypedestal");
		setHardness(6.0F);
		setResistance(12.0F);
		setSoundType(SoundType.STONE);
		setCreativeTab(ACTabs.tabDecoration);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {

		return new TileEntityEnergyPedestal();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = player.getHeldItem(hand);
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null && tile instanceof TileEntityEnergyPedestal)
			if(!((TileEntityEnergyPedestal)tile).getItem().isEmpty()){
				player.inventory.addItemStackToInventory(((TileEntityEnergyPedestal)tile).getItem());
				((TileEntityEnergyPedestal)tile).setItem(ItemStack.EMPTY);
				world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1, false);
				return true;
			} else if(!heldItem.isEmpty()){
				ItemStack newItem = heldItem.copy();
				newItem.setCount(1);
				((TileEntityEnergyPedestal)tile).setItem(newItem);
				heldItem.shrink(1);
				world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1, false);
				return true;
			}
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		Random rand = new Random();
		TileEntityEnergyPedestal pedestal = (TileEntityEnergyPedestal) world.getTileEntity(pos);

		if(pedestal != null){
			if(!pedestal.getItem().isEmpty()){
				float f = rand.nextFloat() * 0.8F + 0.1F;
				float f1 = rand.nextFloat() * 0.8F + 0.1F;
				float f2 = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem item = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, pedestal.getItem());
				float f3 = 0.05F;
				item.motionX = (float)rand.nextGaussian() * f3;
				item.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				item.motionZ = (float)rand.nextGaussian() * f3;
				world.spawnEntity(item);
			}
			ItemStack stack = new ItemStack(getItemDropped(state, rand, 1), 1, damageDropped(state));
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setFloat("PotEnergy", pedestal.getContainedEnergy());
			float f = rand.nextFloat() * 0.8F + 0.1F;
			float f1 = rand.nextFloat() * 0.8F + 0.1F;
			float f2 = rand.nextFloat() * 0.8F + 0.1F;

			EntityItem item = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, stack);
			float f3 = 0.05F;
			item.motionX = (float)rand.nextGaussian() * f3;
			item.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
			item.motionZ = (float)rand.nextGaussian() * f3;
			world.spawnEntity(item);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("PotEnergy")){
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile != null && tile instanceof TileEntityEnergyPedestal)
				((TileEntityEnergyPedestal)tile).addEnergy(stack.getTagCompound().getFloat("PotEnergy"));
		}
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return new java.util.ArrayList<ItemStack>();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
}
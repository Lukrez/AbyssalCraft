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
package com.shinoow.abyssalcraft.common.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemACHoe extends ItemHoe {

	private TextFormatting format;

	public ItemACHoe(ToolMaterial mat, String name){
		this(mat, name, null);
	}

	public ItemACHoe(ToolMaterial mat, String name, TextFormatting format) {
		super(mat);
		setCreativeTab(ACTabs.tabTools);
		setUnlocalizedName(name);
		this.format = format;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return format != null ? format + super.getItemStackDisplayName(par1ItemStack) : super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	@SuppressWarnings("incomplete-switch")
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = playerIn.getHeldItem(hand);
		if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack))
			return EnumActionResult.FAIL;
		else
		{
			int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, playerIn, worldIn, pos);
			if (hook != 0) return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;

			IBlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();

			if (facing != EnumFacing.DOWN && worldIn.isAirBlock(pos.up()))
			{
				if (iblockstate.getMaterial() == Material.GRASS || block == Blocks.GRASS_PATH)
				{
					setBlock(stack, playerIn, worldIn, pos, Blocks.FARMLAND.getDefaultState());
					return EnumActionResult.SUCCESS;
				}

				if (block == Blocks.DIRT)
					switch (iblockstate.getValue(BlockDirt.VARIANT))
					{
					case DIRT:
						setBlock(stack, playerIn, worldIn, pos, Blocks.FARMLAND.getDefaultState());
						return EnumActionResult.SUCCESS;
					case COARSE_DIRT:
						setBlock(stack, playerIn, worldIn, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
						return EnumActionResult.SUCCESS;
					}
			}

			return EnumActionResult.PASS;
		}
	}
}
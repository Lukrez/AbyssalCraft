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
package com.shinoow.abyssalcraft.integration.jei.transmutator;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

import com.shinoow.abyssalcraft.integration.jei.AbyssalCraftRecipeCategoryUid;

public class TransmutationCategory extends TransmutatorRecipeCategory {
	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final String localizedName;

	public TransmutationCategory(IGuiHelper guiHelper) {
		super(guiHelper);
		ResourceLocation location = new ResourceLocation("abyssalcraft", "textures/gui/container/transmutator_NEI.png");
		background = guiHelper.createDrawable(location, 55, 16, 82, 54);
		localizedName = I18n.translateToLocal("container.abyssalcraft.transmutator.nei");
	}

	@Override
	@Nonnull
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		flame.draw(minecraft, 2, 20);
		arrow.draw(minecraft, 24, 18);
	}

	@Nonnull
	@Override
	public String getTitle() {
		return localizedName;
	}

	@Nonnull
	@Override
	public String getUid() {
		return AbyssalCraftRecipeCategoryUid.TRANSMUTATION;
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(inputSlot, true, 0, 0);
		guiItemStacks.init(outputSlot, false, 60, 18);

		guiItemStacks.set(ingredients);

		//		guiItemStacks.setFromRecipe(inputSlot, recipeWrapper.getInputs());
		//		guiItemStacks.setFromRecipe(outputSlot, recipeWrapper.getOutputs());
	}

	@Override
	public IDrawable getIcon() {
		// TODO Auto-generated method stub
		return null;
	}
}

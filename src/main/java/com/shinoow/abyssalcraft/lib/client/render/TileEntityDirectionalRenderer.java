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
package com.shinoow.abyssalcraft.lib.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.lib.tileentity.TEDirectional;

/**
 * TESR for directional TEs ({@link TEDirectional } implementations).<br>
 * Handles rotations applied to the model.
 * @author shinoow
 *
 */
@SideOnly(Side.CLIENT)
public class TileEntityDirectionalRenderer extends TileEntitySpecialRenderer {

	final ModelBase model;
	private final ResourceLocation texture;

	public TileEntityDirectionalRenderer(ModelBase model, String res){
		this.model = model;
		texture = new ResourceLocation(res);
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glPushMatrix();
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		TEDirectional tile = (TEDirectional) te;
		int direction = tile.getDirection();
		GL11.glRotatef(direction * 90, 0.0F, 1.0F, 0.0F);
		model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}

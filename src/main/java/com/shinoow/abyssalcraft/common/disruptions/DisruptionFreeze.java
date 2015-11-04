/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.disruptions;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;

public class DisruptionFreeze extends DisruptionEntry {

	public DisruptionFreeze() {
		super("freeze", null);
	}

	@Override
	public void disrupt(World world, int x, int y, int z, List<EntityPlayer> players) {
		if(!players.isEmpty())
			for(EntityPlayer player : players)
				player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 14));
	}
}

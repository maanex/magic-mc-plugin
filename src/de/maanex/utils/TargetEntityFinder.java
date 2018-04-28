package de.maanex.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;


public class TargetEntityFinder {

	private TargetEntityFinder() {
	}

	public static Entity find(Block b) {
		int i = 0;
		while (i++ < 10) {
			List<Entity> entities = b.getWorld().getNearbyEntities(b.getLocation(), i, i, i).stream().collect(Collectors.toList());
			if (entities.isEmpty()) continue;
			Entity tar = null;
			for (Entity e : new ArrayList<>(entities)) {
				if (e instanceof Player) {
					tar = e;
					entities.clear();
				}
			}
			while ((tar == null || !(tar instanceof LivingEntity)) && !entities.isEmpty()) {
				entities.remove(tar);
				if (!entities.isEmpty()) tar = entities.get(new Random().nextInt(entities.size()));
			}
			if (tar == null || !(tar instanceof LivingEntity)) continue;

			return tar;
		}
		return null;
	}

}

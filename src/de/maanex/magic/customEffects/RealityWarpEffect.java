package de.maanex.magic.customEffects;


import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WeatherType;

import de.maanex.magic.MagicPlayer;


public class RealityWarpEffect extends MagicEffect {

	public int strength;

	public RealityWarpEffect(int duration, int strength) {
		super(duration);
		this.strength = strength;
	}

	@Override
	public void tick(MagicPlayer m) {
		Random r = new Random();
		for (int x = -10; x < 10; x++) {
			for (int y = -10; y < 10; y++) {
				for (int z = -10; z < 10; z++) {
					if (r.nextInt(10) != 0) continue;
					if (Math.abs(x) < 3 && Math.abs(y) < 3 && Math.abs(z) < 3) continue;
					Location l = m.getMCPlayer().getLocation().clone().add(x, y, z);
					if (l.getBlock().getType().equals(Material.AIR)) continue;
					// Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
					// try {
					// int blockid = 0;
					// if (stenght == 1) blockid = l.getBlock().getTypeId();
					// if (stenght == 2) blockid = Math.max(1, l.getBlock().getTypeId() - 1 +
					// r.nextInt(3));
					// if (stenght == 3) blockid = Math.max(1, l.getBlock().getTypeId() - 2 +
					// r.nextInt(5));
					// m.getMCPlayer().sendBlockChange(l, blockid, (byte) r.nextInt(15));
					// } catch (Exception e) {}
					// }, r.nextInt(40));
				}
			}
		}

		if (strength > 1 && r.nextInt(3) == 0) m.getMCPlayer().setPlayerTime(r.nextLong(), true);

		if (strength > 2 && r.nextInt(5) == 0) m.getMCPlayer().setPlayerWeather(WeatherType.values()[r.nextInt(WeatherType.values().length)]);
	}
}

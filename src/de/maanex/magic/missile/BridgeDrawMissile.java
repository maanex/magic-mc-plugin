package de.maanex.magic.missile;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.main.Main;


public class BridgeDrawMissile extends MagicMissile {

	private Location	target;
	private Material	block;
	private byte		data;

	public BridgeDrawMissile(Location startPos, MagicPlayer sender, Location target, Material block, byte data) {
		super(startPos, sender);
		this.target = target;
		this.block = block;
		this.data = data;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		position.add(target.clone().subtract(position).toVector().normalize());

		if (!position.getBlock().getType().isSolid() || position.getBlock().isLiquid()) for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendBlockChange(position, block, (byte) (data + new Random().nextInt(3) - 1));
			Location pos = position.clone();
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
				p.sendBlockChange(pos, pos.getBlock().getType(), pos.getBlock().getData());
			}, 20 * 30);
		}

		if (position.distance(target) <= 1.1) destroy();
	}

	@Override
	public void magicRedirect(Vector vector) {
		// Ignore
	}

}

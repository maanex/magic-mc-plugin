package de.maanex.magic.missile;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.main.Main;


public class EarthPotterMissile extends MagicMissile {

	private Player		target;
	private int			range;
	private Material	block;
	private byte		data;
	private int			life;

	public EarthPotterMissile(Location startPos, MagicPlayer sender, Player target, int range, Material block, byte data, int life) {
		super(startPos, sender);
		this.target = target;
		this.range = range;
		this.block = block;
		this.data = data;
		this.life = life;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		if (target.isSneaking()) range++;

		Random r = new Random();
		position.add(target.getEyeLocation().clone().add(target.getLocation().getDirection().multiply(range)).add(r.nextInt(7) - 3, r.nextInt(7) - 3, r.nextInt(7) - 3).subtract(position).toVector()
				.normalize().multiply(2));

		if (!position.getBlock().getType().isSolid() || position.getBlock().isLiquid()) for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendBlockChange(position, block, (byte) (data + new Random().nextInt(3) - 1));
			Location pos = position.clone();
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
				p.sendBlockChange(pos, pos.getBlock().getType(), pos.getBlock().getData());
			}, 20 * 30);
		}

		if (life-- <= 0) destroy();
	}

	@Override
	public void magicRedirect(Vector vector) {
		position.setDirection(vector);
	}

}

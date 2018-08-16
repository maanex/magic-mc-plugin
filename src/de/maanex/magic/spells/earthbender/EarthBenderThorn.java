package de.maanex.magic.spells.earthbender;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.main.Main;
import de.maanex.utils.BlockUtil;


public class EarthBenderThorn extends MagicSpell {

	public EarthBenderThorn() {
		super(51, "Erdbendigung - Stachel", "uiuiuiuiuiui", 3, 4, SpellType.ACTIVE, SpellCategory.BENDER, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		Location l = caster.getMCPlayer().getLocation().clone().add(caster.getMCPlayer().getLocation().getDirection().multiply(caster.getMCPlayer().isSneaking() ? 15 : 5));
		int c = 5;
		while (c-- > 0 && !l.getBlock().getType().isSolid()) {
			l = l.subtract(0, 1, 0);
		}
		Block b = l.getBlock();
		if (!b.getType().isSolid()) return;

		Vector v = caster.getMCPlayer().getLocation().toVector().subtract(l.toVector()).normalize();
		generateRock(new Random(), l, (int) (v.getX() * 2), (int) (v.getZ() * 2));

		takeMana(caster, mods);
	}

	private void generateRock(Random r, Location l, int xr, int zr) {
		List<Material> blocks = new ArrayList<>();

		for (int x = -5; x < 5; x++)
			for (int z = -5; z < 5; z++)
				for (int y = -3; y < 2; y++) {
					Location q = l.clone().add(x, y, z);
					if (q.getBlock().getType().isSolid()) blocks.add(q.getBlock().getType());
				}

		int h = r.nextInt(6) + 6;
		int cH = h + 1, ra = 0;
		l.add(0, cH, 0);
		while (cH-- > 0) {
			ra = (int) Math.pow(h - cH + 2, 2) / ((h - cH + 1) * 3);
			int rad = Math.max(1, ra), che = cH;

			l.add(cH % 2 == 0 ? xr : 0, -1, cH % 2 == 1 ? zr : 0);

			for (Entity e : l.getWorld().getNearbyEntities(l, rad, 1, rad))
				e.setVelocity(new Vector(-2 * xr, 2, -2 * zr));

			BlockUtil.makeCylinder(l, 1, ra).forEach(o -> {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
					l.getWorld().getPlayers().forEach(p -> p.sendBlockChange(o, blocks.get(r.nextInt(blocks.size())).createBlockData()));
				}, che + rad / 2);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
					l.getWorld().getPlayers().forEach(p -> p.sendBlockChange(o, o.getBlock().getBlockData()));
				}, h - che + r.nextInt(10) + 40);
			});
		}
	}

}

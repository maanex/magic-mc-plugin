package de.maanex.magic.spells;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.main.Main;


public class ProtectionWall extends MagicSpell {

	public ProtectionWall() {
		super(3, "Schutzwand", "Schützt dich! (TÜV-Geprüft)", 3, 4, SpellType.ACTIVE, SpellCategory.PROTECTION, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Player p = caster.getMCPlayer();

		double dis = 3.5;

		for (int i = -4; i < 4; i++)
			for (int y = -7; y < 7; y++)
				place(p.getEyeLocation().clone().add(mod(p.getLocation(), i * 8, y * 10).getDirection().normalize().multiply(dis + Math.abs(y) / 4)), Math.abs(y) + Math.abs(i));

		takeMana(caster, mods);
	}

	private Location mod(Location l, int pitch, int yaw) {
		l = l.clone();
		l.setPitch(l.getPitch() + pitch);
		l.setYaw(l.getYaw() + yaw);
		return l;
	}

	private void place(Location l, int del) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
			if (l.getBlock().isEmpty()) {
				l.getBlock().setType(Material.GLASS);

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> l.getBlock().breakNaturally(), 80 + del);
			}
		}, del);
	}

}

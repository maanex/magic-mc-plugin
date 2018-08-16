package de.maanex.magic.spells;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.main.Main;


public class ProtectionBall extends MagicSpell {

	public ProtectionBall() {
		super(64, "Schutzball", "Schützt dich noch besser!\n(Leider noch nicht TÜV-Geprüft)", 3, 4, SpellType.ACTIVE, SpellCategory.PROTECTION, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		Player p = caster.getMCPlayer();

		double dis = 2.5;

		for (int i = -17; i < 17; i++)
			for (int y = -19; y < 19; y++)
				place(p.getEyeLocation().clone().add(mod(p.getLocation(), i * 8, y * 10).getDirection().normalize().multiply(dis + Math.abs(y) / 10)), (Math.abs(y) + Math.abs(i)) / 3);

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
				l.getBlock().setType(Material.LIGHT_BLUE_STAINED_GLASS);

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> l.getBlock().breakNaturally(), 120 + del);
			}
		}, del);
	}

}

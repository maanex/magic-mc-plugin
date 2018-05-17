package de.maanex.magic.spells.lightmagic;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.main.Main;


public class TrueSight extends MagicSpell {

	public TrueSight() {
		super(12, "Wahre Einsicht", "Sieh an, sieh an!", 1, 5, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.GODLIKE, WandType.LIGHT);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block target = caster.getMCPlayer().getTargetBlock(null, 20 + mods.getEnergy() - 100);
		if (target.getType().equals(Material.AIR)) return;

		for (int x = -2; x < 2; x++) {
			for (int y = -2; y < 2; y++) {
				for (int z = -2; z < 2; z++) {
					Location l = target.getLocation().clone().add(x, y, z);
					if (l.getBlock().getType().equals(Material.AIR)) continue;
					caster.getMCPlayer().sendBlockChange(l, Material.GLASS, (byte) 0);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
						caster.getMCPlayer().sendBlockChange(l, l.getBlock().getType(), l.getBlock().getData());
					}, mods.getEnergy() + new Random().nextInt(40));
				}
			}
		}

		takeMana(caster, mods);
	}

}

package de.maanex.magic.spells.lightmagic;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.magic.wands.WandValues.WandModifier;
import de.maanex.main.Main;


public class TrueSight extends MagicSpell {

	public TrueSight() {
		super(12, "Wahre Einsicht", "Sieh an, sieh an!", 1, 5, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.GODLIKE, WandType.LIGHT);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		Block target = caster.getMCPlayer().getTargetBlock(null, 20 + val.getMod(WandModifier.ENERGY) - 100);
		if (target.getType().equals(Material.AIR)) return;

		for (int x = -2; x < 2; x++) {
			for (int y = -2; y < 2; y++) {
				for (int z = -2; z < 2; z++) {
					Location l = target.getLocation().clone().add(x, y, z);
					if (l.getBlock().getType().equals(Material.AIR)) continue;
					caster.getMCPlayer().sendBlockChange(l, Material.GLASS, (byte) 0);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
						caster.getMCPlayer().sendBlockChange(l, l.getBlock().getType(), l.getBlock().getData());
					}, val.getMod(WandModifier.ENERGY) + new Random().nextInt(40));
				}
			}
		}

		takeMana(caster, val);
	}

}

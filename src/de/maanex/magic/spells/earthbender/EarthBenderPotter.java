package de.maanex.magic.spells.earthbender;


import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.missile.EarthPotterMissile;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;


public class EarthBenderPotter extends MagicSpell {

	public EarthBenderPotter() {
		super(52, "Erdbendigung - Töpfern", "...und hier noch n' bisschen...", 3, 4, SpellType.ACTIVE, SpellCategory.BENDER, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		Block b = caster.getMCPlayer().getTargetBlock(null, 50 + mods.getEnergy() - 100);
		if (b.getType().equals(Material.AIR)) return;

		Random r = new Random();
		for (int x = -5; x < 5; x++)
			for (int y = -5; y < 5; y++)
				for (int z = -5; z < 5; z++) {
					if (r.nextInt(2) != 0) continue;

					Location l = b.getLocation().clone().add(x, y, z).add(b.getLocation().clone().add(caster.getMCPlayer().getEyeLocation()).toVector().normalize().multiply(r.nextInt(3) + 1));

					if (!l.getBlock().getType().isSolid()) continue;

					EarthPotterMissile m = new EarthPotterMissile(l, caster, caster.getMCPlayer(), (int) l.distance(caster.getMCPlayer().getEyeLocation()), l.getBlock().getType(),
							l.getBlock().getData(), 30);
					m.launch();
				}

		takeMana(caster, mods);
	}

}

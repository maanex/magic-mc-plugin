package de.maanex.magic.spells.building;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.basic.Element;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;


public class Digger extends MagicSpell {

	public Digger() {
		super(75, "Bagger", "Brum brum brum brum!", 3, 0, SpellType.ACTIVE, SpellCategory.BUILDING, SpellRarity.RARE, "Reichweite :earth:");
	}

	private static List<Material> breakable;

	private static Random ra;

	static {
		ra = new Random();

		breakable = new ArrayList<>();

		breakable.add(Material.GRASS_BLOCK);
		breakable.add(Material.DIRT);
		breakable.add(Material.SAND);
		breakable.add(Material.GRAVEL);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		if (val.getElement(Element.ESSENCE_BUILDER) <= 0) return;

		Block target = caster.getMCPlayer().getTargetBlock(null, Math.max(1, val.getElement(Element.EARTH) / 2));

		for (int x = -r(); x <= r(); x++)
			for (int z = -r(); z <= r(); z++)
				for (int y = -r(); y <= r(); y++) {
					if (ra.nextInt(r() + 1) < Math.min(Math.min(Math.abs(x), Math.abs(y)), Math.abs(z))) continue;
					Location l = target.getLocation().clone().add(x, y, z);
					if (breakable.contains(l.getBlock().getType())) l.getBlock().breakNaturally();
				}

		takeMana(caster, val);
	}

	private int r() {
		return 1 + Math.min(Math.min(ra.nextInt(3), ra.nextInt(3)), ra.nextInt(3));
	}

}

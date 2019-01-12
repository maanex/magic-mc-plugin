package de.maanex.magic.spells.building;


import java.util.ArrayList;
import java.util.List;

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


public class Drill extends MagicSpell {

	public Drill() {
		super(47, "Bohrer", "Brum brum!", 3, 0, SpellType.ACTIVE, SpellCategory.BUILDING, SpellRarity.RARE, "Reichweite :earth:");
	}

	private static List<Material> breakable;

	static {
		breakable = new ArrayList<>();

		breakable.add(Material.STONE);
		breakable.add(Material.DIRT);
		breakable.add(Material.COBBLESTONE);
		breakable.add(Material.ANDESITE);
		breakable.add(Material.DIORITE);
		breakable.add(Material.GRANITE);
		breakable.add(Material.GRAVEL);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		if (val.getElement(Element.ESSENCE_BUILDER) <= 0) return;

		Block target = caster.getMCPlayer().getTargetBlock(null, Math.max(1, val.getElement(Element.EARTH)));

		boolean face = Math.abs(target.getX() - caster.getMCPlayer().getLocation().getX()) < Math.abs(target.getZ() - caster.getMCPlayer().getLocation().getZ());

		for (int i = -1; i < 2; i++)
			for (int y = -1; y < 2; y++) {
				Location l = target.getLocation().clone().add(face ? i : 0, y, face ? 0 : i);
				if (breakable.contains(l.getBlock().getType())) l.getBlock().breakNaturally();
			}

		takeMana(caster, val);
	}

}

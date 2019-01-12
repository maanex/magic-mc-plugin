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


public class DeGrasser extends MagicSpell {

	public DeGrasser() {
		super(42, "Rasenmäher", "Ein Super Entgrasungswerkzeug!", 1, 5, SpellType.ACTIVE, SpellCategory.BUILDING, SpellRarity.COMMON, "Reichweite :fire:", "Größe :builder:");
	}

	private static List<Material> grass;

	static {
		grass = new ArrayList<>();

		grass.add(Material.TALL_GRASS);
		grass.add(Material.CHORUS_FLOWER);
		grass.add(Material.CHORUS_FLOWER);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		if (val.getElement(Element.ESSENCE_BUILDER) <= 0) return;

		Block target = caster.getMCPlayer().getTargetBlock(null, 1 + val.getElement(Element.FIRE) * 2);

		int ra = val.getElement(Element.ESSENCE_BUILDER);

		for (int x = -ra; x < ra; x++)
			for (int y = -ra / 2; y < ra / 2; y++)
				for (int z = -ra; z < ra; z++) {
					Location l = target.getLocation().clone().add(x, y, z);
					if (grass.contains(l.getBlock().getType())) l.getBlock().breakNaturally();
				}
		takeMana(caster, val);
	}

}

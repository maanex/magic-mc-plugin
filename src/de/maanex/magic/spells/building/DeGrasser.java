package de.maanex.magic.spells.building;


import java.util.ArrayList;
import java.util.List;

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


public class DeGrasser extends MagicSpell {

	public DeGrasser() {
		super(42, "Rasenmäher", "Ein Super Entgrasungswerkzeug!", 1, 5, SpellType.ACTIVE, SpellCategory.BUILDING, SpellRarity.COMMON);
	}

	private static List<Material> grass;

	static {
		grass = new ArrayList<>();

		grass.add(Material.LONG_GRASS);
		grass.add(Material.CHORUS_FLOWER);
		grass.add(Material.YELLOW_FLOWER);
		grass.add(Material.DOUBLE_PLANT);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block target = caster.getMCPlayer().getTargetBlock(null, mods.getEnergy() / 4);

		for (int x = -10; x < 10; x++)
			for (int y = -5; y < 5; y++)
				for (int z = -10; z < 10; z++) {
					Location l = target.getLocation().clone().add(x, y, z);
					if (grass.contains(l.getBlock().getType())) l.getBlock().breakNaturally();
				}
		takeMana(caster, mods);
	}

}

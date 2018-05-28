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


public class TreeDemolisher extends MagicSpell {

	public TreeDemolisher() {
		super(43, "Baumzerschmetterer", "Nerviger Baum? Weg damit!", 3, 4, SpellType.ACTIVE, SpellCategory.BUILDING, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block target = caster.getMCPlayer().getTargetBlock(null, mods.getEnergy() / 4);
		checkAndBreakBlock(target.getLocation(), 0);

		takeMana(caster, mods);
	}

	private static List<Material> remove;

	static {
		remove = new ArrayList<>();

		remove.add(Material.LOG);
		remove.add(Material.LOG_2);
		remove.add(Material.LEAVES);
		remove.add(Material.LEAVES_2);
	}

	private void checkAndBreakBlock(Location loc, int counter) {
		if (remove.contains(loc.getBlock().getType()) && counter < 20) {
			loc.getBlock().breakNaturally();

			checkAndBreakBlock(loc.clone().add(0, 1, 0), counter + 1);
			checkAndBreakBlock(loc.clone().add(0, -1, 0), counter + 1);
			checkAndBreakBlock(loc.clone().add(-1, 0, 0), counter + 1);
			checkAndBreakBlock(loc.clone().add(1, 0, 0), counter + 1);
			checkAndBreakBlock(loc.clone().add(0, 0, -1), counter + 1);
			checkAndBreakBlock(loc.clone().add(0, 0, 1), counter + 1);
		}
	}

}

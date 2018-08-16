package de.maanex.magic.spells.building;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;


public class TreeDemolisher extends MagicSpell {

	public TreeDemolisher() {
		super(43, "Baumzerschmetterer", "Nerviger Baum? Weg damit!", 3, 4, SpellType.ACTIVE, SpellCategory.BUILDING, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		Block target = caster.getMCPlayer().getTargetBlock(null, mods.getEnergy() / 4);
		checkAndBreakBlock(target.getLocation(), 0, true);

		takeMana(caster, mods);
	}

	private static List<Material>	logs;
	private static List<Material>	leaves;

	static {
		logs = new ArrayList<>();
		leaves = new ArrayList<>();

		logs.add(Material.OAK_LOG);
		logs.add(Material.SPRUCE_LOG);
		logs.add(Material.BIRCH_LOG);
		logs.add(Material.JUNGLE_LOG);
		logs.add(Material.DARK_OAK_LOG);
		logs.add(Material.ACACIA_LOG);

		leaves.add(Material.OAK_LEAVES);
		leaves.add(Material.SPRUCE_LEAVES);
		leaves.add(Material.BIRCH_LEAVES);
		leaves.add(Material.JUNGLE_LEAVES);
		leaves.add(Material.DARK_OAK_LEAVES);
		leaves.add(Material.ACACIA_LEAVES);
	}

	private void checkAndBreakBlock(Location loc, int counter, boolean log) {
		if (((logs.contains(loc.getBlock().getType()) && log && counter < 20) || (leaves.contains(loc.getBlock().getType()) && counter < 25))) {
			boolean logn = logs.contains(loc.getBlock().getType());
			loc.getBlock().breakNaturally();

			checkAndBreakBlock(loc.clone().add(0, 1, 0), counter + 1, logn);
			checkAndBreakBlock(loc.clone().add(0, -1, 0), counter + 1, logn);
			checkAndBreakBlock(loc.clone().add(-1, 0, 0), counter + 1, logn);
			checkAndBreakBlock(loc.clone().add(1, 0, 0), counter + 1, logn);
			checkAndBreakBlock(loc.clone().add(0, 0, -1), counter + 1, logn);
			checkAndBreakBlock(loc.clone().add(0, 0, 1), counter + 1, logn);

			if (logn) {
				checkAndBreakBlock(loc.clone().add(1, 1, 1), counter + 1, logn);
				checkAndBreakBlock(loc.clone().add(1, 1, -1), counter + 1, logn);
				checkAndBreakBlock(loc.clone().add(-1, 1, -1), counter + 1, logn);
				checkAndBreakBlock(loc.clone().add(-1, 1, 1), counter + 1, logn);
				checkAndBreakBlock(loc.clone().add(1, -1, 1), counter + 1, logn);
				checkAndBreakBlock(loc.clone().add(1, -1, -1), counter + 1, logn);
				checkAndBreakBlock(loc.clone().add(-1, -1, -1), counter + 1, logn);
				checkAndBreakBlock(loc.clone().add(-1, -1, 1), counter + 1, logn);

				checkAndBreakBlock(loc.clone().add(1, 1, 0), counter + 1, logn);
				checkAndBreakBlock(loc.clone().add(1, -1, 0), counter + 1, logn);

				checkAndBreakBlock(loc.clone().add(-1, 1, 0), counter + 1, logn);
				checkAndBreakBlock(loc.clone().add(-1, -1, 0), counter + 1, logn);

				checkAndBreakBlock(loc.clone().add(0, 1, -1), counter + 1, logn);
				checkAndBreakBlock(loc.clone().add(0, -1, -1), counter + 1, logn);

				checkAndBreakBlock(loc.clone().add(0, 1, 1), counter + 1, logn);
				checkAndBreakBlock(loc.clone().add(0, -1, 1), counter + 1, logn);
			}
		}
	}

}

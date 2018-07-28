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


public class Drill extends MagicSpell {

	public Drill() {
		super(47, "Bohrer", "Brum brum!", 3, 0, SpellType.ACTIVE, SpellCategory.BUILDING, SpellRarity.RARE);
	}

	private static List<Material> breakable;

	static {
		breakable = new ArrayList<>();

		breakable.add(Material.STONE);
		breakable.add(Material.COBBLESTONE);
		breakable.add(Material.ANDESITE);
		breakable.add(Material.DIORITE);
		breakable.add(Material.GRANITE);
		breakable.add(Material.GRAVEL);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block target = caster.getMCPlayer().getTargetBlock(null, Math.max(1, 5 + mods.getEnergy() / 4 - 25));

		boolean face = Math.abs(target.getX() - caster.getMCPlayer().getLocation().getX()) < Math.abs(target.getZ() - caster.getMCPlayer().getLocation().getZ());

		for (int i = -1; i < 2; i++)
			for (int y = -1; y < 2; y++) {
				Location l = target.getLocation().clone().add(face ? i : 0, y, face ? 0 : i);
				System.out.println(l.getBlock().getType() + " + " + breakable.contains(l.getBlock().getType()));
				if (breakable.contains(l.getBlock().getType())) l.getBlock().breakNaturally();
			}

		takeMana(caster, mods);
	}

}

package de.maanex.magic.spells;


import org.bukkit.Material;
import org.bukkit.block.Block;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;


public class Strike extends MagicSpell {

	public Strike() {
		super(1, "Strike", "Ein Blitz!", 3, 3, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.COMMON);
	}

	@Override
	public void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block target = caster.getMCPlayer().getTargetBlock(null, 20 + mods.getEnergy() - 100);
		if (target.getType().equals(Material.AIR)) return;
		target.getLocation().getWorld().strikeLightning(target.getLocation());
		takeMana(caster, mods);
	}

}

package de.maanex.magic.spells;


import org.bukkit.Material;
import org.bukkit.block.Block;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;


public class Strike extends MagicSpell {

	public Strike() {
		super(1, "Strike", "Ein Blitz!", 3, 3, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.COMMON);
	}

	@Override
	public void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		Block target = caster.getMCPlayer().getTargetBlock(null, 20 + mods.getEnergy() - 100);
		if (target.getType().equals(Material.AIR)) return;
		target.getLocation().getWorld().strikeLightning(target.getLocation());
		takeMana(caster, mods);
	}

}

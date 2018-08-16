package de.maanex.magic.spells;


import org.bukkit.Location;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.missile.SlimeshotMissile;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;


public class Slimeshot extends MagicSpell {

	public Slimeshot() {
		super(55, "Slimeschleuder", "Schlonz!", 2, 4, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		Location lo = caster.getMCPlayer().getLocation().clone();
		SlimeshotMissile m = new SlimeshotMissile(caster.getMCPlayer().getEyeLocation(), caster, lo, .7);
		m.launch();
		takeMana(caster, mods);
	}

}

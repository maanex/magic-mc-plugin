package de.maanex.magic.spells;


import org.bukkit.Location;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.magic.missile.SlimeshotMissile;


public class Slimeshot extends MagicSpell {

	public Slimeshot() {
		super(55, "Slimeschleuder", "Schlonz!", 2, 4, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Location lo = caster.getMCPlayer().getLocation().clone();
		SlimeshotMissile m = new SlimeshotMissile(caster.getMCPlayer().getEyeLocation(), caster, lo, .7);
		m.launch();
		takeMana(caster, mods);
	}

}

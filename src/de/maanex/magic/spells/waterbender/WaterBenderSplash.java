package de.maanex.magic.spells.waterbender;


import org.bukkit.Location;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.magic.missile.WaterSplashMissile;


public class WaterBenderSplash extends MagicSpell {

	public WaterBenderSplash() {
		super(22, "Wasserbendigung - Platscher", "Eine GIGANTISCHE Welle...", 5, 5, SpellType.ACTIVE, SpellCategory.BENDER, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		int mis = 10;
		while (mis-- > 0) {
			Location dir = caster.getMCPlayer().getLocation().clone();
			dir.setDirection(dir.getDirection().add(Vector.getRandom().multiply(.4)));
			dir.setDirection(dir.getDirection().subtract(Vector.getRandom().multiply(.4)));
			new WaterSplashMissile(caster.getMCPlayer().getEyeLocation(), caster, dir, mods.getEnergy()).launch();
		}
		takeMana(caster, mods);

	}
}

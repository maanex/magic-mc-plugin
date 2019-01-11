package de.maanex.magic.spells.waterbender;


import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.missile.WaterSplashMissile;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.magic.wands.WandValues.WandModifier;


public class WaterBenderSplash extends MagicSpell {

	public WaterBenderSplash() {
		super(22, "Wasserbendigung - Platscher", "Eine GIGANTISCHE Welle...", 5, 5, SpellType.ACTIVE, SpellCategory.BENDER, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		int mis = 40;
		Location center = caster.getMCPlayer().getLocation().clone();
		while (mis-- > 0) {
			// Location dir = caster.getMCPlayer().getLocation().clone();
			// dir.setDirection(dir.getDirection().add(Vector.getRandom().multiply(.4)));
			// dir.setDirection(dir.getDirection().subtract(Vector.getRandom().multiply(.4)));
			Location loc = caster.getMCPlayer().getTargetBlock(null, val.getMod(WandModifier.ENERGY) / 10).getLocation().clone().add(r(), r(), r());
			if (loc.getBlock().getType().equals(Material.WATER)) new WaterSplashMissile(loc, caster, val.getMod(WandModifier.ENERGY), center).launch();
		}
		takeMana(caster, val);

	}

	private int r() {
		return new Random().nextInt(10) - 5;
	}
}

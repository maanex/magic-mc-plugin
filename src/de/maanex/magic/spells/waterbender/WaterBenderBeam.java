package de.maanex.magic.spells.waterbender;


import java.util.Random;

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
import de.maanex.magic.missile.WaterBeamMissile;


public class WaterBenderBeam extends MagicSpell {

	public WaterBenderBeam() {
		super(73, "Wasserbendigung - Strahl", "Ja nimmt sich halt Wasser und schießt es raus... joa", 3, 5, SpellType.ACTIVE, SpellCategory.BENDER, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		double mis = 40;
		Block target = caster.getMCPlayer().getTargetBlock(null, 60 + mods.getEnergy() - 100);

		while (mis-- > 0) {
			Location loc = caster.getMCPlayer().getLocation().clone().add(r(), r(), r());
			if (loc.getBlock().getType().equals(Material.WATER)) new WaterBeamMissile(loc, caster, mods.getEnergy(), target.getLocation()).launch();
			else mis += .3;
		}
		takeMana(caster, mods);

	}

	private int r() {
		return (new Random().nextInt(5) + 5) * (new Random().nextBoolean() ? 1 : -1);
	}
}

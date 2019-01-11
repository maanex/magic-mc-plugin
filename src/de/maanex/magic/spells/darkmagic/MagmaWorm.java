package de.maanex.magic.spells.darkmagic;


import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.missile.MagmaMissile;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.utils.ParticleUtil;
import de.maanex.utils.TargetEntityFinder;


public class MagmaWorm extends MagicSpell {

	public MagmaWorm() {
		super(15, "Magma Wurm", "Igitt!", 16, 600, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.FORBIDDEN, WandType.DARK);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		Block b = caster.getMCPlayer().getTargetBlock(null, 200);

		Entity tar = TargetEntityFinder.find(b);
		if (tar == null) return;

		Location loc = tar.getLocation().clone().add(0, 1, 0);
		int points = 3;
		for (int iteration = 0; iteration < points; iteration++) {
			double angle = 360.0 / points * iteration;
			double nextAngle = 360.0 / points * (iteration + 1);
			angle = Math.toRadians(angle);
			nextAngle = Math.toRadians(nextAngle);
			double x = Math.cos(angle) * 2;
			double z = Math.sin(angle) * 2;
			double x2 = Math.cos(nextAngle) * 2;
			double z2 = Math.sin(nextAngle) * 2;
			double deltaX = x2 - x;
			double deltaZ = z2 - z;

			for (double d = 0; d < 1; d += .05) {
				loc.add(x + deltaX * d, 0, z + deltaZ * d);
				ParticleUtil.spawn(Particle.FLAME, loc, 1, 0, 0, 0, 0);
				loc.subtract(x + deltaX * d, 0, z + deltaZ * d);
			}
		}

		Random r = new Random();
		int x = 0, z = 0;
		while (Math.abs(x) < 5)
			x = r.nextInt(30) - 15;
		while (Math.abs(z) < 5)
			z = r.nextInt(30) - 15;
		MagmaMissile m = new MagmaMissile(tar.getLocation().clone().add(x, -10, z), caster, tar);
		m.launch();

		takeMana(caster, val);
	}

}

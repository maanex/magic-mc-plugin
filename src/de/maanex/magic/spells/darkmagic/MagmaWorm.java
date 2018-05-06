package de.maanex.magic.spells.darkmagic;


import java.util.Random;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.missile.MagmaMissile;
import de.maanex.magic.wandsuse.WandType;
import de.maanex.utils.Particle;
import de.maanex.utils.TargetEntityFinder;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class MagmaWorm extends MagicSpell {

	public MagmaWorm() {
		super(15, "Magma Wurm", "Igitt!", 16, WandType.DARK);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
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
				Particle pa = new Particle(EnumParticle.FLAME, loc, true, 0, 0, 0, 0, 1);
				pa.sendAll();
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

		takeMana(caster, mods);
	}

}

package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

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
		super(15, "Magma Wurm", "Igitt!", 16, new BuildRequirements(0, 0, 0, 5, 45, 100, 5, 100, 0, 10, 70), WandType.DARK);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block b = caster.getMCPlayer().getTargetBlock(null, 200);

		Entity tar = TargetEntityFinder.find(b);
		if (tar == null) return;

		if (tar instanceof Player) {
			Player p = (Player) tar;
			p.playSound(p.getEyeLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
			Particle pa = new Particle(EnumParticle.NOTE, p.getEyeLocation(), true, 1, 0, 1, .1f, 20);
			pa.sendPlayer(p);
			pa.sendPlayer(caster.getMCPlayer());
		} else {
			Particle pa = new Particle(EnumParticle.NOTE, tar.getLocation(), true, 1, 0, 1, .1f, 20);
			pa.sendPlayer(caster.getMCPlayer());
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

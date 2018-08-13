package de.maanex.magic.customEffects;


import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.manapots.ManaCauldron;
import de.maanex.utils.ParticleUtil;


public class GoldBloodEffect extends MagicEffect {

	public int strength;

	public GoldBloodEffect(int duration, int strength) {
		super(duration);
		this.strength = strength;
	}

	@Override
	public void tick(MagicPlayer m) {
		Random r = new Random();
		Block b = m.getMCPlayer().getLocation().getBlock();

		ParticleUtil.spawn(Particle.REDSTONE, m.getMCPlayer().getEyeLocation().clone().subtract(0, .5, 0), strength * 4, .05, .3, .5, .3, Color.fromRGB(0xdbb925), 1f);

		if (b.isLiquid()) m.addMana(-strength);

		if (b.getWorld().hasStorm() && b.getLocation().getY() < m.getMCPlayer().getEyeLocation().getY() && r.nextInt(5) <= strength) m.addMana(-1);

		if (b.getType().equals(Material.CAULDRON)) {
			ManaCauldron c = ManaCauldron.fromBlock(b);
			c.addMana(Math.min(strength, m.getMana()));
			m.addMana(-strength);
		}
	}
}

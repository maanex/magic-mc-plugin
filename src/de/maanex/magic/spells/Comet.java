package de.maanex.magic.spells;


import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Fireball;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.utils.ParticleUtil;


public class Comet extends MagicSpell {

	public Comet() {
		super(2, "Komet", "Juhu! Ein Komet!... Oh warte!", 3, 2, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block target = caster.getMCPlayer().getTargetBlock(null, 20 + mods.getEnergy() - 100);
		if (target.getType().equals(Material.AIR)) return;

		ParticleUtil.spawn(Particle.REDSTONE, target.getLocation(), 200, 0, 2, 0, 2);
		Fireball f = target.getWorld().spawn(target.getLocation().clone().add(0, 20, 0), Fireball.class);
		f.setDirection(new Vector(0, -0.001, 0));
		f.setVelocity(f.getVelocity().multiply(.1));
		f.setYield(2);

		takeMana(caster, mods);
	}

}

package de.maanex.magic.spells;


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Fireball;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.wandsuse.WandType;
import de.maanex.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class Comet extends MagicSpell {

	public Comet() {
		super(2, "Komet", "Juhu! Ein Komet!... Oh warte!", 10);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block target = caster.getMCPlayer().getTargetBlock(null, 20 + mods.getEnergy() - 100);
		if (target.getType().equals(Material.AIR)) return;

		Particle pa = new Particle(EnumParticle.REDSTONE, target.getLocation(), true, 2, 0, 2, 0, 200);
		pa.sendPlayer(caster.getMCPlayer());
		Fireball f = target.getWorld().spawn(target.getLocation().clone().add(0, 20, 0), Fireball.class);
		f.setDirection(new Vector(0, -0.001, 0));
		f.setVelocity(f.getVelocity().multiply(.1));
		f.setYield(2);

		takeMana(caster, mods);
	}

}

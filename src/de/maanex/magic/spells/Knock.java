package de.maanex.magic.spells;


import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.wandsuse.WandType;
import de.maanex.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class Knock extends MagicSpell {

	public Knock() {
		super(4, "Knock", "KNOCK KNOCK!", 1);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		for (int i = 0; i < 10; i++) {
			Location l = caster.getMCPlayer().getEyeLocation().clone().add(caster.getMCPlayer().getLocation().getDirection().multiply(i));
			Particle pa = new Particle(EnumParticle.CRIT, l, true, .1f, .1f, .1f, .05f, 15);
			pa.sendAll();

			if (i % 3 == 0) l.getWorld().getNearbyEntities(l, 1.2, 1.2, 1.2).forEach(e -> {
				if (e instanceof LivingEntity && !e.isDead() && !e.equals(caster.getMCPlayer())) ((LivingEntity) e).damage(1, caster.getMCPlayer());
			});
		}
		takeMana(caster, mods);
	}

}

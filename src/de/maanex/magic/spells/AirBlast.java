package de.maanex.magic.spells;


import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.wandsuse.WandType;
import de.maanex.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class AirBlast extends MagicSpell {

	public AirBlast() {
		super(5, "Air Blast", "Wusch! Weg von mir!", 15);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Location l = caster.getMCPlayer().getLocation().clone();

		Particle pa = new Particle(EnumParticle.SWEEP_ATTACK, l, true, 10, 10, 10, .2f, 500);
		pa.sendAll();

		for (Entity e : l.getWorld().getNearbyEntities(l, 10, 10, 10)) {
			if (!e.equals(caster.getMCPlayer()) && !e.isDead() && e instanceof LivingEntity) {
				((LivingEntity) e).setVelocity(e.getLocation().toVector().subtract(l.toVector()).normalize().setY(.3).multiply(2));
			}
		}

		takeMana(caster, mods);
	}
}

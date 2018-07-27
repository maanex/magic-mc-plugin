package de.maanex.magic.spells;


import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.utils.ParticleUtil;


public class AirBlast extends MagicSpell {

	public AirBlast() {
		super(5, "Air Blast", "Wusch! Weg von mir!", 5, 18, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.EPIC);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Location l = caster.getMCPlayer().getLocation().clone();

		ParticleUtil.spawn(Particle.SWEEP_ATTACK, l, 500, .2, 10, 10, 10);

		for (Entity e : l.getWorld().getNearbyEntities(l, 10, 10, 10)) {
			if (!e.equals(caster.getMCPlayer()) && !e.isDead() && e instanceof LivingEntity) {
				((LivingEntity) e).setVelocity(e.getLocation().toVector().subtract(l.toVector()).normalize().setY(.3).multiply(2));
			}
		}

		takeMana(caster, mods);
	}
}

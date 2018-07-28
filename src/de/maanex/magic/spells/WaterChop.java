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


public class WaterChop extends MagicSpell {

	public WaterChop() {
		super(40, "Wasserhieb", "*hieb*", 1, 1, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.COMMON);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Location l = caster.getMCPlayer().getEyeLocation().clone().add(caster.getMCPlayer().getLocation().getDirection().multiply(3));
		for (Entity e : l.getWorld().getNearbyEntities(l, 1.4, 1.4, 1.4)) {
			if (!caster.getMCPlayer().equals(e) && (e instanceof LivingEntity)) {
				e.setVelocity(caster.getMCPlayer().getLocation().getDirection().multiply(2));
				((LivingEntity) e).damage(2, caster.getMCPlayer());
			}
		}
		ParticleUtil.spawn(Particle.WATER_SPLASH, l, 40, 0, 1.4, 1.4, 1.4);

		takeMana(caster, mods);
	}

}

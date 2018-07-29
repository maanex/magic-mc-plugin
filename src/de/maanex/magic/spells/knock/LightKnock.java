package de.maanex.magic.spells.knock;


import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.main.Main;
import de.maanex.utils.ParticleUtil;


public class LightKnock extends MagicSpell {

	public LightKnock() {
		super(50, "Licht-Knock", "(Helles) KNOCK KNOCK KNOCK!", 1, 5, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.EPIC, WandType.LIGHT);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Location src = caster.getMCPlayer().getEyeLocation().clone();
		for (int i = 0; i < 20; i++) {
			int q = i;
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
				Location l = src.clone().add(src.getDirection().multiply(q));
				ParticleUtil.spawn(Particle.REDSTONE, l, (20 - q) * 3, .05, .3, .3, .3, Color.WHITE, 2f);

				if (q % 3 == 0) l.getWorld().getNearbyEntities(l, 1.4, 1.4, 1.4).forEach(e -> {
					if (e instanceof LivingEntity && !e.isDead() && !e.equals(caster.getMCPlayer())) {
						((LivingEntity) e).setHealth(Math.min(((LivingEntity) e).getMaxHealth(), ((LivingEntity) e).getHealth() + 4));
						((LivingEntity) e).playEffect(EntityEffect.ENTITY_POOF);
					}
				});
			}, i * 2);
		}
		takeMana(caster, mods);
	}

}

package de.maanex.magic.spells.knock;


import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.main.Main;
import de.maanex.utils.ParticleUtil;


public class LightKnock extends MagicSpell {

	public LightKnock() {
		super(50, "Licht-Knock", "(Helles) KNOCK KNOCK KNOCK!", 1, 5, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.EPIC, WandType.LIGHT);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
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
		takeMana(caster, val);
	}

}

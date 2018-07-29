package de.maanex.magic.spells.knock;


import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.main.Main;
import de.maanex.utils.ParticleUtil;


public class DarkKnock extends MagicSpell {

	public DarkKnock() {
		super(49, "Dunkel-Knock", "(Dunkles) KNOCK KNOCK KNOCK!", 1, 5, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.EPIC, WandType.DARK);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Location src = caster.getMCPlayer().getEyeLocation().clone();
		for (int i = 0; i < 15; i++) {
			int q = i;
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
				Location l = src.clone().add(src.getDirection().multiply(q));
				ParticleUtil.spawn(Particle.REDSTONE, l, (20 - q) * 2, .05, .2, .2, .2, Color.BLACK, 2f);

				if (q % 3 == 0) l.getWorld().getNearbyEntities(l, 1.4, 1.4, 1.4).forEach(e -> {
					if (e instanceof LivingEntity && !e.isDead() && !e.equals(caster.getMCPlayer())) {
						((LivingEntity) e).damage(5, caster.getMCPlayer());
						((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 2, true, false));
					}
				});
			}, i / 2);
		}
		takeMana(caster, mods);
	}

}

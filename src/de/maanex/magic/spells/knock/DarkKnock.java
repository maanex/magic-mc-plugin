package de.maanex.magic.spells.knock;


import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.main.Main;
import de.maanex.utils.ParticleUtil;


public class DarkKnock extends MagicSpell {

	public DarkKnock() {
		super(49, "Dunkel-Knock", "(Dunkles) KNOCK KNOCK KNOCK!", 1, 5, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.EPIC, WandType.DARK);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
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
		takeMana(caster, val);
	}

}

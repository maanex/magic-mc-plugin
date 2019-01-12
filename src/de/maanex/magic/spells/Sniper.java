package de.maanex.magic.spells;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.basic.Element;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.main.Main;
import de.maanex.utils.ParticleUtil;


public class Sniper extends MagicSpell {

	public Sniper() {
		super(34, "Scharfschütze", "Ziehlen. Feuer!", 5, 5, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.VERY_RARE, "Reichweite :air:", "Schaden :earth:");
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		caster.getMCPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 100, true));
		caster.getMCPlayer().setVelocity(new Vector(0, 0, 0));
		Location l = caster.getMCPlayer().getEyeLocation().clone();
		drawRay(caster.getMCPlayer(), l.clone(), val.getElement(Element.AIR) * 15, Particle.CLOUD, 0);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> drawRay(caster.getMCPlayer(), l, val.getElement(Element.AIR) * 15, Particle.CRIT_MAGIC, val.getElement(Element.EARTH) * 2),
				20);
		takeMana(caster, val);
	}

	private void drawRay(Player src, Location l, int dis, Particle particle, int dmg) {
		while (dis-- > 0) {
			l.add(l.getDirection().multiply(.6));
			if (l.getBlock().getType().isSolid()) return;
			ParticleUtil.spawn(particle, l, 1, 0, 0, 0, 0);
			if (dmg > 0) {
				for (Entity e : l.getWorld().getNearbyEntities(l, .3, .3, .3)) {
					if (e.equals(src)) continue;
					if (!(e instanceof LivingEntity)) continue;

					((LivingEntity) e).damage(dmg, e);
				}
			}
		}
	}

}

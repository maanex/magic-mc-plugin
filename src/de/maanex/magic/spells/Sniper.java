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
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.main.Main;
import de.maanex.utils.ParticleUtil;


public class Sniper extends MagicSpell {

	public Sniper() {
		super(34, "Scharfschütze", "Ziehlen. Feuer!", 5, 5, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		caster.getMCPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 100, true));
		caster.getMCPlayer().setVelocity(new Vector(0, 0, 0));
		Location l = caster.getMCPlayer().getEyeLocation().clone();
		drawRay(caster.getMCPlayer(), l.clone(), (int) (mods.getEnergy() * 1.5), Particle.CLOUD, false);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> drawRay(caster.getMCPlayer(), l, (int) (mods.getEnergy() * 1.5), Particle.CRIT_MAGIC, true), 20);
		takeMana(caster, mods);
	}

	private void drawRay(Player src, Location l, int dis, Particle particle, boolean dmg) {
		while (dis-- > 0) {
			l.add(l.getDirection().multiply(.6));
			if (l.getBlock().getType().isSolid()) return;
			ParticleUtil.spawn(particle, l, 1, 0, 0, 0, 0);
			if (dmg) {
				for (Entity e : l.getWorld().getNearbyEntities(l, .3, .3, .3)) {
					if (e.equals(src)) continue;
					if (!(e instanceof LivingEntity)) continue;

					((LivingEntity) e).damage(15, e);
				}
			}
		}
	}

}

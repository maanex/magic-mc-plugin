package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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


public class Taser extends MagicSpell {

	public Taser() {
		super(35, "Stromschlag", "Bei USA Polizisten als Taser bekannt!", 3, 3, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		Random r = new Random();
		for (int i = 0; i <= 20; i++) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> drawRay(r, caster.getMCPlayer(), caster.getMCPlayer().getEyeLocation(), mods.getEnergy() / 2), i);
		}
		takeMana(caster, mods);
	}

	private void drawRay(Random r, Player src, Location l, int dis) {
		src.setVelocity(new Vector(0, 0, 0));
		while (dis-- > 0) {
			l.add(l.getDirection().multiply(.5));
			ParticleUtil.spawn(Particle.CRIT_MAGIC, l, 1, 0, ((double) r.nextInt(10) / 20), ((double) r.nextInt(10) / 20), ((double) r.nextInt(10) / 20));

			for (Entity e : l.getWorld().getNearbyEntities(l, .6, .6, .6)) {
				if (e.equals(src)) continue;
				if (!(e instanceof LivingEntity)) continue;

				((LivingEntity) e).damage(1, e);
				((LivingEntity) e).setVelocity(Vector.getRandom().subtract(Vector.getRandom()));
			}
		}
	}

}

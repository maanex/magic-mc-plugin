package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.main.Main;
import de.maanex.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class Taser extends MagicSpell {

	public Taser() {
		super(35, "Stromschlag", "Bei USA Polizisten als Taser bekannt!", 3, 2, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Random r = new Random();
		for (int i = 0; i <= 20; i++) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> drawRay(r, caster.getMCPlayer(), caster.getMCPlayer().getEyeLocation(), mods.getEnergy() / 2), i);
		}
		takeMana(caster, mods);
	}

	private void drawRay(Random r, Player src, Location l, int dis) {
		while (dis-- > 0) {
			l.add(l.getDirection().multiply(.3));
			new Particle(EnumParticle.CRIT_MAGIC, l, true, (float) ((double) r.nextInt(10) / 10), (float) ((double) r.nextInt(10) / 10), (float) ((double) r.nextInt(10) / 10), 0, 2).sendAll();

			for (Entity e : l.getWorld().getNearbyEntities(l, .3, .3, .3)) {
				if (e.equals(src)) continue;
				if (!(e instanceof LivingEntity)) continue;

				((LivingEntity) e).damage(1, e);
				((LivingEntity) e).setVelocity(Vector.getRandom().multiply(.1));
			}
		}
	}

}

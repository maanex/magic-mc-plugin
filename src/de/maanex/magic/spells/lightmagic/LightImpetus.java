package de.maanex.magic.spells.lightmagic;


import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.main.Main;
import de.maanex.utils.ParticleUtil;


public class LightImpetus extends MagicSpell {

	public LightImpetus() {
		super(61, "Licht Impetus", "(Helles) WUUUSCH!", 1, 10, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.GODLIKE, WandType.LIGHT);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		for (int i = 0; i < mods.getEnergy(); i += 2)
			perform(caster.getMCPlayer(), i);

		caster.getMCPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, mods.getEnergy(), 1, true, false));
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> caster.getMCPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20 * 5, 1, true, false)),
				mods.getEnergy());

		takeMana(caster, mods);
	}

	private void perform(Player p, int delay) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
			p.setVelocity(vel(p));
			ParticleUtil.spawn(Particle.CLOUD, p.getLocation().clone().add(0, 1, 0), 20, .01, .5, 1, .5);
			for (Entity e : p.getWorld().getNearbyEntities(p.getLocation(), 2, 3, 2)) {
				if (e.equals(p)) continue;
				if (!(e instanceof LivingEntity)) continue;
				((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 2));
				((LivingEntity) e).setVelocity(p.getVelocity().multiply(.2));
			}
		}, delay);
	}

	private Vector vel(Player p) {
		Vector o = p.getLocation().getDirection().multiply(.9);
		o.setY(o.getY() * .7);
		return o;
	}

}

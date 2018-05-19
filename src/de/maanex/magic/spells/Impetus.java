package de.maanex.magic.spells;


import org.bukkit.Bukkit;
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


public class Impetus extends MagicSpell {

	public Impetus() {
		super(33, "Impetus", "WUUUSCH!", 1, 10, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		for (int i = 0; i < mods.getEnergy() / 2; i += 2)
			perform(caster.getMCPlayer(), i);
		takeMana(caster, mods);
	}

	private void perform(Player p, int delay) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
			p.setVelocity(vel(p));
			new Particle(EnumParticle.DRAGON_BREATH, p.getLocation().clone().add(0, 1, 0), true, .5f, 1, .5f, .01f, 20).sendAll();
			for (Entity e : p.getWorld().getNearbyEntities(p.getLocation(), 2, 3, 2)) {
				if (e.equals(p)) continue;
				if (!(e instanceof LivingEntity)) continue;
				((LivingEntity) e).damage(2, p);
				((LivingEntity) e).setVelocity(p.getVelocity().multiply(.5));
			}
		}, delay);
	}

	private Vector vel(Player p) {
		Vector o = p.getLocation().getDirection().multiply(1.2);
		o.setY(o.getY() * .4);
		return o;
	}

}

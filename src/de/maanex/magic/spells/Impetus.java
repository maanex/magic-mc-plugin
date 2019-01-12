package de.maanex.magic.spells;


import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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


public class Impetus extends MagicSpell {

	public Impetus() {
		super(33, "Impetus", "WUUUSCH!", 1, 10, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.VERY_RARE, "Dauer :air:");
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		for (int i = 0; i < val.getElement(Element.AIR) * 5; i += 2)
			perform(caster.getMCPlayer(), i);
		takeMana(caster, val);
	}

	private void perform(Player p, int delay) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
			p.setVelocity(vel(p));
			ParticleUtil.spawn(Particle.DRAGON_BREATH, p.getLocation().clone().add(0, 1, 0), 20, .01, .5, 1, .5);
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

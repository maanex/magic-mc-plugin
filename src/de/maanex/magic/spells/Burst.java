package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.missile.MagicMissile;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.utils.ParticleUtil;


public class Burst extends MagicSpell {

	public Burst() {
		super(77, "Burst", "Unleash magic energy!", 2, 4, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.EPIC);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
//		ParticleUtil.spawn(Particle.CRIT, l, 15, .05, .1, .1, .1);
		// alle entities projectiles magic missiles zurückschlagen
		
		for (int i = 0; i < 10; i++) {
			Location l = caster.getMCPlayer().getEyeLocation().clone().add(caster.getMCPlayer().getLocation().getDirection().multiply(i));
			Vector pdir = caster.getMCPlayer().getLocation().getDirection();
			Random r = new Random();
			for (int j = 0; j < 5; j++) {
				pdir = caster.getMCPlayer().getLocation().getDirection().clone().multiply(r.nextInt(20) / 10 + 2);
				ParticleUtil.spawn(Particle.DRAGON_BREATH, l.clone().add(((float) r.nextInt(20) / 10) - 1, ((float) r.nextInt(20) / 10) - 1, ((float) r.nextInt(20) / 10) - 1), 0, .05, pdir.getX() * 5, pdir.getY() * 5, pdir.getZ() * 5);
			}
			Vector vel = caster.getMCPlayer().getLocation().getDirection().clone().multiply(2.5).add(new Vector(0, .1, 0));

			if (i % 3 == 0) l.getWorld().getNearbyEntities(l, 1.2, 1.2, 1.2).forEach(e -> {
				if (!e.equals(caster.getMCPlayer()))
					e.setVelocity(vel);
			});
			
			MagicMissile.getActiveMissiles().forEach(m -> {
				if (m.position.distance(l) < 3.5)
					m.magicRedirect(vel);
			});
		}
		takeMana(caster, val);
	}

}

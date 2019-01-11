package de.maanex.magic.spells.knock;


import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.utils.ParticleUtil;


public class MegaKnock extends MagicSpell {

	public MegaKnock() {
		super(45, "Mega Knock", "KNOCK KNOCK KNOCK KNOCK KNOCK!", 1, 5, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.EPIC);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		for (int i = 0; i < 13; i++) {
			Location l = caster.getMCPlayer().getEyeLocation().clone().add(caster.getMCPlayer().getLocation().getDirection().multiply(i));
			ParticleUtil.spawn(Particle.CRIT, l, 50, .05, .1, .1, .1);

			if (i % 3 == 0) l.getWorld().getNearbyEntities(l, 1.4, 1.4, 1.4).forEach(e -> {
				if (e instanceof LivingEntity && !e.isDead() && !e.equals(caster.getMCPlayer())) ((LivingEntity) e).damage(3, caster.getMCPlayer());
			});
		}
		takeMana(caster, val);
	}

}

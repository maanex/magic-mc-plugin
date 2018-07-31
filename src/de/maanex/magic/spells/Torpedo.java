package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.Particle;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.magic.missile.BasicMissile;
import de.maanex.magic.missile.BasicMissile.BlockHitBehaviour;
import de.maanex.utils.ParticleUtil;


public class Torpedo extends MagicSpell {

	public Torpedo() {
		super(69, "Torpedo", "Wusch?", 1, 4, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.COMMON);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Random r = new Random();

		BasicMissile m = new BasicMissile(caster, 30, 1, BlockHitBehaviour.ABSORB, //
				l -> {
					ParticleUtil.spawn(Particle.EXPLOSION_NORMAL, l, 5, .1, .1, .1, .1);
					return null;
				}, e -> {
					ParticleUtil.spawn(Particle.EXPLOSION_HUGE, e.getLocation(), 20, .1, .3, .3, .3);
					e.damage(8);
					return null;
				});
		m.launch();

		takeMana(caster, mods);
	}

}

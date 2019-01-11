package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.Particle;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.missile.BasicMissile;
import de.maanex.magic.missile.BasicMissile.BlockHitBehaviour;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.utils.ParticleUtil;


public class Torpedo extends MagicSpell {

	public Torpedo() {
		super(69, "Torpedo", "Wusch?", 3, 4, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
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

		takeMana(caster, val);
	}

}

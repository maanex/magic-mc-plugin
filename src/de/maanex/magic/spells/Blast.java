package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.Color;
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


public class Blast extends MagicSpell {

	public Blast() {
		super(68, "Blast", "Puff?", 1, 4, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.COMMON);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		Random r = new Random();

		BasicMissile m = new BasicMissile(caster, 60, .5, BlockHitBehaviour.REFLECT, //
				l -> {
					ParticleUtil.spawn(Particle.REDSTONE, l, 10, .1, .05, .05, .05, Color.fromRGB(r.nextInt(0x33), r.nextInt(0x55) + 0x88, r.nextInt(0x33)), 1f);
					return null;
				}, e -> {
					ParticleUtil.spawn(Particle.REDSTONE, e.getLocation(), 120, .1, .5, .5, .5, Color.fromRGB(r.nextInt(0x44) + 0xAA, r.nextInt(0x44) + 0xAA, r.nextInt(0x33)), 1f);
					e.damage(2);
					return null;
				});
		m.launch();

		takeMana(caster, val);
	}

}

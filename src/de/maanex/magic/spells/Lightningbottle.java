package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Particle;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.missile.BasicMissile;
import de.maanex.magic.missile.BasicMissile.BlockHitBehaviour;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.utils.ParticleUtil;


public class Lightningbottle extends MagicSpell {

	public Lightningbottle() {
		super(70, "Flaschenblitz", "Ja!", 3, 5, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.COMMON);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		Random r = new Random();

		BasicMissile m = new BasicMissile(caster, 60, .4, BlockHitBehaviour.REFLECT, //
				l -> {
					ParticleUtil.spawn(Particle.REDSTONE, l, 5, .1, .1, .1, .1, Color.fromRGB(0x66 + r.nextInt(0x33), 0x66 + r.nextInt(0x33), 0x66 + r.nextInt(0x66)), 1f);
					return null;
				}, e -> {
					ParticleUtil.spawn(Particle.SWEEP_ATTACK, e.getLocation(), 20, .1, .3, .3, .3);
					e.getWorld().strikeLightning(e.getLocation());
					return null;
				});
		m.destroy = l -> {
			l.getWorld().strikeLightningEffect(l);
			return null;
		};
		m.launch();

		takeMana(caster, mods);
	}

}

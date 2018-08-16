package de.maanex.magic.spells;


import org.bukkit.Particle;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

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


public class Airpuff extends MagicSpell {

	public Airpuff() {
		super(54, "Lufthauch", "*hauch*", 2, 4, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		BasicMissile m = new BasicMissile(caster.getMCPlayer().getEyeLocation(), caster, caster.getMCPlayer().getLocation(), 80, .7, BlockHitBehaviour.ABSORB, //
				l -> {
					ParticleUtil.spawn(Particle.FIREWORKS_SPARK, l, 20, .01, .2, .2, .2);
					return null;
				}, e -> {
					e.damage(5, caster.getMCPlayer());
					e.setVelocity(new Vector(0, .6, 0));
					e.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, -1, true, false));
					return null;
				});
		m.launch();
		takeMana(caster, mods);
	}

}

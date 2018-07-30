package de.maanex.magic.spells;


import org.bukkit.Particle;
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
import de.maanex.magic.missile.BasicMissile;
import de.maanex.utils.ParticleUtil;


public class Airpuff extends MagicSpell {

	public Airpuff() {
		super(54, "Lufthauch", "*hauch*", 2, 4, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		BasicMissile m = new BasicMissile(caster.getMCPlayer().getEyeLocation(), caster, caster.getMCPlayer().getLocation(), 80, .7, //
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

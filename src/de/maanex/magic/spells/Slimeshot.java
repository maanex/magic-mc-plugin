package de.maanex.magic.spells;


import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.magic.missile.BasicMissile;
import de.maanex.utils.ParticleUtil;


public class Slimeshot extends MagicSpell {

	public Slimeshot() {
		super(55, "Slimeschleuder", "Schlonz!", 2, 4, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Location lo = caster.getMCPlayer().getLocation().clone();
		BasicMissile m = new BasicMissile(caster.getMCPlayer().getEyeLocation(), caster, lo, 80, .7, //
				l -> {
					ParticleUtil.spawn(Particle.SLIME, l, 20, .01, .2, .2, .2);
					return null;
				}, e -> {
					e.damage(5, caster.getMCPlayer());
					e.setVelocity(lo.getDirection().multiply(.4));
					e.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, -1, true, false));
					return null;
				});
		m.launch();
		takeMana(caster, mods);
	}

}

package de.maanex.magic.spells;


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
import de.maanex.utils.ParticleUtil;


public class Clarity extends MagicSpell {

	public Clarity() {
		super(58, "Klarheit", "So klar als währ ich da, DAB+", 0, 30, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		caster.getMCPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 7));
		takeMana(caster, mods);
		ParticleUtil.spawn(Particle.DAMAGE_INDICATOR, caster.getMCPlayer().getEyeLocation(), 30, .1, .3, .5, .3);
	}

}

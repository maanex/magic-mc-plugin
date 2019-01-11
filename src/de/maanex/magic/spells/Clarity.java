package de.maanex.magic.spells;


import org.bukkit.Particle;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.utils.ParticleUtil;


public class Clarity extends MagicSpell {

	public Clarity() {
		super(58, "Klarheit", "So klar als währ ich da, DAB+", 0, 30, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		caster.getMCPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 7));
		takeMana(caster, val);
		ParticleUtil.spawn(Particle.DAMAGE_INDICATOR, caster.getMCPlayer().getEyeLocation(), 30, .1, .3, .5, .3);
	}

}

package de.maanex.magic.spells.basic;


import org.bukkit.Particle;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.utils.ParticleUtil;


public class FireSpirit extends MagicSpell {

	public FireSpirit() {
		super(24, "Feuergeist", "Spiritu ardoris", 0, 0, SpellType.NOT_USEABLE, SpellCategory.UTILITY, SpellRarity.COMMON);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		ParticleUtil.spawn(caster.getMCPlayer(), Particle.FLAME, caster.getMCPlayer().getEyeLocation(), 300, .2, 2, .3, 2);
	}

}

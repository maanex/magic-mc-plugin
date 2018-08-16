package de.maanex.magic.spells.basic;


import org.bukkit.Particle;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.utils.ParticleUtil;


public class FireSpirit extends MagicSpell {

	public FireSpirit() {
		super(24, "Feuergeist", "Spiritu ardoris", 0, 0, SpellType.NOT_USEABLE, SpellCategory.UTILITY, SpellRarity.COMMON);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		ParticleUtil.spawn(caster.getMCPlayer(), Particle.FLAME, caster.getMCPlayer().getEyeLocation(), 300, .2, 2, .3, 2);
	}

}

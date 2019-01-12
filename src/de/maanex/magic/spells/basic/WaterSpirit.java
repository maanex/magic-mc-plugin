package de.maanex.magic.spells.basic;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.utils.ParticleUtil;


public class WaterSpirit extends MagicSpell {

	public WaterSpirit() {
		super(26, "Wassergeist", "Spiritus Aqua", 0, 0, SpellType.NOT_USEABLE, SpellCategory.UTILITY, SpellRarity.BASIC);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		ParticleUtil.spawn(caster.getMCPlayer(), org.bukkit.Particle.WATER_SPLASH, caster.getMCPlayer().getEyeLocation(), 300, .2, 2, .3, 2);
	}

}

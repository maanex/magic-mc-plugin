package de.maanex.magic.spells.basic;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.utils.ParticleUtil;


public class WaterSpirit extends MagicSpell {

	public WaterSpirit() {
		super(26, "Wassergeist", "Spiritus Aqua", 0, 0, SpellType.NOT_USEABLE, SpellCategory.UTILITY, SpellRarity.COMMON);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		ParticleUtil.spawn(caster.getMCPlayer(), org.bukkit.Particle.WATER_SPLASH, caster.getMCPlayer().getEyeLocation(), 300, .2, 2, .3, 2);
	}

}

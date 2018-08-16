package de.maanex.magic.spells.building;


import org.bukkit.Particle;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.utils.ParticleUtil;


public class MasterBuildersEssence extends MagicSpell {

	public MasterBuildersEssence() {
		super(48, "Baumeisters Essenz", "Entdecke den Bob in dir!", 1, 5, SpellType.ACTIVE, SpellCategory.BUILDING, SpellRarity.COMMON);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		ParticleUtil.spawn(caster.getMCPlayer(), Particle.NAUTILUS, caster.getMCPlayer().getEyeLocation(), 300, .2, .3, 1, .3);
	}

}

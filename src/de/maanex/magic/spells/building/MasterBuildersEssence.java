package de.maanex.magic.spells.building;


import org.bukkit.Particle;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.utils.ParticleUtil;


public class MasterBuildersEssence extends MagicSpell {

	public MasterBuildersEssence() {
		super(48, "Baumeisters Essenz", "Entdecke den Bob in dir!", 1, 5, SpellType.ACTIVE, SpellCategory.BUILDING, SpellRarity.COMMON);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		ParticleUtil.spawn(caster.getMCPlayer(), Particle.NAUTILUS, caster.getMCPlayer().getEyeLocation(), 300, .2, .3, 1, .3);
	}

}

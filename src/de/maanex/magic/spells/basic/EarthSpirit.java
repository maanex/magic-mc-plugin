package de.maanex.magic.spells.basic;


import org.bukkit.Material;
import org.bukkit.Particle;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.utils.ParticleUtil;


public class EarthSpirit extends MagicSpell {

	public EarthSpirit() {
		super(25, "Erdgeist", "Spiritus terrae", 0, 0, SpellType.NOT_USEABLE, SpellCategory.UTILITY, SpellRarity.COMMON);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		ParticleUtil.spawn(caster.getMCPlayer(), Particle.BLOCK_CRACK, caster.getMCPlayer().getEyeLocation(), 300, .2, 2, .3, 2, Material.DIRT.getData());
	}

}

package de.maanex.magic.spells.basic;


import org.bukkit.Material;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.wandsuse.WandType;
import de.maanex.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class EarthSpirit extends MagicSpell {

	public EarthSpirit() {
		super(25, "Erdgeist", "Spiritus terrae", 0);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Particle pa = new Particle(EnumParticle.BLOCK_CRACK, caster.getMCPlayer().getEyeLocation(), false, 2, .3f, 2, .2f, 300);
		pa.setC(Material.DIRT.getId());
		pa.sendPlayer(caster.getMCPlayer());
	}

}

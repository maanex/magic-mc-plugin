package de.maanex.magic.spells.basic;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.wandsuse.WandType;
import de.maanex.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class WaterSpirit extends MagicSpell {

	public WaterSpirit() {
		super(26, "Wassergeist", "Spiritus Aqua", 0);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Particle pa = new Particle(EnumParticle.WATER_SPLASH, caster.getMCPlayer().getEyeLocation(), false, 2, .3f, 2, .2f, 300);
		pa.sendPlayer(caster.getMCPlayer());
	}

}

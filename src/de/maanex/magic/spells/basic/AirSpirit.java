package de.maanex.magic.spells.basic;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class AirSpirit extends MagicSpell {

	public AirSpirit() {
		super(27, "Luftgeist", "Spiritus caeli", 0, 0, SpellType.NOT_USEABLE, SpellCategory.UTILITY, SpellRarity.COMMON);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Particle pa = new Particle(EnumParticle.CLOUD, caster.getMCPlayer().getEyeLocation(), false, 2, .3f, 2, .2f, 300);
		pa.sendPlayer(caster.getMCPlayer());
	}

}

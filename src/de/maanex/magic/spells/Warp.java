package de.maanex.magic.spells;


import org.bukkit.Location;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.wandsuse.WandType;
import de.maanex.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class Warp extends MagicSpell {

	public Warp() {
		super(8, "Warp", "Zack, Zack - Hier, Dort", 2, new BuildRequirements(5, 20, 0, 20, 5, 20, 0, 20, 0, 20, 105));
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Particle pa;
		pa = new Particle(EnumParticle.DRIP_LAVA, caster.getMCPlayer().getLocation(), true, .4f, .4f, .4f, .5f, 20);
		pa.sendAll();
		Location l = caster.getMCPlayer().getEyeLocation().clone().add(caster.getMCPlayer().getLocation().getDirection().multiply(6));
		if (l.getBlock().isEmpty()) {
			caster.getMCPlayer().teleport(l);
			caster.getMCPlayer().setVelocity(l.getDirection());
		}
		caster.getMCPlayer().setFallDistance(0);
		pa = new Particle(EnumParticle.DRIP_LAVA, caster.getMCPlayer().getLocation(), true, .4f, .4f, .4f, .5f, 20);
		pa.sendAll();

		takeMana(caster, mods);
	}

}

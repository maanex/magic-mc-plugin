package de.maanex.magic.spells;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.wandsuse.WandType;
import de.maanex.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class Levitate extends MagicSpell {

	public Levitate() {
		super(19, "Übersicht", "Gut Aussicht von hier oben!", 2);
	}

	public static List<Player> inAir = new ArrayList<>();

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		if (!caster.getMCPlayer().isOnGround()) return;
		caster.getMCPlayer().addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 10, 255, true));
		caster.getMCPlayer().setVelocity(new Vector(0, 2, 0));
		inAir.add(caster.getMCPlayer());
		takeMana(caster, mods);
	}

	public static void tick() {
		for (Player p : new ArrayList<>(inAir)) {
			if (p.isOnGround()) {
				inAir.remove(p);
				return;
			}

			Particle pa = new Particle(EnumParticle.CLOUD, p.getLocation().subtract(0, .5, 0), true, .4f, 0, .4f, 0, 2);
			pa.sendAll();
		}
	}

}

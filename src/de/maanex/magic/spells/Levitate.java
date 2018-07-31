package de.maanex.magic.spells;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.utils.ParticleUtil;


public class Levitate extends MagicSpell {

	public Levitate() {
		super(19, "Übersicht", "Gut Aussicht von hier oben!", 2, 15, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.RARE);
	}

	public static List<Player> inAir = new ArrayList<>();

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		if (!caster.getMCPlayer().isOnGround()) return;
		caster.getMCPlayer().addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 10, -1, true));
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

			ParticleUtil.spawn(Particle.CLOUD, p.getLocation().subtract(0, .5, 0), 2, 0, .4, 0, .4);
		}
	}

}

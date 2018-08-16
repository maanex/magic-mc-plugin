package de.maanex.magic.spells;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.utils.ParticleUtil;


public class Levitate extends MagicSpell {

	public Levitate() {
		super(19, "Übersicht", "Gut Aussicht von hier oben!", 2, 15, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.RARE);
	}

	public static List<Player> inAir = new ArrayList<>();

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
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

package de.maanex.magic.spells;


import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.WandType;


public class HolyShield extends MagicSpell {

	public HolyShield() {
		super(9, "Heiliges Schild!", "Es Schützt!", 11);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Player p = caster.getMCPlayer();

		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 250, true, false));
		p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 40, 250, true, false));
		p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 40, 250, true, false));
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 250, true, false));
		p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 40, 250, true, false));

		takeMana(caster, mods);
	}

}

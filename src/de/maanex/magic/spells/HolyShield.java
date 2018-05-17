package de.maanex.magic.spells;


import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;


public class HolyShield extends MagicSpell {

	public HolyShield() {
		super(9, "Heiliges Schild!", "Es Schützt!", 11, 5, SpellType.ACTIVE, SpellCategory.PROTECTION, SpellRarity.EPIC);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Player p = caster.getMCPlayer();

		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 250, true, false));
		p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 80, 250, true, false));
		p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 80, 250, true, false));

		takeMana(caster, mods);
	}

}

package de.maanex.magic.spells;


import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;


public class HolyShield extends MagicSpell {

	public HolyShield() {
		super(9, "Glorreiches Schild!", "Es Schützt!", 2, 15, SpellType.ACTIVE, SpellCategory.PROTECTION, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		Player p = caster.getMCPlayer();

		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 250, true, false));
		p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 80, 250, true, false));
		p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 80, 250, true, false));

		takeMana(caster, val);
	}

}

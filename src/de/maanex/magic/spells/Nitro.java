package de.maanex.magic.spells;


import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;


public class Nitro extends MagicSpell {

	public Nitro() {
		super(6, "Nitro", "Gotta go faster!", 4, 20, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		caster.getMCPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 4, true, false));
		caster.getMCPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 4, true, false));
		caster.getMCPlayer().setVelocity(caster.getMCPlayer().getLocation().getDirection().multiply(2));
		takeMana(caster, mods);
	}

}

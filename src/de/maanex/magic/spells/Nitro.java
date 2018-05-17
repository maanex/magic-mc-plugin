package de.maanex.magic.spells;


import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;


public class Nitro extends MagicSpell {

	public Nitro() {
		super(6, "Nitro", "Gotta go faster!", 4, 20, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		caster.getMCPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 4, true, false));
		caster.getMCPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 4, true, false));
		caster.getMCPlayer().setVelocity(caster.getMCPlayer().getLocation().getDirection().multiply(2));
		takeMana(caster, mods);
	}

}

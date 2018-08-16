package de.maanex.magic.spells;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.missile.StunMissile;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;


public class Stun extends MagicSpell {

	public Stun() {
		super(20, "Stun", "Hält fest!", 2, 5, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		StunMissile mis = new StunMissile(caster.getMCPlayer().getEyeLocation(), caster, caster.getMCPlayer().getLocation().clone(), mods.getEnergy());
		mis.launch();
		takeMana(caster, mods);
	}

}

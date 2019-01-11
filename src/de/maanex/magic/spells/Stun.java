package de.maanex.magic.spells;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.missile.StunMissile;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.magic.wands.WandValues.WandModifier;


public class Stun extends MagicSpell {

	public Stun() {
		super(20, "Stun", "Hält fest!", 2, 5, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		StunMissile mis = new StunMissile(caster.getMCPlayer().getEyeLocation(), caster, caster.getMCPlayer().getLocation().clone(), val.getMod(WandModifier.ENERGY));
		mis.launch();
		takeMana(caster, val);
	}

}

package de.maanex.magic.spells;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.basic.Element;
import de.maanex.magic.missile.StunMissile;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;


public class Stun extends MagicSpell {

	public Stun() {
		super(20, "Stun", "Hält fest!", 2, 5, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.VERY_RARE, "Reichweite :water:/:fire:");
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		StunMissile mis = new StunMissile(caster.getMCPlayer().getEyeLocation(), caster, caster.getMCPlayer().getLocation().clone(),
				10 * Math.max(val.getElement(Element.WATER), val.getElement(Element.FIRE)));
		mis.launch();
		takeMana(caster, val);
	}

}

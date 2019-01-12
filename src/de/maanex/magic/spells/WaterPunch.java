package de.maanex.magic.spells;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.basic.Element;
import de.maanex.magic.missile.WaterPunchMissile;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;


public class WaterPunch extends MagicSpell {

	public WaterPunch() {
		super(39, "Wasserschlag", "Knock, nur 5x besser!", 2, 1, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.RARE, "Reichweite :water:");
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		WaterPunchMissile mis = new WaterPunchMissile(caster.getMCPlayer().getEyeLocation(), caster, caster.getMCPlayer().getLocation().clone(), val.getElement(Element.WATER) * 5);
		mis.launch();
		takeMana(caster, val);
	}

}

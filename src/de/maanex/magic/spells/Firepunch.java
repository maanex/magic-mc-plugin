package de.maanex.magic.spells;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.missile.FirePunchMissile;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.magic.wands.WandValues.WandModifier;


public class Firepunch extends MagicSpell {

	public Firepunch() {
		super(21, "Feuerschlag", "Knock, nur 1000x besser!", 4, 1, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		FirePunchMissile mis = new FirePunchMissile(caster.getMCPlayer().getEyeLocation(), caster, caster.getMCPlayer().getLocation().clone(), val.getMod(WandModifier.ENERGY));
		mis.launch();
		takeMana(caster, val);
	}

}

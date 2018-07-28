package de.maanex.magic.spells;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.magic.missile.WaterPunchMissile;


public class WaterPunch extends MagicSpell {

	public WaterPunch() {
		super(39, "Wasserschlag", "Knock, nur 5x besser!", 2, 1, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		WaterPunchMissile mis = new WaterPunchMissile(caster.getMCPlayer().getEyeLocation(), caster, caster.getMCPlayer().getLocation().clone(), mods.getEnergy());
		mis.launch();
		takeMana(caster, mods);
	}

}

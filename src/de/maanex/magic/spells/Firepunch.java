package de.maanex.magic.spells;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.missile.FirePunchMissile;
import de.maanex.magic.wandsuse.WandType;


public class Firepunch extends MagicSpell {

	public Firepunch() {
		super(21, "Feuerschlag", "Knock, nur 1000x besser!", 4, new BuildRequirements(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		FirePunchMissile mis = new FirePunchMissile(caster.getMCPlayer().getEyeLocation(), caster, caster.getMCPlayer().getLocation().clone(), mods.getEnergy());
		mis.launch();
		takeMana(caster, mods);
	}

}

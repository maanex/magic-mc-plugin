package de.maanex.magic.spells;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.WandType;


public class Fireball extends MagicSpell {

	public Fireball() {
		super(10, "Feuerball", "Schieﬂt einen Feuerball", 8);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		caster.getMCPlayer().launchProjectile(org.bukkit.entity.Fireball.class);
		takeMana(caster, mods);
	}
}

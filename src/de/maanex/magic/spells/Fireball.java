package de.maanex.magic.spells;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;


public class Fireball extends MagicSpell {

	public Fireball() {
		super(10, "Feuerball", "Schieﬂt einen Feuerball", 1, 8, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.COMMON);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		caster.getMCPlayer().launchProjectile(org.bukkit.entity.Fireball.class);
		takeMana(caster, mods);
	}
}

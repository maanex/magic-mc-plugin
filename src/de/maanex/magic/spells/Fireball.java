package de.maanex.magic.spells;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;


public class Fireball extends MagicSpell {

	public Fireball() {
		super(10, "Feuerball", "Schieﬂt einen Feuerball", 1, 8, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.COMMON);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		caster.getMCPlayer().launchProjectile(org.bukkit.entity.Fireball.class);
		takeMana(caster, mods);
	}
}

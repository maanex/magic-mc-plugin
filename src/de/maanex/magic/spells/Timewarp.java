package de.maanex.magic.spells;


import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class Timewarp extends MagicSpell {

	public Timewarp() {
		super(37, "Zeitsprung", "Teleportiert dich zurück!", 4, 20, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.RARE);
	}

	private HashMap<Player, Location> pos = new HashMap<>();

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		if (!pos.containsKey(caster.getMCPlayer())) {
			pos.put(caster.getMCPlayer(), caster.getMCPlayer().getLocation());
			new Particle(EnumParticle.ENCHANTMENT_TABLE, caster.getMCPlayer().getLocation(), false, .5f, 1, .5f, 1, 100).sendAll();
		} else {
			Location loc = pos.get(caster.getMCPlayer());
			pos.remove(caster.getMCPlayer());
			if (!loc.getWorld().equals(caster.getMCPlayer().getWorld()) || loc.distance(caster.getMCPlayer().getLocation()) > 100) {
				caster.getMCPlayer().sendMessage("§8Distanz zu groß!");
				return;
			}

			new Particle(EnumParticle.ENCHANTMENT_TABLE, caster.getMCPlayer().getLocation(), false, .5f, 1, .5f, 1, 100).sendAll();
			caster.getMCPlayer().teleport(loc);
			takeMana(caster, mods);
		}
	}

}

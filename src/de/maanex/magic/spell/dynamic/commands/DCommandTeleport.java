package de.maanex.magic.spell.dynamic.commands;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.dynamic.DynamicSpell;
import de.maanex.magic.spell.dynamic.params.DEntity;
import de.maanex.magic.spell.dynamic.params.DLocation;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;

public class DCommandTeleport extends DCommand {

	public String[] args;
	public Entity e;
	public Location l;
	
	public DCommandTeleport(String code, DynamicSpell spell, MagicPlayer caster, WandType type, WandValues val) {
		super("t", code, spell, caster, type, val);
		args = code.split(";");
	}

	@Override
	public void execute() {
		if (args.length < 2) return;
		e = DEntity.parse(args[0], spell, caster, type, val);
		if (e == null) return;
		l = DLocation.parse(args[1], spell, caster, type, val);
		if (l == null) return;
		e.teleport(l);
	}

	@Override
	public void mutate(int strength) {
		code = "";
		code += DEntity.mutate(args[0], strength);
		code += ";";
		code += DLocation.mutate(args[1], strength);
		args = code.split(";");
	}

}

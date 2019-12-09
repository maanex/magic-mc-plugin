package de.maanex.magic.spell.dynamic.commands;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.dynamic.DynamicSpell;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;

public abstract class DCommand {

	public String prefix;
	public MagicPlayer caster;
	public WandType type;
	public WandValues val;
	public DynamicSpell spell;
	public String code;
	
	public DCommand(String prefix, String code, DynamicSpell spell, MagicPlayer caster, WandType type, WandValues val) {
		this.prefix = prefix;
		this.code = code;
		this.spell = spell;
		this.caster = caster;
		this.type = type;
		this.val = val;
	}
	
	public abstract void execute();

	public abstract void mutate(int strength);
	
	public static DCommand parse(String code, DynamicSpell spell, MagicPlayer caster, WandType type, WandValues val) {
		String c = code.split(";")[0];
		switch (c) {
			case "t":
				return new DCommandTeleport(code.substring(2), spell, caster, type, val);
				
			default:
				return null;
		}
	}
	
}

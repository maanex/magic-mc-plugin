package de.maanex.magic.spell.dynamic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.dynamic.commands.DCommand;
import de.maanex.magic.spell.dynamic.params.DEntity;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;

public class DynamicSpell {
	
	public MagicPlayer caster;
	public WandType type;
	public WandValues val;
	public String code;
	
	public List<DCommand> commandStack;
	public List<DEntity> entityStack;

	public DynamicSpell(String code, MagicPlayer caster, WandType type, WandValues val) {
		this.caster = caster;
		this.type = type;
		this.val = val;
		this.code = code;
		
		this.commandStack = new ArrayList<>();
		
		parse(code);
	}
	
	private void parse(String code) {
		int cursor = 0;
		for (String line : code.split("\n")) {
			cursor++;
			if (line.equals("")) break;
			DCommand cmd = DCommand.parse(line, this, caster, type, val);
			if (cmd != null) commandStack.add(cmd);
		}
	}
	
	public void execute() {
		commandStack.forEach(s -> s.execute());
	}
	
	public void mutate(int strength) {
		if (strength == 0) return;
		Random r = new Random();
		String nc = "";
		for (DCommand s : commandStack) {
			s.mutate(strength / 2 + r.nextInt(strength));
			nc += s.prefix + ";" + s.code + "\n";
		}
		code = nc;
	}

}

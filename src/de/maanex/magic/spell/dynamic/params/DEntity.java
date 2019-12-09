package de.maanex.magic.spell.dynamic.params;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.dynamic.DynamicSpell;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.utils.TargetEntityFinder;

public class DEntity {

	private DEntity() {
	}
	
	public static Entity parse(String code, DynamicSpell spell, MagicPlayer caster, WandType type, WandValues val) {
		Random r = new Random();
		double rad;
		String source = code.split(":")[0];
		switch (source) {
			case "p":
				return caster.getMCPlayer();
				
			case "r":
				rad = Math.abs(DNumber.parse(code.split(":")[1], spell, caster, type, val) + 1);				
				Collection<Entity> ent = caster.getMCPlayer().getWorld().getNearbyEntities(caster.getMCPlayer().getLocation(), rad, rad, rad);
				List<Entity> suitable = new ArrayList<>();
				for (Entity e : ent) {
					if (e instanceof LivingEntity && !e.equals(caster.getMCPlayer()))
						suitable.add(e);
				}
				if (suitable.size() == 0) return null;
				return suitable.get(r.nextInt(suitable.size()));
				
			case "l":
				rad = Math.abs(DNumber.parse(code.split(":")[1], spell, caster, type, val) + 1);
				Block b = caster.getMCPlayer().getTargetBlock(null, (int) rad * 10);
				if (b == null || !b.getType().isSolid()) return null;
				return TargetEntityFinder.find(b);
				
			case "s":
				// TODO
				
			default: 
				return null;	
		}
	}

	
	public static String mutate(String code, int strength) {
		if (strength == 0) return code;
		Random r = new Random();
		boolean mutateSource = r.nextInt(100) < strength;
		return mutateSource ? randomEntity(r) : code;
	}
	
	public static String randomEntity(Random r) {
		switch (r.nextInt(4)) {
			case 0:
				return "p";
			case 1:
				return "r:" + DNumber.randomNumber();
			case 2:
				return "l:" + DNumber.randomNumber();
			case 3:
//				return "s"; TODO
		}
		return "l:" + DNumber.randomNumber();
	}
	

}

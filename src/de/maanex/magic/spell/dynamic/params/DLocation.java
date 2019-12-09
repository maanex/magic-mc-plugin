package de.maanex.magic.spell.dynamic.params;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.block.Block;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.dynamic.DynamicSpell;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;

public class DLocation {

	private DLocation() {
	}
	
	public static Location parse(String code, DynamicSpell spell, MagicPlayer caster, WandType type, WandValues val) {
		Random r = new Random();
		
		double rad;
		
		Location out = null;
		
		String source = code.split(":")[0];
		switch (source.split(",")[0]) {
			case "h":
				out = caster.getMCPlayer().getEyeLocation().clone();
				break;
				
			case "f":
				out = caster.getMCPlayer().getLocation().clone();
				break;
				
			case "r":
				out = caster.getMCPlayer().getLocation().clone();
				rad = Math.abs(DNumber.parse(source.split(",")[1], spell, caster, type, val) + 1);
				out.add((r.nextInt((int) (rad * 20)) - rad * 10) / 10
					, (r.nextInt((int) (rad * 20)) - rad * 10) / 10
					, (r.nextInt((int) (rad * 20)) - rad * 10) / 10);
				out.setPitch(caster.getMCPlayer().getLocation().getPitch());
				out.setYaw(caster.getMCPlayer().getLocation().getYaw());
				break;
				
			case "l":
				rad = Math.abs(DNumber.parse(source.split(",")[1], spell, caster, type, val));
				Block b = caster.getMCPlayer().getTargetBlock(null, (int) rad * 10);
				if (b == null || !b.getType().isSolid()) return null;
				out = b.getLocation().clone();
				out.setPitch(caster.getMCPlayer().getLocation().getPitch());
				out.setYaw(caster.getMCPlayer().getLocation().getYaw());
				break;
				
			default: 
				return null;	
		}
		
		for (int i = 1; i < code.split(":").length; i++) {
			String cmd = code.split(":")[i];
			switch (cmd.split(",")[0]) {
				case "r":
					rad = Math.abs(DNumber.parse(cmd.split(",")[1], spell, caster, type, val) + 1);
					out.add((r.nextInt((int) (rad * 20)) - rad * 10) / 10
						, (r.nextInt((int) (rad * 20)) - rad * 10) / 10
						, (r.nextInt((int) (rad * 20)) - rad * 10) / 10);
					break;
					
				case "e":
					rad = DNumber.parse(cmd.split(",")[1], spell, caster, type, val);
					out.add(out.getDirection().multiply(rad));
					break;
					
				case "m":
					// TODO
					break;

					
				case "o":
					double rot1 = DNumber.parse(cmd.split(",")[1], spell, caster, type, val);
					double rot2 = DNumber.parse(cmd.split(",")[2], spell, caster, type, val);
					out.setPitch(out.getPitch() + (float) rot1);
					out.setYaw(out.getYaw() + (float) rot2);
					break;
			}
		}
		
		return out;
	}
	
	public static String mutate(String code, int strength) {
		if (strength == 0) return code;
		Random r = new Random();
		boolean mutateSource = r.nextInt(100) < strength;
		
		String out = "";
		String src = code.split(":")[0];
		if (mutateSource) {
			out = randomSource(r);
			if (src.contains(",") && out.contains(","))
					out = out.split(",")[0] + src.split(",")[1];
		} else {
			out += src;

			List<String> mods = new ArrayList<>(Arrays.asList(code.split(":")));
			mods.remove(0);

			int removeMods = Math.min(Math.min(r.nextInt(strength), r.nextInt(strength)), Math.min(r.nextInt(strength), r.nextInt(strength)) + 1);
			int addMods = Math.min(Math.min(r.nextInt(strength), r.nextInt(strength)), Math.min(r.nextInt(strength), r.nextInt(strength)));
			int modMods = Math.min(r.nextInt(strength), r.nextInt(strength));
			
			if (mods.size() < r.nextInt(3)) addMods++;
			
			while (removeMods-- > 0) {
				if (mods.isEmpty()) break;
				mods.remove(r.nextInt(mods.size()));
			}
			
			while (addMods-- > 0) {
				mods.add(randomModifier(r));
			}
			
			if (!mods.isEmpty()) {
				while (modMods-- > 0) {
					int index = r.nextInt(mods.size());
					String[] data = mods.get(index).split(",");
					switch (data[0]) {
						case "r":
							data[1] = DNumber.mutate(data[1], strength);
							break;
						case "e":
							data[1] = DNumber.mutate(data[1], strength);
							break;
						case "m": // TODO
							break;
						case "o":
							data[1] = DNumber.mutate(data[1], strength);
							data[2] = DNumber.mutate(data[2], strength);
							break;
					}
					mods.set(index, String.join(",", data));
				}
				
				out += ":" + String.join(":", mods);
			}
		}
		
		
		return out;
	}
	
	public static String randomLocation(int complexity) {
		Random r = new Random();
		String out = randomSource(r);
		int modifiers = r.nextInt(complexity);
		while (modifiers-- > 0)
			out += ":" + randomModifier(r);
		return out;
	}
	
	private static String randomSource(Random r) {
		switch (r.nextInt(4)) {
			case 0:
				return "h";
			case 1:
				return "f";
			case 2:
				return "r," + DNumber.randomNumber();
			case 3:
				return "l," + DNumber.randomNumber();
		}
		return null;
	}
	
	private static String randomModifier(Random r) {
		if (r.nextBoolean()) return "e," + DNumber.randomNumber();
		if (r.nextBoolean()) return "o," + DNumber.randomNumber() + "," + DNumber.randomNumber();
		return "r," + DNumber.randomNumber();
		// TODO M
	}

}

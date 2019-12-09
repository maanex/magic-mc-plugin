package de.maanex.magic.spell.dynamic.params;

import java.util.Random;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.dynamic.DynamicSpell;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;

public class DNumber {

	private DNumber() {
	}
	
	public static double parse(String code, DynamicSpell spell, MagicPlayer caster, WandType type, WandValues val) {
		try {
			return Double.parseDouble(code);
		} catch (NumberFormatException e) {
			switch (code.charAt(0)) {
				case 'r':
					double range = Double.parseDouble(code.substring(1));
					return Math.random() * range;
					
				default:
					return 0;
			}
		}
	}
	
	public static String mutate(String code, int strength) {
		if (strength == 0) return code;
		Random r = new Random();
		try {
			double num = Double.parseDouble(code);
			num += r.nextInt(strength) * (r.nextBoolean() ? 1 : -1);
			num /= 2;
			return num + "";
		} catch (Exception e) {
			switch (code.charAt(0)) {
				case 'r':
					double num = Double.parseDouble(code.substring(1));
					num += r.nextInt(strength) * (r.nextBoolean() ? 1 : -1);
					num /= 2;
					return "r" + num;
			}
		}
		return code;
	}
	
	public static String randomNumber() {
		Random r = new Random();
		boolean dynamic = r.nextInt(10) == 0;
		if (!dynamic) {
			return "" + randNum(r);
		} else {
			switch (r.nextInt(1)) {
				case 0:
					return "r" + randNum(r); 
			}
		}
		
		return "0";
	}
	
	private static double randNum(Random r) {
		return (double) (Math.min(r.nextInt(r.nextInt(3000) + 1), r.nextInt(r.nextInt(3000) + 1)) * (r.nextBoolean() ? 1 : -1)) / 100;
	}

}

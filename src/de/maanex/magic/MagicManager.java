package de.maanex.magic;


import java.util.ArrayList;
import java.util.List;


public class MagicManager {

	private MagicManager() {
	}

	private static List<MagicSpell> spells = new ArrayList<>();

	//

	static {}

	//

	public static void registerSpell(MagicSpell s) {
		spells.add(s);
	}

	public static void unregisterSpell(MagicSpell s) {
		spells.remove(s);
	}

	public static void unregisterSpell(Class<? extends MagicSpell> s) {
		for (MagicSpell c : new ArrayList<>(spells))
			if (c.getClass().equals(s)) spells.remove(c);
	}

	public static List<MagicSpell> getAllSpells() {
		return spells;
	}

	@SuppressWarnings("unchecked")
	public static <T extends MagicSpell> T getSpell(Class<T> cls) {
		for (MagicSpell s : spells)
			if (cls.isInstance(s)) return (T) s;
		return null;
	}
}

package de.maanex.magic.database;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.maanex.magic.MagicManager;
import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.SpellRecipe;


public class ManaDbInterface {

	private ManaDbInterface() {
	}

	public static void saveValues(MagicPlayer player) {
		Database.set(struuid(player) + ".mana", player.getMana());
		Database.set(struuid(player) + ".manaCap", player.getManaCap());
		Database.set(struuid(player) + ".maxMana", player.getMaxMana());
		Database.set(struuid(player) + ".researchedRecipes", processResRecSave(player.getResearchedRecipes()));
	}

	public static void parseValues(MagicPlayer player) {
		player.setMana(Database.get(struuid(player) + ".mana", Integer.class));
		player.setManaCap(Database.get(struuid(player) + ".manaCap", Integer.class));
		player.setMaxMana(Database.get(struuid(player) + ".maxMana", Integer.class));
		player.setResearchedRecipes(processResRecSave(Database.get(struuid(player) + ".researchedRecipes")));
	}

	private static String struuid(MagicPlayer player) {
		return player.getMCPlayer().getUniqueId().toString();
	}

	/*
	 * Tools
	 */

	private static Object processResRecSave(List<SpellRecipe> res) {
		String out = "";
		for (SpellRecipe r : res) {
			out += ";" + r.getSpell1().getID() + "-" + r.getSpell2().getID() + "-" + r.getResult().getID();
		}
		out = out.replaceFirst(";", "");
		return out;
	}

	private static List<SpellRecipe> processResRecSave(Object in) {
		String s = String.valueOf(in);
		List<SpellRecipe> out = new ArrayList<>();
		List<String> r = Arrays.asList(s.split(";"));

		for (SpellRecipe e : MagicManager.getAllSpellRecipes()) {
			if (r.contains(e.getSpell1().getID() + "-" + e.getSpell2().getID() + "-" + e.getResult().getID())) out.add(e);
		}

		return out;
	}
}

package de.maanex.magic.database;


import de.maanex.magic.MagicPlayer;


public class ManaDbInterface {

	private ManaDbInterface() {
	}

	public static void saveValues(MagicPlayer player) {
		Database.set(struuid(player) + ".mana", player.getMana());
		Database.set(struuid(player) + ".manaCap", player.getManaCap());
		Database.set(struuid(player) + ".maxMana", player.getMaxMana());
	}

	public static void parseValues(MagicPlayer player) {
		player.setMana(Database.get(struuid(player) + ".mana", Integer.class));
		player.setManaCap(Database.get(struuid(player) + ".manaCap", Integer.class));
		player.setMaxMana(Database.get(struuid(player) + ".maxMana", Integer.class));
	}

	private static String struuid(MagicPlayer player) {
		return player.getMCPlayer().getUniqueId().toString();
	}

}

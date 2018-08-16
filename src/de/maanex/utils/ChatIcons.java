package de.maanex.utils;


public enum ChatIcons {

	ELEMENTUM("elementum", 0), //
	EARTH("earth", 1), //
	WATER("water", 2), //
	FIRE("fire", 3), //
	AIR("air", 4),//

	;

	private String	alias;
	private int		id;

	ChatIcons(String alias, int id) {
		this.alias = alias;
		this.id = id;
	}

	public String get() {
		return ChatIcons.get(id);
	}

	//

	public static String get(int id) {
		return Character.toString((char) (0x9000 + id));
	}

	public static String translate(String s) {
		for (ChatIcons i : ChatIcons.values())
			s = s.replace(":" + i.alias + ":", i.get());
		return s;
	}

}

package de.maanex.magic.spell;


public enum SpellRarity {

	BASIC("Fundamental", 0), COMMON("Gewöhnlich", 1), RARE("Selten", 2), VERY_RARE("Sehr Selten", 3), EPIC("Episch", 4), LEGENDARY("Legendär", 5), MYTHIC("Mythisch", 6), //

	FORBIDDEN("Verboten", 7), GODLIKE("Göttlich", 8)

	;

	private String	displayname;
	private int		sort;

	SpellRarity(String displayname, int sort) {
		this.displayname = displayname;
		this.sort = sort;
	}

	public String getDisplayName() {
		return displayname;
	}

	public int getSort() {
		return sort;
	}

}

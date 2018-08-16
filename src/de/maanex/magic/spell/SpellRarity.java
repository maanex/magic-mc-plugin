package de.maanex.magic.spell;


public enum SpellRarity {

	COMMON("Gewöhnlich"), RARE("Selten"), VERY_RARE("Sehr Selten"), EPIC("Episch"), LEGENDARY("Legendär"), MYTHIC("Mythisch"), //

	FORBIDDEN("Verboten"), GODLIKE("Göttlich")

	;

	private String displayname;

	SpellRarity(String displayname) {
		this.displayname = displayname;
	}

	public String getDisplayName() {
		return displayname;
	}

}

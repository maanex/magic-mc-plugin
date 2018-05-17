package de.maanex.magic.enumeri;


public enum SpellType {

	ACTIVE("Aktiv"), PASSIVE("Passiv")

	;

	private String displayname;

	SpellType(String displayname) {
		this.displayname = displayname;
	}

	public String getDisplayName() {
		return displayname;
	}

}

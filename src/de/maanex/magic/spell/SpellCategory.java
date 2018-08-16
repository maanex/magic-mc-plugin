package de.maanex.magic.spell;


public enum SpellCategory {

	COMBAT("Kampf"), PROTECTION("Schutz"), UTILITY("Werkzeug"), BENDER("Bendigung"), HALLUCINATION("Halluzination"), BUILDING("Konstruktion")

	;

	private String displayname;

	SpellCategory(String displayname) {
		this.displayname = displayname;
	}

	public String getDisplayName() {
		return displayname;
	}

}

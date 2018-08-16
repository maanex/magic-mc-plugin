package de.maanex.magic.basic;


import de.maanex.utils.ChatIcons;


public enum Element {

	EARTH("Erde", ChatIcons.EARTH.get()), //
	FIRE("Feuer", ChatIcons.FIRE.get()), //
	WATER("Wasser", ChatIcons.WATER.get()), //
	AIR("Luft", ChatIcons.AIR.get()), //

	ESSENCE_DARK("Dunkle Essenz", "Dunkelheit"), //
	ESSENCE_LIGHT("Licht Essenz", "Licht"), //
	ESSENCE_BUILDER("Baumeister Essenz", "Baumeister"), //
	ESSENCE_BENDER("Elementarbendigungs Essenz", "Bendigung"),//

	;

	static {
		EARTH.compliment = AIR;
		AIR.compliment = EARTH;

		FIRE.compliment = WATER;
		WATER.compliment = FIRE;

		ESSENCE_DARK.compliment = ESSENCE_LIGHT;
		ESSENCE_LIGHT.compliment = ESSENCE_DARK;

		ESSENCE_BUILDER.compliment = ESSENCE_BENDER;
		ESSENCE_BENDER.compliment = ESSENCE_BUILDER;
	}

	//

	private String	display;
	private String	loredisplay;
	private Element	compliment;

	private Element(String display, String loredisplay) {
		this.display = display;
		this.loredisplay = loredisplay;
	}

}

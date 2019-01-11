package de.maanex.magic.basic;


import de.maanex.utils.ChatIcons;


public enum Element {

	EARTH("ee", "Erde", "§r" + ChatIcons.EARTH.get() + "§7 "), //
	FIRE("ef", "Feuer", "§r" + ChatIcons.FIRE.get() + "§c "), //
	WATER("ew", "Wasser", "§r" + ChatIcons.WATER.get() + "§b "), //
	AIR("ea", "Luft", "§r" + ChatIcons.AIR.get() + "§f "), //

	ESSENCE_DARK("ed", "Dunkle Essenz", "§r" + ChatIcons.DARK.get() + "§8 "), //
	ESSENCE_LIGHT("el", "Licht Essenz", "§r" + ChatIcons.LIGHT.get() + "§f "), //
	ESSENCE_BUILDER("eu", "Baumeister Essenz", "§r" + ChatIcons.BUILDER.get() + "§9 "), //
	ESSENCE_BENDER("eb", "Elementarbendigungs Essenz", "§r" + ChatIcons.BENDER.get() + "§6 "),//

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

	private String	id;
	private String	display;
	private String	loredisplay;
	private Element	compliment;

	private Element(String id, String display, String loredisplay) {
		this.id = id;
		this.display = display;
		this.loredisplay = loredisplay;
	}

	//

	public String getId() {
		return id;
	}

	public String getDisplay() {
		return display;
	}

	public String getLoredisplay() {
		return loredisplay;
	}

	public Element getCompliment() {
		return compliment;
	}

}

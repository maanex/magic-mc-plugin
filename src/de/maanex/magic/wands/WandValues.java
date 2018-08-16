package de.maanex.magic.wands;


import java.util.HashMap;
import java.util.List;

import de.maanex.magic.basic.Element;


public class WandValues {

	private HashMap<Element, Integer>		elements	= new HashMap<>();
	private HashMap<WandModifier, Integer>	mods		= new HashMap<>();

	public WandValues(int earth, int fire, int water, int air) {
		elements.put(Element.EARTH, earth);
		elements.put(Element.FIRE, fire);
		elements.put(Element.WATER, water);
		elements.put(Element.AIR, air);
	}

	public WandValues setElement(Element e, int i) {
		elements.put(e, i);
		return this;
	}

	public WandValues setMod(WandModifier m, int i) {
		mods.put(m, i);
		return this;
	}

	public int getElement(Element e) {
		return elements.get(e);
	}

	public int getMod(WandModifier m) {
		return mods.get(m);
	}

	//

	public void addLore(List<String> lore) {
		elements.forEach((e, a) -> lore.add(e + ": " + a));

		if (!mods.isEmpty()) {
			lore.add("");
			mods.forEach((m, a) -> lore.add(m + ": " + a));
		}
	}

	/*
	 * 
	 */

	public static enum WandModifier {
		SAVETY("Sicherheit"), //
		MANAUSE("Manaverbrauch"), //
		COOLDOWNREDUCTION("Abklingzeitverringerung"),//

		;

		private String display;

		private WandModifier(String display) {
			this.display = display;
		}

		public String getDisplay() {
			return display;
		}
	}

}

package de.maanex.magic.wands;


import java.util.HashMap;
import java.util.List;
import java.util.Random;

import de.maanex.magic.basic.Element;


public class WandValues {

	int										xp			= 0;
	private HashMap<Element, Integer>		elements	= new HashMap<>();
	private HashMap<WandModifier, Integer>	mods		= new HashMap<>();

	public WandValues(int earth, int fire, int water, int air) {
		elements.put(Element.EARTH, earth);
		elements.put(Element.FIRE, fire);
		elements.put(Element.WATER, water);
		elements.put(Element.AIR, air);
	}

	public WandValues setXp(int xp) {
		this.xp = xp;
		return this;
	}

	public int getXp() {
		return xp;
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
		return elements.containsKey(e) ? elements.get(e) : 0;
	}

	public int getMod(WandModifier m) {
		return mods.containsKey(m) ? mods.get(m) : m.getNorm();
	}

	public int getModsSize() {
		return mods.size();
	}

	//

	private String meta = "";

	public void addLore(List<String> lore) {
		meta = "";

		// lore.add(xp + ""); // TODO
		// lore.add("");

		elements.forEach((e, a) -> {
			lore.add(e.getLoredisplay() + a);
			meta += ";" + e.getId() + a;
		});

		if (!mods.isEmpty()) {
			lore.add("");
			mods.forEach((m, a) -> {
				lore.add(m.getLoreDisplay().replace("#", a + ""));
				meta += ";" + m.getId() + a;
			});
		}

		lore.add("");
		lore.add("§0" + meta.substring(1));
	}

	public static WandValues fromLore(List<String> lore) {
		WandValues v = new WandValues(0, 0, 0, 0);

		String last = lore.get(lore.size() - 1).substring(2);
		for (String s : last.split(";")) {
			String a = s.charAt(0) + "";
			String sub = s.charAt(1) + "";
			int val = Integer.parseInt(s.substring(2));

			switch (a) {
				case "x":
					v.xp = Integer.parseInt(s.substring(1));
					break;

				case "e":
					for (Element e : Element.values())
						if (e.getId().equalsIgnoreCase("e" + sub)) {
							v.elements.put(e, val);
						}
					break;

				case "m":
					for (WandModifier m : WandModifier.values())
						if (m.getId().equalsIgnoreCase("m" + sub)) {
							v.mods.put(m, val);
						}
					break;
			}
		}

		return v;
	}

	/*
	 * 
	 */

	public static enum WandModifier {

		SAVETY("ms", "Sicherheit", "§eSicherheit§6: §e#§6%", 100, 50), //
		MANAUSE("mm", "Manaverbrauch", "§3Manaverbrauch§9: §3#§9%", 100, 30), //
		COOLDOWNREDUCTION("mc", "Cooldown", "§7Cooldown§8: §7#§8%", 100, 50), //
		MANAREG("mr", "Manaregeneration", "§bManaregeneration§1: §b#§1%", 100, 20), //
		XPBONUS("mx", "Erfahrungsbonus", "§2Erfahrungsbonus§a: §2#§a%", 100, 50),//

		;

		private String	id;
		private String	display;
		private String	loredisplay;
		private int		valNorm;
		private int		valAlter;

		private WandModifier(String id, String display, String loredisplay, int valNorm, int valAlter) {
			this.id = id;
			this.display = display;
			this.loredisplay = loredisplay;
			this.valNorm = valNorm;
			this.valAlter = valAlter;
		}

		public String getId() {
			return id;
		}

		public String getDisplay() {
			return display;
		}

		public String getLoreDisplay() {
			return loredisplay;
		}

		public int randValue(Random r) {
			int mod = r.nextInt(valAlter);
			int x = (r.nextBoolean() ? 1 : -1) * mod + valNorm;

			if (this.equals(SAVETY) && x > 100) x = 100;
			if (this.equals(MANAREG) && x < 100) x = 100;
			if (this.equals(XPBONUS) && x < 100) x = 100;

			return x;
		}

		public int getNorm() {
			return valNorm;
		}
	}

}

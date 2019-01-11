package de.maanex.magic.wands;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.World.Environment;

import de.maanex.magic.basic.Element;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spells.basic.AirSpirit;
import de.maanex.magic.spells.basic.EarthSpirit;
import de.maanex.magic.spells.basic.Elementum;
import de.maanex.magic.spells.basic.EssenceBender;
import de.maanex.magic.spells.basic.EssenceBrightness;
import de.maanex.magic.spells.basic.EssenceDarkness;
import de.maanex.magic.spells.basic.FireSpirit;
import de.maanex.magic.spells.basic.WaterSpirit;
import de.maanex.magic.spells.building.MasterBuildersEssence;
import de.maanex.magic.wands.WandValues.WandModifier;


public class WandGenerator {

	private static List<Class<? extends MagicSpell>> valid;

	private static HashMap<Short, String> skinNames = new HashMap<>();

	static {
		valid = new ArrayList<>();
		valid.add(Elementum.class);
		valid.add(FireSpirit.class);
		valid.add(WaterSpirit.class);
		valid.add(EarthSpirit.class);
		valid.add(AirSpirit.class);
		valid.add(EssenceBender.class);
		valid.add(MasterBuildersEssence.class);
		valid.add(EssenceBrightness.class);
		valid.add(EssenceDarkness.class);
	}

	static {
		skinNames.put((short) 1, "§bUnidentifizierter Zauberstab");
		skinNames.put((short) 2, "§bGewöhnlicher Zauberstab");
		skinNames.put((short) 3, "§bLangstab");
		skinNames.put((short) 4, "§bLichtbringer Stab");
		skinNames.put((short) 5, "§bVerstärkter Zauberstab");
		skinNames.put((short) 6, "§bSonnenstab");
		skinNames.put((short) 7, "§cDunkelstab");
		skinNames.put((short) 8, "§cStab der ewigen Nacht");
		skinNames.put((short) 9, "§6Lichtstab");
		skinNames.put((short) 10, "§6Stab des ewigen Tages");
		skinNames.put((short) 11, "§aNachthüter Stab");
		skinNames.put((short) 12, "§aNachtwächter Stab");
		skinNames.put((short) 13, "§aWeltenentdecker Stab");
		skinNames.put((short) 14, "§aWeltenreiser Stab");
	}

	private WandGenerator() {
	}

	//

	public static boolean valid(List<Class<? extends MagicSpell>> mods) {
		for (Class<? extends MagicSpell> s : mods)
			if (!valid.contains(s)) return false;
		return true;
	}

	public static Wand create(List<Class<? extends MagicSpell>> mods, String owner) {
		return create(mods, owner, null);
	}

	public static Wand create(List<Class<? extends MagicSpell>> mods, String owner, Environment env) {
		Random r = new Random();

		WandValues values;

		int maxele = 25 + r.nextInt(5);

		int a = 0, b = 0, c = 0, d = 0;
		a = Math.min(r.nextInt(maxele), r.nextInt(maxele));
		maxele -= a;
		b = Math.min(r.nextInt(maxele), r.nextInt(maxele));
		maxele -= b;
		c = Math.min(r.nextInt(maxele), r.nextInt(maxele));
		maxele -= c;
		d = Math.min(r.nextInt(maxele), r.nextInt(maxele));

		switch (r.nextInt(4)) {
			case 0:
				values = r.nextBoolean() ? new WandValues(a, b, c, d) : new WandValues(a, c, b, d);
				break;

			case 1:
				values = r.nextBoolean() ? new WandValues(d, b, c, a) : new WandValues(d, c, b, a);
				break;

			case 2:
				values = r.nextBoolean() ? new WandValues(b, a, d, c) : new WandValues(b, d, a, c);
				break;

			case 3:
				values = r.nextBoolean() ? new WandValues(c, a, d, b) : new WandValues(c, d, a, b);
				break;

			default:
				values = new WandValues(0, 0, 0, 0);
		}

		// Applying mods

		int mfire = 0, mwater = 0, mair = 0, mearth = 0, mmods = 0, mdark = 0, mlight = 0, mbuild = 0, mbend = 0;
		for (Class<? extends MagicSpell> s : mods) {
			if (s.equals(Elementum.class)) mmods++;
			if (s.equals(FireSpirit.class)) mfire++;
			if (s.equals(WaterSpirit.class)) mwater++;
			if (s.equals(EarthSpirit.class)) mearth++;
			if (s.equals(AirSpirit.class)) mair++;
			if (s.equals(EssenceBender.class)) mbend++;
			if (s.equals(MasterBuildersEssence.class)) mbuild++;
			if (s.equals(EssenceBrightness.class)) mlight++;
			if (s.equals(EssenceDarkness.class)) mdark++;
		}

		int fadd = mfire * 2 + r.nextInt(mfire * 2 + 1), aadd = mair * 2 + r.nextInt(mair * 2 + 1), wadd = mwater * 2 + r.nextInt(mwater * 2 + 1), eadd = mearth * 2 + r.nextInt(mearth * 2 + 1);

		aadd -= fadd / 3;
		wadd -= fadd / 3;
		eadd -= fadd / 3;

		fadd -= aadd / 3;
		wadd -= aadd / 3;
		eadd -= aadd / 3;

		fadd -= wadd / 3;
		aadd -= wadd / 3;
		eadd -= wadd / 3;

		fadd -= eadd / 3;
		aadd -= eadd / 3;
		wadd -= eadd / 3;

		values.setElement(Element.FIRE, Math.max(0, values.getElement(Element.FIRE) + fadd));
		values.setElement(Element.WATER, Math.max(0, values.getElement(Element.WATER) + wadd));
		values.setElement(Element.AIR, Math.max(0, values.getElement(Element.AIR) + aadd));
		values.setElement(Element.EARTH, Math.max(0, values.getElement(Element.EARTH) + eadd));

		if (mdark > 0 && mlight > 0) {
			mdark = 0;
			mlight = 0;
		}
		if (mbuild > 0 && mbend > 0) {
			mbuild = 0;
			mbend = 0;
		}

		if (mdark > 0) values.setElement(Element.ESSENCE_DARK, mdark + r.nextInt(mdark * 3));
		if (mlight > 0) values.setElement(Element.ESSENCE_LIGHT, mlight + r.nextInt(mlight * 3));
		if (mbuild > 0) values.setElement(Element.ESSENCE_BUILDER, mbuild + r.nextInt(mbuild * 3));
		if (mbend > 0) values.setElement(Element.ESSENCE_BENDER, mbend + r.nextInt(mbend * 3));

		//

		int modis = r.nextInt(mmods / 2 + 2) + 1 + mmods / 2;

		while (modis-- > 0) {
			WandModifier m = WandModifier.values()[r.nextInt(WandModifier.values().length)];
			if (values.getMod(m) == m.getNorm()) {
				int v;
				do {
					v = m.randValue(r);
				} while (v == m.getNorm());
				values.setMod(m, v);
			} else if (r.nextBoolean()) modis++;
		}

		//

		WandType type = WandType.WOODEN;

		if (mdark > 0) type = WandType.DARK;
		if (mlight > 0) type = WandType.LIGHT;

		short skin = getFittingSkin(values, type, env);
		String name = skinNames.get(skin);

		return Wand.builder().setOwner(owner).setSkin(skin).setName(name).setType(type).setValues(values).build();
		// return null; // TODO
	}

	private static short getFittingSkin(WandValues val, WandType type, Environment environment) {
		int nrg = val.getMod(WandModifier.ENERGY);
		int mnu = val.getMod(WandModifier.MANAUSE);
		if (nrg == -1) return 1;

		if (type.equals(WandType.DARK)) {
			if (nrg >= 115) return 8;
			else return 7;
		}

		if (type.equals(WandType.LIGHT)) {
			if (nrg >= 115) return 10;
			else return 9;
		}

		if (Environment.NETHER.equals(environment)) {
			if (nrg > 110) return 12;
			if (mnu < 90) return 11;
		}

		if (Environment.THE_END.equals(environment)) {
			if (nrg > 110) return 14;
			if (mnu < 90) return 13;
		}

		if (new Random().nextInt(30) == 0) return 6;

		if (Math.abs(nrg - 100) > 20 || Math.abs(mnu - 100) > 20) return 3;

		if (val.getModsSize() > 1) return 5;

		if (val.getElement(Element.AIR) > 20 || val.getElement(Element.EARTH) > 20 || val.getElement(Element.WATER) > 20 || val.getElement(Element.FIRE) > 20) { return 4; }

		return 2;
	}

}

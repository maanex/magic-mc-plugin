package de.maanex.magic.generators;


import java.util.Random;

import de.maanex.magic.WandModifiers;


public class WandModsGen {

	private WandModsGen() {
	}

	public static WandModifiers generate() {
		Random r = new Random();
		int[] val = new int[] { 100, 100, 100 };

		int dyn = r.nextInt(3);

		int yo = 0;
		for (int i = 0; i < 3; i++) {
			if (i != dyn) {
				int q = (r.nextInt(20) - 10);
				yo += q;
				val[i] = 100 + q;
			}
		}
		val[dyn] = 100 - yo;

		return new WandModifiers(-val[0] + 200, Math.min(100, val[1]), val[2]);
	}

}

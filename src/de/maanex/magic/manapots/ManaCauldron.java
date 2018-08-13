package de.maanex.magic.manapots;


import java.util.HashMap;

import org.bukkit.block.Block;


public class ManaCauldron {

	private static HashMap<Block, ManaCauldron> cauldrons = new HashMap<>();

	public static ManaCauldron fromBlock(Block b) {
		if (cauldrons.containsKey(b)) return cauldrons.get(b);

		ManaCauldron c = new ManaCauldron(b);
		cauldrons.put(b, c);
		return c;
	}

	public static void update() {
		for (ManaCauldron c : cauldrons.values())
			c.tick();
	}

	//

	private Block b;

	private double	mana;
	private int		maxmana;
	private double	saturation;

	private ManaCauldron(Block b) {
		this.b = b;

		this.mana = 0;
		this.maxmana = 10;
		this.saturation = 0;
	}

	//

	int tick = 0;

	public void tick() {
		if (tick++ > 100) tick = 0;
		if (maxmana > 10) if (tick % 10 == 0) maxmana--;

		if (mana > maxmana) {
			double delta = mana - maxmana;
			mana -= delta / 10;
		}

		if (saturation > mana) {
			double delta = saturation - mana;
			saturation -= delta / 10;
		}
	}

	//

	public void clear() {
		mana = 0;
		maxmana = Math.max(10, maxmana / 2);
		saturation = 0;
	}

	public Block getBlock() {
		return b;
	}

	public int getMana() {
		return (int) mana;
	}

	public void addMana(int mana) {
		this.mana += mana;
		if (this.mana < 0) this.mana = 0;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getMaxMana() {
		return maxmana;
	}

	public void addMaxMana(int maxmana) {
		this.maxmana += maxmana;
		if (this.maxmana < 0) this.maxmana = 0;
	}

	public void setMaxMana(int maxmana) {
		this.maxmana = maxmana;
	}

	public int getSaturation() {
		return (int) saturation;
	}

	public void addSaturation(int saturation) {
		this.saturation += saturation;
		if (this.saturation < 0) this.saturation = 0;
	}

	public void setSaturation(int saturation) {
		this.saturation = saturation;
	}

}

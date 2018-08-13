package de.maanex.magic.customEffects;


import de.maanex.magic.MagicPlayer;


public class ManaRegenEffect extends MagicEffect {

	public int mana;

	public ManaRegenEffect(int duration, int mana) {
		super(duration);
		this.mana = mana;
	}

	@Override
	public void tick(MagicPlayer m) {
		int add = mana / Math.max(1, duration);
		m.addMana(add, false);
	}

}

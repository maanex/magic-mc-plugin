package de.maanex.magic.customEffects;


public class ManaRegenEffect extends MagicEffect {

	public int level;

	public ManaRegenEffect(int duration, int level) {
		super(duration);
		this.level = level;
	}

}

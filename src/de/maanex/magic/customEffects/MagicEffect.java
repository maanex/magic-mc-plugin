package de.maanex.magic.customEffects;

import de.maanex.magic.MagicPlayer;

public abstract class MagicEffect {

	public int duration;

	public MagicEffect(int duration) {
		this.duration = duration;
	}

	public void tick(MagicPlayer m) {
	}

}

package de.maanex.magic.wands;


import de.maanex.magic.MagicPlayer;


public class WandBuilder {

	private Wand w;

	WandType	type;
	short		skin;
	String		name;
	String		owner;
	WandValues	values;

	WandBuilder(Wand wand) {
		this.w = wand;

		this.owner = "";
	}

	//

	public WandBuilder applyPreset(WandPreset preset) {
		preset.apply(this);
		return this;
	}

	public WandBuilder setType(WandType type) {
		this.type = type;
		return this;
	}

	public WandBuilder setSkin(int skin) {
		this.skin = (short) skin;
		return this;
	}

	public WandBuilder setSkin(short skin) {
		this.skin = skin;
		return this;
	}

	public WandBuilder setOwner(MagicPlayer player) {
		this.owner = player.getMCPlayer().getUniqueId().toString();
		return this;
	}

	public WandBuilder setOwner(String uuid) {
		this.owner = uuid;
		return this;
	}

	public WandBuilder setValues(WandValues values) {
		this.values = values;
		return this;
	}

	//

	public Wand build() {
		w.apply(this);
		return w;
	}

	/*
	 * 
	 */

	public static enum WandPreset {

		UNIDENTIFIED(WandType.UNIDENTIFIED, 1, WandType.UNIDENTIFIED.getDisplayname(), null),//

		;

		private WandType	type;
		private short		skin;
		private String		name;
		private WandValues	values;

		private WandPreset(WandType type, int skin, String name, WandValues values) {
			this.type = type;
			this.skin = (short) skin;
			this.name = name;
			this.values = values;
		}

		//

		public void apply(WandBuilder builder) {
			builder.type = type;
			builder.skin = skin;
			builder.name = name;
			builder.values = values;
		}

	}

}

package de.maanex.magic;


import java.util.HashMap;

import de.maanex.magic.wandsuse.WandType;


public abstract class MagicSpell {

	private String				name, desc;
	private BuildRequirements	req;
	private int					id, manacost;
	private WandType			reqWandType	= null;

	public MagicSpell(int id, String name, String desc, int manacost, BuildRequirements req) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.manacost = manacost;
		this.req = req;
	}

	public MagicSpell(int id, String name, String desc, int manacost, BuildRequirements req, WandType reqWandType) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.manacost = manacost;
		this.req = req;
		this.reqWandType = reqWandType;
	}

	protected abstract void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods);

	protected boolean canAffortSpell(MagicPlayer caster, WandModifiers mods) {
		return (int) (manacost * ((double) mods.getManause() / 100)) <= caster.getMana();
	}

	protected void takeMana(MagicPlayer caster, WandModifiers mods) {
		caster.addMana((int) (-manacost * ((double) mods.getManause() / 100)));
	}

	public void interaction(MagicPlayer player, WandType type, WandModifiers mod) {
		if (canAffortSpell(player, mod)) {
			if (reqWandType != null) {
				if (type.equals(reqWandType)) onCastPerform(player, type, mod);
				else player.getMCPlayer().sendMessage("§7Du benötigst für diesen Zauber einen geeigneten Zauberstab!");
			} else onCastPerform(player, type, mod);
		} else player.getMCPlayer().sendMessage("§7Du hast nicht genug Mana!");
	}

	public BuildRequirements getBuildRequirements() {
		return this.req;
	}

	public String getName() {
		return this.name;
	}

	public String getDesc() {
		return this.desc;
	}

	public int getID() {
		return this.id;
	}

	public int getManacost() {
		return this.manacost;
	}

	public WandType getRequiredWandType() {
		return this.reqWandType;
	}

	/*
	 * 
	 */

	public static class BuildRequirements {

		int water_min, water_max, wood_min, wood_max, fire_min, fire_max, earth_min, earth_max, metal_min, metal_max, common;

		public BuildRequirements(int water_min, int water_max, int wood_min, int wood_max, int fire_min, int fire_max, int earth_min, int earth_max, int metal_min, int metal_max, int common) {
			this.water_min = water_min;
			this.water_max = water_max;
			this.wood_min = wood_min;
			this.wood_max = wood_max;
			this.fire_min = fire_min;
			this.fire_max = fire_max;
			this.earth_min = earth_min;
			this.earth_max = earth_max;
			this.metal_min = metal_min;
			this.metal_max = metal_max;
			this.common = common;
		}

		public int getWater_min() {
			return water_min;
		}

		public int getWater_max() {
			return water_max;
		}

		public int getWood_min() {
			return wood_min;
		}

		public int getWood_max() {
			return wood_max;
		}

		public int getFire_min() {
			return fire_min;
		}

		public int getFire_max() {
			return fire_max;
		}

		public int getEarth_min() {
			return earth_min;
		}

		public int getEarth_max() {
			return earth_max;
		}

		public int getMetal_min() {
			return metal_min;
		}

		public int getMetal_max() {
			return metal_max;
		}

		public int getCommon() {
			return common;
		}

		public boolean doesMeetRequirements(HashMap<Elements, Integer> in) {
			return in.get(Elements.WATER) >= water_min && in.get(Elements.WATER) <= water_max //
					&& in.get(Elements.WOOD) >= wood_min && in.get(Elements.WOOD) <= wood_max //
					&& in.get(Elements.FIRE) >= fire_min && in.get(Elements.FIRE) <= fire_max //
					&& in.get(Elements.EARTH) >= earth_min && in.get(Elements.EARTH) <= earth_max //
					&& in.get(Elements.METAL) >= metal_min && in.get(Elements.METAL) <= metal_max //
			;
		}

	}

}

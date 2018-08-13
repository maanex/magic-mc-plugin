package de.maanex.survival.customOres;


import org.bukkit.Material;


public enum CustomItems {

	CRYSTALLIT("Crystallit", Material.POLAR_BEAR_SPAWN_EGG), //
	TUDIUM("Tudium", Material.GHAST_SPAWN_EGG), //

	;

	private String		displayname;
	private Material	item;

	private CustomItems(String displayname, Material item) {
		this.displayname = displayname;
		this.item = item;
	}

	public String getDisplayname() {
		return displayname;
	}

	public Material getItem() {
		return item;
	}
}

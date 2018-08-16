package de.maanex.survival.customOres;


import org.bukkit.Material;


public enum CustomBlocks {

	ENDERIT("Enderit", Material.STRIPPED_OAK_WOOD), //
	TUDIUM("Tudium", Material.STRIPPED_SPRUCE_WOOD), //
	CRYSTALLIT("Crystallit", Material.STRIPPED_BIRCH_WOOD), //

	WAND_WORKBENCH("Zauberstab Werkbank", Material.STRIPPED_JUNGLE_WOOD), //

	UNUSED0("TODO", Material.STRIPPED_ACACIA_WOOD), //
	UNUSED1("TODO", Material.STRIPPED_DARK_OAK_WOOD),//

	;

	private String		displayname;
	private Material	block;

	private CustomBlocks(String displayname, Material block) {
		this.displayname = displayname;
		this.block = block;
	}

	public String getDisplayname() {
		return displayname;
	}

	public Material getBlock() {
		return block;
	}

}

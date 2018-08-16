package de.maanex.magic.wands;


import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public enum WandType {

	UNIDENTIFIED("§3Unidentifizierter Zauberstab", "unidentified_wand"), //
	WOODEN("§bZauberstab", "wooden_wand"), //
	DARK("§cDunkelstab", "dark_wand"), //
	LIGHT("§6Lichtstab", "light_wand"),//

	;

	private String displayname, lorename;

	WandType(String displayname, String lorename) {
		this.displayname = displayname;
		this.lorename = lorename;
	}

	public String getLoreName() {
		return lorename;
	}

	public String getDisplayname() {
		return displayname;
	}

	public static WandType getFromLoreName(String lorename) {
		for (WandType type : values())
			if (type.lorename.equalsIgnoreCase(lorename)) return type;
		return null;
	}

	public static WandType getFromItem(ItemStack s) {
		if (s == null) return null;
		if (!s.hasItemMeta()) return null;
		ItemMeta m = s.getItemMeta();
		if (!m.hasLore()) return null;
		String txt = m.getLore().get(0);
		if (!txt.startsWith("§0")) return null;
		return getFromLoreName(txt.replace("§0", ""));
	}

}

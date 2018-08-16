package de.maanex.magic.crafting;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

import de.maanex.magic.wands.Wand;
import de.maanex.magic.wands.WandBuilder.WandPreset;


public class Wands {

	public Wands() {
	}

	@SuppressWarnings("deprecation")
	public static void registerRecipe() {
		// ShapedRecipe res = new
		// ShapedRecipe(LegacyWandBuilder.get(Environment.NORMAL).withMods(new
		// LegacyWandModifiers(-1, -1, -1)).build());
		ShapedRecipe res = new ShapedRecipe(Wand.builder().applyPreset(WandPreset.UNIDENTIFIED).build().asItem());

		res.shape("AAI", "ASA", "IAA");

		res.setIngredient('A', Material.AIR);
		res.setIngredient('S', Material.STICK);
		res.setIngredient('I', Material.IRON_NUGGET);

		Bukkit.addRecipe(res);
	}

}

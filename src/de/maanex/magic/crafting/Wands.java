package de.maanex.magic.crafting;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.inventory.ShapedRecipe;

import de.maanex.magic.items.WandBuilder;


public class Wands {

	public Wands() {
	}

	@SuppressWarnings("deprecation")
	public static void registerRecipe() {
		ShapedRecipe res = new ShapedRecipe(WandBuilder.get(Environment.NORMAL).build());

		res.shape("AAI", "ASA", "IAA");

		res.setIngredient('A', Material.AIR);
		res.setIngredient('S', Material.STICK);
		res.setIngredient('I', Material.IRON_NUGGET);

		Bukkit.addRecipe(res);
	}

}

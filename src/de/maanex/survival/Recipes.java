package de.maanex.survival;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;


public class Recipes {

	private Recipes() {
	}

	public static void registerAll() {
		registerSmoothStone();
	}

	public static void registerSmoothStone() {
		ShapedRecipe res = new ShapedRecipe(new ItemStack(Material.SMOOTH_STONE));

		res.shape("000", "0B0", "0B0");

		res.setIngredient('B', Material.STONE_SLAB);

		Bukkit.addRecipe(res);
	}

}

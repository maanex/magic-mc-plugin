package de.maanex.survival;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import de.maanex.survival.customItems.CustomItems;
import de.maanex.survival.customOres.CustomBlocks;
import de.maanex.survival.customOres.CustomOreItems;


@SuppressWarnings("deprecation")
public class Recipes {

	private Recipes() {
	}

	public static void registerAll() {
		registerSmoothStone();
		registerCrystallitPickaxe();
		registerEndOrb();
		registerWandWorkbench();
	}

	public static void registerSmoothStone() {
		ShapedRecipe res = new ShapedRecipe(new ItemStack(Material.SMOOTH_STONE));

		res.shape("000", "0B0", "0B0");

		res.setIngredient('B', Material.STONE_SLAB);

		Bukkit.addRecipe(res);
	}

	public static void registerCrystallitPickaxe() {
		ShapedRecipe res = new ShapedRecipe(CustomItems.CRYSTALLIT_PICKAXE.getItem());

		res.shape("ECE", "0S0", "0S0");

		res.setIngredient('E', Material.EMERALD);
		res.setIngredient('C', CustomOreItems.CRYSTALLIT.getItem());
		res.setIngredient('S', Material.STICK);

		Bukkit.addRecipe(res);
	}

	public static void registerEndOrb() {
		ShapedRecipe res = new ShapedRecipe(CustomItems.END_ORB.getItem());

		res.shape("EEE", "EOE", "EEE");

		res.setIngredient('E', CustomOreItems.ENDERIT.getItem());
		res.setIngredient('O', Material.OBSIDIAN);

		Bukkit.addRecipe(res);
	}

	public static void registerWandWorkbench() {
		ShapedRecipe res = new ShapedRecipe(new ItemStack(CustomBlocks.WAND_WORKBENCH.getBlock()));

		res.shape("EEE", "EWE", "EEE");

		res.setIngredient('E', Material.IRON_NUGGET);
		res.setIngredient('W', Material.CRAFTING_TABLE);

		Bukkit.addRecipe(res);
	}

}

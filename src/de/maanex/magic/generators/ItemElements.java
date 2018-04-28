package de.maanex.magic.generators;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.maanex.magic.Elements;


public class ItemElements {

	public ItemElements() {
	}

	private static HashMap<Material, int[]> elements = new HashMap<>();

	static {
		elements.put(Material.STONE, new int[] { 0, 0, 0, 1, 1 });
		elements.put(Material.GRASS, new int[] { 0, 0, 0, 3, 0 });
		elements.put(Material.DIRT, new int[] { 0, 0, 0, 2, 0 });
		elements.put(Material.COBBLESTONE, new int[] { 0, 0, 0, 1, 0 });
		elements.put(Material.WOOD, new int[] { 0, 3, 0, 0, 0 });
		elements.put(Material.SAND, new int[] { 2, 0, 2, 0, 0 });
		elements.put(Material.LOG, new int[] { 0, 5, 0, 0, 0 });
		elements.put(Material.NETHERRACK, new int[] { 0, 0, 2, 0, 0 });
		elements.put(Material.MAGMA, new int[] { 0, 0, 5, 0, 0 });
		elements.put(Material.ICE, new int[] { 2, 0, 0, 0, 0 });
		elements.put(Material.GLASS, new int[] { 0, 0, 1, 1, 0 });
		elements.put(Material.ENDER_STONE, new int[] { 1, 1, 1, 1, 1 });
		elements.put(Material.PRISMARINE, new int[] { 1, 0, 0, 1, 0 });

		//

		elements.put(Material.SAPLING, new int[] { 0, 1, 0, 1, 0 });
		elements.put(Material.CHEST, new int[] { 0, 2, 0, 0, 0 });
		elements.put(Material.IRON_FENCE, new int[] { 0, 0, 0, 0, 2 });
		elements.put(Material.ANVIL, new int[] { 0, 0, 0, 0, 10 });
		elements.put(Material.ENDER_CHEST, new int[] { 2, 2, 2, 2, 2 });
		elements.put(Material.DOUBLE_PLANT, new int[] { 1, 1, 0, 1, 0 });

		//

		elements.put(Material.IRON_DOOR, new int[] { 0, 0, 0, 1, 2 });
		elements.put(Material.OBSERVER, new int[] { 0, 0, 1, 1, 1 });

		//

		elements.put(Material.MINECART, new int[] { 0, 1, 0, 0, 2 });
		elements.put(Material.RAILS, new int[] { 0, 0, 0, 1, 1 });
		elements.put(Material.BOAT, new int[] { 2, 2, 0, 0, 0 });

		//

		elements.put(Material.BEACON, new int[] { 15, 15, 15, 15, 15 });
		elements.put(Material.COAL, new int[] { 0, 0, 1, 2, 1 });
		elements.put(Material.DIAMOND, new int[] { 1, 1, 2, 3, 3 });
		elements.put(Material.IRON_INGOT, new int[] { 0, 0, 1, 1, 5 });
		elements.put(Material.GOLD_INGOT, new int[] { 0, 0, 0, 1, 6 });
		elements.put(Material.STICK, new int[] { 0, 1, 0, 0, 0 });
		elements.put(Material.WHEAT, new int[] { 1, 1, 0, 1, 0 });
		elements.put(Material.FLINT, new int[] { 0, 0, 1, 1, 0 });
		elements.put(Material.WATER_BUCKET, new int[] { 8, 0, 0, 0, 0 });
		elements.put(Material.LAVA_BUCKET, new int[] { 0, 0, 8, 0, 0 });
		elements.put(Material.PAPER, new int[] { 1, 2, 0, 0, 0 });
		elements.put(Material.ENDER_PEARL, new int[] { 0, 0, 1, 4, 0 });
		elements.put(Material.EMERALD, new int[] { 3, 2, 0, 1, 0 });

		//

		elements.put(Material.APPLE, new int[] { 2, 0, 0, 0, 0 });
		elements.put(Material.BREAD, new int[] { 1, 0, 0, 1, 0 });
		elements.put(Material.GOLDEN_APPLE, new int[] { 2, 2, 2, 2, 2 });
		elements.put(Material.GOLDEN_CARROT, new int[] { 1, 1, 1, 1, 1 });
		elements.put(Material.COOKED_FISH, new int[] { 3, 0, 0, 1, 0 });
		elements.put(Material.RAW_FISH, new int[] { 5, 0, 0, 0, 0 });
		elements.put(Material.CAKE, new int[] { 0, 0, 0, 2, 0 });
		elements.put(Material.COOKIE, new int[] { 1, 0, 0, 1, 0 });
		elements.put(Material.ROTTEN_FLESH, new int[] { 0, 1, 0, 3, 0 });
		elements.put(Material.POTATO, new int[] { 1, 0, 0, 0, 0 });
		elements.put(Material.PUMPKIN_PIE, new int[] { 1, 1, 0, 0, 0 });

		//

		//

		//

		elements.put(Material.BLAZE_POWDER, new int[] { 0, 0, 2, 0, 0 });
		elements.put(Material.MAGMA_CREAM, new int[] { 0, 0, 3, 0, 0 });
	}

	public static HashMap<Elements, Integer> getElementValue(ItemStack[] items) {
		return getElementValue(Arrays.asList(items));
	}

	public static HashMap<Elements, Integer> getElementValue(List<ItemStack> items) {
		HashMap<Elements, Integer> out = new HashMap<>();
		out.put(Elements.WATER, 0);
		out.put(Elements.WOOD, 0);
		out.put(Elements.FIRE, 0);
		out.put(Elements.EARTH, 0);
		out.put(Elements.METAL, 0);

		for (ItemStack i : items) {
			if (i == null) continue;
			if (elements.containsKey(i.getType())) {
				int[] e = elements.get(i.getType());
				out.put(Elements.WATER, out.get(Elements.WATER) + e[0]);
				out.put(Elements.WOOD, out.get(Elements.WOOD) + e[1]);
				out.put(Elements.FIRE, out.get(Elements.FIRE) + e[2]);
				out.put(Elements.EARTH, out.get(Elements.EARTH) + e[3]);
				out.put(Elements.METAL, out.get(Elements.METAL) + e[4]);
			}
		}

		return out;
	}

}

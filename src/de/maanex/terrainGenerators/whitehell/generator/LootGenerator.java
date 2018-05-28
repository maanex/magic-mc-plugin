package de.maanex.terrainGenerators.whitehell.generator;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;


public class LootGenerator {

	private LootGenerator() {
	}

	public static List<ItemStack> generateLoot(LootRarity a, Random r) {
		List<ItemStack> out = new ArrayList<>();

		// TODO

		return out;
	}

	public static void fillChest(Chest c, LootRarity a, Random r) {

	}

	public static enum LootRarity {

		COMMON, RARE, EPIC, LEGENDARY;

	}

}

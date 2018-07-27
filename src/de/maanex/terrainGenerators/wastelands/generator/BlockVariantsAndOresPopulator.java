package de.maanex.terrainGenerators.wastelands.generator;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;


@SuppressWarnings("deprecation")
public class BlockVariantsAndOresPopulator extends BlockPopulator {

	private static List<Material>	stone_variants	= new ArrayList<>();
	private static List<Material>	hard_variants	= new ArrayList<>();
	private static List<Material>	dirt_variants	= new ArrayList<>();
	private static List<Material>	ore_variants	= new ArrayList<>();

	static {
		stone_variants.add(Material.STONE);
		stone_variants.add(Material.ANDESITE);
		stone_variants.add(Material.POLISHED_ANDESITE);
		stone_variants.add(Material.CHISELED_STONE_BRICKS);
		stone_variants.add(Material.STONE_BRICKS);
		stone_variants.add(Material.COBBLESTONE);
		stone_variants.add(Material.SMOOTH_STONE);
		stone_variants.add(Material.GRAY_CONCRETE);

		dirt_variants.add(Material.DIRT);
		dirt_variants.add(Material.DIRT);
		dirt_variants.add(Material.DIRT);
		dirt_variants.add(Material.DIRT);
		dirt_variants.add(Material.SOUL_SAND);
		dirt_variants.add(Material.BROWN_CONCRETE_POWDER);

		hard_variants.add(Material.OBSIDIAN);
		hard_variants.add(Material.OBSIDIAN);
		hard_variants.add(Material.OBSIDIAN);
		hard_variants.add(Material.MAGMA_BLOCK);
		hard_variants.add(Material.MAGMA_BLOCK);
		hard_variants.add(Material.COAL_BLOCK);
		hard_variants.add(Material.BLACK_CONCRETE);
		hard_variants.add(Material.BLACK_CONCRETE_POWDER);

		ore_variants.add(Material.DIAMOND_ORE);
		ore_variants.add(Material.EMERALD_ORE);
		ore_variants.add(Material.GOLD_ORE);
		ore_variants.add(Material.IRON_ORE);
		ore_variants.add(Material.IRON_ORE);
		ore_variants.add(Material.IRON_ORE);
		ore_variants.add(Material.COAL_ORE);
		ore_variants.add(Material.COAL_ORE);
		ore_variants.add(Material.COAL_ORE);
		ore_variants.add(Material.COAL_ORE);
		ore_variants.add(Material.MAGMA_BLOCK);
		ore_variants.add(Material.MAGMA_BLOCK);
		ore_variants.add(Material.MAGMA_BLOCK);
		ore_variants.add(Material.MAGMA_BLOCK);
		ore_variants.add(Material.MAGMA_BLOCK);
		ore_variants.add(Material.MAGMA_BLOCK);
	}

	public BlockVariantsAndOresPopulator() {
	}

	@Override
	public void populate(World w, Random r, Chunk c) {
		for (int x = 0; x < 16; x++)
			for (int z = 0; z < 16; z++)
				for (int y = c.getWorld().getHighestBlockYAt(x + c.getX() * 16, z + c.getZ() * 16); y > 0; y--) {
					if (c.getBlock(x, y, z).getType().equals(Material.STONE)) {
						Material m = stone_variants.get(r.nextInt(stone_variants.size()));
						if (r.nextBoolean()) {
							c.getBlock(x, y, z).setType(m);
						}
						if (r.nextInt(100) == 0) {
							c.getBlock(x, y, z).setType(ore_variants.get(r.nextInt(ore_variants.size())));
						}
					} else if (c.getBlock(x, y, z).getType().equals(Material.OBSIDIAN)) {
						Material m = hard_variants.get(r.nextInt(hard_variants.size()));
						if (r.nextBoolean()) {
							if (r.nextInt(120) > y) m = Material.BEDROCK;

							c.getBlock(x, y, z).setType(m);
						}
					} else if (c.getBlock(x, y, z).getType().equals(Material.DIRT)) {
						Material m = dirt_variants.get(r.nextInt(dirt_variants.size()));
						if (r.nextBoolean()) {
							c.getBlock(x, y, z).setType(m);
						}
					}
				}

	}

}

package de.maanex.terrainGenerators.wastelands.generator;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.material.MaterialData;


@SuppressWarnings("deprecation")
public class BlockVariantsAndOresPopulator extends BlockPopulator {

	private static List<MaterialData>	rock_stone	= new ArrayList<>();
	private static List<Material>		rock_types	= new ArrayList<>();

	static {
		rock_stone.add(new MaterialData(Material.STONE, (byte) 0));
		rock_stone.add(new MaterialData(Material.STONE, (byte) 0));
		rock_stone.add(new MaterialData(Material.STONE, (byte) 0));
		rock_stone.add(new MaterialData(Material.STONE, (byte) 5));
		rock_stone.add(new MaterialData(Material.STONE, (byte) 6));
		rock_stone.add(new MaterialData(Material.COBBLESTONE, (byte) 0));
		rock_stone.add(new MaterialData(Material.SMOOTH_BRICK, (byte) 0));
		rock_stone.add(new MaterialData(Material.CONCRETE, (byte) 8));

		rock_types.add(Material.DIAMOND_ORE);
		rock_types.add(Material.EMERALD_ORE);
		rock_types.add(Material.GOLD_ORE);
		rock_types.add(Material.IRON_ORE);
		rock_types.add(Material.COAL_ORE);
		rock_types.add(Material.REDSTONE_ORE);
		rock_types.add(Material.LAPIS_ORE);
		rock_types.add(Material.STONE);
		rock_types.add(Material.MAGMA);
	}

	public BlockVariantsAndOresPopulator() {
	}

	@Override
	public void populate(World w, Random r, Chunk c) {
		boolean putBuilding = false;
		if (c.getBlock(0, 20, 0).getType().equals(Material.BEDROCK) && c.getBlock(0, 20, 15).getType().equals(Material.BEDROCK) && c.getBlock(15, 20, 0).getType().equals(Material.BEDROCK)
				&& c.getBlock(15, 20, 15).getType().equals(Material.BEDROCK) && r.nextInt(10) == 0)
			putBuilding = true;
		for (int x = 0; x < 16; x++)
			for (int z = 0; z < 16; z++)
				for (int y = c.getWorld().getHighestBlockYAt(x + c.getX() * 16, z + c.getZ() * 16); y > 0; y--) {
					if (c.getBlock(x, y, z).getType().equals(Material.STONE)) {
						MaterialData m = rock_stone.get(r.nextInt(rock_stone.size()));
						if (r.nextBoolean()) {
							c.getBlock(x, y, z).setType(m.getItemType());
							c.getBlock(x, y, z).setData(m.getData());
						}
					}
				}

	}

}

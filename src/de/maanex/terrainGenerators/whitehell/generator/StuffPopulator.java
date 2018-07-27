package de.maanex.terrainGenerators.whitehell.generator;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import de.maanex.utils.BlockUtil;


@SuppressWarnings("deprecation")
public class StuffPopulator extends BlockPopulator {

	private static List<Material>	rock_stone	= new ArrayList<>();
	private static List<Material>	rock_types	= new ArrayList<>();

	static {
		rock_stone.add(Material.STONE);
		rock_stone.add(Material.ANDESITE);
		rock_stone.add(Material.POLISHED_ANDESITE);
		rock_stone.add(Material.COBBLESTONE);
		rock_stone.add(Material.SMOOTH_STONE);
		rock_stone.add(Material.GRAY_CONCRETE);

		rock_types.add(Material.DIAMOND_ORE);
		rock_types.add(Material.EMERALD_ORE);
		rock_types.add(Material.GOLD_ORE);
		rock_types.add(Material.IRON_ORE);
		rock_types.add(Material.COAL_ORE);
		rock_types.add(Material.REDSTONE_ORE);
		rock_types.add(Material.LAPIS_ORE);
		rock_types.add(Material.STONE);
		rock_types.add(Material.MAGMA_BLOCK);
	}

	public StuffPopulator() {
	}

	@Override
	public void populate(World w, Random r, Chunk c) {
		boolean putBuilding = false;
		if (c.getBlock(0, 20, 0).getType().equals(Material.BEDROCK) && c.getBlock(0, 20, 15).getType().equals(Material.BEDROCK) && c.getBlock(15, 20, 0).getType().equals(Material.BEDROCK)
				&& c.getBlock(15, 20, 15).getType().equals(Material.BEDROCK) && r.nextInt(10) == 0)
			putBuilding = true;
		for (int x = 0; x < 16; x++)
			for (int z = 0; z < 16; z++) {
				if (WhiteHellGenerator.isCoreSpot(w, r, c.getX() * 16 + x, c.getZ() * 16 + z)) {
					if (putBuilding) {
						putBuilding(w, c.getX() * 16, c.getZ() * 16, r);
						return;
					}
					if (r.nextInt(16 * 16) == 0) {
						Location l = c.getBlock(x, WhiteHellGenerator.GROUND_Y, z).getLocation().clone();
						switch (r.nextInt(4)) {
							case 0:
								w.generateTree(l, TreeType.BIG_TREE);
								break;
							case 1:
								generateRock(r, l);
								break;
							case 2:
								generateGrassPatch(r, l);
								break;
							case 3:
								if (r.nextInt(20) != 0) break;
								generateGigantRock(r, l);
								break;
						}
					}
				}
			}
	}

	private void generateRock(Random r, Location l) {
		int h = r.nextInt(6) + 4;
		int cH = h + 1, ra = 0;
		int xr = r.nextInt(3) - 1, zr = r.nextInt(3) - 1;
		l.add(0, cH, 0);
		Material rockType = rock_types.get(r.nextInt(rock_types.size()));
		while (cH-- > 0) {
			ra = (int) Math.pow(h - cH + 2, 2) / ((h - cH + 1) * 3);
			final int rad = Math.max(1, ra);
			BlockUtil.makeCylinder(l.add(cH % 2 == 0 ? xr : 0, -1, cH % 2 == 1 ? zr : 0), 1, ra).forEach(o -> {
				if (r.nextInt(rad) >= o.distance(l) && r.nextBoolean()) o.getBlock().setType(rockType);
				else o.getBlock().setType(rock_stone.get(r.nextInt(rock_stone.size())));
			});
		}
	}

	private void generateGrassPatch(Random r, Location l) {
		for (int x = -10; x < 10; x++)
			for (int z = -10; z < 10; z++) {
				Location q = l.clone().add(x, 0, z);
				if (r.nextInt((int) Math.max(1, q.distance(l))) == 0 && q.getBlock().isEmpty()) {
					q.getBlock().setType(Material.GRASS);
					if (Math.abs(x) + Math.abs(z) < 4 && r.nextBoolean()) {
						q.getBlock().setType(Material.TALL_GRASS);
						q.clone().add(0, 1, 0).getBlock().setType(Material.TALL_GRASS);
					}
				}
			}
	}

	private void generateGigantRock(Random r, Location l) { // TODO
		int h = r.nextInt(10) + 13;
		int cH = h + 1, ra = 0;
		int xr = r.nextInt(3) - 1, zr = r.nextInt(3) - 1;
		l.add(0, cH, 0);
		Material rockType1 = rock_types.get(r.nextInt(rock_types.size()));
		Material rockType2 = rock_types.get(r.nextInt(rock_types.size()));
		while (cH-- > 0) {
			ra = (int) Math.pow(h - cH + 2, 2) / ((h - cH + 1) * 3);
			final int rad = Math.max(1, ra);
			BlockUtil.makeCylinder(l.add(cH % 4 == 0 ? xr : 0, -1, cH % 4 == 2 ? zr : 0), 1, ra).forEach(o -> {
				if (r.nextInt(rad) >= o.distance(l) && r.nextBoolean()) o.getBlock().setType(r.nextBoolean() ? rockType1 : rockType2);
				else o.getBlock().setType(rock_stone.get(r.nextInt(rock_stone.size())));
			});
		}
	}

	private void putBuilding(World w, int x, int z, Random r) {
		for (int rx = 0; rx < 16; rx++)
			for (int rz = 0; rz < 16; rz++)
				w.getBlockAt(x + rx, 20, z + rz).setType(Material.BEDROCK);
		BuildingsGenerator.putBuilding(w, x + 8, z + 8, r);
	}

}

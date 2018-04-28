package de.maanex.whitehell.generator;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;


public class WhiteHellGenerator extends ChunkGenerator {

	private static HashMap<World, SimplexOctaveGenerator> gen = new HashMap<>();

	public static final int GROUND_Y = 25;

	@Override
	public ChunkData generateChunkData(World world, Random random, int cx, int cz, BiomeGrid biome) {
		SimplexOctaveGenerator generator = new SimplexOctaveGenerator(new Random(world.getSeed()), 8);
		generator.setScale(0.01D);
		gen.put(world, generator);

		ChunkData chunk = createChunkData(world);

		chunk.setRegion(0, 20, 0, 16, GROUND_Y, 16, Material.CONCRETE);

		for (int y = 5; y < 10; y++) {
			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					biome.setBiome(x, z, Biome.SAVANNA);

					if (isSaveSpot(world, random, cx * 16 + x, cz * 16 + z)) {
						chunk.setBlock(x, GROUND_Y - 1, z, Material.GRASS);
						chunk.setBlock(x, 20, z, Material.BEDROCK);
					}
				}
			}
		}

		return chunk;
	}

	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList(new StuffPopulator());
	}

	//

	public static boolean isSaveSpot(World w, Random r, int x, int z) {
		double a = Math.abs(gen.get(w).noise(x, z, 0.5D, 0.5D) * 100);
		return a + r.nextInt(30) > 100;
	}

	public static boolean isCoreSpot(World w, Random r, int x, int z) {
		double a = Math.abs(gen.get(w).noise(x, z, 0.5D, 0.5D) * 100);
		return a > 100;
	}

	public static int getSaveValue(World w, Random r, int x, int z) {
		double a = Math.abs(gen.get(w).noise(x, z, 0.5D, 0.5D) * 100);
		return (int) a + r.nextInt(30);
	}

	public static int getCoreValue(World w, Random r, int x, int z) {
		double a = Math.abs(gen.get(w).noise(x, z, 0.5D, 0.5D) * 100);
		return (int) a;
	}

}

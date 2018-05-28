package de.maanex.terrainGenerators.wastelands.generator;


import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.material.MaterialData;
import org.bukkit.util.noise.SimplexOctaveGenerator;


public class WastelandsGenerator extends ChunkGenerator {

	public static final int	GROUND_Y		= 1;
	public static final int	SEA_LEVEL		= 2;
	public static final int	VULCANO_HIGHT	= 150;

	@SuppressWarnings("deprecation")
	@Override
	public ChunkData generateChunkData(World world, Random r, int cx, int cz, BiomeGrid biome) {
		SimplexOctaveGenerator basicGen = new SimplexOctaveGenerator(new Random(world.getSeed()), 10);
		basicGen.setScale(0.01D);

		ChunkData chunk = createChunkData(world);

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				biome.setBiome(x, z, Biome.SKY);
				int wx = cx * 16 + x, wz = cz * 16 + z;

				double pr = basicGen.noise(wx, wz, .2d, .5d);
				double ps = basicGen.noise(wx, wz, .001d, 1d) - .1;
				int bl = (int) (pr * 100 * ps) + 100;
				boolean vulcano = false;
				if (bl > VULCANO_HIGHT) {
					bl = VULCANO_HIGHT - (bl - VULCANO_HIGHT);
					vulcano = true;
				}

				bl = Math.abs(bl);
				if (Math.abs(ps) + (double) r.nextInt(100) / 1000 < .85) {
					chunk.setRegion(x, 0, z, x + 1, bl / 5 * 4, z + 1, Material.STONE);
					chunk.setRegion(x, bl / 5 * 4, z, x + 1, bl - 1, z + 1, Material.DIRT);
					chunk.setRegion(x, bl - 1, z, x + 1, bl, z + 1, new MaterialData(Material.DIRT, (byte) 2));
				} else {
					chunk.setRegion(x, 0, z, x + 1, Math.abs(bl), z + 1, Material.STONE);
					if (vulcano && bl < VULCANO_HIGHT - 5) {
						chunk.setRegion(x, bl, z, x + 1, VULCANO_HIGHT - 5, z + 1, Material.LAVA);
					}
				}

				double mag = basicGen.noise(wx + 200, wz + 200, .2d, .5d);
				if (Math.abs(mag) <= .05) {
					chunk.setRegion(x, (int) (bl - (.05 - Math.abs(mag)) * 300) - 5, z, x + 1, bl - 1, z + 1, Material.LAVA);
					chunk.setRegion(x, (int) (bl - (.05 - Math.abs(mag)) * 300) - 1, z, x + 1, bl, z + 1, Material.AIR);
				} else if (Math.abs(mag) <= .06) {
					chunk.setRegion(x, bl - r.nextInt(2) - 1, z, x + 1, bl, z + 1, Material.AIR);
				}

				if (bl < SEA_LEVEL) chunk.setRegion(x, bl, z, x + 1, SEA_LEVEL, z + 1, Material.WATER);
			}
		}

		chunk.setRegion(0, 0, 0, 16, GROUND_Y, 16, Material.BEDROCK);

		return chunk;
	}

	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList(new OrePopulator());
	}

	//

}

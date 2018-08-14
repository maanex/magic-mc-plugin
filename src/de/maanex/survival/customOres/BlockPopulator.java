package de.maanex.survival.customOres;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

import de.maanex.main.Main;


public class BlockPopulator implements Listener {

	private static List<Biome> crystallitBiomes = new ArrayList<>();

	static {
		crystallitBiomes.add(Biome.ICE_SPIKES);
		crystallitBiomes.add(Biome.FROZEN_OCEAN);
		crystallitBiomes.add(Biome.FROZEN_RIVER);
		crystallitBiomes.add(Biome.DEEP_FROZEN_OCEAN);
		crystallitBiomes.add(Biome.COLD_OCEAN);
		crystallitBiomes.add(Biome.DEEP_COLD_OCEAN);
		crystallitBiomes.add(Biome.SNOWY_BEACH);
		crystallitBiomes.add(Biome.SNOWY_MOUNTAINS);
		crystallitBiomes.add(Biome.SNOWY_TAIGA);
		crystallitBiomes.add(Biome.SNOWY_TAIGA_HILLS);
		crystallitBiomes.add(Biome.SNOWY_TAIGA_MOUNTAINS);
		crystallitBiomes.add(Biome.SNOWY_TUNDRA);
	}

	public BlockPopulator() {
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onChunkGen(ChunkPopulateEvent e) {
		Random r = new Random();
		for (int x = 0; x < 16; x++)
			for (int z = 0; z < 16; z++)
				for (int y = 0; y <= 128; y++) {
					Block block = e.getChunk().getBlock(x, y, z);

					if (e.getWorld().getEnvironment().equals(Environment.NORMAL)) {
						if (y >= 25 && y <= 45 && crystallitBiomes.contains(block.getBiome()) && block.getType().equals(Material.STONE)) {
							if (r.nextInt(20 * 16 * 16 / 5) == 0) Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
								block.setType(CustomBlocks.CRYSTALLIT.getBlock());
							}, 20);
						}
					} else if (e.getWorld().getEnvironment().equals(Environment.NETHER)) {
						if (r.nextInt(128 * 16 * 16) == 0) Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
							if (block.getType().equals(Material.NETHERRACK)) {
								for (int ax = -2; ax <= 2; ax++)
									for (int ay = -2; ay <= 2; ay++)
										for (int az = -2; az <= 2; az++) {
											Block b = block.getLocation().clone().add(ax, ay, az).getBlock();
											if (r.nextInt(7) == 0 && b.getType().equals(Material.NETHERRACK)) b.setType(CustomBlocks.TUDIUM.getBlock());
										}
							}
						}, 20);
					} else if (e.getWorld().getEnvironment().equals(Environment.THE_END)) {
						if (r.nextInt(128 * 16 * 16 / 2) == 0) Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
							if (block.getType().equals(Material.END_STONE)) {
								for (int ax = -4; ax <= 4; ax++)
									for (int ay = -4; ay <= 4; ay++)
										for (int az = -4; az <= 4; az++) {
											Block b = block.getLocation().clone().add(ax, ay, az).getBlock();
											if (r.nextInt(20) == 0 && b.getType().equals(Material.END_STONE)) b.setType(CustomBlocks.ENDERIT.getBlock());
										}
							}
						}, 20);
					}
				}
	}

}

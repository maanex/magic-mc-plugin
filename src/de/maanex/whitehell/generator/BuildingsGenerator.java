package de.maanex.whitehell.generator;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.CuboidClipboard.FlipDirection;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.world.DataException;

import de.maanex.whitehell.generator.LootGenerator.LootRarity;


@SuppressWarnings("deprecation")
public class BuildingsGenerator {

	public BuildingsGenerator() {
	}

	public static final String						SCHEMATIC_FILE_LOCATIONS	= "./plugins/WhiteHell/Buildings/";
	public static final HashMap<String, LootRarity>	BUILDINGS					= new HashMap<>();

	static {
		BUILDINGS.put("StoneTowerSmall1.schematic", LootRarity.COMMON);
		BUILDINGS.put("StoneTowerSmall2.schematic", LootRarity.COMMON);

		BUILDINGS.put("Well1.schematic", LootRarity.COMMON);
		BUILDINGS.put("Well2.schematic", LootRarity.COMMON);

		BUILDINGS.put("Statue1.schematic", LootRarity.COMMON);
		BUILDINGS.put("StatueStone1.schematic", LootRarity.COMMON);
		BUILDINGS.put("StatueStone2.schematic", LootRarity.COMMON);
		BUILDINGS.put("StatueStone3.schematic", LootRarity.COMMON);
		BUILDINGS.put("StatueStone4.schematic", LootRarity.COMMON);
		BUILDINGS.put("StatueStone5.schematic", LootRarity.COMMON);

		BUILDINGS.put("Altar1.schematic", LootRarity.COMMON);
		BUILDINGS.put("Altar2.schematic", LootRarity.COMMON);
		BUILDINGS.put("Altar3.schematic", LootRarity.COMMON);
		BUILDINGS.put("Altar4.schematic", LootRarity.COMMON);

		BUILDINGS.put("Greenings1.schematic", LootRarity.COMMON);

		BUILDINGS.put("Logs1.schematic", LootRarity.COMMON);
		BUILDINGS.put("Logs2.schematic", LootRarity.COMMON);

		BUILDINGS.put("Mornument1.schematic", LootRarity.COMMON);
		BUILDINGS.put("Mornument2.schematic", LootRarity.COMMON);
		BUILDINGS.put("Mornument3.schematic", LootRarity.COMMON);
	}

	public static void putBuilding(World w, int x, int z, Random r) {
		String file = BUILDINGS.keySet().toArray(new String[BUILDINGS.size()])[r.nextInt(BUILDINGS.size())];
		CuboidClipboard cc = loadArea(w, new File(SCHEMATIC_FILE_LOCATIONS, file), new Vector(x, WhiteHellGenerator.GROUND_Y, z), r);
		if (cc == null) return;

		for (int ix = x - cc.getWidth() / 2; ix < x + cc.getWidth() / 2; ix++)
			for (int iz = z - cc.getLength() / 2; iz < z + cc.getLength() / 2; iz++)
				for (int iy = WhiteHellGenerator.GROUND_Y; iy < WhiteHellGenerator.GROUND_Y + cc.getHeight(); iy++) {
					Block b = w.getBlockAt(ix, iy, iz);
					if (b == null || b.isEmpty() || !b.getType().equals(Material.CHEST) || !(b instanceof Chest)) continue;
					LootGenerator.fillChest((Chest) b, BUILDINGS.get(file), r);
				}
	}

	private static CuboidClipboard loadArea(World world, File file, Vector origin, Random r) {
		try {
			EditSession es = new EditSession(new BukkitWorld(world), 999999999);
			CuboidClipboard cc = CuboidClipboard.loadSchematic(file);

			if (r.nextBoolean()) {
				cc.flip(r.nextBoolean() ? FlipDirection.NORTH_SOUTH : FlipDirection.WEST_EAST);
			} else {
				cc.rotate2D(r.nextInt(4) * 90);
			}

			cc.paste(es, origin, false);
			return cc;
		} catch (DataException | IOException | MaxChangedBlocksException e) {
			e.printStackTrace();
		}
		return null;
	}
}

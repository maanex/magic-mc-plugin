package de.maanex.magic.structures;


import org.bukkit.Material;
import org.bukkit.block.Block;


public class RunicTableSpotter {

	private RunicTableSpotter() {
	}

	public static boolean isRunicTable(Block b) {
		return (b.getType().equals(Material.ENCHANTMENT_TABLE) //
				&& b.getLocation().clone().add(-1, -1, -1).getBlock().getType().equals(Material.OBSIDIAN) //
				&& b.getLocation().clone().add(0, -1, -1).getBlock().getType().equals(Material.OBSIDIAN) //
				&& b.getLocation().clone().add(1, -1, -1).getBlock().getType().equals(Material.OBSIDIAN) //
				&& b.getLocation().clone().add(-1, -1, 0).getBlock().getType().equals(Material.OBSIDIAN) //
				&& b.getLocation().clone().add(0, -1, 0).getBlock().getType().equals(Material.OBSIDIAN) //
				&& b.getLocation().clone().add(1, -1, 0).getBlock().getType().equals(Material.OBSIDIAN) //
				&& b.getLocation().clone().add(-1, -1, 1).getBlock().getType().equals(Material.OBSIDIAN) //
				&& b.getLocation().clone().add(0, -1, 1).getBlock().getType().equals(Material.OBSIDIAN) //
				&& b.getLocation().clone().add(1, -1, 1).getBlock().getType().equals(Material.OBSIDIAN) //

				&& b.getLocation().clone().add(1, 0, 1).getBlock().getType().equals(Material.REDSTONE_WIRE) //
				&& b.getLocation().clone().add(0, 0, 1).getBlock().getType().equals(Material.REDSTONE_WIRE) //
				&& b.getLocation().clone().add(-1, 0, 1).getBlock().getType().equals(Material.REDSTONE_WIRE) //
				&& b.getLocation().clone().add(1, 0, 0).getBlock().getType().equals(Material.REDSTONE_WIRE) //
				&& b.getLocation().clone().add(-1, 0, 0).getBlock().getType().equals(Material.REDSTONE_WIRE) //
				&& b.getLocation().clone().add(1, 0, -1).getBlock().getType().equals(Material.REDSTONE_WIRE) //
				&& b.getLocation().clone().add(0, 0, -1).getBlock().getType().equals(Material.REDSTONE_WIRE) //
				&& b.getLocation().clone().add(-1, 0, -1).getBlock().getType().equals(Material.REDSTONE_WIRE) //
		);
	}

}

package de.maanex.survival.customOres;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class NoLogStripping implements Listener {

	private static List<Material>	disabled	= new ArrayList<>();
	private static List<Material>	axes		= new ArrayList<>();

	static {
		disabled.add(Material.OAK_WOOD);
		disabled.add(Material.SPRUCE_WOOD);
		disabled.add(Material.BIRCH_WOOD);
		disabled.add(Material.JUNGLE_WOOD);
		disabled.add(Material.ACACIA_WOOD);
		disabled.add(Material.DARK_OAK_WOOD);

		axes.add(Material.WOODEN_AXE);
		axes.add(Material.STONE_AXE);
		axes.add(Material.IRON_AXE);
		axes.add(Material.GOLDEN_AXE);
		axes.add(Material.DIAMOND_AXE);
	}

	public NoLogStripping() {
	}

	@EventHandler
	public void onStrip(PlayerInteractEvent e) {
		if (e.getItem() == null) return;
		if (!axes.contains(e.getItem().getType())) return;
		if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if (!disabled.contains(e.getClickedBlock().getType())) return;
		e.setCancelled(true);
	}

}

package de.maanex.magic.listener;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.magic.structures.RunicTableSpotter;


public class RunicTableUse implements Listener {

	private static String TABLE_NAME = "§dRunic Table";

	private static ItemStack TABLE_INV_PLACEHOLDER;

	static {
		TABLE_INV_PLACEHOLDER = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
		ItemMeta m1 = TABLE_INV_PLACEHOLDER.getItemMeta();
		m1.setDisplayName("§0");
		TABLE_INV_PLACEHOLDER.setItemMeta(m1);
	}

	public RunicTableUse() {
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null) return;
		if (RunicTableSpotter.isRunicTable(e.getClickedBlock())) {
			e.setCancelled(true);
			e.getPlayer().openInventory(buildInv(e.getPlayer()));
		}
	}

	private static Inventory buildInv(HumanEntity player) {
		Inventory inv = Bukkit.createInventory(player, InventoryType.HOPPER, TABLE_NAME);

		return inv;
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getClickedInventory().getName().equalsIgnoreCase(TABLE_NAME)) {

		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if (e.getInventory().getName().equalsIgnoreCase(TABLE_NAME)) {
			Location l = e.getPlayer().getTargetBlock(null, 10).getLocation();
			if (l == null) l = e.getPlayer().getLocation();

		}
	}

}

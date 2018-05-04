package de.maanex.magic.listener;


import java.util.Arrays;
import java.util.List;

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

	private static ItemStack	TABLE_INV_PLACEHOLDER;
	private static ItemStack	TABLE_INV_INFO_SIGN;

	private static List<Integer>	lockedSlots	= Arrays.asList(0, 1, 2, 6, 7, 8, 9, 10, 11, 15, 17, 18, 19, 20, 24, 25, 26);
	private static List<Integer>	inputSlots	= Arrays.asList(3, 4, 5, 12, 13, 14, 21, 22, 23);
	private static int				resSlot		= 16;

	static {
		TABLE_INV_PLACEHOLDER = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
		ItemMeta m1 = TABLE_INV_PLACEHOLDER.getItemMeta();
		m1.setDisplayName("§0");
		TABLE_INV_PLACEHOLDER.setItemMeta(m1);

		TABLE_INV_INFO_SIGN = new ItemStack(Material.SIGN, 1);
		ItemMeta m2 = TABLE_INV_INFO_SIGN.getItemMeta();
		m2.setDisplayName("§dInfo!");
		m2.setLore(Arrays.asList("§5Kommt irgendwann mal!"));
		TABLE_INV_INFO_SIGN.setItemMeta(m2);
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

	@SuppressWarnings("unused")
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getClickedInventory().getName().equalsIgnoreCase(TABLE_NAME)) {

		}
	}

	@SuppressWarnings("unused")
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if (e.getInventory().getName().equalsIgnoreCase(TABLE_NAME)) {
			Location l = e.getPlayer().getTargetBlock(null, 10).getLocation();
			if (l == null) l = e.getPlayer().getLocation();

			for (int i : inputSlots) {
				if (e.getInventory().getItem(i) != null) l.getWorld().dropItem(l, e.getInventory().getItem(i));
			}

		}
	}

}

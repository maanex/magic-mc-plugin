package de.maanex.magic.listener;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.survival.customOres.CustomBlocks;


public class WandWorkbenchUse implements Listener {

	private static String TABLE_NAME = "§2Zauberstab Werkbank";

	public WandWorkbenchUse() {
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null) return;
		if (e.getPlayer().isSneaking()) return;
		if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if (!e.getClickedBlock().getType().equals(CustomBlocks.WAND_WORKBENCH.getBlock())) return;
		e.setCancelled(true);

		e.getPlayer().openInventory(buildInv(e.getPlayer()));
	}

	private static Inventory buildInv(HumanEntity player) {
		Inventory inv = Bukkit.createInventory(player, 72, TABLE_NAME);

		ItemStack guiBackground = new ItemStack(Material.STONE_HOE);
		setSkin(guiBackground, (short) 6);
		ItemMeta m = guiBackground.getItemMeta();
		m.setDisplayName(new Random().nextInt(30) == 0 ? "§cHallo, ich bin ein Pfeil!" : "§0");
		guiBackground.setItemMeta(m);

		inv.setItem(0, guiBackground);
		return inv;
	}

	//

	private static ItemStack setSkin(ItemStack s, short skin) {
		s.setDurability(skin);
		ItemMeta meta = s.getItemMeta();
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
		s.setItemMeta(meta);
		return s;
	}
}

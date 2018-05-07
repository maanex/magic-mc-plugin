package de.maanex.magic.crafting;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.magic.MagicManager;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.items.DefaultItems;


public class Spellbook implements Listener {

	public Spellbook() {
	}

	@SuppressWarnings("deprecation")
	public static void registerRecipe() {
		ShapedRecipe res = new ShapedRecipe(DefaultItems.getSpellbook());

		res.shape("III", "IBI", "III");

		res.setIngredient('B', Material.BOOK);
		res.setIngredient('I', Material.IRON_NUGGET);

		Bukkit.addRecipe(res);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getPlayer().getItemInHand() == null || e.getItem() == null) return;

		// Prevent Hoe Function With Spell Items
		if (e.getItem().getType().equals(Material.IRON_HOE) && e.getItem().getItemMeta().isUnbreakable()) e.setCancelled(true);

		if (!e.getPlayer().getItemInHand().getType().equals(Material.BOOK) || e.getPlayer().getInventory().getItemInOffHand().getType().equals(Material.BOOK)) return;
		if (e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(DefaultItems.SPELLBOOK_NAME)) {
			List<MagicSpell> spells = parseSpells(e.getItem().getItemMeta());
			Inventory inv = Bukkit.createInventory(e.getPlayer(), InventoryType.HOPPER, DefaultItems.SPELLBOOK_NAME);
			int c = 0;
			for (MagicSpell s : spells)
				inv.setItem(c++, s.getItemStack());
			e.getPlayer().openInventory(inv);
		}
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getInventory().getName().equalsIgnoreCase(DefaultItems.SPELLBOOK_NAME)) {
			if (e.getCurrentItem() != null && e.getCurrentItem().getType().equals(Material.BOOK)) e.setCancelled(true);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		if (e.getInventory().getName().equalsIgnoreCase(DefaultItems.SPELLBOOK_NAME)) {
			if (e.getPlayer().getItemInHand().getType().equals(Material.BOOK)) {
				ItemMeta m = e.getPlayer().getItemInHand().getItemMeta();

				List<MagicSpell> spells = new ArrayList<>();

				for (ItemStack s : e.getInventory().getContents()) {
					if (s == null) continue;
					if (!s.getType().equals(Material.IRON_HOE) || !s.hasItemMeta()) {
						e.getPlayer().getWorld().dropItem(e.getPlayer().getEyeLocation(), s);
						continue;
					}

					ItemMeta me = s.getItemMeta();
					if (!me.hasLore()) continue;
					String l = me.getLore().get(0);
					if (!l.startsWith("§0")) continue;
					try {
						int i = Integer.parseInt(l.replace("§0", ""));
						for (MagicSpell p : MagicManager.getAllSpells())
							if (p.getID() == i) spells.add(p);
					} catch (Exception ex) {
						continue;
					}
				}

				m.setLore(makeLore(spells));
				e.getPlayer().getItemInHand().setItemMeta(m);
			}
		}
	}

	public static List<MagicSpell> parseSpells(ItemMeta m) {
		List<String> lore = m.getLore();
		List<MagicSpell> out = new ArrayList<>();
		for (String s : lore) {
			s = s.replace("§0", "");
			if (s.equalsIgnoreCase("")) continue;
			int i = Integer.parseInt(s);
			for (MagicSpell p : MagicManager.getAllSpells())
				if (p.getID() == i) out.add(p);
		}
		return out;
	}

	public static List<String> makeLore(List<MagicSpell> spells) {
		List<String> out = new ArrayList<>();
		for (MagicSpell s : spells) {
			out.add("§0" + s.getID());
		}
		out.add("§0");
		return out;
	}

	public static boolean isSpellbook(ItemStack i) {
		return i.hasItemMeta() && i.getItemMeta().getDisplayName().equalsIgnoreCase(DefaultItems.SPELLBOOK_NAME);
	}
}

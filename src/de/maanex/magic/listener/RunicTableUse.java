package de.maanex.magic.listener;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.magic.MagicManager;
import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.spells.basic.Elementum;
import de.maanex.magic.structures.RunicTableSpotter;
import de.maanex.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class RunicTableUse implements Listener {

	private static String TABLE_NAME = "§dRunic Table";

	public RunicTableUse() {
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null) return;
		if (e.getClickedBlock().getType().equals(Material.ENCHANTMENT_TABLE) && e.getItem() != null && e.getItem().getType().equals(Material.PAPER)) {
			if (MagicPlayer.get(e.getPlayer()).getMana() > 0) {
				MagicPlayer.get(e.getPlayer()).addMana(-1);
				e.getItem().setAmount(e.getItem().getAmount() - 1);
				e.getPlayer().getWorld().dropItem(e.getPlayer().getEyeLocation(), MagicManager.getSpell(Elementum.class).getItemStack());
			} else {
				Particle pa = new Particle(EnumParticle.SMOKE_NORMAL, e.getClickedBlock().getLocation(), false, 1, 1, 1, .2f, 50);
				pa.sendPlayer(e.getPlayer());
			}
			e.setCancelled(true);
			return;
		}
		if (RunicTableSpotter.isRunicTable(e.getClickedBlock())) {
			e.setCancelled(true);
			e.getPlayer().openInventory(buildInv(e.getPlayer()));
		}
	}

	private static Inventory buildInv(HumanEntity player) {
		Inventory inv = Bukkit.createInventory(player, InventoryType.HOPPER, TABLE_NAME);

		ItemStack empty = new ItemStack(Material.STONE_HOE);
		setSkin(empty, (short) 3);
		ItemMeta m = empty.getItemMeta();
		m.setDisplayName("§0");
		empty.setItemMeta(m);

		ItemStack guiBackground = new ItemStack(Material.STONE_HOE);
		setSkin(guiBackground, (short) 1);
		m = guiBackground.getItemMeta();
		m.setDisplayName(new Random().nextInt(30) == 0 ? "§cHallo, ich bin ein Pfeil!" : "§0");
		guiBackground.setItemMeta(m);

		ItemStack res = new ItemStack(Material.STONE_HOE);
		setSkin(res, (short) 2);
		m = res.getItemMeta();
		m.setDisplayName("§6Linksklicken zum Kombinieren!");
		m.setLore(Arrays.asList("§5Rechtsklicken um Wahrscheinlichkeiten", "   §5anzuzeigen! (1 Mana)"));
		res.setItemMeta(m);

		inv.setItem(2, empty);
		inv.setItem(3, guiBackground);
		inv.setItem(4, res);
		return inv;
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getClickedInventory().getName().equalsIgnoreCase(TABLE_NAME)) {
			if (e.getSlot() <= 1) return;
			if (e.getSlot() <= 3) {
				e.setCancelled(true);
				return;
			} else {
				// REMOVE 1 BEL
				e.setCancelled(true);
				if (e.getClick().equals(ClickType.LEFT)) {

				} else if (e.getClick().equals(ClickType.RIGHT)) {

				}
			}
		}
	}

	private static HashMap<MagicSpell, Integer> getResults(ItemStack i, ItemStack u) {
		if (i == null || u == null) return null;
		MagicSpell j = MagicSpell.parse(i);
		MagicSpell k = MagicSpell.parse(u);
		if (j == null || k == null) return null;

		// for(SpellRecipe r : )
		return null;
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if (e.getInventory().getName().equalsIgnoreCase(TABLE_NAME)) {
			Location l = e.getPlayer().getTargetBlock(null, 10).getLocation();
			if (l == null) l = e.getPlayer().getLocation();

			for (int i : Arrays.asList(0, 1, 4))
				if (e.getInventory().getItem(i) != null) {
					if (e.getInventory().getItem(i).getType().equals(Material.IRON_HOE)) l.getWorld().dropItem(l, e.getInventory().getItem(i));
				}
		}
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

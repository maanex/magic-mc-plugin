package de.maanex.magic.listener;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
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
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellRecipe;
import de.maanex.magic.spells.basic.Elementum;
import de.maanex.magic.structures.RunicTableSpotter;
import de.maanex.main.Main;
import de.maanex.utils.ParticleUtil;


public class RunicTableUse implements Listener {

	private static String TABLE_NAME = "§dRunic Table";

	public RunicTableUse() {
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null) return;
		if (e.getClickedBlock().getType().equals(Material.ENCHANTING_TABLE) && e.getItem() != null && e.getItem().getType().equals(Material.PAPER)) {
			if (MagicPlayer.get(e.getPlayer()).getMana() > 0) {
				MagicPlayer.get(e.getPlayer()).addMana(-1);
				e.getItem().setAmount(e.getItem().getAmount() - 1);
				e.getPlayer().getWorld().dropItem(e.getPlayer().getEyeLocation(), MagicManager.getSpell(Elementum.class).getItemStack());
			} else {
				ParticleUtil.spawn(e.getPlayer(), Particle.SMOKE_NORMAL, e.getClickedBlock().getLocation(), 50, .2, 1, 1, 1);
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
		m.setDisplayName("§eLinksklicken zum Kombinieren!");
		m.setLore(Arrays.asList("§dRechtsklicken um Wahrscheinlichkeiten", "§danzuzeigen! §5(1 Mana)"));
		res.setItemMeta(m);

		inv.setItem(2, empty);
		inv.setItem(3, guiBackground);
		inv.setItem(4, res);
		return inv;
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getClickedInventory() != null && e.getClickedInventory().getName().equalsIgnoreCase(TABLE_NAME)) {
			if (e.getSlot() <= 1) return;
			if (e.getSlot() <= 3) {
				e.setCancelled(true);
				return;
			} else {
				if (e.getCurrentItem() == null) return;
				if (!e.getCurrentItem().getType().equals(Material.STONE_HOE)) {

				} else {
					if (e.getClick().equals(ClickType.LEFT) && (e.getCursor() == null || e.getCursor().getType().equals(Material.AIR))) {
						HashMap<SpellRecipe, Integer> er = getResults(e.getInventory().getItem(0), e.getInventory().getItem(1));

						if (er == null || er.isEmpty()) {
							e.setCancelled(true);
							return;
						}

						SpellRecipe s = getSpell(er);
						s.getResult().updateExistingItemStack(e.getCurrentItem());

						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
							ItemStack res = new ItemStack(Material.STONE_HOE);
							setSkin(res, (short) 2);
							ItemMeta m = res.getItemMeta();
							m.setDisplayName("§eLinksklicken zum Kombinieren!");
							m.setLore(Arrays.asList("§dRechtsklicken um Wahrscheinlichkeiten", "§danzuzeigen! §5(1 Mana)"));
							res.setItemMeta(m);
							e.getInventory().setItem(4, res);
						}, 1);

						e.getInventory().setItem(0, null);
						e.getInventory().setItem(1, null);

						if (e.getWhoClicked() instanceof Player) MagicPlayer.get((Player) e.getWhoClicked()).researchRecipe(s);
					} else if (e.getClick().equals(ClickType.RIGHT)) {
						e.setCancelled(true);
						HashMap<SpellRecipe, Integer> er = getResults(e.getInventory().getItem(0), e.getInventory().getItem(1));
						if (er == null || er.isEmpty()) return;

						ItemMeta m = e.getCurrentItem().getItemMeta();
						List<String> lore = new ArrayList<>(Arrays.asList("§dRechtsklicken um Wahrscheinlichkeiten", "§danzuzeigen! §5(1 Mana)", "§0"));

						int t = 0;
						for (int i : er.values())
							t += i;
						for (SpellRecipe r : er.keySet()) {
							double per = Math.round(((double) r.getProbability() / t) * 10000.0) / 100.0;
							String p = r.getResult().getName();
							if (e.getWhoClicked() instanceof Player && !MagicPlayer.get((Player) e.getWhoClicked()).hasResearchedRecipe(r)) p = "???";
							lore.add("§a" + p + " §2" + per + "%");
						}

						m.setLore(lore);
						e.getCurrentItem().setItemMeta(m);
					} else e.setCancelled(true);
				}
			}
		}
	}

	private static SpellRecipe getSpell(HashMap<SpellRecipe, Integer> av) {
		int t = 0;
		for (int i : av.values())
			t += i;
		int r = new Random().nextInt(t);
		for (SpellRecipe s : av.keySet()) {
			r -= av.get(s);
			if (r <= 0) return s;
		}
		return av.keySet().stream().findFirst().get();
	}

	private static HashMap<SpellRecipe, Integer> getResults(ItemStack i, ItemStack u) {
		if (i == null || u == null) return null;
		MagicSpell j = MagicSpell.parse(i);
		MagicSpell k = MagicSpell.parse(u);
		if (j == null || k == null) return null;

		HashMap<SpellRecipe, Integer> out = new HashMap<>();

		for (SpellRecipe r : MagicManager.getAllSpellRecipes()) {
			if ((r.getSpell1().equals(j) && r.getSpell2().equals(k)) || r.getSpell1().equals(k) && r.getSpell2().equals(j)) out.put(r, r.getProbability());
		}
		return out;
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

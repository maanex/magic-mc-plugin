package de.maanex.magic.crafting;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.magic.MagicManager;
import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellRecipe;
import de.maanex.magic.spells.basic.Elementum;


public class HelpBook implements Listener {

	@SuppressWarnings("deprecation")
	public static void registerRecipe() {
		ShapedRecipe res = new ShapedRecipe(getBook());

		res.shape("000", "0B0", "000");

		res.setIngredient('B', Material.BOOK);

		Bukkit.addRecipe(res);
	}

	private static ItemStack getBook() {
		ItemStack out = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta m = (BookMeta) out.getItemMeta();

		m.setTitle("§2Codex Magica");
		m.setAuthor("Tude Magic Division");
		m.setLore(Arrays.asList("§0codexmagica"));

		//

		m.addPage("Ja ups! Da ist wohl ein Fehler aufgetreten :/");

		//

		out.setItemMeta(m);
		return out;
	}

	@EventHandler
	public void onOpen(PlayerInteractEvent e) {
		if (e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasLore() && e.getItem().getItemMeta().getLore().get(0).contains("§0codexmagica")) {
			e.setCancelled(true);
			openInv(e.getPlayer(), 0);
		}
	}

	private void openInv(Player p, int page) {
		List<MagicSpell> spells = MagicManager.getAllSpells();
		int invsize = 45;

		// Sorting spells by rarity and id
		spells = spells.stream().sorted((a, b) -> {
			int ra = a.getRarity().getSort();
			int rb = b.getRarity().getSort();
			if (ra == rb) return a.getID() < b.getID() ? -1 : 1;
			else return ra < rb ? -1 : 1;
		}).collect(Collectors.toList());

		int pages = (int) Math.ceil((double) spells.size() / invsize);

		Inventory inv = Bukkit.createInventory(p, invsize + 9, "§2Codex Magica Seite " + (page + 1));

		ItemStack unk = new ItemStack(Material.STONE_HOE);
		setSkin(unk, (short) 2);
		ItemMeta m = unk.getItemMeta();
		m.setDisplayName("§eUnbekannter Spruch");
		m.setLore(Arrays.asList("§7Erforschen zum Freischalten!"));
		unk.setItemMeta(m);

		// Adding items and checking if the player knows a recipe, else blur that spell
		for (int i = 0; i < invsize; i++) {
			if (i + page * invsize >= spells.size()) break;
			MagicSpell spell = spells.get(i + page * invsize);
			ItemStack s = spell.getItemStack();
			if (!p.getGameMode().equals(GameMode.CREATIVE) && !(spell instanceof Elementum)) {
				MagicPlayer mp = MagicPlayer.get(p);
				boolean has = false;
				for (SpellRecipe r : mp.getResearchedRecipes()) {
					if (r.getResult().equals(spell)) {
						has = true;
						break;
					}
				}
				if (!has) s = unk;
			}
			inv.setItem(i, s);
		}

		ItemStack s = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		m = s.getItemMeta();
		m.setDisplayName("§0");
		s.setItemMeta(m);
		for (int i = invsize; i < invsize + 9; i++)
			inv.setItem(i, s);

		s = new ItemStack(page == 0 ? Material.GREEN_STAINED_GLASS_PANE : Material.LIME_STAINED_GLASS_PANE);
		m = s.getItemMeta();
		m.setDisplayName(page == 0 ? "§7" : "§a" + "Vorherige Seite");
		s.setItemMeta(m);
		inv.setItem(invsize, s);

		s = new ItemStack(page >= pages - 1 ? Material.GREEN_STAINED_GLASS_PANE : Material.LIME_STAINED_GLASS_PANE);
		m = s.getItemMeta();
		m.setDisplayName(page >= pages - 1 ? "§7" : "§a" + "Nächste Seite");
		s.setItemMeta(m);
		inv.setItem(invsize + 8, s);

		p.openInventory(inv);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (!e.getInventory().getName().startsWith("§2Codex Magica Seite")) return;
		if (e.getClickedInventory() == null) return;
		if (!e.getClickedInventory().equals(e.getInventory())) return;
		if (!(e.getWhoClicked() instanceof Player)) return;
		int invsize = 45;
		int pages = (int) Math.ceil((double) MagicManager.getAllSpells().size() / invsize);
		int page = Integer.parseInt(e.getInventory().getName().replace("§2Codex Magica Seite ", "")) - 1;

		if (e.getSlot() < invsize) e.setCancelled(true);
		else {
			e.setCancelled(true);
			if (e.getSlot() == invsize && page > 0) {
				e.getWhoClicked().closeInventory();
				openInv((Player) e.getWhoClicked(), page - 1);
			}
			if (e.getSlot() == invsize + 8 && page < pages - 1) {
				e.getWhoClicked().closeInventory();
				openInv((Player) e.getWhoClicked(), page + 1);
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

	/*
	 * 
	 */

	private static ItemStack getOldBook() {
		ItemStack out = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta m = (BookMeta) out.getItemMeta();

		m.setTitle("§2Codex Magica - Über Zauberei");
		m.setAuthor("Tude Magic Division");

		//

		m.addPage("-------------------" + //
				"\nÜber Zauberei" + //
				"\n-------------------" + //
				"\n" + //
				"\nEin Lexikon der Tude Magic Division" + //
				"\n" + //
				"\nAuf den nächsten Seiten finden Sie alle grundlegenen Dinge die Sie über Magie wissen müssen."//
		);

		m.addPage("§l§nInhalt§r" + //
				"\n" + //
				"\n1. Mana und natürliche Magie" + //
				"\n" + //
				"\n2. Magische Utensilien" + //
				"\n" + //
				"\n3. Zaubersprüche erforschen" + //
				"\n" + //
				"\n4. Grundbegriffe" + //
				"\n" + //
				"\n5. Sonstiges & Dank" //
		);

		m.addPage("-------------------" + //
				"\n§lKap I:§0 Mana und natürliche Magie" + //
				"\n-------------------" + //
				"\n" + //
				"\nEin jeder besitzt Mana. Sie ist klar sichtbar über der Schnellzugriffsleiste des Spielers sichtbar. Aufgeteilt in anfangs 20 Balken, zeigen die blau gefärbten, wie viele Einheiten" //
		);

		m.addPage("an Mana verbleiben" + //
				"\n" + //
				"\nMana regeneriert automatisch, allerdings nur die hellgrau gefärbte Distanz. Um den dunkelgrauen Bereich mit Mana zu füllen, werden andere Methoden benötigt: Der einfachste Weg, schneller Mana zu regenerieren und diese Limitierung zu" //
		);

		m.addPage("umgehen ist das benutzen von Manatränken. Dazu mehr in Kapitel II." //
		);

		m.addPage("-------------------" + //
				"\n§lKap II:§0 Magische Utensilien" + //
				"\n-------------------" + //
				"\n" + //
				"\nDas wichtigste Werkzeug eines jeden Zauberers ist sicher sein Zauberstab. Hergestellt wird dieser ganz einfach durch platzierung eines Stockes in der Mitte einer Werkbank und" //
		);

		m.addPage(
				"zwei Eisennuggets oben rechts und unten links davon. Dieser Zauberstab besitzt allerdings noch nicht die Fähigkeit damit zu Zaubern. Um dies zu ermöglichen, muss einfach mit diesem in der Hand ein Zaubertisch angeklickt werden. Für diesen Prozess sind" //
		);

		m.addPage("3 Erfahrungslevel von nöten." + //
				"\n" + //
				"\n Ein Zauberstab alleine reicht allerdings noch nicht um zu Zaubern: Ebenso essenziell wie jender ist für jeden Zauberer sein Zauberbuch in dem er seine gelernten Zaubersprüche nachschlagen kann. Zur Herstellung von"//
		);
		m.addPage(
				"einem Zauberbuch muss in einer Werkbank ein normales Buch vollständig mit Eisennuggets umrandet werden. Vom Tisch entnommen, kann es mit rechtsklick geöffnet und Zaubersprüche darin abgelegt werden. (Mehr dazu in Kapittel III)"
						+ //
						"\n" + //
						"\nAls letztes wichtiges"//
		);

		//

		out.setItemMeta(m);
		return out;
	}
}
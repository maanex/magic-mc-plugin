package de.maanex.magic.listener;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.wands.Wand;
import de.maanex.magic.wands.WandGenerator;
import de.maanex.magic.wands.WandType;
import de.maanex.main.Main;
import de.maanex.survival.customOres.CustomBlocks;


public class WandWorkbenchUse implements Listener {

	private static final String	TABLE_PREFIX	= "§2wwb:";
	private static final String	EMPTY_SUFFIX	= "empty";
	private static final String	IDENTIFY_SUFFIX	= "identify";
	private static final String	UPGRADE_SUFFIX	= "upgrade";
	private static final String	ARTEFACT_SUFFIX	= "artefact";

	//

	public static int			middleSlot;
	public static List<Integer>	identifySlots;
	public static int			confirmSlot;

	static {
		middleSlot = 31;
		identifySlots = new ArrayList<>(Arrays.asList(12, 14, 29, 33, 48, 50));
		confirmSlot = 61;
	}

	public WandWorkbenchUse() {
	}

	//

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
		return buildInv(player, null, EMPTY_SUFFIX);
	}

	private static Inventory buildInv(HumanEntity player, Inventory in, String type) {
		Inventory inv = Bukkit.createInventory(player, 72, TABLE_PREFIX + type);
		if (in != null) for (int i = 0; i < in.getSize(); i++)
			if (in.getItem(i) != null) inv.setItem(i, in.getItem(i));

		ItemMeta m;

		ItemStack empty = new ItemStack(Material.STONE_HOE);
		setSkin(empty, (short) 3);
		m = empty.getItemMeta();
		m.setDisplayName("§0");
		empty.setItemMeta(m);
		for (int i = 0; i < inv.getSize(); i++) {
			if (i == middleSlot) i++;
			inv.setItem(i, empty);
		}

		ItemStack guiBackground = new ItemStack(Material.STONE_HOE);
		switch (type) {
			case EMPTY_SUFFIX:
				setSkin(guiBackground, (short) 15);
				break;
			case IDENTIFY_SUFFIX:
				setSkin(guiBackground, (short) 16);
				break;
		}
		m = guiBackground.getItemMeta();
		m.setDisplayName("§0");
		guiBackground.setItemMeta(m);
		inv.setItem(0, guiBackground);

		switch (type) {
			case IDENTIFY_SUFFIX:
				for (int i : identifySlots)
					inv.setItem(i, null);

				ItemStack confirm = new ItemStack(Material.STONE_HOE);
				setSkin(confirm, (short) 11);
				m = confirm.getItemMeta();
				m.setDisplayName("§r§aIdentifizieren");
				confirm.setItemMeta(m);
				inv.setItem(confirmSlot, confirm);
				break;
		}

		return inv;
	}

	//

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getClickedInventory() != null && e.getWhoClicked().getOpenInventory().getTitle().startsWith(TABLE_PREFIX)) {
			boolean real = e.getClickedInventory().getTitle().startsWith(TABLE_PREFIX);
			Inventory inv = e.getWhoClicked().getOpenInventory().getTopInventory();
			String page = e.getWhoClicked().getOpenInventory().getTitle().replaceFirst(TABLE_PREFIX, "");

			switch (page) {
				case EMPTY_SUFFIX:
					if (real && e.getSlot() != middleSlot) e.setCancelled(true);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
						if (inv.getItem(middleSlot) != null) {
							Wand w = Wand.fromItem(inv.getItem(middleSlot));
							if (w == null) return;
							if (w.getType().equals(WandType.UNIDENTIFIED)) e.getWhoClicked().openInventory(buildInv(e.getWhoClicked(), inv, IDENTIFY_SUFFIX));
						}
					}, 1);
					break;

				case IDENTIFY_SUFFIX:
					if (real && e.getSlot() != middleSlot && !identifySlots.contains(e.getSlot())) {
						e.setCancelled(true);
					}

					if (real && e.getSlot() == confirmSlot) {
						List<ItemStack> modifiers = new ArrayList<>();
						for (int i : identifySlots)
							if (inv.getItem(i) != null) modifiers.add(inv.getItem(i));
						if (validateIdentification(modifiers) == "") {
							Wand res = WandGenerator.create(modifiersToClass(modifiers), e.getWhoClicked().getUniqueId().toString(), e.getWhoClicked().getLocation().getWorld().getEnvironment());
							if (res != null) {
								for (int i : identifySlots)
									inv.setItem(i, null);
								inv.setItem(middleSlot, res.asItem());
								e.getWhoClicked().openInventory(buildInv(e.getWhoClicked(), inv, EMPTY_SUFFIX));
								break;
							}
						}
					}

					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
						if (inv.getItem(middleSlot) == null) {
							for (int i : identifySlots)
								if (inv.getItem(i) != null) e.getWhoClicked().getWorld().dropItem(e.getWhoClicked().getEyeLocation(), inv.getItem(i));

							e.getWhoClicked().openInventory(buildInv(e.getWhoClicked(), inv, EMPTY_SUFFIX));
						} else {
							List<ItemStack> modifiers = new ArrayList<>();
							for (int i : identifySlots)
								if (inv.getItem(i) != null) modifiers.add(inv.getItem(i));
							String s = validateIdentification(modifiers);
							ItemStack confirm = inv.getItem(confirmSlot);
							ItemMeta m = confirm.getItemMeta();
							if (s == "") {
								m.setDisplayName("§r§aIdentifizieren");
								m.setLore(null);
								confirm.setItemMeta(m);
								setSkin(confirm, (short) 11);
							} else {
								m.setDisplayName("§r§7Identifizieren");
								m.setLore(Arrays.asList("§c" + s));
								confirm.setItemMeta(m);
								setSkin(confirm, (short) 10);
							}
						}
					}, 1);
					break;
			}
		}
	}

	//

	private List<Class<? extends MagicSpell>> modifiersToClass(List<ItemStack> modifiers) {
		List<Class<? extends MagicSpell>> out = new ArrayList<>();
		for (ItemStack s : modifiers) {
			MagicSpell sp = MagicSpell.parse(s);
			if (sp == null) return null;
			out.add(sp.getClass());
		}
		return out;
	}

	private String validateIdentification(List<ItemStack> modifiers) {
		if (modifiers.isEmpty()) return "";
		List<Class<? extends MagicSpell>> mods = modifiersToClass(modifiers);
		if (mods == null || mods.isEmpty()) return "Ungültiges Item";
		boolean valid = WandGenerator.valid(mods);
		if (valid) return "";
		return "Ungültige Sprüche / Kombination";
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

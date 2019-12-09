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

import de.maanex.magic.basic.Element;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spells.basic.AirSpirit;
import de.maanex.magic.spells.basic.EarthSpirit;
import de.maanex.magic.spells.basic.EssenceBender;
import de.maanex.magic.spells.basic.EssenceBrightness;
import de.maanex.magic.spells.basic.EssenceDarkness;
import de.maanex.magic.spells.basic.FireSpirit;
import de.maanex.magic.spells.basic.WaterSpirit;
import de.maanex.magic.spells.building.MasterBuildersEssence;
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
			case UPGRADE_SUFFIX:
				setSkin(guiBackground, (short) 16);
				break;
		}
		m = guiBackground.getItemMeta();
		m.setDisplayName("§0");
		guiBackground.setItemMeta(m);
		inv.setItem(0, guiBackground);

		ItemStack confirm;
		switch (type) {
			case IDENTIFY_SUFFIX:
				for (int i : identifySlots)
					inv.setItem(i, null);

				confirm = new ItemStack(Material.STONE_HOE);
				setSkin(confirm, (short) 11);
				m = confirm.getItemMeta();
				m.setDisplayName("§r§aIdentifizieren");
				confirm.setItemMeta(m);
				inv.setItem(confirmSlot, confirm);
				break;
			case UPGRADE_SUFFIX:
				for (int i : identifySlots)
					inv.setItem(i, null);

				confirm = new ItemStack(Material.STONE_HOE);
				setSkin(confirm, (short) 11);
				m = confirm.getItemMeta();
				m.setDisplayName("§r§aUpgraden");
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
							else if (w.getValues() != null && w.getValues().getUpgrades() > 0) e.getWhoClicked().openInventory(buildInv(e.getWhoClicked(), inv, UPGRADE_SUFFIX));
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

				case UPGRADE_SUFFIX:
					if (real && e.getSlot() != middleSlot && !identifySlots.contains(e.getSlot())) {
						e.setCancelled(true);
					}
					
					ItemStack wandItem = inv.getItem(middleSlot);
					Wand w = Wand.fromItem(wandItem);
					if (w == null) {
						for (int i : identifySlots)
							if (inv.getItem(i) != null) e.getWhoClicked().getWorld().dropItem(e.getWhoClicked().getEyeLocation(), inv.getItem(i));
						e.getWhoClicked().openInventory(buildInv(e.getWhoClicked(), inv, EMPTY_SUFFIX));
						break;
					}

					if (real && e.getSlot() == confirmSlot) {
						List<ItemStack> modifiers = new ArrayList<>();
						for (int i : identifySlots)
							if (inv.getItem(i) != null) modifiers.add(inv.getItem(i));
						
						if (validateUpgrade(w, modifiers) == "") {
							applyWandUpgrade(w, modifiers);
							ItemMeta met = wandItem.getItemMeta();
							met.setLore(w.asItem().getItemMeta().getLore());
							wandItem.setItemMeta(met);
							for (int i : identifySlots)
								inv.setItem(i, null);
							e.getWhoClicked().openInventory(buildInv(e.getWhoClicked(), inv, EMPTY_SUFFIX));
							break;
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
							String s = validateUpgrade(w, modifiers);
							ItemStack confirm = inv.getItem(confirmSlot);
							ItemMeta m = confirm.getItemMeta();
							if (s == "") {
								m.setDisplayName("§r§aUpgraden");
								m.setLore(null);
								confirm.setItemMeta(m);
								setSkin(confirm, (short) 11);
							} else {
								m.setDisplayName("§r§7Upgraden");
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

	private String validateUpgrade(Wand wand, List<ItemStack> modifiers) {
		if (modifiers.isEmpty()) return "";
		List<Class<? extends MagicSpell>> mods = modifiersToClass(modifiers);
		if (mods == null || mods.isEmpty()) return "Ungültiges Item";
		boolean valid = WandGenerator.valid(mods);
		if (!valid) return "Nur Geister und Essenzen können zum Upgraden genutzt werden";
		
		int mfire = 0, mwater = 0, mair = 0, mearth = 0, mdark = 0, mlight = 0, mbuild = 0, mbend = 0;
		for (Class<? extends MagicSpell> s : mods) {
			if (s.equals(FireSpirit.class)) mfire++;
			if (s.equals(WaterSpirit.class)) mwater++;
			if (s.equals(EarthSpirit.class)) mearth++;
			if (s.equals(AirSpirit.class)) mair++;
			if (s.equals(EssenceBender.class)) mbend++;
			if (s.equals(MasterBuildersEssence.class)) mbuild++;
			if (s.equals(EssenceBrightness.class)) mlight++;
			if (s.equals(EssenceDarkness.class)) mdark++;
		}
		
		if (mfire > 0 && wand.getValues().getElement(Element.FIRE) <= 0) return "Feuer Upgrade nicht möglich, da der Stab kein Feuer besitzt!";
		if (mwater > 0 && wand.getValues().getElement(Element.WATER) <= 0) return "Wasser Upgrade nicht möglich, da der Stab kein Wasser besitzt!";
		if (mair > 0 && wand.getValues().getElement(Element.AIR) <= 0) return "Luft Upgrade nicht möglich, da der Stab keine Luft besitzt!";
		if (mearth > 0 && wand.getValues().getElement(Element.EARTH) <= 0) return "Erd Upgrade nicht möglich, da der Stab keine Erde besitzt!";
		if (mdark > 0 && wand.getValues().getElement(Element.ESSENCE_DARK) <= 0) return "Dunkelessenz Upgrade nicht möglich, da der Stab keine Dunkelessenz besitzt!";
		if (mlight > 0 && wand.getValues().getElement(Element.ESSENCE_LIGHT) <= 0) return "Lichtessenz Upgrade nicht möglich, da der Stab keine Lichtssenz besitzt!";
		if (mbuild > 0 && wand.getValues().getElement(Element.ESSENCE_BUILDER) <= 0) return "Baumeister Upgrade nicht möglich, da der Stab keine Baumeisteressenz besitzt!";
		if (mbend > 0 && wand.getValues().getElement(Element.ESSENCE_BENDER) <= 0) return "Bendigungs Upgrade nicht möglich, da der Stab keine Bendigungsessenz besitzt!";
		
		return "";
	}
	
	private void applyWandUpgrade(Wand w, List<ItemStack> modifiers) {
		List<Class<? extends MagicSpell>> mods = modifiersToClass(modifiers);
		int mfire = 0, mwater = 0, mair = 0, mearth = 0, mdark = 0, mlight = 0, mbuild = 0, mbend = 0;
		for (Class<? extends MagicSpell> s : mods) {
			if (s.equals(FireSpirit.class)) mfire++;
			if (s.equals(WaterSpirit.class)) mwater++;
			if (s.equals(EarthSpirit.class)) mearth++;
			if (s.equals(AirSpirit.class)) mair++;
			if (s.equals(EssenceBender.class)) mbend++;
			if (s.equals(MasterBuildersEssence.class)) mbuild++;
			if (s.equals(EssenceBrightness.class)) mlight++;
			if (s.equals(EssenceDarkness.class)) mdark++;
		}
		if (mfire == 6) mfire = 10;
		if (mwater == 6) mwater = 10;
		if (mearth == 6) mearth = 10;
		if (mair == 6) mair = 10;
		if (mbend == 6) mbend = 10;
		if (mbuild == 6) mbuild = 10;
		if (mlight == 6) mlight = 10;
		if (mdark == 6) mdark = 10;

		if (mfire > 0) w.getValues().setElement(Element.FIRE, w.getValues().getElement(Element.FIRE) + mfire);
		if (mwater > 0) w.getValues().setElement(Element.WATER, w.getValues().getElement(Element.WATER) + mwater);
		if (mearth > 0) w.getValues().setElement(Element.EARTH, w.getValues().getElement(Element.EARTH) + mearth);
		if (mair > 0) w.getValues().setElement(Element.AIR, w.getValues().getElement(Element.AIR) + mair);
		if (mbend > 0) w.getValues().setElement(Element.ESSENCE_BENDER, w.getValues().getElement(Element.ESSENCE_BENDER) + mbend);
		if (mbuild > 0) w.getValues().setElement(Element.ESSENCE_BUILDER, w.getValues().getElement(Element.ESSENCE_BUILDER) + mbuild);
		if (mlight > 0) w.getValues().setElement(Element.ESSENCE_LIGHT, w.getValues().getElement(Element.ESSENCE_LIGHT) + mlight);
		if (mdark > 0) w.getValues().setElement(Element.ESSENCE_DARK, w.getValues().getElement(Element.ESSENCE_DARK) + mdark);
		
		w.getValues().setUpgrades(w.getValues().getUpgrades() - 1);
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

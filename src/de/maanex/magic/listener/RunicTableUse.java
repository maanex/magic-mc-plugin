package de.maanex.magic.listener;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.magic.Elements;
import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.customEffects.ManaLockEffect;
import de.maanex.magic.generators.ItemElements;
import de.maanex.magic.items.DefaultItems;
import de.maanex.magic.spells.AirBlast;
import de.maanex.magic.spells.ArrowStorm;
import de.maanex.magic.spells.Comet;
import de.maanex.magic.spells.DarkSeal;
import de.maanex.magic.spells.EarthBenderBridge;
import de.maanex.magic.spells.EarthBenderCannon;
import de.maanex.magic.spells.Fireball;
import de.maanex.magic.spells.Firepunch;
import de.maanex.magic.spells.Frostwave;
import de.maanex.magic.spells.HolyShield;
import de.maanex.magic.spells.Knock;
import de.maanex.magic.spells.Levitate;
import de.maanex.magic.spells.MagmaWorm;
import de.maanex.magic.spells.Nitro;
import de.maanex.magic.spells.PainfullSting;
import de.maanex.magic.spells.ProtectionWall;
import de.maanex.magic.spells.Strike;
import de.maanex.magic.spells.Stun;
import de.maanex.magic.spells.TheSeeker;
import de.maanex.magic.spells.TrueSight;
import de.maanex.magic.spells.Warp;
import de.maanex.magic.spells.WaterBenderSplash;
import de.maanex.magic.structures.RunicTableSpotter;
import de.maanex.main.Main;


public class RunicTableUse implements Listener {

	private static String TABLE_NAME = "§dRunic Table";

	private static ItemStack	TABLE_INV_PLACEHOLDER;
	private static ItemStack	TABLE_INV_INFO_SIGN;

	private static List<Integer>	lockedSlots	= Arrays.asList(0, 1, 2, 6, 7, 8, 9, 10, 11, 15, 17, 18, 19, 20, 24, 25, 26);
	private static List<Integer>	inputSlots	= Arrays.asList(3, 4, 5, 12, 13, 14, 21, 22, 23);
	private static int				resSlot		= 16;

	public static List<MagicSpell> spells = new ArrayList<>();

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

		//

		spells.add(new Strike());
		spells.add(new Comet());
		spells.add(new ProtectionWall());
		spells.add(new Knock());
		spells.add(new AirBlast());
		spells.add(new Nitro());
		spells.add(new ArrowStorm());
		spells.add(new Warp());
		spells.add(new HolyShield());
		spells.add(new Fireball());
		spells.add(new TheSeeker());
		spells.add(new TrueSight());
		spells.add(new Frostwave());
		spells.add(new PainfullSting());
		spells.add(new MagmaWorm());
		spells.add(new EarthBenderBridge());
		spells.add(new EarthBenderCannon());
		spells.add(new DarkSeal());
		spells.add(new Levitate());
		spells.add(new Stun());
		spells.add(new Firepunch());
		spells.add(new WaterBenderSplash());
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
		Inventory inv = Bukkit.createInventory(player, InventoryType.CHEST, TABLE_NAME);
		for (int i : lockedSlots)
			inv.setItem(i, TABLE_INV_PLACEHOLDER);
		inv.setItem(10, TABLE_INV_INFO_SIGN);
		return inv;
	}

	@SuppressWarnings("unused")
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (true) return;
		// Bukkit.broadcastMessage(e.getInventory().getName() + "§f + " +
		// e.getClickedInventory().getName());
		if (e.getInventory().getName().equalsIgnoreCase(TABLE_NAME)) {
			// Bukkit.broadcastMessage("JA");
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
				updateRes(e.getClickedInventory());
				if (e.getWhoClicked() instanceof Player) ((Player) e.getWhoClicked()).updateInventory();
			}, 2);
		}
		if (e.getClickedInventory().getName().equalsIgnoreCase(TABLE_NAME)) {

			if (lockedSlots.contains(e.getSlot())) e.setCancelled(true);

			if (e.getSlot() == resSlot) {
				if (e.getClickedInventory().getItem(e.getSlot()) != null) {
					// TODO FALLS UNKOWN -> JETZT ÄNDERN
					for (int i : inputSlots) {
						if (e.getClickedInventory().getItem(i) == null) continue;
						if (e.getClickedInventory().getItem(i).getType().equals(Material.WATER_BUCKET) || e.getClickedInventory().getItem(i).getType().equals(Material.LAVA_BUCKET))
							e.getClickedInventory().setItem(i, new ItemStack(Material.BUCKET));
						else e.getClickedInventory().getItem(i).setAmount(e.getClickedInventory().getItem(i).getAmount() - 1);
					}
				}
			}
		}
	}

	private void updateRes(Inventory i) {
		List<ItemStack> items = new ArrayList<>();
		for (int n : inputSlots)
			if (i.getItem(n) != null) items.add(i.getItem(n));
		Bukkit.broadcastMessage(items.size() + "");
		if (items.isEmpty()) i.setItem(resSlot, null);
		else {
			i.setItem(resSlot, DefaultItems.getBasicSpell(spells.get(0)));
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

			if (true) return;

			int itemAm = 0;
			boolean bottle = false;
			int bucketAm = 0;
			for (ItemStack s : e.getInventory().getContents()) {
				if (s == null) continue;
				if (s.getType().equals(Material.WATER_BUCKET) || s.getType().equals(Material.LAVA_BUCKET)) bucketAm++;
				itemAm++;
				if (s.getType().equals(Material.POTION)) bottle = true;
			}

			if (itemAm == 0) return;
			if (itemAm == 1 && bottle) {
				MagicPlayer m = MagicPlayer.get((Player) e.getPlayer());
				if (m.getMana() <= 0) return;
				l.getWorld().dropItem(l, DefaultItems.getManaPot(m.getMana()));
				m.applyEffect(new ManaLockEffect(60));
				m.setMana(0);
			}

			while (bucketAm-- > 0)
				l.getWorld().dropItem(l, new ItemStack(Material.BUCKET));

			//

			HashMap<Elements, Integer> val = ItemElements.getElementValue(e.getInventory().getContents());

			boolean allZero = true;
			for (int i : val.values())
				if (i != 0) {
					allZero = false;
					continue;
				}
			if (allZero) return;

			MagicSpell choosenOne = null;

			for (MagicSpell s : spells) {
				if (s.getBuildRequirements().doesMeetRequirements(val)) {
					if (choosenOne == null || s.getBuildRequirements().getCommon() > choosenOne.getBuildRequirements().getCommon()) choosenOne = s;
				}
			}

			if (choosenOne != null) {
				l.getWorld().dropItem(l, DefaultItems.getBasicSpell(choosenOne));
			}
		}
	}

}

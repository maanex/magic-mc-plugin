package de.maanex.magic.listener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.maanex.magic.Elements;
import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.customEffects.ManaLockEffect;
import de.maanex.magic.generators.ItemElements;
import de.maanex.magic.items.DefaultItems;
import de.maanex.magic.spells.AirBlast;
import de.maanex.magic.spells.ArrowStorm;
import de.maanex.magic.spells.BridgeBend;
import de.maanex.magic.spells.Comet;
import de.maanex.magic.spells.DarkSeal;
import de.maanex.magic.spells.Fireball;
import de.maanex.magic.spells.Frostwave;
import de.maanex.magic.spells.HolyShield;
import de.maanex.magic.spells.Knock;
import de.maanex.magic.spells.Levitate;
import de.maanex.magic.spells.MagmaWorm;
import de.maanex.magic.spells.Nitro;
import de.maanex.magic.spells.PainfullSting;
import de.maanex.magic.spells.ProtectionWall;
import de.maanex.magic.spells.StoneCannonBend;
import de.maanex.magic.spells.Strike;
import de.maanex.magic.spells.Stun;
import de.maanex.magic.spells.TheSeeker;
import de.maanex.magic.spells.TrueSight;
import de.maanex.magic.spells.Warp;
import de.maanex.magic.structures.RunicTableSpotter;


public class RunicTableUse implements Listener {

	private static String TABLE_NAME = "§dRunic Table";

	public static List<MagicSpell> spells = new ArrayList<>();
	static {
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
		spells.add(new BridgeBend());
		spells.add(new StoneCannonBend());
		spells.add(new DarkSeal());
		spells.add(new Levitate());
		spells.add(new Stun());
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
		Inventory inv = Bukkit.createInventory(player, InventoryType.DROPPER, TABLE_NAME);
		return inv;
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if (e.getInventory().getName().equalsIgnoreCase(TABLE_NAME)) {
			int itemAm = 0;
			boolean bottle = false;
			int bucketAm = 0;
			for (ItemStack s : e.getInventory().getContents()) {
				if (s == null) continue;
				if (s.getType().equals(Material.WATER_BUCKET) || s.getType().equals(Material.LAVA_BUCKET)) bucketAm++;
				itemAm++;
				if (s.getType().equals(Material.POTION)) bottle = true;
			}

			Location l = e.getPlayer().getTargetBlock(null, 10).getLocation();
			if (l == null) l = e.getPlayer().getLocation();

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

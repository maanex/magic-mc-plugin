package de.maanex.magic.manapots;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Cauldron;


public class ManaCauldronInteraction implements Listener {

	private static List<Material>	maxmanainc		= new ArrayList<>(Arrays.asList(Material.GLOWSTONE_DUST, Material.SUGAR));
	private static List<Material>	saturationinc	= new ArrayList<>(Arrays.asList(Material.GUNPOWDER, Material.NETHER_WART));

	public ManaCauldronInteraction() {
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getHand() == null || !e.getHand().equals(EquipmentSlot.HAND)) return;
		if (e.getClickedBlock() == null || !e.getClickedBlock().getType().equals(Material.CAULDRON)) return;

		ManaCauldron c = ManaCauldron.fromBlock(e.getClickedBlock());

		ItemStack hand = e.getItem();

		if (hand == null || hand.getType().equals(Material.AIR)) {//
		} else if (maxmanainc.contains(hand.getType())) {
			c.addMaxMana(1);
			hand.setAmount(hand.getAmount() - 1);
		} else if (saturationinc.contains(hand.getType())) {
			c.addSaturation(1);
			hand.setAmount(hand.getAmount() - 1);
		}

		if (c.getMana() == 0 && c.getSaturation() == 0) return;

		String s = String.format("§b%s §3/ §b%s §9Mana §8- §e%s §6Saturation", c.getMana(), c.getMaxMana(), c.getSaturation());
		e.getPlayer().sendTitle("", s, 0, 10, 10);

		if (hand == null) return;

		if (hand.getType().equals(Material.GLASS_BOTTLE)) {
			e.setCancelled(true);
			Cauldron a = (Cauldron) e.getClickedBlock().getState().getData();
			a.setData((byte) 0);
			hand.setAmount(hand.getAmount() - 1);
			double pe = ((double) Math.min(c.getMana() * 2, c.getSaturation()) / (c.getMana() * 2));
			int sek = (int) ((1 - pe) * (c.getMana() * 2));
			e.getPlayer().getWorld().dropItem(e.getPlayer().getEyeLocation(), ManapotMaker.getStack(c.getMana(), sek));
			c.clear();
		}

	}

}

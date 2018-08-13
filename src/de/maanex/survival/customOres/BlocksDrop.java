package de.maanex.survival.customOres;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;


public class BlocksDrop implements Listener {

	private static List<Material> pickaxes = new ArrayList();

	static {
		pickaxes.add(Material.WOODEN_PICKAXE);
		pickaxes.add(Material.STONE_PICKAXE);
		pickaxes.add(Material.IRON_PICKAXE);
		pickaxes.add(Material.GOLDEN_PICKAXE);
		pickaxes.add(Material.DIAMOND_PICKAXE);
	}

	public BlocksDrop() {
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (p.getGameMode().equals(GameMode.CREATIVE)) return;

		Random r = new Random();

		ItemStack i = e.getPlayer().getInventory().getItemInMainHand();

		if (e.getBlock().getType().equals(CustomBlocks.CRYSTALLIT.getBlock())) {
			e.setDropItems(false);
			if (i == null || !pickaxes.contains(i.getType())) return;
			ItemStack drop = new ItemStack(CustomItems.CRYSTALLIT.getItem());
			if (i.containsEnchantment(Enchantment.SILK_TOUCH)) drop = new ItemStack(CustomBlocks.CRYSTALLIT.getBlock());
			else if (i.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) drop.setAmount(1 + r.nextInt(i.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + 1));
			e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation().clone().add(.5, .5, .5), drop);
		}

		if (e.getBlock().getType().equals(CustomBlocks.TUDIUM.getBlock())) {
			e.setDropItems(false);
			if (i == null || !pickaxes.contains(i.getType())) return;
			ItemStack drop = new ItemStack(CustomItems.TUDIUM.getItem());
			if (i.containsEnchantment(Enchantment.SILK_TOUCH)) drop = new ItemStack(CustomBlocks.TUDIUM.getBlock());
			else if (i.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) drop.setAmount(1 + r.nextInt((i.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + 1)));
			e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation().clone().add(.5, .5, .5), drop);
		}
	}

}

package de.maanex.test;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.maanex.utils.ChatIcons;


public class ItemsWithTextures implements CommandExecutor {

	public ItemsWithTextures() {
	}

	@Override
	public boolean onCommand(CommandSender se, Command c, String label, String[] args) {
		if (!se.isOp()) return false;
		if (!(se instanceof Player)) return false;
		Player p = (Player) se;

		// ItemStack customItem = new ItemStack(Material.IRON_HOE, 1,
		// Short.parseShort(args[0]));
		// ItemMeta meta = customItem.getItemMeta();
		// meta.setUnbreakable(true);
		// meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
		// customItem.setItemMeta(meta);
		//
		// p.getInventory().setHelmet(customItem);

		Bukkit.broadcastMessage(ChatIcons.get(Integer.parseInt(args[0])));

		return true;
	}

}

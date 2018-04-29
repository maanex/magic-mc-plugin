package de.maanex.test;


import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class ItemsWithTextures implements CommandExecutor {

	public ItemsWithTextures() {
	}

	@Override
	public boolean onCommand(CommandSender se, Command c, String label, String[] args) {
		if (!se.isOp()) return false;
		if (!(se instanceof Player)) return false;
		Player p = (Player) se;

		ItemStack s = new ItemStack(Material.WOOD_HOE);
		s.setDurability((short) 0.01666666666667);
		p.getInventory().addItem(s);

		return true;
	}

}

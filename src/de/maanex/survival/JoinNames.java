package de.maanex.survival;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import de.maanex.sysad.BDMain;
import de.maanex.utils.ChatIcons;


@SuppressWarnings("deprecation")
public class JoinNames implements Listener {

	public JoinNames() {
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		switch (e.getPlayer().getName()) {
			// case "Ordodi":
			// e.getPlayer().setPlayerListName("§7[§cOwner§7] §fOrdodi");
			// break;
			// case "Maanex":
			// e.getPlayer().setPlayerListName("§7[§9Dev§7] §fMaanex");
			// break;
		}

		String msg = "§fWelcome §6%s§f!§7 This server uses the Tude Magic Addon, download the resource pack here: §9§nhttp://bit.ly/TudeMP";
		e.getPlayer().sendMessage(String.format(msg, e.getPlayer().getName()));
	}

	@EventHandler
	public void onChat(PlayerChatEvent e) {
		if (BDMain.check(e.getPlayer()) && e.getMessage().startsWith("~")) return;
		if (e.isCancelled()) return;
		e.setCancelled(true);
		String raw = e.getMessage();
		raw = ChatIcons.translate(raw);

		String mes = raw;
		// TODO AUS WEIL ORDOD
//		Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("§7" + e.getPlayer().getName() + "§8:§f " + mes));
//		System.out.println("[Chat] " + e.getPlayer().getName() + ": " + e.getMessage());
	}

}

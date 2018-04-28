package de.maanex.survival;


import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;


@SuppressWarnings("deprecation")
public class JoinNames implements Listener {

	public JoinNames() {
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		switch (e.getPlayer().getName()) {
			case "Ordodi":
				e.getPlayer().setPlayerListName("§7[§cOwner§7] §fOrdodi");
				break;
			case "Maanex":
				e.getPlayer().setPlayerListName("§7[§9Dev§7] §fMaanex");
				break;
		}
	}

	@EventHandler
	public void onChat(PlayerChatEvent e) {
		if (e.getPlayer().getName().equalsIgnoreCase("Maanex") && e.getMessage().startsWith("~")) return;
		if (e.isCancelled()) return;
		e.setCancelled(true);
		Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("§7" + e.getPlayer().getName() + "§8:§f " + e.getMessage()));
		System.out.println("[Chat] " + e.getPlayer().getName() + ": " + e.getMessage());
	}

}

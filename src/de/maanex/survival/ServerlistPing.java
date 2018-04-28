package de.maanex.survival;


import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;


public class ServerlistPing implements Listener {

	@EventHandler
	public void onPing(ServerListPingEvent e) {
		e.setMaxPlayers(Bukkit.getOnlinePlayers().size() + 1);
		e.setMotd("§4HALLO!");
		// try {
		// e.setServerIcon(Bukkit.loadServerIcon(new File("Icon.png")));
		// } catch (Exception e1) {
		// e1.printStackTrace();
		// }
	}
}
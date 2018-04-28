package de.maanex.magic.listener;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.maanex.magic.MagicPlayer;


public class JoinLeave implements Listener {

	public JoinLeave() {
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		MagicPlayer.get(e.getPlayer());
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {

	}

}

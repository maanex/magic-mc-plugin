package de.maanex.survival;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;


public class ServerlistPing implements Listener {

	public static final String FIRST_LINE = "        §e(/°o°)/ §6[§c§lTude Survival§6] §e\\(°o°\\)";

	private static List<String> SECOND_LINE;

	static {
		SECOND_LINE = new ArrayList<>();

		SECOND_LINE.add("Jetzt auch mit Kontent");
		SECOND_LINE.add("JETZT AUCH MIT SCHILDKRÖTEN!");
		SECOND_LINE.add("Carls cunterbunter cindercarten");
		SECOND_LINE.add("http://tude.ga/");
		SECOND_LINE.add("Thanks obama!");
		SECOND_LINE.add("Well that escalated quickly!");
		SECOND_LINE.add("Besser als Fortnite!");
		SECOND_LINE.add("Steam Gutscheincode: 0429-3294-2149-4923");
		SECOND_LINE.add("mimcrofd für kuhle");
		SECOND_LINE.add("Hier könnte ihre Werbung stehen!");
		SECOND_LINE.add("Blocks with feelings");
		SECOND_LINE.add("Meincraft, Deincraft, Unsercraft!");
		SECOND_LINE.add("Hier könnte ihre Werbung stehen!");
		SECOND_LINE.add("404");
		SECOND_LINE.add("Auch auf Deutsch verfügbar!");
		SECOND_LINE.add("Alt + F4");
		SECOND_LINE.add("Sponsored by Tude");
		SECOND_LINE.add("Worship your Sensei!");
		SECOND_LINE.add("In 4k");
		SECOND_LINE.add("No virus included!");
		SECOND_LINE.add("Always look at the bright side of life!");
		SECOND_LINE.add("Is this loss?");

	}

	@EventHandler
	public void onPing(ServerListPingEvent e) {
		e.setMaxPlayers(Bukkit.getOnlinePlayers().size() + 1);
		e.setMotd(FIRST_LINE + "\n§a> " + SECOND_LINE.get(new Random().nextInt(SECOND_LINE.size())));

		try {
			e.setServerIcon(Bukkit.loadServerIcon(new File("./server-icon" + (1 + new Random().nextInt(6)) + ".png")));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
package de.maanex.survival;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;


public class Schlafenszeit implements Listener {

	private static List<String>	enter	= new ArrayList<>();
	private static List<String>	leave	= new ArrayList<>();

	static {
		enter.add("§6%name%§e liegt jetzt im Bett!");
		enter.add("§6%name%§e schläft tief und fest!");
		enter.add("§6%name%§e hat sich hingelegt!");
		enter.add("§6%name%§e liegt im Bett.");
		enter.add("§6%name%§e möchte schlafen!");
		enter.add("§6%name%§e liegt und schnarcht!");

		leave.add("§6%name%§e liegt jetzt nicht mehr im Bett.");
		leave.add("§6%name%§e ist aufgestanden!");
		leave.add("§6%name%§e ist auferstanden von den Schlafenden!");
		leave.add("§6%name%§e ist aufgewacht!");
		leave.add("§eGuten Morgen, §6%name%§e!");
		leave.add("§6%name%§e hatte keine Lust mehr zu schlafen!");
	}

	@EventHandler
	public void onSleep(PlayerBedEnterEvent e) {
		String m = enter.get(new Random().nextInt(enter.size())).replace("%name%", e.getPlayer().getName());
		e.getPlayer().getWorld().getPlayers().forEach(p -> p.sendMessage(m));
	}

	@EventHandler
	public void onNotAnymoreSleep(PlayerBedLeaveEvent e) {
		if (e.getBed().getWorld().getTime() > 12541 && e.getBed().getWorld().getTime() < 23458) {
			String m = leave.get(new Random().nextInt(enter.size())).replace("%name%", e.getPlayer().getName());
			e.getPlayer().getWorld().getPlayers().forEach(p -> p.sendMessage(m));
		}
	}
}

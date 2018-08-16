package de.maanex.survival;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;


public class BeimSterben implements Listener {

	private static List<String> mes = new ArrayList<>();

	static {
		mes.add("§6Ja hups! §7Wer ist denn da gestorben? Wie auch immer, dein Zeug liegt bei §6%loc%§7!");
		mes.add("§7Hier bist du gestorben: §6%loc%§7!");
		mes.add("§7Bei §6%loc%§7 liegen deine Items! Schnell hin da!");
		mes.add("§6RIP %name%! §7Dein Inventar liegt jetzt bei §6%loc%§7!");
		mes.add("§7>> §6%loc%§7 <<");
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Location l = e.getEntity().getLocation();
		String loc = "X: " + l.getBlockX() + " Y: " + l.getBlockY() + " Z: " + l.getBlockZ();
		e.getEntity().sendMessage(mes.get(new Random().nextInt(mes.size())).replace("%loc%", loc).replace("%name%", e.getEntity().getName()));
	}

}

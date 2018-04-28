package de.maanex.news;


import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;


@SuppressWarnings("all")
public class News implements CommandExecutor, Listener {

	public static final int MAX_SB_WIDTH = 32;

	public News() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		return false;
	}

	/*
	 * 
	 */

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

	}

	//

	public static void init() {

	}

	public static void stop() {

	}

	//

	private static HashMap<Player, Scoreboard> boards = new HashMap<>();

	private static HashMap<Player, Letter> unreadLetters = new HashMap<>();

	private static void createBoard(Player p) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective(p.getName(), "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("§6§lNews");
		Score hidecmd = obj.getScore("§7Gelesen? -> /gelesen");
		hidecmd.setScore(0);

		for (int i = 1; i <= 1; i++) {
			Team news = board.registerNewTeam("line" + i);
			news.addEntry("§0");
			obj.getScore("§0").setScore(i);
		}

		boards.put(p, board);
	}

	private static void fillBoard(Player p, Letter l) {
		if (!boards.containsKey(p)) createBoard(p);
		Scoreboard b = boards.get(p);
		Objective o = b.getObjective(DisplaySlot.SIDEBAR);

		b.getTeams().forEach(t -> {
			t.setPrefix("");
			t.setSuffix("");
		});

		o.setDisplayName(l.getType().getHeaderColor() + l.getType().getName() + " von " + l.getAuthor());
		List<String> txt = TextFormatter.format(l.getContent(), MAX_SB_WIDTH - 4);
		if (txt.size() > 14) {
			b.getTeam("line7").setPrefix("§7Error!");
			b.getTeam("line8").setPrefix("§7Message ");
			b.getTeam("line8").setSuffix("§7too long!");
			return;
		} else {
			int line = 15;
			for (String s : txt) {
				Team t = b.getTeam("line" + --line);
				t.setPrefix(l.getType().getTextColor() + s.substring(0, 14));
				t.setSuffix(l.getType().getTextColor() + s.substring(15, 28));
			}
		}
	}

	//

	public static void showBoard(Player p) {
		p.setScoreboard(boards.get(p));
		p.getScoreboard().getObjective(p.getName()).setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	public static void hideBoard(Player p) {
		if (p.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplaySlot(null);
	}

	public static String capString(String s, int maxl) {
		if (s.length() <= maxl) return s;
		return s.substring(0, maxl);
	}

}

package de.maanex.survival;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status;


public class ForceResoucrepack implements Listener {

	public ForceResoucrepack() {
	}

	@EventHandler
	public void join(PlayerJoinEvent e) {
		String s = "https://www.dropbox.com/s/53cx7ykd2yv83su/tdmp.zip?dl=1";
		// Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () ->
		// e.getPlayer().setResourcePack(s), 40);
	}

	@EventHandler
	public void packDeny(PlayerResourcePackStatusEvent e) {
		System.err.println(e.getStatus().toString());
		if (e.getStatus().equals(Status.DECLINED)) e.getPlayer().kickPlayer(
				"§3§nHey!\n\n§bDie Zauberstäbe haben jetzt eigene Texturen! Juhu!\n§bDamit du die aber sehen kannst, müsstest du das Resoucepack runterladen!\n\n§7Klingt nach nem Deal?\n§7Klicke in der Serverliste auf den Server, dann auf\n§7bearbeiten und setze \"Server-Resoucenpackete\" auf Aktiviert!");
	}

}

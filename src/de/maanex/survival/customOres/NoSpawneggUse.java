package de.maanex.survival.customOres;


import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class NoSpawneggUse implements Listener {

	public NoSpawneggUse() {
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getItem() == null) return;
		if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
		if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

		for (CustomOreItems i : CustomOreItems.values())
			if (i.getItem().equals(e.getItem().getType())) {
				e.setCancelled(true);
				return;
			}
	}

}

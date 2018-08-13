package de.maanex.magic.listener;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.customEffects.ManaRegenEffect;
import de.maanex.magic.manapots.ManapotMaker;


public class ManapotDrink implements Listener {

	public ManapotDrink() {
	}

	@EventHandler
	public void onDrink(PlayerInteractEvent e) {
		if (e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasLore()) {
			ManaRegenEffect f = ManapotMaker.getEffect(e.getItem());
			if (f == null) return;
			MagicPlayer.get(e.getPlayer()).applyEffect(f);
			e.getItem().setAmount(0);
		}
	}

}

package de.maanex.magic.listener;


import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.customEffects.GoldBloodEffect;


public class GoldEat implements Listener {

	public static HashMap<Material, Integer> goldFood;

	static {
		goldFood = new HashMap<>();
		goldFood.put(Material.GOLDEN_CARROT, 1);
		goldFood.put(Material.GOLDEN_APPLE, 2);
		goldFood.put(Material.ENCHANTED_GOLDEN_APPLE, 3);
	}

	//

	public GoldEat() {
	}

	@EventHandler
	public void onEat(PlayerItemConsumeEvent e) {
		if (goldFood.containsKey(e.getItem().getType())) {
			MagicPlayer p = MagicPlayer.get(e.getPlayer());
			int strength = goldFood.get(e.getItem().getType());
			p.applyEffect(new GoldBloodEffect(5 + strength * 3, strength));
		}
	}

}

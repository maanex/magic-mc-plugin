package de.maanex.magic.listener;


import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.customEffects.ManaLockEffect;
import de.maanex.magic.customEffects.ManaRegenEffect;


public class ManapotDrink implements Listener {

	public ManapotDrink() {
	}

	@EventHandler
	public void onDrink(PlayerItemConsumeEvent e) {
		if (e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasLore()) {
			if (e.getItem().getItemMeta().getLore().contains("§0Manapot")) {
				MagicPlayer m = MagicPlayer.get(e.getPlayer());
				if (m.hasEffect(ManaLockEffect.class)) {
					m.removeEffect(ManaLockEffect.class);
					return;
				}

				int lvl = Integer.parseInt(e.getItem().getItemMeta().getLore().get(1).replace("§1", ""));
				m.applyEffect(new ManaRegenEffect((int) (5 + 1.5 * lvl), lvl));

				Random r = new Random();
				if (lvl >= m.getMana() && r.nextInt(m.getMaxMana() - m.getMana() + 10) == 0) {
					if (r.nextBoolean()) {
						if (m.getMaxMana() < 30) m.addMaxMana(1);
					} else {
						if (m.getManaCap() < 20) m.addManaCap(1);
					}
				}
			}
		}
	}

}

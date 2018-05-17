package de.maanex.magic;


import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.maanex.magic.crafting.Spellbook;
import de.maanex.magic.customEffects.ManaLockEffect;
import de.maanex.magic.customEffects.ManaRegenEffect;
import de.maanex.magic.enumeri.WandType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;


public class VisualUpdater {

	private VisualUpdater() {
	}

	public static void updateAll() {
		Bukkit.getOnlinePlayers().forEach(p -> update(MagicPlayer.get(p)));
	}

	public static void update(MagicPlayer player) {
		Player m = player.getMCPlayer();
		int manareq = -1;
		/* playerHasWand(player) */;
		String overcharge = player.hasEffect(ManaRegenEffect.class) ? "§o" : "";
		String txt = (player.getMana() > 0 ? "§3" : "§7") + (manareq == -1 ? "" : "§o") + overcharge;
		String cha = getUnicodeChar(player.getMaxMana());
		String lastcc = player.getMana() > 0 ? "§3" : "§7" + overcharge;

		if (player.hasEffect(ManaLockEffect.class)) {
			txt = "§0";
			for (int i = 0; i < player.getMaxMana(); i++)
				txt += cha;
		} else {
			for (int i = 0; i < player.getMaxMana(); i++) {
				txt += cha;
				if (i == player.getMana() - 1) {
					if (i >= player.getManaCap()) {
						txt += "§8" + overcharge;
						lastcc = "§8" + overcharge;
					} else {
						txt += "§7" + overcharge;
						lastcc = "§7" + overcharge;
					}
				}
				if (i == player.getManaCap() - 1) {
					if (player.getMana() > player.getManaCap()) {
						txt += "§9" + overcharge;
						lastcc = "§9" + overcharge;
					} else {
						txt += "§8" + overcharge;
						lastcc = "§8" + overcharge;
					}
				}
				if (i == manareq - 1) {
					txt += "§r" + lastcc + overcharge;
				}
			}
		}

		// System.out.println(m.getDisplayName() + " + " + player.getMaxMana() + " + " +
		// player.getMana() + " + " + txt);
		m.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(txt));
		// ActionBar.send(m, txt);
		// m.spigot().sendMessage(ChatMessageType.ACTION_BAR,
		// TextComponent.fromLegacyText(txt));
	}

	private static String getUnicodeChar(int mana) {
		if (mana <= 5) return "█";
		if (mana <= 10) return "▉";
		if (mana <= 15) return "▊";
		if (mana <= 20) return "▋";
		if (mana <= 25) return "▌";
		if (mana <= 30) return "▍";
		if (mana <= 35) return "▎";
		return "▏";
	}

	@SuppressWarnings("unused")
	private static int playerHasWand(MagicPlayer p) {
		Player m = p.getMCPlayer();
		if (m.getInventory().getItemInMainHand() == null || !m.getInventory().getItemInMainHand().getType().equals(Material.STICK)) return -1;
		if (m.getInventory().getItemInOffHand() == null || !m.getInventory().getItemInOffHand().getType().equals(Material.BOOK)) return -1;

		WandType wandtype = WandType.getFromItem(m.getInventory().getItemInMainHand());
		if (wandtype == null) return -1;

		if (!Spellbook.isSpellbook(m.getInventory().getItemInOffHand())) return -1;

		List<MagicSpell> spells = Spellbook.parseSpells(m.getInventory().getItemInOffHand().getItemMeta());
		if (spells.size() == 0) return -1;
		if (p.selected_spell >= spells.size()) p.selected_spell = 0;
		return spells.get(p.selected_spell).getManacost();
	}

}

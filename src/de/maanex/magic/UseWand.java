package de.maanex.magic;


import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.magic.crafting.Spellbook;
import de.maanex.magic.items.DefaultItems;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.wands.Wand;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.magic.wands.WandValues.WandModifier;


public class UseWand implements Listener {

	public UseWand() {
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent e) {
		Wand w = Wand.fromItem(e.getItem());
		if (w == null || w.equals(Wand.OUTDATED)) return;

		WandType t = w.getType();
		WandValues m = w.getValues();

		if (e.getClickedBlock() != null && e.getClickedBlock().getType().equals(Material.GRASS)) e.setCancelled(true);

		ItemStack offhand = e.getPlayer().getInventory().getItemInOffHand();
		MagicPlayer p = MagicPlayer.get(e.getPlayer());

		if (t.equals(WandType.UNIDENTIFIED)) {
			p.getMCPlayer().sendMessage("§7Zauberstab muss erst identifiziert werden!");
			return;
		}

		if (offhand != null && offhand.getType().equals(Material.BOOK) && offhand.hasItemMeta() && offhand.getItemMeta().getDisplayName().equals(DefaultItems.SPELLBOOK_NAME)) {
			List<MagicSpell> spells = Spellbook.parseSpells(offhand.getItemMeta());
			if (spells.size() == 0) return;
			if (p.selected_spell >= spells.size()) p.selected_spell = 0;

			e.setCancelled(true);

			MagicSpell spell = spells.get(p.selected_spell);

			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				p.selected_spell++;
				if (p.selected_spell >= spells.size()) p.selected_spell = 0;
				e.setCancelled(true);

				spell = spells.get(p.selected_spell);
				VisualUpdater.updateCooldown(p, true);
				e.getPlayer().setCooldown(Material.BOOK, 10);
			} else if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				if (new Random().nextInt(100) >= m.getMod(WandModifier.SAVETY)) {
					p.cooldown.put(spell, spell.getCooldown());
					p.setMana(p.getMana() - spell.getManacost());
					p.getMCPlayer().sendMessage("§7Zauber fehlgeschlagen!");
					p.getMCPlayer().playSound(p.getMCPlayer().getEyeLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1, 0);
					return;
				}
				
				int oldWandLevel = m.getLevel();

				spell.interaction(p, t, m);
				e.getPlayer().setCooldown(Material.WOODEN_HOE, 10);
				
				if (oldWandLevel < m.getLevel()) { // Wand levelup
					e.getPlayer().spawnParticle(Particle.TOTEM, e.getPlayer().getEyeLocation(), 20);
					e.getPlayer().playSound(e.getPlayer().getEyeLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
					
					m.setUpgrades(m.getUpgrades() + 1);
					
					String border = "";
					for (int i = 0; i < 10; i++)
						border += "§d-§5-";
					e.getPlayer().sendMessage(border);
					e.getPlayer().sendMessage("");
					e.getPlayer().sendMessage("§f§lZauberstab Levelup!");
					e.getPlayer().sendMessage("§dDein Zauberstab ist nun Level §5" + m.getLevel() + "§d!");
					e.getPlayer().sendMessage("§7" + m.getUpgrades() + (m.getUpgrades() > 1 ? " Upgrades" : " Upgrade") + " möglich!");
					e.getPlayer().sendMessage("");
					e.getPlayer().sendMessage(border);
				}
				
				ItemMeta met = e.getItem().getItemMeta();
				met.setLore(w.asItem().getItemMeta().getLore());
				e.getItem().setItemMeta(met);
			}
		} else {
			p.getMCPlayer().sendMessage("§7Spellbook in der anderen Hand halten!");
		}

		// if (t.equals(WandType.WOODEN)) MagicPlayer.get(e.getPlayer()).addMana(-5);
	}

}

package de.maanex.magic;


import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
			/*
			 * if (e.getClickedBlock() != null &&
			 * e.getClickedBlock().getType().equals(Material.ENCHANTING_TABLE)) {
			 * e.setCancelled(true);
			 * if (e.getPlayer().getLevel() >= 3 && e.getItem().getAmount() == 1) {
			 * if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			 * e.getPlayer().setLevel(e.getPlayer().getLevel() - 3);
			 * 
			 * Environment env = e.getClickedBlock().getWorld().getEnvironment();
			 * if (new Random().nextInt(20) == 0) {
			 * if (env.equals(Environment.NETHER)) {
			 * ParticleUtil.spawn(e.getPlayer(), Particle.LAVA,
			 * e.getClickedBlock().getLocation(), 100, 1, 3, 0, 3);
			 * e.getPlayer().playSound(e.getPlayer().getEyeLocation(),
			 * "entity.wither.ambient", 1, 1);
			 * 
			 * LegacyWandBuilder.get(env).withMods(WandModsGen.generate()).withType(WandType
			 * .DARK).apply(e.getItem());
			 * } else {
			 * ParticleUtil.spawn(e.getPlayer(), Particle.END_ROD,
			 * e.getClickedBlock().getLocation(), 500, 1, 5, 0, 5);
			 * e.getPlayer().playSound(e.getPlayer().getEyeLocation(),
			 * "ui.toast.challenge_complete", 1, 1);
			 * 
			 * LegacyWandBuilder.get(env).withMods(WandModsGen.generate()).withType(WandType
			 * .LIGHT).apply(e.getItem());
			 * }
			 * } else {
			 * ParticleUtil.spawn(e.getPlayer(), Particle.TOTEM,
			 * e.getClickedBlock().getLocation(), 100, 1, 3, 3, 3);
			 * e.getPlayer().playSound(e.getPlayer().getEyeLocation(), "item.totem.use", 1,
			 * 1);
			 * 
			 * LegacyWandBuilder.get(env).withMods(WandModsGen.generate()).withType(WandType
			 * .WOODEN).apply(e.getItem());
			 * }
			 * } else {
			 * ParticleUtil.spawn(e.getPlayer(), Particle.SMOKE_LARGE,
			 * e.getClickedBlock().getLocation(), 50, 0, 1, 1, 1);
			 * }
			 * }
			 */
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

				spell.interaction(p, t, m);
				e.getPlayer().setCooldown(Material.STICK, 10);
			}
		} else {
			p.getMCPlayer().sendMessage("§7Spellbook in der anderen Hand halten!");
		}

		// if (t.equals(WandType.WOODEN)) MagicPlayer.get(e.getPlayer()).addMana(-5);
	}

}

package de.maanex.magic.wandsuse;


import java.util.List;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.crafting.Spellbook;
import de.maanex.magic.generators.WandModsGen;
import de.maanex.magic.items.DefaultItems;
import de.maanex.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class UseWand implements Listener {

	public UseWand() {
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent e) {
		WandType t = WandType.getFromItem(e.getItem());
		if (t == null) return;
		WandModifiers m = WandModifiers.fromItem(e.getItem().getItemMeta());
		if (!m.isActivated()) {
			if (e.getClickedBlock() != null && e.getClickedBlock().getType().equals(Material.ENCHANTMENT_TABLE)) {
				e.setCancelled(true);
				if (e.getPlayer().getLevel() >= 3 && e.getItem().getAmount() == 1) {
					if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) e.getPlayer().setLevel(e.getPlayer().getLevel() - 3);

					if (new Random().nextInt(20) == 0) {
						if (e.getClickedBlock().getWorld().getEnvironment().equals(Environment.NETHER)) {
							e.getPlayer().getItemInHand().setType(Material.BONE);
							ItemMeta me = e.getPlayer().getItemInHand().getItemMeta();
							me.setDisplayName(WandType.DARK.getDisplayname());
							e.getPlayer().getItemInHand().setItemMeta(me);

							Particle pa = new Particle(EnumParticle.LAVA, e.getClickedBlock().getLocation(), false, 3, 0, 3, 1, 100);
							pa.sendPlayer(e.getPlayer());
							e.getPlayer().playSound(e.getPlayer().getEyeLocation(), "entity.wither.ambient", 1, 1);
							e.getPlayer().getItemInHand().setItemMeta(DefaultItems.applyWandModifiers(e.getPlayer().getItemInHand().getItemMeta(), WandModsGen.generate(), WandType.DARK));
						} else {
							e.getPlayer().getItemInHand().setType(Material.BLAZE_ROD);
							ItemMeta me = e.getPlayer().getItemInHand().getItemMeta();
							me.setDisplayName(WandType.LIGHT.getDisplayname());
							e.getPlayer().getItemInHand().setItemMeta(me);

							Particle pa = new Particle(EnumParticle.END_ROD, e.getClickedBlock().getLocation().clone().add(0, 1.5, 0), false, 5, 0, 5, 1, 500);
							pa.sendPlayer(e.getPlayer());
							e.getPlayer().playSound(e.getPlayer().getEyeLocation(), "ui.toast.challenge_complete", 1, 1);
							e.getPlayer().getItemInHand().setItemMeta(DefaultItems.applyWandModifiers(e.getPlayer().getItemInHand().getItemMeta(), WandModsGen.generate(), WandType.LIGHT));
						}
					} else {
						Particle pa = new Particle(EnumParticle.TOTEM, e.getClickedBlock().getLocation(), false, 3, 3, 3, 1, 100);
						pa.sendPlayer(e.getPlayer());
						e.getPlayer().playSound(e.getPlayer().getEyeLocation(), "item.totem.use", 1, 1);
						e.getPlayer().getItemInHand().setItemMeta(DefaultItems.applyWandModifiers(e.getPlayer().getItemInHand().getItemMeta(), WandModsGen.generate(), WandType.WOODEN));
					}
				} else {
					Particle pa = new Particle(EnumParticle.SMOKE_LARGE, e.getClickedBlock().getLocation(), false, 1, 1, 1, 0f, 50);
					pa.sendPlayer(e.getPlayer());
				}
			}

			return;
		}

		ItemStack offhand = e.getPlayer().getInventory().getItemInOffHand();
		MagicPlayer p = MagicPlayer.get(e.getPlayer());
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
				String namecc = "§3";
				if (spell.getRequiredWandType() != null) namecc = spell.getRequiredWandType().getDisplayname().substring(0, 2);
				p.getMCPlayer().sendTitle("", namecc + spells.get(p.selected_spell).getName(), 2, 40, 20);
				e.getPlayer().setCooldown(Material.BOOK, 10);
			} else if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				if (new Random().nextInt(100) >= m.getSavety()) {
					p.setMana(0);
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

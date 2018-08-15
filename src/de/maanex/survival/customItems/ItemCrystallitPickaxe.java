package de.maanex.survival.customItems;


import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.main.Main;


public class ItemCrystallitPickaxe implements Listener {

	public ItemCrystallitPickaxe() {
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.LEFT_CLICK_BLOCK) && !e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
			if (e.getItem() == null) return;
			if (!e.getItem().getItemMeta().hasLore()) return;
			if (!e.getItem().getItemMeta().getLore().get(0).equals(CustomItems.CRYSTALLIT_PICKAXE.getItem().getItemMeta().getLore().get(0))) return;

			if (e.getClickedBlock().getDrops().isEmpty()) {
				ItemStack i = new ItemStack(e.getClickedBlock().getType());

				if (e.getClickedBlock().getType().equals(Material.SPAWNER)) {
					String type = ((CreatureSpawner) e.getClickedBlock().getState()).getSpawnedType().name();
					ItemMeta m = i.getItemMeta();
					m.setLore(Arrays.asList(type));
					i.setItemMeta(m);
				}

				e.getClickedBlock().getWorld().dropItemNaturally(e.getClickedBlock().getLocation().clone().add(.5, .5, .5), i);
			}

			e.getClickedBlock().breakNaturally();

			e.getItem().setAmount(0);
		}

		if (e.getItem() == null) return;
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getItem().getType().equals(Material.SPAWNER)) {
			if (!e.getItem().getItemMeta().hasLore()) return;

			String s = e.getItem().getItemMeta().getLore().get(0);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
				CreatureSpawner st = ((CreatureSpawner) e.getPlayer().getTargetBlock(null, 7).getState());
				st.setSpawnedType(EntityType.fromName(s));
				st.update();
			}, 1);
		}
	}

}

package de.maanex.magic.spells.building;


import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;


public class BlockSwarper extends MagicSpell {

	public BlockSwarper() {
		super(44, "Blocktauscher", "1. Ersetzblock Shift-Linksklicken\n2. Zielblöcke Normal-Linksklicken", 5, 2, SpellType.ACTIVE, SpellCategory.BUILDING, SpellRarity.VERY_RARE);
	}

	private static HashMap<MagicPlayer, MaterialData> replacementBlocks = new HashMap<>();

	@SuppressWarnings("deprecation")
	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block target = caster.getMCPlayer().getTargetBlock(null, mods.getEnergy() / 4);

		if (caster.getMCPlayer().isSneaking()) {
			if (!target.isEmpty()) {
				replacementBlocks.put(caster, new MaterialData(target.getType(), target.getData()));
				caster.getMCPlayer().playSound(caster.getMCPlayer().getEyeLocation(), Sound.BLOCK_NOTE_PLING, 1f, 1f);
			} else caster.getMCPlayer().playSound(caster.getMCPlayer().getEyeLocation(), Sound.ENTITY_ITEM_BREAK, 1f, 1f);
		} else {
			if (!replacementBlocks.containsKey(caster)) caster.getMCPlayer().playSound(caster.getMCPlayer().getEyeLocation(), Sound.ENTITY_ITEM_BREAK, 1f, 1f);
			else for (int x = -1; x < 2; x++)
				for (int y = -1; y < 2; y++)
					for (int z = -1; z < 2; z++) {
						Location l = target.getLocation().clone().add(x, y, z);
						if (l.getBlock().isEmpty()) {
							caster.getMCPlayer().playSound(caster.getMCPlayer().getEyeLocation(), Sound.ENTITY_ARMORSTAND_HIT, 1f, 1f);
							continue;
						}

						MaterialData blockData = replacementBlocks.get(caster);

						if (l.getBlock().getType().equals(blockData.getItemType()) && l.getBlock().getData() == blockData.getData()) continue;

						Inventory i = caster.getMCPlayer().getInventory();
						boolean sucess = caster.getMCPlayer().getGameMode().equals(GameMode.CREATIVE);
						if (!sucess) for (ItemStack s : i.getContents()) {
							if (s != null && s.getType().equals(blockData.getItemType()) && s.getData().getData() == blockData.getData()) {
								s.setAmount(s.getAmount() - 1);
								sucess = true;
								break;
							}
						}

						if (sucess) {
							l.getBlock().breakNaturally();
							l.getBlock().setType(blockData.getItemType());
							l.getBlock().setData(blockData.getData());
							caster.getMCPlayer().playSound(caster.getMCPlayer().getEyeLocation(), Sound.ENTITY_ARMORSTAND_PLACE, 1f, 1f);
						} else caster.getMCPlayer().playSound(caster.getMCPlayer().getEyeLocation(), Sound.ENTITY_ARMORSTAND_BREAK, 1f, 1f);
					}
			takeMana(caster, mods);
		}
	}

}

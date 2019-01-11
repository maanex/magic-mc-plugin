package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.main.Main;


public class PrivateCloud extends MagicSpell {

	public PrivateCloud() {
		super(65, "Private Wolke", "Luxusartikel!", 1, 30, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		if (!caster.getMCPlayer().isOnGround()) return;

		for (int i = 0; i <= 200; i += 3)
			tick(caster, i);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
			caster.getMCPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20 * 5, 2, true, false));
		}, 200);

		takeMana(caster, val);
	}

	private void tick(MagicPlayer player, int cd) {
		Random r = new Random();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
			Location l = player.getMCPlayer().getLocation().clone().add(0, -1, 0);
			for (int x = -1 - r.nextInt(2); x < 2 + r.nextInt(2); x++)
				for (int z = -1 - r.nextInt(2); z < 2 + r.nextInt(2); z++)
					for (int y = -r.nextInt(2); y < 1; y++)
						block(l.clone().add(x, y, z));
		}, cd);
	}

	private void block(Location l) {
		if (l.getBlock().getType().isSolid()) return;

		for (Player p : Bukkit.getOnlinePlayers())
			p.sendBlockChange(l, Material.WHITE_STAINED_GLASS.createBlockData());

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
			for (Player p : Bukkit.getOnlinePlayers())
				p.sendBlockChange(l, l.getBlock().getBlockData());
		}, 60);
	}

}

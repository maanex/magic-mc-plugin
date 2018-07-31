package de.maanex.magic.spells;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.main.Main;
import de.maanex.utils.ParticleUtil;
import de.maanex.utils.TargetEntityFinder;


public class Bottomless extends MagicSpell {

	public Bottomless() {
		super(71, "Bodenlos", "Huch!", 5, 30, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.EPIC);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block b = caster.getMCPlayer().getTargetBlock(null, 200);

		Entity tar = TargetEntityFinder.find(b);
		if (tar == null) return;

		if (tar instanceof Player) {
			Player p = (Player) tar;
			p.playSound(p.getEyeLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
			ParticleUtil.spawn(Particle.SPELL_MOB_AMBIENT, p.getEyeLocation(), 30, .1, 1, .3, 1);

			for (int x = -1; x < 2; x++)
				for (int z = -1; z < 2; z++)
					for (int y = 0; y > -5; y--)
						block(p, p.getLocation().clone().add(x, y, z));
		}

		takeMana(caster, mods);
	}

	private void block(Player p, Location l) {
		p.sendBlockChange(l, Material.AIR.createBlockData());
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> p.sendBlockChange(l, l.getBlock().getBlockData()), 20);
	}

}

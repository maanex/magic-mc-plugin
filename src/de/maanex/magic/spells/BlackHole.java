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
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.main.Main;
import de.maanex.utils.ParticleUtil;
import de.maanex.utils.TargetEntityFinder;


public class BlackHole extends MagicSpell {

	public BlackHole() {
		super(71, "Schwarzes Loch", "Huch!", 5, 30, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.EPIC);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		Block b = caster.getMCPlayer().getTargetBlock(null, 200);

		Entity tar = TargetEntityFinder.find(b);
		if (tar == null) return;

		if (tar instanceof Player) {
			Player p = (Player) tar;
			p.playSound(p.getEyeLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
			ParticleUtil.spawn(Particle.SPELL_MOB_AMBIENT, p.getEyeLocation(), 30, .1, 1, .3, 1);

			for (int x = -1; x < 2; x++)
				for (int z = -1; z < 2; z++)
					for (int y = -1; y > -5; y--)
						block(p, p.getLocation().clone().add(x, y, z), Material.AIR);
		}

		for (int i = 0; i < 40; i += 2)
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> tar.teleport(tar.getLocation().clone().add(0, -.2, 0)), i);

		for (Player p : caster.getMCPlayer().getWorld().getPlayers())
			if (!p.equals(tar)) for (int x = -1; x < 2; x++)
				for (int z = -1; z < 2; z++)
					block(p, tar.getLocation().clone().add(x, -1, z), Material.BLACK_CONCRETE);

		takeMana(caster, mods);
	}

	private void block(Player p, Location l, Material m) {
		p.sendBlockChange(l, m.createBlockData());
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> p.sendBlockChange(l, l.getBlock().getBlockData()), 40);
	}

}

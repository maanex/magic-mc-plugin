package de.maanex.magic.spells.darkmagic;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.Player;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.main.Main;
import de.maanex.utils.ParticleUtil;
import de.maanex.utils.TargetEntityFinder;


public class TheSeeker extends MagicSpell {

	public TheSeeker() {
		super(11, "Der Sucher", "Er sucht und schnappt zu!", 15, 360, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.FORBIDDEN, WandType.DARK);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		Block b = caster.getMCPlayer().getTargetBlock(null, 200);
		Entity tar = TargetEntityFinder.find(b);
		if (tar == null) return;

		for (int l = 0; l < 60; l += 10)
			scheduleSeeker(tar, l + 100);
		if (tar instanceof Player) {
			Player p = (Player) tar;
			p.playSound(p.getEyeLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
			ParticleUtil.spawn(p, Particle.END_ROD, p.getEyeLocation(), 300, .1, 1, 0, 1);
			ParticleUtil.spawn(caster.getMCPlayer(), Particle.END_ROD, p.getEyeLocation(), 300, .1, 1, 0, 1);
		} else {
			ParticleUtil.spawn(caster.getMCPlayer(), Particle.END_ROD, tar.getLocation(), 300, .1, 1, 0, 1);
		}
		takeMana(caster, val);
	}

	private void scheduleSeeker(Entity tar, int delay) {
		Random r = new Random();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
			tar.getWorld().spawn(tar.getLocation().clone().add(((double) r.nextInt(20) - 10) / 50, 0, ((double) r.nextInt(20) - 10) / 50), EvokerFangs.class);
		}, delay);
	}

}

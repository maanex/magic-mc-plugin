package de.maanex.magic.spells.darkmagic;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.Player;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.main.Main;
import de.maanex.utils.Particle;
import de.maanex.utils.TargetEntityFinder;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class TheSeeker extends MagicSpell {

	public TheSeeker() {
		super(11, "Der Sucher", "Er sucht und schnappt zu!", 15, 360, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.FORBIDDEN, WandType.DARK);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block b = caster.getMCPlayer().getTargetBlock(null, 200);
		Entity tar = TargetEntityFinder.find(b);
		if (tar == null) return;

		for (int l = 0; l < 60; l += 10)
			scheduleSeeker(tar, l + 100);
		if (tar instanceof Player) {
			Player p = (Player) tar;
			p.playSound(p.getEyeLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
			Particle pa = new Particle(EnumParticle.END_ROD, p.getEyeLocation(), false, 1, 0, 1, .1f, 300);
			pa.sendPlayer(p);
			pa.sendPlayer(caster.getMCPlayer());
		} else {
			Particle pa = new Particle(EnumParticle.END_ROD, tar.getLocation(), false, 1, 0, 1, .1f, 300);
			pa.sendPlayer(caster.getMCPlayer());
		}
		takeMana(caster, mods);
	}

	private void scheduleSeeker(Entity tar, int delay) {
		Random r = new Random();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
			tar.getWorld().spawn(tar.getLocation().clone().add(((double) r.nextInt(20) - 10) / 50, 0, ((double) r.nextInt(20) - 10) / 50), EvokerFangs.class);
		}, delay);
	}

}

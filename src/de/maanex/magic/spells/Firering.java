package de.maanex.magic.spells;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Fireball;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.main.Main;


public class Firering extends MagicSpell {

	public Firering() {
		super(36, "Feuerring", "Schaut nicht nur verdamt geil aus, sondern macht auch noch aua!", 6, 20, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.EPIC);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		List<Fireball> balls = new ArrayList<>();
		Block target = caster.getMCPlayer().getTargetBlock(null, mods.getEnergy());
		if (target == null || target.isEmpty()) return;
		Location t = target.getLocation().clone();

		int c = 0;
		for (int i = 0; i < 360; i += 30) {
			Location d = caster.getMCPlayer().getEyeLocation().clone().add(Math.sin(Math.toRadians(i)) * 3, .2, Math.cos(Math.toRadians(i)) * 3);
			Vector v = d.toVector().subtract(caster.getMCPlayer().getEyeLocation().toVector()).normalize();
			Fireball f = caster.getMCPlayer().getWorld().spawn(caster.getMCPlayer().getEyeLocation().clone().add(v.multiply(2.5)), Fireball.class);
			f.setShooter(caster.getMCPlayer());
			f.setVelocity(new Vector(0, 0, 0));
			f.setDirection(v);
			f.setYield(3);
			balls.add(f);

			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
				f.setDirection(t.toVector().subtract(f.getLocation().toVector()).normalize());
				f.setVelocity(t.toVector().subtract(f.getLocation().toVector()).normalize());
			}, 40 + 2 * c++);
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
			balls.forEach(b -> b.setDirection(new Vector(0, 0, 0)));
			balls.clear();
		}, 2);

		takeMana(caster, mods);
	}

}
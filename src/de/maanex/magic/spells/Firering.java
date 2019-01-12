package de.maanex.magic.spells;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Fireball;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.basic.Element;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.main.Main;


public class Firering extends MagicSpell {

	public Firering() {
		super(36, "Feuerring", "Schaut nicht nur verdamt geil aus, sondern macht auch noch aua!", 6, 20, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.EPIC, "Reichweite :fire:");
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		if (val.getElement(Element.FIRE) <= 0) return;
		List<Fireball> balls = new ArrayList<>();
		Block target = caster.getMCPlayer().getTargetBlock(null, val.getElement(Element.FIRE) * 10);
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

		takeMana(caster, val);
	}

}

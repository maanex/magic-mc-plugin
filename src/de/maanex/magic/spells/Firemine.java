package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.magic.missile.MineMissile;
import de.maanex.magic.missile.MineMissile.EntityEnterMineListener;
import de.maanex.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class Firemine extends MagicSpell {

	public Firemine() {
		super(28, "Feuermine", "Huch!", 5, 3, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block b = caster.getMCPlayer().getTargetBlock(null, mods.getEnergy() / 20);
		if (b == null || !b.getType().isSolid()) return;
		Location l = b.getLocation().clone().add(.5, 1.05, .5);
		MineMissile mis = new MineMissile(l, caster, 20 * 60 * 2, 4, .5, new Particle(EnumParticle.FLAME, l, false, .2f, .1f, .2f, 0, 1));
		Particle pa = new Particle(EnumParticle.LAVA, l, true, .2f, 0, .2f, 1, 1);
		Random r = new Random();
		mis.setRunTick(() -> {
			if (r.nextInt(100) == 0) pa.sendAll();
		});
		mis.setListener(new EntityEnterMineListener() {
			@Override
			public void onEnter(Entity e) {
				e.setFireTicks(100);
				pa.sendAll();
				pa.sendAll();
				pa.sendAll();
				pa.sendAll();
				caster.getMCPlayer().playSound(caster.getMCPlayer().getEyeLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
			}
		});
		mis.launch();

		takeMana(caster, mods);
	}

}

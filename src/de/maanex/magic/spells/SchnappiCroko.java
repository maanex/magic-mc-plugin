package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.magic.missile.MineMissile;
import de.maanex.magic.missile.MineMissile.EntityEnterMineListener;
import de.maanex.utils.ParticleUtil;
import de.maanex.utils.ParticleUtil.ParticlePreset;


public class SchnappiCroko extends MagicSpell {

	public SchnappiCroko() {
		super(60, "Schnappi das kleine Krokodil", "Schni schna schappi! Schappi schnappi schnap!", 5, 3, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block b = caster.getMCPlayer().getTargetBlock(null, mods.getEnergy() / 20);
		if (b == null || !b.getType().isSolid()) return;
		Location l = b.getLocation().clone().add(.5, 1.05, .5);
		MineMissile mis = new MineMissile(l, caster, 20 * 60 * 2, 0, .5, new ParticlePreset(Particle.SMOKE_NORMAL, l, 1, 0, .2f, .1f, .2f));
		ParticlePreset pa = new ParticlePreset(Particle.EXPLOSION_NORMAL, l, 4, 0, .2, .2, .2);
		Random r = new Random();
		mis.setRunTick(() -> {
			if (r.nextInt(200) == 0) ParticleUtil.spawn(pa);
		});
		mis.setListener(new EntityEnterMineListener() {
			@Override
			public void onEnter(Entity e) {
				if (e instanceof LivingEntity) ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 1, 20));
				EvokerFangs v = e.getLocation().getWorld().spawn(l, EvokerFangs.class);
				v.setOwner(caster.getMCPlayer());

				ParticleUtil.spawn(pa);
				ParticleUtil.spawn(pa);
				ParticleUtil.spawn(pa);
				ParticleUtil.spawn(pa);
				caster.getMCPlayer().playSound(caster.getMCPlayer().getEyeLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
			}
		});
		mis.launch();

		takeMana(caster, mods);
	}

}

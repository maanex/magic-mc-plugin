package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.missile.MineMissile;
import de.maanex.magic.missile.MineMissile.EntityEnterMineListener;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.magic.wands.WandValues.WandModifier;
import de.maanex.utils.ParticleUtil;
import de.maanex.utils.ParticleUtil.ParticlePreset;


public class Watermine extends MagicSpell {

	public Watermine() {
		super(59, "Wassermine", "Huch!", 5, 3, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		Block b = caster.getMCPlayer().getTargetBlock(null, val.getMod(WandModifier.ENERGY) / 20);
		if (b == null || !b.getType().isSolid()) return;
		Location l = b.getLocation().clone().add(.5, 1.05, .5);
		MineMissile mis = new MineMissile(l, caster, 20 * 60 * 2, 4, .5, new ParticlePreset(Particle.WATER_WAKE, l, 2, 0, .2f, .2f, .2f));
		ParticlePreset pa = new ParticlePreset(Particle.WATER_SPLASH, l, 4, 1, .2, 0, .2);
		Random r = new Random();
		mis.setRunTick(() -> {
			if (r.nextInt(10) == 0) ParticleUtil.spawn(pa);
		});
		mis.setListener(new EntityEnterMineListener() {
			@Override
			public void onEnter(Entity e) {
				if (e instanceof LivingEntity) ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 3, 2));
				ParticleUtil.spawn(pa);
				ParticleUtil.spawn(pa);
				ParticleUtil.spawn(pa);
				ParticleUtil.spawn(pa);
				caster.getMCPlayer().playSound(caster.getMCPlayer().getEyeLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
			}
		});
		mis.launch();

		takeMana(caster, val);
	}

}

package de.maanex.magic.spells;


import org.bukkit.Particle;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.magic.missile.BasicMissile;
import de.maanex.magic.missile.BasicMissile.BlockHitBehaviour;
import de.maanex.utils.ParticleUtil;


public class Discharge extends MagicSpell {

	public Discharge() {
		super(67, "Entladung", "Nimmt dein gesamtes Mana und verwandelt es in pure Energie!", 1, 4, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.EPIC);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		int mana = caster.getMana();
		caster.setMana(0);

		BasicMissile m = new BasicMissile(caster, (int) Math.sqrt(mana * mods.getEnergy()), .8, BlockHitBehaviour.ABSORB, //
				l -> {
					ParticleUtil.spawn(Particle.CRIT_MAGIC, l, 10, .2, .3, .3, .3);
					return null;
				}, e -> {
					e.damage(mana, caster.getMCPlayer());
					e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 20));
					e.setVelocity(new Vector(0, -1, 0));
					return null;
				});
		m.launch();

		takeMana(caster, mods);
	}

}

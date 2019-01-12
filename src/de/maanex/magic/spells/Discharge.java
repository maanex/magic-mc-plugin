package de.maanex.magic.spells;


import org.bukkit.Particle;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.basic.Element;
import de.maanex.magic.missile.BasicMissile;
import de.maanex.magic.missile.BasicMissile.BlockHitBehaviour;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.utils.ParticleUtil;


public class Discharge extends MagicSpell {

	public Discharge() {
		super(67, "Entladung", "Nimmt dein gesamtes Mana und verwandelt es in pure Energie!", 1, 4, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.EPIC, "Schaden Mana, :elementum:");
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		int mana = caster.getMana();
		caster.setMana(0);

		int el = Math.max(Math.max(val.getElement(Element.FIRE), val.getElement(Element.WATER)), Math.max(val.getElement(Element.EARTH), val.getElement(Element.AIR)));
		BasicMissile m = new BasicMissile(caster, (int) Math.sqrt(mana * el * 5), .8, BlockHitBehaviour.ABSORB, //
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

		takeMana(caster, val);
	}

}

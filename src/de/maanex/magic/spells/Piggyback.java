package de.maanex.magic.spells;


import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.magic.wands.WandValues.WandModifier;
import de.maanex.utils.ParticleUtil;
import de.maanex.utils.TargetEntityFinder;


public class Piggyback extends MagicSpell {

	public Piggyback() {
		super(72, "Huckepack", "YAY!", 0, 5, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.EPIC);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		Block b = caster.getMCPlayer().getTargetBlock(null, 200);

		if (caster.getMCPlayer().getPassengers().size() > 0) return;

		Entity tar = TargetEntityFinder.find(b);
		if (tar == null) return;

		if (tar.getLocation().distance(caster.getMCPlayer().getLocation()) > val.getMod(WandModifier.ENERGY) / 10) return;

		if (tar instanceof Player) {
			Player p = (Player) tar;
			p.playSound(p.getEyeLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
			ParticleUtil.spawn(Particle.SPELL_MOB_AMBIENT, p.getEyeLocation(), 30, .1, 1, .3, 1);
		}

		caster.getMCPlayer().addPassenger(tar);

		takeMana(caster, val);
	}

}

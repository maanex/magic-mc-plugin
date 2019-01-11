package de.maanex.magic.spells;


import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.missile.PainfullStingMissile;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.utils.ParticleUtil;
import de.maanex.utils.TargetEntityFinder;


public class PainfullSting extends MagicSpell {

	public PainfullSting() {
		super(14, "Schmerzhafter Stich", "Autsch!", 5, 1, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		Block b = caster.getMCPlayer().getTargetBlock(null, 200);

		Entity tar = TargetEntityFinder.find(b);
		if (tar == null) return;

		if (tar instanceof Player) {
			Player p = (Player) tar;
			p.playSound(p.getEyeLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
			ParticleUtil.spawn(p, Particle.NOTE, p.getEyeLocation(), 20, .1, 1, 0, 1);
			ParticleUtil.spawn(caster.getMCPlayer(), Particle.NOTE, p.getEyeLocation(), 20, .1, 1, 0, 1);
		} else {
			ParticleUtil.spawn(caster.getMCPlayer(), Particle.NOTE, tar.getLocation(), 20, .1, 1, 0, 1);
		}

		PainfullStingMissile m = new PainfullStingMissile(caster.getMCPlayer().getEyeLocation(), caster, tar);
		m.launch();

		takeMana(caster, val);
	}

}

package de.maanex.magic.spells;


import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.missile.HomingMissile;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.utils.ParticleUtil;
import de.maanex.utils.TargetEntityFinder;


public class Homing extends MagicSpell {

	public Homing() {
		super(74, "Zielsucher", "Biep biep biep biep!", 4, 3, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		Block b = caster.getMCPlayer().getTargetBlock(null, 300);

		Entity tar = TargetEntityFinder.find(b);
		if (tar == null) return;

		if (tar instanceof Player) {
			Player p = (Player) tar;
			p.playSound(p.getEyeLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
		}

		ParticleUtil.spawn(caster.getMCPlayer(), Particle.CLOUD, tar.getLocation().clone().add(0, 1.5, 0), 40, 0, .7, 0, .7);

		HomingMissile m = new HomingMissile(caster.getMCPlayer().getEyeLocation(), caster, tar);
		m.launch();

		takeMana(caster, val);
	}

}

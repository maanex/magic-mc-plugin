package de.maanex.magic.spells;


import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.magic.missile.PainfullStingMissile;
import de.maanex.utils.Particle;
import de.maanex.utils.TargetEntityFinder;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class PainfullSting extends MagicSpell {

	public PainfullSting() {
		super(14, "Schmerzhafter Stich", "Autsch!", 5, 1, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block b = caster.getMCPlayer().getTargetBlock(null, 200);

		Entity tar = TargetEntityFinder.find(b);
		if (tar == null) return;

		if (tar instanceof Player) {
			Player p = (Player) tar;
			p.playSound(p.getEyeLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
			Particle pa = new Particle(EnumParticle.NOTE, p.getEyeLocation(), true, 1, 0, 1, .1f, 20);
			pa.sendPlayer(p);
			pa.sendPlayer(caster.getMCPlayer());
		} else {
			Particle pa = new Particle(EnumParticle.NOTE, tar.getLocation(), true, 1, 0, 1, .1f, 20);
			pa.sendPlayer(caster.getMCPlayer());
		}

		PainfullStingMissile m = new PainfullStingMissile(caster.getMCPlayer().getEyeLocation(), caster, tar);
		m.launch();

		takeMana(caster, mods);
	}

}

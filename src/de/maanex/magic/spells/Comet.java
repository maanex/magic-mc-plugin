package de.maanex.magic.spells;


import org.bukkit.Material;
import org.bukkit.Particle;
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
import de.maanex.utils.ParticleUtil;


public class Comet extends MagicSpell {

	public Comet() {
		super(2, "Komet", "Juhu! Ein Komet!... Oh warte!", 3, 2, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.RARE, "Reichweite :fire:");
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		Block target = caster.getMCPlayer().getTargetBlock(null, val.getElement(Element.FIRE) * 2);
		if (target.getType().equals(Material.AIR)) return;

		ParticleUtil.spawn(Particle.CLOUD, target.getLocation(), 200, 0, 2, 0, 2);
		Fireball f = target.getWorld().spawn(target.getLocation().clone().add(0, 20, 0), Fireball.class);
		f.setDirection(new Vector(0, -0.001, 0));
		f.setVelocity(f.getVelocity().multiply(.1));
		f.setYield(2);

		takeMana(caster, val);
	}

}

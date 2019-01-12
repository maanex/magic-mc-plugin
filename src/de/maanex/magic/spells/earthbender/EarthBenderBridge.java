package de.maanex.magic.spells.earthbender;


import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.basic.Element;
import de.maanex.magic.missile.BridgeDrawMissile;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;


public class EarthBenderBridge extends MagicSpell {

	public EarthBenderBridge() {
		super(16, "Erdbendigung - Brücke", "Schmiede eine mächtige Brücke!", 18, 0, SpellType.ACTIVE, SpellCategory.BENDER, SpellRarity.VERY_RARE, "Reichweite :earth:");
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		if (val.getElement(Element.EARTH) <= 0) return;
		if (val.getElement(Element.ESSENCE_BENDER) <= 0) return;

		Block b = caster.getMCPlayer().getTargetBlock(null, val.getElement(Element.EARTH) * 10);
		if (b.getType().equals(Material.AIR)) return;

		Random r = new Random();
		for (int x = -4; x < 4; x++)
			for (int y = -4; y < 4; y++)
				for (int z = -4; z < 4; z++) {
					if (r.nextInt(2) != 0) continue;

					Location l = b.getLocation().clone().add(x, y, z).add(b.getLocation().clone().add(caster.getMCPlayer().getEyeLocation()).toVector().normalize().multiply(r.nextInt(3) + 1));

					if (!l.getBlock().getType().isSolid()) continue;

					BridgeDrawMissile m = new BridgeDrawMissile(l, caster, caster.getMCPlayer().getLocation().clone().add(x - 1 + r.nextInt(3), -1 - r.nextInt(3), z - 1 + r.nextInt(3)),
							l.getBlock().getType(), l.getBlock().getData());
					m.launch();
				}

		takeMana(caster, val);
	}

}

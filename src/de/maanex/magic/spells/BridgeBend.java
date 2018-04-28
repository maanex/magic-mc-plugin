package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.missile.BridgeDrawMissile;
import de.maanex.magic.wandsuse.WandType;


public class BridgeBend extends MagicSpell {

	public BridgeBend() {
		super(16, "Erdbendigung - Brücke", "Schmiede eine mächtige Brücke!", 18, new BuildRequirements(0, 0, 0, 0, 5, 10, 16, 20, 0, 0, 110));
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block b = caster.getMCPlayer().getTargetBlock(null, 30 + mods.getEnergy() - 100);
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

		takeMana(caster, mods);
	}

}

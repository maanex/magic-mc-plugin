package de.maanex.magic.spells.darkmagic;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.main.Main;
import de.maanex.utils.ParticleUtil;
import de.maanex.utils.TargetEntityFinder;


public class DarkSeal extends MagicSpell {

	public DarkSeal() {
		super(18, "Dunkles Siegel", "Markiere einen Gegner mit dem Dunklen Siegel!", 7, 60, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.FORBIDDEN, WandType.DARK);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		Block b = caster.getMCPlayer().getTargetBlock(null, 200);
		Entity tar = TargetEntityFinder.find(b);
		if (tar == null) return;

		for (int i = 0; i < 40; i += 2) {
			partDel(tar, i);
		}

		for (Entity e : tar.getWorld().getNearbyEntities(tar.getLocation(), 50, 50, 50)) {
			if (e instanceof Creature) {
				((Creature) e).setTarget((LivingEntity) tar);
				((Creature) e).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 20, 3, true));
			}
		}

		takeMana(caster, val);
	}

	private void partDel(Entity tar, int i) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
			Location loc = tar.getLocation().clone().add(0, 1, 0);
			int points = 5;
			for (int iteration = 0; iteration < points; iteration++) {
				double angle = 360.0 / points * iteration;
				double nextAngle = 360.0 / points * (iteration + 1);
				angle = Math.toRadians(angle);
				nextAngle = Math.toRadians(nextAngle);
				double x = Math.cos(angle) * 2;
				double z = Math.sin(angle) * 2;
				double x2 = Math.cos(nextAngle) * 2;
				double z2 = Math.sin(nextAngle) * 2;
				double deltaX = x2 - x;
				double deltaZ = z2 - z;

				for (double d = 0; d < 1; d += .1) {
					loc.add(x + deltaX * d, 0, z + deltaZ * d);
					ParticleUtil.spawn(Particle.BLOCK_DUST, loc, 1, 0, 0, 0, 0, Material.COAL_BLOCK.getData());
					loc.subtract(x + deltaX * d, 0, z + deltaZ * d);
				}
			}
		}, i);
	}

}

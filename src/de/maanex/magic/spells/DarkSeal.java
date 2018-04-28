package de.maanex.magic.spells;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.wandsuse.WandType;
import de.maanex.main.Main;
import de.maanex.utils.Particle;
import de.maanex.utils.TargetEntityFinder;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class DarkSeal extends MagicSpell {

	public DarkSeal() {
		super(18, "Dunkles Siegel", "Markiere einen Gegner mit dem Dunklen Siegel!", 7, new BuildRequirements(0, 0, 10, 20, 0, 0, 0, 0, 10, 20, 120), WandType.DARK);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block b = caster.getMCPlayer().getTargetBlock(null, 200);
		Entity tar = TargetEntityFinder.find(b);
		if (tar == null) return;

		for (int i = 0; i < 40; i++) {
			partDel(tar, i);
		}

		for (Entity e : tar.getWorld().getNearbyEntities(tar.getLocation(), 50, 50, 50)) {
			if (e instanceof Creature) {
				((Creature) e).setTarget((LivingEntity) tar);
				((Creature) e).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 20, 3, true));
			}
		}

		takeMana(caster, mods);
	}

	@SuppressWarnings("deprecation")
	private void partDel(Entity tar, int i) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
			Particle pa = new Particle(EnumParticle.BLOCK_DUST, tar.getLocation().clone().add(0, 1, 0), true, 2, 0, 2, 0, 100);
			pa.setC(Material.COAL_BLOCK.getId());
			pa.sendAll();
		}, i);
	}

}

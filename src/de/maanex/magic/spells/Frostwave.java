package de.maanex.magic.spells;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.main.Main;
import de.maanex.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;


public class Frostwave extends MagicSpell {

	public Frostwave() {
		super(13, "Frostwelle", "Brrrr!", 2, 4, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.VERY_RARE);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		List<Entity> damaged = new ArrayList<>();
		Random r = new Random();
		for (int c = 1; c != 10; c++) {
			Location f = caster.getMCPlayer().getTargetBlock(null, c).getLocation();
			for (int x = -3; x != 3; x++) {
				for (int y = -3; y != 3; y++) {
					for (int z = -3; z != 3; z++) {
						final Block b = f.clone().add(x, y, z).getBlock();

						int q = (int) f.distance(b.getLocation());
						if (q < 1) q = 1;
						if (!b.getType().equals(Material.AIR) && (r.nextInt(q) == 0) && f.distance(b.getLocation()) < 4.1) {

							// if (r.nextBoolean() && r.nextBoolean() && r.nextBoolean() && r.nextBoolean())
							// playSoundForAll(Sound.BLOCK_GLASS_BREAK, b.getLocation());

							for (Player p : Bukkit.getOnlinePlayers())
								p.sendBlockChange(b.getLocation(), Material.PACKED_ICE, (byte) 0);
							Particle pa = new Particle(EnumParticle.BLOCK_CRACK, b.getLocation().add(0.5, 1, 0.5), false, 0, 0, 0, 1, 5);
							pa.setC(Material.PACKED_ICE.getId());
							pa.sendAll();

							Bukkit.getScheduler().runTaskLater(Main.instance, new Runnable() {

								@Override
								public void run() {
									for (Player p : Bukkit.getOnlinePlayers())
										p.sendBlockChange(b.getLocation(), b.getType(), b.getData());
									Particle pa = new Particle(EnumParticle.BLOCK_CRACK, b.getLocation().add(0.5, 1, 0.5), false, 0, 0, 0, 1, 5);
									pa.setC(b.getTypeId() + (b.getData() * 4096));
									pa.sendAll();
								}
							}, new Random().nextInt(40) + 60);
						}
						for (Entity en : b.getChunk().getEntities()) {
							if (en.getWorld().equals(caster.getMCPlayer().getWorld()) && !en.equals(caster.getMCPlayer())) {
								if (en.getLocation().distance(b.getLocation()) < 1.1 && !damaged.contains(en)) {
									damaged.add(en);
									try {
										((LivingEntity) en).damage(5, caster.getMCPlayer());
										((LivingEntity) en).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 1));
									} catch (Exception e2) {
										// 8)
									}
								}
							}
						}
					}
				}
			}
		}
		takeMana(caster, mods);
	}

}

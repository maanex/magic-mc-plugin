package de.maanex.magic.spells;


import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.main.Main;
import de.maanex.utils.ParticleUtil;


public class Warp extends MagicSpell {

	public Warp() {
		super(8, "Warp", "Zack, Zack - Hier, Dort", 2, 0, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.RARE);
	}

	private static HashMap<Player, Integer> dark_wand_show_tasks = new HashMap<>();

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		Particle patype = type.equals(WandType.LIGHT) ? Particle.END_ROD : Particle.FLAME;

		Location org = caster.getMCPlayer().getEyeLocation().clone();

		Location l = null;
		boolean s = caster.getMCPlayer().isSneaking();
		int c = s ? 1 : 6;
		do {
			l = caster.getMCPlayer().getEyeLocation().clone().add(caster.getMCPlayer().getLocation().getDirection().normalize().multiply(c += s ? 1 : -1)).subtract(0, 1, 0);
			if (c <= 0 || c >= 7) return;
		} while (!l.getBlock().isEmpty() || !l.clone().add(0, -.4, 0).getBlock().isEmpty());

		caster.getMCPlayer().teleport(l.clone().add(0, -.4, 0));
		caster.getMCPlayer().setVelocity(l.getDirection());
		caster.getMCPlayer().setFallDistance(0);

		if (!type.equals(WandType.DARK)) {
			double dis = .02;
			double xoff = (l.getX() - org.getX()) * dis;
			double yoff = (l.getY() - org.getY()) * dis;
			double zoff = (l.getZ() - org.getZ()) * dis;
			for (double d = 0; d < 1; d += dis) {
				org.add(xoff, yoff, zoff);
				ParticleUtil.spawn(patype, org, 2, 0, .02, .02, .02);
			}
		} else {
			if (!caster.getMCPlayer().hasPotionEffect(PotionEffectType.INVISIBILITY)) ParticleUtil.spawn(Particle.SMOKE_LARGE, org, 70, 0, .5, .5, .5);

			if (dark_wand_show_tasks.containsKey(caster.getMCPlayer())) Bukkit.getScheduler().cancelTask(dark_wand_show_tasks.get(caster.getMCPlayer()));
			else Bukkit.getOnlinePlayers().stream().filter(p -> !p.equals(caster.getMCPlayer())).forEach(p -> p.hidePlayer(caster.getMCPlayer()));

			dark_wand_show_tasks.put(caster.getMCPlayer(), Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
				dark_wand_show_tasks.remove(caster.getMCPlayer());
				Bukkit.getOnlinePlayers().stream().filter(p -> !p.equals(caster.getMCPlayer())).forEach(p -> p.showPlayer(caster.getMCPlayer()));
			}, 60));

			caster.getMCPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 60, 1));
		}

		if (type.equals(WandType.LIGHT) && new Random().nextInt(5) == 0) {
			caster.getMCPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 7));
		}

		takeMana(caster, mods);
	}

}

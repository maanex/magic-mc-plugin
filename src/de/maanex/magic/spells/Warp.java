package de.maanex.magic.spells;


import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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


public class Warp extends MagicSpell {

	public Warp() {
		super(8, "Warp", "Zack, Zack - Hier, Dort", 2, 0, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.RARE);
	}

	private static HashMap<Player, Integer> dark_wand_show_tasks = new HashMap<>();

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		EnumParticle patype = type.equals(WandType.LIGHT) ? EnumParticle.END_ROD : EnumParticle.FLAME;

		Location org = caster.getMCPlayer().getEyeLocation().clone();
		Location l = caster.getMCPlayer().getEyeLocation().clone().add(caster.getMCPlayer().getLocation().getDirection().normalize().multiply(6)).subtract(0, 1, 0);

		if (l.getBlock().isEmpty()) {
			caster.getMCPlayer().teleport(l);
			caster.getMCPlayer().setVelocity(l.getDirection());
		} else return;

		caster.getMCPlayer().setFallDistance(0);

		if (!type.equals(WandType.DARK)) {
			double dis = .02;
			double xoff = (l.getX() - org.getX()) * dis;
			double yoff = (l.getY() - org.getY()) * dis;
			double zoff = (l.getZ() - org.getZ()) * dis;
			for (double d = 0; d < 1; d += dis) {
				org.add(xoff, yoff, zoff);
				new Particle(patype, org, true, .02f, .02f, .02f, 0, 2).sendAll();
			}
		} else {
			if (!caster.getMCPlayer().hasPotionEffect(PotionEffectType.INVISIBILITY)) new Particle(EnumParticle.SMOKE_LARGE, org, true, .5f, .5f, .5f, 0, 70).sendAll();

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

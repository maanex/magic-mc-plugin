package de.maanex.magic.spells.lightmagic;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
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


public class Phase extends MagicSpell implements Listener {

	public Phase() {
		super(41, "Phase", "Bewege dich durch Materie!", 1, 5, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.GODLIKE, WandType.LIGHT);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		caster.getMCPlayer().setGameMode(GameMode.SPECTATOR);
		startTimer(caster);
		inPhase.add(caster.getMCPlayer());
		takeMana(caster, val);
	}

	private static List<Player> inPhase = new ArrayList<>();

	private void startTimer(MagicPlayer caster) {
		caster.getMCPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5000, 1, true, false));
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
			caster.addMana(-1);
			if (caster.getMana() <= 0 || (!caster.getMCPlayer().getLocation().getBlock().getType().isSolid() && !caster.getMCPlayer().getEyeLocation().getBlock().getType().isSolid())) {
				caster.getMCPlayer().setGameMode(GameMode.SURVIVAL);
				inPhase.remove(caster.getMCPlayer());
				caster.getMCPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
			} else startTimer(caster);
		}, 20);
	}

	@EventHandler
	public void onTp(PlayerTeleportEvent e) {
		if (inPhase.contains(e.getPlayer())) e.setCancelled(true);
	}

	public static void tick() {
		for (Player p : inPhase) {
			ParticleUtil.spawn(Particle.CLOUD, p.getEyeLocation().clone().add(0, .5, 0), 2, 0, .5, 1, .5);
		}
	}

}

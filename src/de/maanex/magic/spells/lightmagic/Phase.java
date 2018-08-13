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

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.main.Main;
import de.maanex.utils.ParticleUtil;


public class Phase extends MagicSpell implements Listener {

	public Phase() {
		super(41, "Phase", "Bewege dich durch Materie!", 1, 5, SpellType.ACTIVE, SpellCategory.UTILITY, SpellRarity.GODLIKE, WandType.LIGHT);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		caster.getMCPlayer().setGameMode(GameMode.SPECTATOR);
		startTimer(caster);
		inPhase.add(caster.getMCPlayer());
		takeMana(caster, mods);
	}

	private static List<Player> inPhase = new ArrayList<>();

	private void startTimer(MagicPlayer caster) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
			caster.addMana(-1);
			if (caster.getMana() <= 0 || (!caster.getMCPlayer().getLocation().getBlock().getType().isSolid() && !caster.getMCPlayer().getEyeLocation().getBlock().getType().isSolid())) {
				caster.getMCPlayer().setGameMode(GameMode.SURVIVAL);
				inPhase.remove(caster.getMCPlayer());
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

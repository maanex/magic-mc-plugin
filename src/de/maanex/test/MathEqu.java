package de.maanex.test;


import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.maanex.main.Main;
import de.maanex.utils.ParticleUtil;


public class MathEqu implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender se, Command c, String label, String[] args) {
		if (!se.isOp()) return false;
		if (!(se instanceof Player)) return false;
		Player p = (Player) se;

		if (args.length < 4) {
			p.sendMessage("/equ <delay> <x> <z> <equ> [particle] [xoff] [yoff] [zoff]");
			return true;
		}

		int delay = Integer.parseInt(args[0]);
		final int x = Integer.parseInt(args[1]);
		final int z = Integer.parseInt(args[2]);
		Location src = p.getLocation();

		List<Location> pos = new ArrayList<>();

		final Particle part;
		if (args.length >= 5) part = Particle.valueOf(args[4]);
		else part = Particle.FLAME;

		src = src.clone();
		if (args.length >= 6) {
			if (args[5].startsWith("~")) src.add(Double.valueOf(args[5]), 0, 0);
			else src.setX(Double.valueOf(args[5].replace("~", "")));
		}
		if (args.length >= 7) {
			if (args[6].startsWith("~")) src.add(0, Double.valueOf(args[6]), 0);
			else src.setY(Double.valueOf(args[6].replace("~", "")));
		}
		if (args.length >= 8) {
			if (args[7].startsWith("~")) src.add(0, 0, Double.valueOf(args[7]));
			else src.setZ(Double.valueOf(args[7].replace("~", "")));
		}

		for (double ix = x; ix > -x; ix -= .3)
			for (double iz = z; iz > -z; iz -= .3) {
				String equ = args[3];
				equ = equ.replace("X", ix + "");
				equ = equ.replace("Z", iz + "");
				equ = equ.replace("_", " ");

				ScriptEngineManager manager = new ScriptEngineManager();
				ScriptEngine engine = manager.getEngineByName("js");
				double y = 0;
				try {
					y = (double) engine.eval(equ);
				} catch (ScriptException e) {
					e.printStackTrace();
				}

				pos.add(src.clone().add(ix, y, iz));
			}

		for (int i = 0; i < 200; i += 10)
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
				pos.forEach(l -> ParticleUtil.spawn(part, l, 1, 0, 0, 0, 0));
			}, delay + i);

		return true;
	}

}

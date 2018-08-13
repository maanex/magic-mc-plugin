package de.maanex.magic;


import org.bukkit.Bukkit;


public class ManaRegeneration {

	private ManaRegeneration() {
	}

	public static void doTick() {
		Bukkit.getOnlinePlayers().forEach(p -> {
			MagicPlayer m = MagicPlayer.get(p);
			int add = m.nat_mana_reg_c++ > 2 ? add = (m.nat_mana_reg_c = 0) + 1 : 0;
			m.addMana(add, true);
		});
	}

}

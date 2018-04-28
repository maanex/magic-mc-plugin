package de.maanex.magic;


import org.bukkit.Bukkit;

import de.maanex.magic.customEffects.ManaRegenEffect;


public class ManaRegeneration {

	private ManaRegeneration() {
	}

	public static void doTick() {
		Bukkit.getOnlinePlayers().forEach(p -> {
			MagicPlayer m = MagicPlayer.get(p);
			int add = m.nat_mana_reg_c++ > 2 ? add = (m.nat_mana_reg_c = 0) + 1 : 0;
			boolean usecap = true;
			if (m.hasEffect(ManaRegenEffect.class)) {
				ManaRegenEffect e = m.getEffect(ManaRegenEffect.class);
				if (e == null) return;
				add += e.level / 10;
				usecap = false;
			}
			m.addMana(add, usecap);

			// if (m.getMCPlayer().isSneaking()) m.setMana(m.getMaxMana());
		});
	}

}

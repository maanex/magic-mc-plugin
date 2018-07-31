package de.maanex.magic.spells;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;


public class Waterbomb extends MagicSpell implements Listener {

	public Waterbomb() {
		super(62, "Wasserbombe", "Lul", 1, 2, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.RARE);
	}

	private List<FallingBlock> bombs = new ArrayList<>();

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		FallingBlock f = caster.getMCPlayer().getLocation().getWorld().spawnFallingBlock(caster.getMCPlayer().getEyeLocation(), Material.LIGHT_BLUE_STAINED_GLASS.createBlockData());

		f.setVelocity(caster.getMCPlayer().getLocation().getDirection());
		f.setDropItem(false);

		bombs.add(f);

		takeMana(caster, mods);
	}

	@EventHandler
	public void onFallingBlockLand(EntityChangeBlockEvent e) {
		if (e.getEntity() instanceof FallingBlock) {
			FallingBlock b = (FallingBlock) e.getEntity();
			if (bombs.contains(b)) {
				e.setCancelled(true);
				bombs.remove(b);
				b.getWorld().getNearbyEntities(b.getLocation(), 1, 1, 1).forEach(n -> {
					if (n instanceof LivingEntity) {
						LivingEntity r = (LivingEntity) n;
						r.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 1));
						r.playEffect(EntityEffect.HURT);
					}
				});
			}
		}
	}

}

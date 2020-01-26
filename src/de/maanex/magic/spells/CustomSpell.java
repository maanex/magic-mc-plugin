package de.maanex.magic.spells;

import java.util.Random;

import org.bukkit.Material;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.spell.dynamic.DynamicSpell;
import de.maanex.magic.spell.dynamic.params.DEntity;
import de.maanex.magic.spell.dynamic.params.DLocation;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;

public class CustomSpell extends MagicSpell {

	public CustomSpell() {
		super(99, "Custom Spell", "big woah", 4, 30, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.EPIC, "Yeetus maximus :air:");
	}
	
	String spell = String.join("\n", 
		"t;p;r,3:e,r20"
		);

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		caster.getMCPlayer().sendBlockChange(caster.getMCPlayer().getLocation(), Material.END_GATEWAY.createBlockData());
		
		if (true) return;
		
		DynamicSpell s = new DynamicSpell(spell, caster, type, val);
		if (type.equals(WandType.DARK)) {
			spell = "t;" + DEntity.randomEntity(new Random()) + ";" + DLocation.randomLocation(5);
			System.out.println(spell);			
			return;
		}
		if (type.equals(WandType.LIGHT)) {
			s.mutate(5);
			spell = s.code;
			System.out.println(spell);
			return;
		}
		s.execute();
	}

	/*
	 * definitions can return null
	 * if a definition returns null the command might not execute at all
	 * 
	 * 
	 * bsp
	 * t;p;r,4:e,2
	 * 
	 * 
	 * -define
	 * command;arguments
	 * 
	 * script:
	 # s - summon; what:blueprint where:location
	 # e - effect; what:entity effect:enumname duration:integer strength:integer
	 * t - teleport; what:entity where:location
	 # a - accelerate; what:entity direction:vector
	 *  
	 *  
	 * entity:
	 * first arg: entity finding
	 * 		p - player / caster
	 * 		r - random entity in radius; radius:integer
	 * 		l - entity looking at; radius:integer
	 # 		s - previously summoned entity
	 * 
	 *  
	 * location:
	 * first arg: base location:
	 * 		h - player head
	 * 		f - player foot
	 * 		r - random in radius; radius:integer
	 * 		l - looking at; radius:integer
	 * r - randomize; radius:integer
	 * e - extend in vector direction; distance:integer
	 # m - move towards other location; location:location distance:integer
	 * o - rotate; pitch:integer yaw:integer
	 * 
	 * 
	 * number:
	 * int or double or:
	 * r - range
	 * 
	 * 
	 * blueprint:
	 * one of:
	 * 		#mcentity
	 * 		#missile
	 * 
	 * 
	 * mcentity:
	 * first arg: entity type enum
	 * 
	 * 
	 * missile:
	 * TODO
	 * 
	 *  
	 */

}

package de.maanex.magic.spell;

import de.maanex.magic.MagicManager;

public class SpellRecipe {

	private MagicSpell	spell1, spell2, result;
	private int			probability;

	public SpellRecipe(Class<? extends MagicSpell> spell1, Class<? extends MagicSpell> spell2, Class<? extends MagicSpell> result, int probability) {
		super();
		this.spell1 = MagicManager.getSpell(spell1);
		this.spell2 = MagicManager.getSpell(spell2);
		this.result = MagicManager.getSpell(result);
		this.probability = probability;
	}

	public SpellRecipe(MagicSpell spell1, MagicSpell spell2, MagicSpell result, int probability) {
		super();
		this.spell1 = spell1;
		this.spell2 = spell2;
		this.result = result;
		this.probability = probability;
	}

	public MagicSpell getSpell1() {
		return spell1;
	}

	public void setSpell1(MagicSpell spell1) {
		this.spell1 = spell1;
	}

	public MagicSpell getSpell2() {
		return spell2;
	}

	public void setSpell2(MagicSpell spell2) {
		this.spell2 = spell2;
	}

	public MagicSpell getResult() {
		return result;
	}

	public void setResult(MagicSpell result) {
		this.result = result;
	}

	public int getProbability() {
		return probability;
	}

	public void setProbability(int probability) {
		this.probability = probability;
	}

}

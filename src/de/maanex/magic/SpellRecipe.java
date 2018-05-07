package de.maanex.magic;


public class SpellRecipe {

	private Class<? extends MagicSpell>	spell1, spell2;
	private MagicSpell					result;
	private int							probability;

	public SpellRecipe(Class<? extends MagicSpell> spell1, Class<? extends MagicSpell> spell2, MagicSpell result, int probability) {
		super();
		this.spell1 = spell1;
		this.spell2 = spell2;
		this.result = result;
		this.probability = probability;
	}

	public Class<? extends MagicSpell> getSpell1() {
		return spell1;
	}

	public void setSpell1(Class<? extends MagicSpell> spell1) {
		this.spell1 = spell1;
	}

	public Class<? extends MagicSpell> getSpell2() {
		return spell2;
	}

	public void setSpell2(Class<? extends MagicSpell> spell2) {
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

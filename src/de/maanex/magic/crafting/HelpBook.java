package de.maanex.magic.crafting;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BookMeta;


public class HelpBook {

	@SuppressWarnings("deprecation")
	public static void registerRecipe() {
		ShapedRecipe res = new ShapedRecipe(getBook());

		res.shape("000", "0B0", "000");

		res.setIngredient('B', Material.BOOK);

		Bukkit.addRecipe(res);
	}

	private static ItemStack getBook() {
		ItemStack out = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta m = (BookMeta) out.getItemMeta();

		m.setTitle("§2Codex Magica - Über Zauberei");
		m.setAuthor("Tude Magic Division");

		//

		m.addPage("-------------------" + //
				"\nÜber Zauberei" + //
				"\n-------------------" + //
				"\n" + //
				"\nEin Lexikon der Tude Magic Division" + //
				"\n" + //
				"\nAuf den nächsten Seiten finden Sie alle grundlegenen Dinge die Sie über Magie wissen müssen."//
		);

		m.addPage("§l§nInhalt§r" + //
				"\n" + //
				"\n1. Mana und natürliche Magie" + //
				"\n" + //
				"\n2. Magische Utensilien" + //
				"\n" + //
				"\n3. Zaubersprüche erforschen" + //
				"\n" + //
				"\n4. Grundbegriffe" + //
				"\n" + //
				"\n5. Sonstiges & Dank" //
		);

		m.addPage("-------------------" + //
				"\n§lKap I:§0 Mana und natürliche Magie" + //
				"\n-------------------" + //
				"\n" + //
				"\nEin jeder besitzt Mana. Sie ist klar sichtbar über der Schnellzugriffsleiste des Spielers sichtbar. Aufgeteilt in anfangs 20 Balken, zeigen die blau gefärbten, wie viele Einheiten" //
		);

		m.addPage("an Mana verbleiben" + //
				"\n" + //
				"\nMana regeneriert automatisch, allerdings nur die hellgrau gefärbte Distanz. Um den dunkelgrauen Bereich mit Mana zu füllen, werden andere Methoden benötigt: Der einfachste Weg, schneller Mana zu regenerieren und diese Limitierung zu" //
		);

		m.addPage("umgehen ist das benutzen von Manatränken. Dazu mehr in Kapitel II." //
		);

		m.addPage("-------------------" + //
				"\n§lKap II:§0 Magische Utensilien" + //
				"\n-------------------" + //
				"\n" + //
				"\nDas wichtigste Werkzeug eines jeden Zauberers ist sicher sein Zauberstab. Hergestellt wird dieser ganz einfach durch platzierung eines Stockes in der Mitte einer Werkbank und" //
		);

		m.addPage(
				"zwei Eisennuggets oben rechts und unten links davon. Dieser Zauberstab besitzt allerdings noch nicht die Fähigkeit damit zu Zaubern. Um dies zu ermöglichen, muss einfach mit diesem in der Hand ein Zaubertisch angeklickt werden. Für diesen Prozess sind" //
		);

		m.addPage("3 Erfahrungslevel von nöten." + //
				"\n" + //
				"\n Ein Zauberstab alleine reicht allerdings noch nicht um zu Zaubern: Ebenso essenziell wie jender ist für jeden Zauberer sein Zauberbuch in dem er seine gelernten Zaubersprüche nachschlagen kann. Zur Herstellung von"//
		);
		m.addPage(
				"einem Zauberbuch muss in einer Werkbank ein normales Buch vollständig mit Eisennuggets umrandet werden. Vom Tisch entnommen, kann es mit rechtsklick geöffnet und Zaubersprüche darin abgelegt werden. (Mehr dazu in Kapittel III)"
						+ //
						"\n" + //
						"\nAls letztes wichtiges"//
		);

		//

		out.setItemMeta(m);
		return out;
	}
}
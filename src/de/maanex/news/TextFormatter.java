package de.maanex.news;


import java.util.ArrayList;
import java.util.List;


public class TextFormatter {

	private TextFormatter() {
	}

	public static List<String> format(String text, int len) {
		List<String> out = new ArrayList<>();
		String current = "";
		for (String s : text.split(" ")) {
			if (s.length() > len) {
				current = "";
				String q = s;
				while (q.length() > len) {
					out.add(q.substring(0, len));
					q = q.substring(len);
				}
				current = q;
				continue;
			}

			if ((current + s).length() > len) {
				out.add(current.replaceFirst(" ", ""));
				current = s;
			} else {
				current += " " + s;
			}
		}
		if (!current.equalsIgnoreCase("")) out.add(current);
		return out;
	}

}

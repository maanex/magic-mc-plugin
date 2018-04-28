package de.maanex.news;


import org.json.simple.JSONObject;


public class Letter {

	private LetterType	type;
	private String		author, reciever, content;

	public Letter(LetterType type, String author, String reciever, String content) {
		this.type = type;
		this.author = author;
		this.reciever = reciever;
		this.content = content;
	}

	public LetterType getType() {
		return type;
	}

	public String getAuthor() {
		return author;
	}

	public String getReciever() {
		return reciever;
	}

	public String getContent() {
		return content;
	}

	/*
	 * 
	 */

	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject out = new JSONObject();
		out.put("type", type.name());
		out.put("author", author);
		out.put("reciever", reciever);
		out.put("content", content);
		return out;
	}

	public Letter fromJSON(JSONObject o) {
		return new Letter(LetterType.valueOf(o.get("type").toString()), o.get("author").toString(), o.get("reciever").toString(), o.get("content").toString());
	}

	/*
	 * 
	 */

	public static enum LetterType {
		NEWS("§3", "§b", "News"), //
		BRIEF("§5", "§d", "Brief"), //
		ZEITUNG("§4", "§c", "Wichtig!"), //

		;

		private String cch, cct, name;

		private LetterType(String cch, String cct, String name) {
			this.cch = cch;
			this.cct = cct;
			this.name = name;
		}

		public String getHeaderColor() {
			return cch;
		}

		public String getTextColor() {
			return cct;
		}

		public String getName() {
			return name;
		}
	}

}

package com.nubebuster.hg.Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DataPair {

	private String name;
	private UUID uuid;

	public DataPair(String name) throws Exception {
		this.name = name;
		String response = readUrlGet("https://api.mojang.com/users/profiles/minecraft/" + name);
		JsonElement el = new JsonParser().parse(response);
		JsonObject obj = el.getAsJsonObject();
		this.name = obj.get("name").toString();
		this.uuid = getUUID(obj.get("id").toString().replace("\"", ""));
	}

	public DataPair(String name, UUID uuid) {
		this.name = name;
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public UUID getUUID() {
		return uuid;
	}

	private static UUID getUUID(String id) {
		return UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-"
				+ id.substring(16, 20) + "-" + id.substring(20, 32));
	}

	private static String readUrlGet(String urlString) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);
			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}
}

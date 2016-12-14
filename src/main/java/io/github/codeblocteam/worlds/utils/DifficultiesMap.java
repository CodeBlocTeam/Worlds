package io.github.codeblocteam.worlds.utils;

import java.util.HashMap;

public class DifficultiesMap {
	public HashMap<String, Integer> get() {
		HashMap<String, Integer> gamemodesMap = new HashMap<String, Integer>();
		gamemodesMap.put("peaceful", (Integer) 0);
		gamemodesMap.put("easy", (Integer) 1);
		gamemodesMap.put("normal", (Integer) 2);
		gamemodesMap.put("hard", (Integer) 3);
		return gamemodesMap;
	}
}


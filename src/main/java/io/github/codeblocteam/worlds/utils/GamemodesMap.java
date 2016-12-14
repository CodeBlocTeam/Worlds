package io.github.codeblocteam.worlds.utils;

import java.util.HashMap;

public class GamemodesMap {
	public HashMap<String, Integer> get() {
		HashMap<String, Integer> gamemodesMap = new HashMap<String, Integer>();
		gamemodesMap.put("0", (Integer) 0);
		gamemodesMap.put("survival", (Integer) 0);
		gamemodesMap.put("1", (Integer) 1);
		gamemodesMap.put("creative", (Integer) 1);
		gamemodesMap.put("2", (Integer) 2);
		gamemodesMap.put("adventure", (Integer) 2);
		gamemodesMap.put("3", (Integer) 3);
		gamemodesMap.put("spectator", (Integer) 3);
		return gamemodesMap;
	}
}

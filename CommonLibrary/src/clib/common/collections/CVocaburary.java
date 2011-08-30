package clib.common.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CVocaburary {

	private Map<String, Counter> map = new HashMap<String, Counter>();

	public void add(String word) {
		add(word, 1);
	}

	public void add(String word, int count) {
		if (!map.containsKey(word)) {
			map.put(word, new Counter(count));
		} else {
			map.get(word).add(count);
		}
	}

	public void addAll(CVocaburary another) {
		for (String word : another.map.keySet()) {
			if (word == null) {
				continue;
			}
			int count = another.map.get(word).getCount();
			add(word, count);
		}
	}

	public Map<String, Integer> getHistgram() {
		Map<String, Integer> histgram = new LinkedHashMap<String, Integer>();
		for (String word : map.keySet()) {
			int count = map.get(word).getCount();
			histgram.put(word, count);
		}
		return histgram;
	}

	public List<String> getWords() {
		return new ArrayList<String>(getHistgram().keySet());
	}

	public String toString() {
		return getHistgram().toString();
	}

	class Counter {
		private int count = 0;

		public Counter(int initialCount) {
			this.count = initialCount;
		}

		public void add(int count) {
			this.count += count;
		}

		public int getCount() {
			return count;
		}
	}
}

package ch4;


import javax.print.DocFlavor;
import java.util.*;
import java.util.function.BiFunction;


/**
 * @author pfjia
 * @since 2017/11/18 15:13
 */
public class OneDifferChar {
	public static void printHighChangeables(Map<String, List<String>> adjWords,
			int minWords) {
		for (Map.Entry<String, List<String>> entry : adjWords.entrySet()) {
			List<String> words = entry.getValue();
			if (words.size() >= minWords) {
				System.out.print(entry.getKey() + " (");
				System.out.print(words.size() + "):");
				words.forEach(s -> System.out.print(" " + s));
				System.out.println();
			}
		}
	}


	/**
	 * 
	 * @param word1
	 * @param word2
	 * @return true if word1 and word2 are the same length and differ in only one character.
	 */
	private static boolean oneCharOff(String word1, String word2) {
		if (word1.length() != word2.length()) {
			return false;
		}
		int differ = 0;
		for (int i = 0; i < word1.length(); i++) {
			if (word1.charAt(i) != word2.charAt(i)) {
				if (++differ > 1) {
					return false;
				}
			}
		}
		return differ == 1;
	}


	/**
	 * Computes a mpa in which the keys are words and values are lists of words
	 * that differ in only one character from the corresponding key.
	 * Uses a quadratic(adj:二次的) algorithm(with appropriate Map)
	 * @param theWords
	 * @return
	 */
	public static Map<String, List<String>> computeAdjacentWords(
			List<String> theWords) {
		Map<String, List<String>> adjWords = new TreeMap<>();
		String[] words = new String[theWords.size()];
		theWords.toArray(words);
		for (int i = 0; i < words.length; i++) {
			for (int j = i + 1; j < words.length; j++) {
				if (oneCharOff(words[i], words[j])) {
					update(adjWords, words[i], words[j]);
					update(adjWords, words[j], words[i]);
				}
			}
		}
		return adjWords;
	}


	//

	/**
	 * 优化:按长度分组
	 * 
	 * @param theWords
	 * @return
	 */
	public static Map<String, List<String>> computeAdjacentWords2(
			List<String> theWords) {
		Map<String, List<String>> adjWords = new TreeMap<>();
		Map<Integer, List<String>> wordsByLength = new TreeMap<>();
		// Group the words by their length
		for (String w : theWords) {
			update(wordsByLength, w.length(), w);
		}

		// Work on each group separately
		for (List<String> groupsWords : wordsByLength.values()) {
			String[] words = new String[groupsWords.size()];
			groupsWords.toArray(words);
			for (int i = 0; i < words.length; i++) {
				for (int j = i + 1; j < words.length; j++) {
					if (oneCharOff(words[i], words[j])) {
						update(adjWords, words[i], words[j]);
						update(adjWords, words[j], words[i]);
					}
				}
			}
		}
		return adjWords;
	}


	/**
	 * ...,but speeds things by maintaining an additional map that groups words by their length.
	 * 优化:按单词再分组
	 * 分析:由O(N^2)变为O(N),所有的单词只循环了一次(按长度分组时已经循环了一次),在一次循环时将其放入对应的Map中,而后二重循环遍历Map(O(N^2)),
	 * 此时由于在Map中的都是相邻单词,其N^2中的N远远小于theWords.size()
	 * 
	 * @param theWords
	 * @return
	 */
	public static Map<String, List<String>> computeAdjacentWords3(
			List<String> theWords) {
		Map<String, List<String>> adjWords = new TreeMap<>();
		Map<Integer, List<String>> wordsByLength = new TreeMap<>();
		// Group the words by their length
		for (String w : theWords) {
			update(wordsByLength, w.length(), w);
		}

		// Work on each group separately
		for (Map.Entry<Integer, List<String>> entry : wordsByLength
				.entrySet()) {
			List<String> groupWords = entry.getValue();
			int groupNum = entry.getKey();

			// Work on each position in each group
			for (int i = 0; i < groupNum; i++) {
				// Remove one character in specified position,computing
				// representative.
				// Words with same representative are adjacent ,so first
				// populate a map
				Map<String, List<String>> repToWord = new TreeMap<>();
				for (String str : groupWords) {
					String representative = str.substring(0, i)
							+ str.substring(i + 1);
					update(repToWord, representative, str);
				}

				// and then look for map values with more than one string
				for (List<String> wordClique : repToWord.values()) {
					if (wordClique.size() > 2) {
						for (String s1 : wordClique) {
							for (String s2 : wordClique) {
								if (!Objects.equals(s1, s2)) {
									update(adjWords, s1, s2);
								}
							}
						}
					}
				}
			}
		}
		return adjWords;
	}


	private static <E> void update(Map<E, List<String>> m, E key,
			String value) {
		List<String> lst = m.computeIfAbsent(key, k -> new ArrayList<>());
		lst.add(value);
	}
}

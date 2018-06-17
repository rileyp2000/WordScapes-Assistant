import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class WordScapeSolver {

	private final HashSet<String> DICT = initDict();
	private String letters;
	private int length;
	private ArrayList<String> words;
	private ArrayList<ArrayList<String>> allWords;
	private JFrame window;

	public WordScapeSolver() {
		Scanner kybd = new Scanner(System.in);
		System.out.print("Enter the letters given: ");
		letters = kybd.nextLine().trim();

		length = 3;

		words = new ArrayList<String>();
		allWords = new ArrayList<ArrayList<String>>();
		initAllWords();
		process();
		// System.out.println(words);
		printOutput();
	}
	
	public void initAllWords(){
		for(int i = 0; i <= letters.length(); i++){
			allWords.add(new ArrayList<String>());
		}
	}
	
	/**
	 * Creates the dictionary of words to reference
	 * 
	 * @return
	 */
	private HashSet<String> initDict() {
		String fileName = "words_alpha.txt";
		Scanner allExpressions = null;
		// Scanner based on given file name
		try {
			allExpressions = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.exit(1);
		}

		HashSet<String> dictionary = new HashSet<String>(371000);

		while (allExpressions.hasNextLine()) {
			dictionary.add(allExpressions.nextLine().trim());
		}

		return dictionary;
	}

	/**
	 * Formats the print output
	 */
	private void printOutput() {
		for (int lst = 3; lst <= letters.length(); lst++) {
			System.out.println("All words of length " + (lst));
			for (int i = 0; i < allWords.get(lst).size(); i++) {
				String ss = format(allWords.get(lst).get(i), letters.length());
				if (i % 7 == 0)
					System.out.println();
				System.out.print(ss + "|");
			}
			System.out.println();
			System.out.println();
		}
	}

	/**
	 * Formats the string to have the correct spacing on each side
	 * 
	 * @param s
	 *            String to format
	 * @param len
	 *            length of total letters
	 * @return formatted String
	 */
	private String format(String s, int len) {
		int l = (len - s.length()) / 2;

		String white = "";

		for (int i = 0; i < l; i++)
			white += " ";

		if (l % 2 != 0)
			return white + s + " " + white;
		else
			return white + s + white;
	}

	/**
	 * Determines if the String (or any of substrings) are words
	 * 
	 * @param s
	 *            Thing to find words from
	 * @return List of words from the String
	 */
	private ArrayList<String> isWord(String s) {
		ArrayList<String> ret = new ArrayList<String>();
		for (int i = s.length() - 1; i >= 0; i--) {
			String tmp = s.substring(i);
			if (DICT.contains(tmp)) {
				if (tmp.length() == length)
					ret.add(tmp);
				allWords.get(tmp.length()).add(tmp);
			}

		}
		return ret;
	}

	/**
	 * Swaps characters in a String
	 * 
	 * @param a
	 *            String to swap
	 * @param i
	 *            index of first char to swap
	 * @param j
	 *            index of second char to swap
	 * @return the swapped String
	 */
	private String swap(String a, int i, int j) {
		char c;
		char[] ca = a.toCharArray();
		c = ca[i];
		ca[i] = ca[j];
		ca[j] = c;

		return String.valueOf(ca);
	}

	/**
	 * Gets all the permutations of the letters, then removes the duplicate
	 * answers
	 */
	private void process() {
		permute(letters, 0, letters.length());
		removeDuplicates(words);
		ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> s : allWords)
			temp.add(removeDuplicates(s));
		allWords = temp;
	}

	/**
	 * Removes Duplicate strings from the lists
	 * 
	 * @param wds
	 *            List to check for duplicates from
	 */
	private ArrayList<String> removeDuplicates(ArrayList<String> wds) {
		for (int i = 0; i < wds.size(); i++) {
			String temp = wds.get(i);
			int j = i + 1;
			while (j < wds.size()) {
				if (temp.equals(wds.get(j)))
					wds.remove(wds.get(j));
				else
					j++;
			}
		}
		return wds;
	}

	/**
	 * Finds all possible strings from a list
	 * 
	 * @param str
	 *            current string
	 * @param l
	 *            left index
	 * @param r
	 *            right index
	 */
	private void permute(String str, int l, int r) {
		if (l == r) {
			if (isWord(str).size() != 0) {
				for (String s : isWord(str))
					words.add(s);
			}
		} else {
			for (int i = l; i < r; i++) {
				str = swap(str, l, i);
				permute(str, l + 1, r);
				str = swap(str, l, i);
			}
		}
	}

	public static void main(String[] args) {
		WordScapeSolver s = new WordScapeSolver();
	}
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Dictionary {

	public Node root;

	/**
	 * create an empty constructor
	 */
	public Dictionary() {
		root = new Node("", "", false);
		root.valid = false;
	}

	/**
	 * Create a dictionary from a file
	 *
	 * @param filename
	 */
	public Dictionary(String filename, String file2) {
		root = new Node("", "", false);
		root.valid = false;
		String[] hashes = new String[200000];
		String[] plain = new String[200000];
		try (BufferedReader reader = Files.newBufferedReader(
				Paths.get(filename), Charset.forName("UTF-8"));
				BufferedReader reader2 = Files.newBufferedReader(
						Paths.get(file2), Charset.forName("UTF-8"))) {
			String line = null;
			int i = 0;
			while ((line = reader2.readLine()) != null) {
				if (line.trim() != null) {
					plain[i] = line;
					i++;
				}
			}
			i = 0;
			while ((line = reader.readLine()) != null) {
				if (line.trim() != null) {
					hashes[i] = line;
					i++;
				}
			}

			for (i = 0; i < 200000; i ++) {
				if (hashes[i] != null) {
					add(hashes[i], plain[i]);
				}
			}
		} catch (IOException e) {
			System.out.println("Invalid path: " + filename);
		}
	}

	public static String md5Java(String message) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(message.getBytes("UTF-8"));

			// converting byte array to Hexadecimal String
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}

			digest = sb.toString();
		} catch (UnsupportedEncodingException ex) {
			System.out.println("Unsupported Encoding");
		} catch (NoSuchAlgorithmException ex) {
			System.out.println("No such algorithm");
		}
		return digest;

	}

	/**
	 * Add a word into the dictionary
	 *
	 * A node whose prefix is not the prefix of the word you are looking for.
	 * This is the hard case.
	 *
	 * You need to: Create a new node. The prefix stored in this node is the
	 * longest common prefix of the word you are inserting and the prefix stored
	 * at the original root.
	 *
	 * Thus, if you were inserting "hamster" into a node whose prefix was
	 * "hamburger", then the prefix of this new node would be "ham" Let suffix
	 * and suffixWord be the suffix of the original prefix and the suffix of the
	 * word you are adding, after extracting the common prefix.
	 *
	 * So, if you are insetring hamster into a node whose prefix was hamburger,
	 * then suffixWord would be "ster" and suffix would be "burger".
	 *
	 * Set the prefix of the original tree to suffix, and set the child of the
	 * new node corrseponding to the first letter of suffix to the original tree
	 * Recursively insert suffixWord into the approprate child of the new node
	 * Return the new node
	 *
	 * @param word
	 */

	public void add(String hash, String word) {
		if (!checkBool(hash))
			root = add(root, hash, word);
	}

	private Node add(Node node, String hash, String word) {
		hash = hash.toLowerCase();
		if (node == null) {
			Node newNode = new Node(hash, word, true);
			return newNode;
		} else if (node.prefix.equals(hash)) {
			node.valid = true;
			return node;
		} else {
			String getCommonPre = getCommonPre(hash, node.prefix);
			if (getCommonPre.equals(node.prefix)) {
				String wordSuffix = hash.substring(node.prefix.length());
				node.letters[getIndex(wordSuffix)] = add(
						node.letters[getIndex(wordSuffix)], wordSuffix, word);
				return node;
			} else {
				Node commonNode;
				if (getCommonPre.equals(hash)) {
					commonNode = new Node(getCommonPre, word, true);
				} else {
					commonNode = new Node(getCommonPre, word, false);
				}
				String treeSuffix = node.prefix
						.substring(getCommonPre.length());
				String wordSuffix = hash.substring(getCommonPre.length());
				node.prefix = treeSuffix;
				commonNode.letters[getIndex(node.prefix)] = node;
				if (wordSuffix != null) {
					commonNode.letters[getIndex(wordSuffix)] = add(
							commonNode.letters[getIndex(wordSuffix)],
							wordSuffix, word);
				}
				return commonNode;
			}
		}
	}

	public boolean checkBool(String word) {
		 return checkBool(root.letters[getIndex(word)], word);
	}

	private boolean checkBool(Node n, String word) {
		if ((n != null) && (word != null) && !word.equals("")) {
			if (word.startsWith(n.prefix)) {
				if (word.equals(n.prefix)) {
					if (n.valid) {
						return true;
					} else {
						return false;
					}
				} else {
					word = word.substring(n.prefix.length());
					return checkBool(n.letters[getIndex(word)], word);
				}
			}
		}

		return false;
	}

	private String getCommonPre(String word, String nextword) {
		String prefix = "";
		int len = Math.min(word.length(), nextword.length());
		for (int i = 0; i < len; i++) {
			if (word.substring(0, i + 1).equals(nextword.substring(0, i + 1))) {
				prefix = word.substring(0, i + 1);
			} else {
				return prefix;
			}
		}
		return prefix;
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	public int getIndex(String word) {
//		if (Character.isDigit(word.charAt(0))) {
//			int firstNum = word.charAt(0);
//			char toChar = (char)firstNum;
//			System.out.println(toChar);
//			return toChar - 'a';
//		}
		return ((int)word.charAt(0)) - '!';
	}

	/**
	 * Checks to see if a word is in the dictionary
	 *
	 * @param word
	 * @return
	 */
	public ReturnObj check(String word) {
		return check(root.letters[getIndex(word)], word);
	}

	private ReturnObj check(Node n, String word) {
		if ((n != null) && (word != null) && !word.equals("")) {
			if (word.startsWith(n.prefix)) {
				if (word.equals(n.prefix)) {
					if (n.valid) {
						ReturnObj answer = new ReturnObj(true, n.word);
						return answer;
					} else {
						ReturnObj answer = new ReturnObj(false, n.word);
						return answer;
					}
				} else {
					word = word.substring(n.prefix.length());
					return check(n.letters[getIndex(word)], word);
				}
			}
		}
		if (n != null) {
			ReturnObj answer = new ReturnObj(false, n.word);
			return answer;
		} else {
			ReturnObj answer = new ReturnObj(false, "");
			return answer;
		}
	}

	/**
	 * Checks to see if a prefix matches a word in the dictionary
	 *
	 * @param prefix
	 * @return
	 */
	public boolean checkPrefix(String prefix) {
		if (prefix.equals("")) {
			return true;
		}
		if (prefix.length() > 0)
			return checkPrefix(root.letters[getIndex(prefix)], prefix);
		return false;
	}

	private boolean checkPrefix(Node n, String word) {

		if (n.prefix.startsWith(word) || n.prefix.equalsIgnoreCase(word)) {
			return true;
		} else if (word.startsWith(n.prefix)) {
			String shortWord = word.substring(n.prefix.length());
			return checkPrefix(n.letters[getIndex(shortWord)], shortWord);

		}
		return false;

	}

	/**
	 * Print out the contents of the dictionary, in alphabetical order, one word
	 * per line.
	 */
	public void print() {
		for (Node node : root.letters) {
			if (node != null) {
				print(node, node.prefix);
			}
		}
	}

	/**
	 * Print out the tree structure of the dictionary, in a pre-order fashion.
	 */
	public void printTree() {
		printTree(root, 0);

	}

	public void printTree(Node n, int indent) {
		if (n.letters.length > 0) {
			for (Node node : n.letters) {
				if (node != null) {
					for (int i = 0; i < indent; i++) {
						System.out.print("\t");
					}
					System.out.print(node.prefix + "\n");
					printTree(node, indent + 1);
				}
			}

		}
	}

	public void print(Node n, String word) {
		if (n.valid) {
			System.out.println(word);
		} else {
			for (Node node : n.letters) {
				if (node != null) {
					if (node.valid) {
						System.out.println(word + node.prefix);
					} else {
						print(node, word + node.prefix);
					}
				}
			}
		}
	}

	/**
	 * Return an array of the entries in the dictionary that are as close as
	 * possible to the parameter word.
	 *
	 * @param word
	 * @param numSuggestions
	 * @return
	 */
//	public String[] suggest(String word, int numSuggestions) {
//		if (check(word)) {
//			return new String[] { word };
//		}
//		String[] suggestions = new String[numSuggestions];
//		for (int i = 0; i < numSuggestions; i++) {
//			suggestions[i] = word;
//		}
//		return suggestions;
//	}

}
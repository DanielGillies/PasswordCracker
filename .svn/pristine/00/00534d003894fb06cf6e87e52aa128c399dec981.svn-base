import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;


public class PasswordCracker {

	public static void main(String[] args) {
		try(BufferedReader reader = Files.newBufferedReader(Paths.get(args[0]), Charset.forName("UTF-8"))) {
			long startTime = System.currentTimeMillis();
			HashSet<String> input = new HashSet<>();
			String line = null;
			int count = 0;
			while ((line = reader.readLine()) != null) {
				if (line != null) {
					input.add(line.trim());
				}
			}
			System.err.println("Building TRIE...");
			Dictionary passwords = new Dictionary("passwordsBuilt.txt", "passwordsBuiltPlain.txt");
			System.err.println("Finished building TRIE!");
			System.err.println("Searching for words...");
			long startTime2 = System.currentTimeMillis();
			for (String word : input) {
				ReturnObj retrieved = passwords.check(word);
				if (retrieved.found == true) {
					System.out.println("Found " + word + " = " + retrieved.word);
					count++;
				}
			}
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("Searched for: " + ((endTime - startTime2)/1000.0));
			System.out.println("Found " + count + " passwords in " + (totalTime/1000.0) + " seconds.");
		} catch (IOException e) {
			System.out.println("Could not find file");
		}
	}

}


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

public class Converter {

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

	public static void main(String[] args) {
		try (BufferedReader reader = Files.newBufferedReader(
				Paths.get("passwords.txt"), Charset.forName("UTF-8"));
				BufferedWriter writer = Files.newBufferedWriter(
						Paths.get("passwordsBuilt.txt"), Charset.forName("UTF-8"));
				BufferedWriter writer2 = Files.newBufferedWriter(
						Paths.get("passwordsBuiltPlain.txt"), Charset.forName("UTF-8")))
						{

			HashSet<String> input = new HashSet<>();
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line != null) {
					input.add(line.trim());
				}
			}
			for (String word : input) {
				writer.write(md5Java(word));
				writer.newLine();
				writer2.write(word);
				writer2.newLine();
				for (int i = 0; i < word.length(); i++) {
					String newString;
					if (word.charAt(i) == 'i') {
						newString = word.substring(0, i) + "1" + word.substring(i+1);
//						System.out.println(newString);
						writer.write(md5Java(newString.toString()));
						writer.newLine();
						writer2.write(newString.toString());
						writer2.newLine();
					} else if (word.charAt(i) == 'a') {
						newString = word.substring(0, i) + "4" + word.substring(i+1);
						writer.write(md5Java(newString.toString()));
						writer.newLine();
						writer2.write(newString.toString());
						writer2.newLine();
//						System.out.println(newString);
					} else if (word.charAt(i) == 'e') {
						newString = word.substring(0, i) + "3" + word.substring(i+1);
						writer.write(md5Java(newString.toString()));
						writer.newLine();
						writer2.write(newString.toString());
						writer2.newLine();
//						System.out.println(newString);
					} else if (word.charAt(i) == 'o') {
						newString = word.substring(0, i) + "0" + word.substring(i+1);
						writer.write(md5Java(newString.toString()));
						writer.newLine();
						writer2.write(newString.toString());
						writer2.newLine();
//						System.out.println(newString);
					} else if (word.charAt(i) == 'g') {
						newString = word.substring(0, i) + "6" + word.substring(i+1);
						writer.write(md5Java(newString.toString()));
						writer.newLine();
						writer2.write(newString.toString());
						writer2.newLine();
//						System.out.println(newString);
					} else if (word.charAt(i) == 's') {
						newString = word.substring(0, i) + "5" + word.substring(i+1);
						writer.write(md5Java(newString.toString()));
						writer.newLine();
						writer2.write(newString.toString());
						writer2.newLine();
//						System.out.println(newString);
					} else {

					}
				}
				for (int i = 0; i < word.length(); i++) {
					if ((word.charAt(i) >= 97) && (word.charAt(i) <= 122)) {
						String newString = word.substring(0, i) + word.substring(i, i+1).toUpperCase() + word.substring(i+1);
						writer.write(md5Java(newString));
						writer.newLine();
						writer2.write(newString);
						writer2.newLine();
//						System.out.println(newString);
					}
				}
				for (int i = 0; i < 10; i++) {
					StringBuilder newString = new StringBuilder(word);
					newString.append(i);
//					System.out.println(newString.toString());
					writer.write(md5Java(newString.toString()));
					writer.newLine();
					writer2.write(newString.toString());
					writer2.newLine();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
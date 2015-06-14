import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Trimmer {


	public static void main(String[] args) {
		try(BufferedReader reader = Files.newBufferedReader(Paths.get("trimthis"), Charset.forName("UTF-8"));
				BufferedWriter writer = Files.newBufferedWriter(Paths.get("trimmed.txt"), Charset.forName("UTF-8"))) {

			String line;
			int count = 0;
			while ((line = reader.readLine()) != null) {
				if (line != null) {
					if (line.length() >= 6) {
						writer.write(line);
					} else {
						count++;
					}
				}
			}
			System.out.println("Took out " + count + " lines");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

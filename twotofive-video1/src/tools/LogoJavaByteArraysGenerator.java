package tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class LogoJavaByteArraysGenerator {
	public static void main(String[] args) throws IOException {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				String filename = "D:\\git\\twotofive-video1\\twotofive-video1\\media\\cerner-" + i + "-" + j + ".png";
				Path path = Paths.get(filename);

				byte[] bytes = Files.readAllBytes(path);
				String base64 = Base64.getEncoder().encodeToString(bytes);
				StringBuilder builder = new StringBuilder();
				int chunksize = 100;
				for (int k = 0; k < base64.length(); k += chunksize) {
					if (k > 0) {
						builder.append("+ ");
					}
					String line = base64.substring(k,
							Math.min(k + chunksize, base64.length()));
					builder.append("\"" + line + "\"\n");
				}
				System.out.println(builder + ",");
			}
		}

	}
}

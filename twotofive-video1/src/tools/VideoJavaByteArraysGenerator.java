package tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class VideoJavaByteArraysGenerator {
	public static void main(String[] args) throws IOException {
		String filename = "D:\\git\\twotofive-video1\\twotofive-video1\\media\\Engineering-Impact-Thud.mp4";
		Path path = Paths.get(filename);
	    byte[] bytes = Files.readAllBytes(path);
	    String base64 = Base64.getEncoder().encodeToString(bytes);
	    StringBuilder builder = new StringBuilder();
	    int chunksize = 100;
	    for (int i = 0; i < base64.length(); i+=chunksize) {
	    	if (i > 0) {
	    		builder.append("+ ");
	    	}
	    	String line = base64.substring(i,  Math.min(i + chunksize, base64.length()));
	    	builder.append("\"" + line + "\"\n");
	    }
	    System.out.println(builder);
	}
}

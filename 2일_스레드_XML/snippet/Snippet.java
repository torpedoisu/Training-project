package snippet;

public class Snippet {
	try {
		System.out.println("Closing stream...");
		if (br != null) br.close();
		if (osr != null) osr.close();
	} catch (IOException e) {
		System.out.println("=== [Error] IO Exception while closing  ===");
		e.printStackTrace();
	}
}


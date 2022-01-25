import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Application {

	public static void main(String[] args) throws IOException {
		DuplicateCounter duplicateCounter = new DuplicateCounter();
		FileInputStream input = null;
		File file = new File("unique_word_counts.txt");
		input = new FileInputStream("problem2.txt");
		duplicateCounter.count(input);
		duplicateCounter.write(file);
		System.out.println("Program finished");
	}

}

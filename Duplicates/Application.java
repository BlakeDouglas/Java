import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Application {

	public static void main(String[] args) throws IOException {
		FileInputStream input = null;
		File file = new File("unique_words.txt");
		input = new FileInputStream("problem1.txt");
		DuplicateRemover duplicateRemover = new DuplicateRemover();
		duplicateRemover.remove(input);
		duplicateRemover.write(file);
		System.out.print("Program finished");
	}

}

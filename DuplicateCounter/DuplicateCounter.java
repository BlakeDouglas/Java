// Program Created By: Blake Douglas

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DuplicateCounter
{
	// Variable Declaration
	private ArrayList<Integer> wordCounter = new ArrayList<Integer>();
	private ArrayList<Integer> wordCounter2 = new ArrayList<Integer>();
	private ArrayList<String> word = new ArrayList<String>();
	private ArrayList<String> hold = new ArrayList<String>();
	private int flag = 0;
	private String holder = "";
	private int counter = 0;


	public void count(FileInputStream dataFile) throws IOException
	{
		// Read input file
		Scanner inFs = new Scanner(dataFile);
		while(inFs.hasNext())
		{
			// add text to arraylist
			word.add(inFs.next());
		}
		for(int j = 0; j < word.size(); j++)
		{
			holder = word.get(j);
			// Compare each word to every other word in the array list
			for(int i = 0; i < word.size(); i++)
			{
				// Increment counter if it's a duplicate word
				if(word.get(i).compareTo(holder) == 0)
				{
					counter += 1;
					flag = counter;
				}
			}
			// Add total count for each word and reset counter
			wordCounter.add(counter);
			counter = 0;
		}
		// Close scanner
		inFs.close();
	}


	// Write output to a file
	public void write(File outputFile) throws IOException
	{
		FileWriter fw = new FileWriter(outputFile);
		PrintWriter pw = new PrintWriter(fw);
		// Write content to file
		for(int i = 0; i < word.size(); i++)
		{
			pw.println(word.get(i) + " " + wordCounter.get(i));
		}
		// Close the writer
		pw.close();
		}

}

// Created By Blake Douglas

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DuplicateRemover
{
	// Variable Declaration
	private ArrayList<String> uniqueWords = new ArrayList<String>();
	private String newWord = "";
	private int count = 0;
	private int flag = 0;

	// Function to remove duplicates
	public void remove(FileInputStream dataFile ) throws IOException
	{
		Scanner inFS = new Scanner(dataFile);
		// Read in the file
		while(inFS.hasNext())
		{
			newWord = inFS.next();
			// Check if the word has been added before
			if(count == 0)
			{
				// Add word to array list
				uniqueWords.add(newWord);
				count += 1;
			}
			else
			{
				for(int i = 0; i < uniqueWords.size(); i++)
				{
					// Make sure the new word is a duplicate
					if(uniqueWords.get(i).compareTo(newWord) == 0)
					{
						flag = 1;
					}
				}
				// Only add the word if it hasn't been added before
				if(flag == 0)
				{
					uniqueWords.add(newWord);
				}
				flag = 0;
			}
		}
		// Close the file scanner
		inFS.close();
	}



 	// Write the new file without the repeated words
	public void write(File outputFile) throws IOException
	{
		FileWriter fw = new FileWriter(outputFile);
		PrintWriter pw = new PrintWriter(fw);
		for(int i = 0; i < uniqueWords.size(); i++)
		{
			pw.print(uniqueWords.get(i) + " ");
		}
		// Close the writer
		pw.close();
	}
}

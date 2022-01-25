// Program Created By: Blake Douglas
// COP3330 Fall, 2019
import java.util.Scanner;
public class Polling
{

	public static void main(String[] args)
	{
		// Variable Declaration
		Scanner scnr = new Scanner(System.in);
		String[] topics = {"Video games", "Food", "School", "Working", "Social Life"};
		int[][] responses = new int[5][10];
		int answer;
		int choice = 1;
		int count = 0;
		int max = 0;
		int iMax = 0;
		int min = 100000000;
		int iMin = 0;
		int[] avg = new int[5];

		// 0 out all of the results
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				responses[i][j] = 0;
			}
		}

		// Print out the poll and record the user's responses
		while(choice == 1)
		{

			System.out.println("Enter your rating of each topic from 1 (least important) to 10 (most important)");
			for(int i = 0; i < 5; i++)
			{
				// Print each topic and record their response
				System.out.print(topics[i] + ": ");
				answer = scnr.nextInt();
				responses[i][answer - 1] += 1;
			}

			// Print menu to either exit or allow next user to answer the questions
			System.out.println("1 - Answer again\n2 - Exit and see results");
			choice = scnr.nextInt();
		}


		// Calculates the average for every topic
		for(int i = 0; i < 5; i++)
		{
			avg[i] = 0;
		}
		for(int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if(responses[i][j] > 0)
				{
					count += responses[i][j];
				avg[i] += responses[i][j] * (j+1);
				}
			}
			avg[i] = avg[i] / count;
			count = 0;
		}

		// Prints the 2D array with all the responses and averages
		System.out.println("\nRatings: \n\t      1 2 3 4 5 6 7 8 9 10\tAverage");
		for(int i = 0; i < 5; i++)
		{
			System.out.format("%12s: ", topics[i]);
			for(int j = 0; j < 10; j++)
			{

				System.out.print(responses[i][j] + " ");
			}
			System.out.print("\t" + avg[i] + "\n");
		}

		// Finds maximum average value in responses
		for(int i = 0; i < 5; i++)
		{
			if(avg[i] > max) {
				max = avg[i];
				iMax = i;
			}
		}

		// Finds minimum average value in responses
		for(int i = 0; i < 5; i++)
		{
			if(avg[i] < min) {
				min = avg[i];
				iMin = i;
			}
		}

		// Print out max and min topics
		System.out.println("\n" + topics[iMax] + " recieved the highest point total of: " + max);
		System.out.println(topics[iMin] + " recieved the lowest point total of: " + min);

	}

}

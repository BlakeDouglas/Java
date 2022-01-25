// Program created by Blake Douglas
// COP 3330 Fall, 2019
import java.util.Scanner;
public class decrypt
{

	public static void main(String[] args)
	{
		// Variable Declaration
		Scanner scnr = new Scanner(System.in);
		int num;
		int [] decrypt = new int[4];
		int [] inOrder = new int[4];

		// Obtain user input (Message to be decrypted)
		System.out.print("Enter encrypted Number: ");
		num = scnr.nextInt();

		// Change int num to int array for manipulation
		for(int i = 3; i >= 0; i--)
		{
			decrypt[i] = num % 10;
			num /= 10;
		}

		// Reverses the swap to put the letters in the correct order
		inOrder[0] = decrypt[2];
		inOrder[1] = decrypt[3];
		inOrder[2] = decrypt[0];
		inOrder[3] = decrypt[1];

		// Reverses the modulus by adding the reciprocal
		// and modding by 10 again
		for(int i = 0; i < 4; i++)
		{
			inOrder[i] = (inOrder[i] + 3) % 10;
		}

		// Prints original message
		System.out.print("Original Number: ");
		for(int i = 0; i < 4; i++)
		{
			System.out.print(inOrder[i]);
		}
		System.out.println();
	}
}

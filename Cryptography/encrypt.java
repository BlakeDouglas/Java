// Program created by Blake Douglas
// COP 3330 Fall, 2019
import java.util.Scanner;
public class encrypt
{

	public static void main(String[] args)
	{
		// Variable declaration
		Scanner scnr = new Scanner(System.in);
		int data;
		int encrypt;
		int [] cipherText = new int[4];
		int [] swapped = new int[4];

		// Obtain user input (Message to encrypt)
		System.out.print("Enter your 4 digit data: ");
		data = scnr.nextInt();

		// Turns int data into an int array
		for(int i = 3; i >= 0; i--)
		{
			cipherText[i] = data%10;
			data /= 10;
		}

		// Encrypts by shifting 7 and modding by 10
		for(int i = 0; i < 4; i++)
		{
			cipherText[i] = (cipherText[i] + 7) % 10;
		}

		// Swaps positions 0/2 and 1/3.
		swapped[0] = cipherText[2];
		swapped[1] = cipherText[3];
		swapped[2] = cipherText[0];
		swapped[3] = cipherText[1];

		// Prints encrypted message
		System.out.print("Encrypted Message: ");
		for(int i = 0; i < 4; i++)
		{
			System.out.print(swapped[i]);
		}
		System.out.println();
	}
}

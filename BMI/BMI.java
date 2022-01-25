// Program Created By: Blake Douglas
// COP3330 Fall, 2019
import java.util.Scanner;
public class BMI
{

	public static void main(String[] args)
	{
		// Variable Declaration
		Scanner scnr = new Scanner(System.in);
		double pounds;
		double kilograms;
		double weight;
		double height;
		int formula;
		double totalBMI = 0;
		String category;

		// Print menu
		System.out.println("Welcome to the BMI calculator!\n");
		System.out.println("For Pounds and Inches, enter 1\nFor Kilograms and Meters, enter 2");

		// Get user input (Which mode to use)
		System.out.print("Choice: ");
		formula = scnr.nextInt();

		// Pounds and inches were selected
		if(formula == 1)
		{
			System.out.print("\nWeight in Pounds: ");
			weight = scnr.nextDouble();
			System.out.print("Height in inches: ");
			height = scnr.nextDouble();
			totalBMI = (703 * weight) / (height * height);
		}

		// Kilograms and meters were selected
		else if(formula == 2)
		{
			System.out.print("Weight in Kilograms: ");
			weight = scnr.nextDouble();
			System.out.print("Height in Meters: ");
			height = scnr.nextDouble();
			totalBMI = (weight) / (height * height);
		}

		// Print total BMI
		System.out.printf("\nYour BMI is: %.2f\n", totalBMI);

		// Calculate which category their BMI belongs to
		if(totalBMI > 30)
		{
			category = "Obesity";
		}
		else if(totalBMI < 18.5)
		{
			category = "Underweight";
		}
		else if(totalBMI > 18.49 && totalBMI < 24.5)
		{
			category = "Normal weight";
		}
		else
		{
			category = "Overweight";
		}

		// Print weight category according to:
		// https://www.nhlbi.nih.gov/health/educational/lose_wt/BMI/bmicalc.htm
		System.out.println("Your Category is: " + category);
		System.out.println("\nAccording to the National Heart Lung and Blood Institute\nBMI Categories:");
		System.out.println("Underweight = <18.5\nNormal weight = 18.5-24.9\nOverweight = 25-29.9\nObesity = BMI of 30 or greater");
	}

}

Create a class called DuplicateCounter. Create an instance method called count that takes a single parameter called dataFile (representing the path to a text file) and uses a 
Map of Strings to count how many times each word occurs in dataFile. The counts should be stored in an instance variable called wordCounter. 
Create an instance method called write that takes a single parameter called outputFile (representing the path to a text file) and writes the contents of wordCounter to the file 
pointed to by outputFile. The output file should be overwritten if it already exists, and created if it does not exist.

Create a separate class called Application that contains a main method which illustrates the use of DuplicateCounter by calling both the remove and write methods. 
Your input file must be called problem2.txt and your output file must be called unique_word_counts.txt.

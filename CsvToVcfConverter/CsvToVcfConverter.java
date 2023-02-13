import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
public class CsvToVcfConverter {
    


    // Filters out hotspots, suspended lines, etc...
    // True if there is a match
    // False if there isn't a match
    public static boolean isException(String input) {

        //  Add name exceptions here. These are things you don't want contacts for
        // Be careful in your exceptions. If the name contains this anywhere, it will skip it
        // i.e. Filtering "even" will make it skip "Steven"
        String [] exceptions = {"hotspot", "hotsport", "mfm", "mount", "trailer", "suspended", "landfill", "card", "aircard", "mi-fi", "ipad", "tablet", "scale", "gateway", "plant", "cradle", "veterans"};

        for (int i = 0; i < exceptions.length; i++) {
            // If username is blank or equals one of the exceptions, skip it
            if (input.contains(exceptions[i]) || input.equals("")) {
                return true;
            }
        }
        return false;
    }

    // Returns index of space count 'num' in the string
    public static int desiredSpaceIndex(String input, int num) {
        int index = 0;
        int count = 0;
        int retVal = -1;
        for (char c : input.toCharArray()) {
            if (c == ' ') {
                count++;
                if (count == num) {
                    retVal = index;
                }
            }
            index++;
        }
        return retVal;
    }

   // Changes John III Smith -> John Smith III
   public static String fixMiddleNumerals(String input) {
    int flag = 0;
    int wordCount = 1;
    String prefix = "";
    String retString = "";
    String words[] = input.split("\\s");
    for(String c : words) {
        if (c.contains("ii")) {
            flag = 1;
            prefix = c;
        }
        else {
            retString += c;
            retString += (flag == 0 && wordCount <= words.length - 1) ? " " : "";
        }
        wordCount++;
    }
    if (flag == 1) {
        retString += retString.charAt(retString.length() - 1) == ' ' ? prefix : " " + prefix;
    }
    return retString;
}


    
    // Capitalize every word in names and III
    public static String capitalize(String input) {
        String words[] = input.split("\\s");
        String capital="";
      
        for(String c : words) {
            int indexCounter = 0;
            for (char i : c.toCharArray()) {

                if (indexCounter == 0) {
                    String fLetter = c.substring(0, 1).toUpperCase();
                    capital += fLetter;
                }

                // Handles Ron John-Snow
                if (i == '-') {
                    String fLetter = c.substring(indexCounter + 1, indexCounter + 2).toUpperCase();
                    capital += fLetter;
                }
                // Case for John Smith III
                // or John III Smith
                // Short circuit before going out of bounds
                if (i == 'i' && c.length() >= indexCounter+2 && c.charAt(indexCounter + 1) == 'i') {
                        String numerals = c.substring(indexCounter + 1, indexCounter + 2).toUpperCase();
                        capital += numerals;
                }
                else {
                    if (indexCounter + 2 <= c.length()) {
                        String restOfName = c.substring(indexCounter + 1, indexCounter + 2);
                        capital += restOfName;
                    }
                }
                indexCounter++;
            }
            capital += " ";
        }
        return capital;
    }

    // Returns total number of spaces in the string
    public static int spaces(String input) {
        int spaceCount = 0;
        for (char c : input.toCharArray()) {
            if (c == ' ') {
                spaceCount++;
            }
        }
        return spaceCount;
    }

    // Removes double space input errors from the usernames
    public static String removeDoubleSpaces(String input) {
        String retString = "";
        int runningCounter = 0;
        for (char c : input.toCharArray()) {
            if (c == ' ') {
                // Checks for space as first char or space after a space
                if (runningCounter == 0 || input.charAt(runningCounter - 1) == ' ') {
                    System.out.println("Successfully removed double space typo"); 
                }
                else {
                    retString += Character.toString(c);
                }
            }
            else {
                retString += Character.toString(c);
            }
            runningCounter++;
        }
        return retString;
    }

    // Removes 1 letter words from name string
    // John A King -> John King
    public static String initialRemover(String input) {
        String retString = "";
        int index = 0;
        int initialRemoverOffset = 0;
        String words[] = input.split("\\s");
        for(String c : words) {
            if (c.length() == 1) {
                initialRemoverOffset++;
                continue;
            }   
            else {
                retString += c;
                retString += index <= words.length - (2 + initialRemoverOffset) ? " " : "";
            }
            index++;
        }
        return retString;
    }

    // Checks if the name has a suffix
    // John Smith Jr -> True
    // John Smith III -> True
    // John Smith -> False
    public static boolean hasSuffix(String input) {
        if (input.contains("jr") || input.contains("sr") || input.contains("ii")) {
            return true;
        }
        return false;
    }
    public static void main(String[] args) throws FileNotFoundException {
        String vStart = "BEGIN:VCARD\nVERSION:3.0\n";
        String vEnd = "END:VCARD\n";
        String vTel = "TEL;TYPE=voice,work,pref:";
        String name = "N:";
        String foreName = "FN:";
        boolean numFlag = false;

        // Grab file path from user
        Scanner inputScnr = new Scanner(System.in);
        System.out.println("CSV file needs to be in USERNAME,PHONE NUMBER format. Cannot be backwards and cannot contain other content.");
        System.out.print("Enter File Path:\ne.g.: C:\\Users\\JSmith\\HCC Name-Number.csv\nPath: ");
        String filePath = inputScnr.nextLine();

        //String filePath = "C:\\Users\\bdouglas\\HCC Name-Number (1).csv";

        // Close scanner to stop memory leak
        inputScnr.close();

        // Open File to read from given file path
        File readFile = new File(filePath);
        // Define scanner to parse through file
        Scanner scnr = new Scanner(readFile);
        // Need to use commas and new lines as breaks
        scnr.useDelimiter("\n|,");


        // Open File we will be writing to
        try {
            FileWriter vcfWriter = new FileWriter("contacts.vcf");
            int rowChecker = 1;

            // Parse until the Read file is over
            while (scnr.hasNext()) {
                // Skip the first row of input (Typically Column names) for Read file
                while (rowChecker < 3) {
                    scnr.next();
                    rowChecker++;
                }

                // Write info to Write file
                // We are reading in a name
                if (numFlag == false) {
                    // Captures name value and turns all to lowercase
                    String userName = scnr.next();
                    userName = userName.toLowerCase();

                    // First space index in the name value
                    int spaceIndex;
                    spaceIndex = userName.indexOf(' ');
                    
                    // Filter names list only for people
                    if (isException(userName)) {
                        scnr.nextLine();
                        continue;
                    }

                    else {

                        // Fixes double space error from Verizon report
                        userName = removeDoubleSpaces(userName);
                        // Capitalizes each word of username
                        String fullUserName = capitalize(userName);

                        // Formats for N:  
                        // Removes any singular initials, if present
                        userName = initialRemover(userName);
                        // Total # spaces in the name value
                        int spaceCount = spaces(userName);
                        // Places Roman Numerals at end of string
                        userName = fixMiddleNumerals(userName);
                        
                        // Checks if a suffix exists
                        if (hasSuffix(userName)) {
                            if (spaceCount >= 3) {
                                // Last;First;Middle;;Suffix
                                try {
                                    userName = userName.substring(desiredSpaceIndex(userName, spaceCount - 1) + 1, desiredSpaceIndex(userName, spaceCount)) + " " + userName.substring(0, spaceIndex) + " " + userName.substring(spaceIndex + 1, desiredSpaceIndex(userName, 2)) + "  " + userName.substring(desiredSpaceIndex(userName, spaceCount)+1);
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    System.out.println("Index out of bounds exception on suffix case with middle name: " + userName);
                                }
                            }
                            // Last;First;;;Suffix
                            else {
                                try {
                                userName = userName.substring(desiredSpaceIndex(userName, spaceCount - 1) + 1, desiredSpaceIndex(userName, spaceCount)) + " " + userName.substring(0, desiredSpaceIndex(userName, 1)) + "   " + userName.substring(desiredSpaceIndex(userName, spaceCount)+1);
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    System.out.println("Index out of bounds exception on suffix case w/o middle name: " + userName);
                                }
                            }
                        }
                        // Removed initials and handled suffix case so if more than one space, must be middle name
                        // Last;First;Additional
                        else if (spaceCount >= 2) {
                            try {
                                userName = userName.substring(desiredSpaceIndex(userName, spaceCount) + 1) + " " + userName.substring(0, spaceIndex) + " " + userName.substring(spaceIndex + 1, desiredSpaceIndex(userName, 2));
                            } catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("Index out of bounds exception on name with middle name: " + userName);
                            }
                        }
                        //Last;First
                        else {
                            try {  
                                userName = userName.substring(spaceIndex + 1) + " " + userName.substring(0, spaceIndex);
                            }
                            catch (ArrayIndexOutOfBoundsException e) {
                                System.out.println("Index out of bounds exception on First Last name case: " + userName);
                            }
                        }
                       
                        
                        userName = capitalize(userName);
                        userName = userName.replace(' ', ';');
                        // BEGIN:VCARD
                        // VERSION:3.0
                        //
                        vcfWriter.write(vStart);

                        // Format N: and FN: names and add.
                        // BEGIN:VCARD
                        // VERSION:3.0
                        // N: Smith;Bob
                        // FN:Bob Smith
                        // 
                        vcfWriter.write(name + userName + "\n");
                        vcfWriter.write(foreName + fullUserName + "\n");
                    }
                    
                }
                else {
                    // Format the value if it's a phone number before adding
                    // BEGIN:VCARD
                    // VERSION:3.0
                    // N: Smith;Bob
                    // FN:Bob Smith
                    // TEL;TYPE=voice,work,pref:xxx xxx xxxx
                    // 
                    String number = scnr.next();
                    String numFormat = number.replace("-", " ");
                    vcfWriter.write(vTel + numFormat + "\n");
                }


                // Add END tag if we just inserted the number
                // BEGIN:VCARD
                // VERSION:3.0
                // N: Smith;Bob
                // FN:Bob Smith
                // TEL;TYPE=voice,mobile,pref:xxx xxx xxxx
                // END:VCARD
                if (numFlag == true) {
                    vcfWriter.write(vEnd);
                }
                numFlag = !numFlag;
                
            }

            // Close writers and scanners to ensure no memory leak
            scnr.close();        
            vcfWriter.close();
            System.out.println("\n\nContacts File Created Successfully\n\n");
            
        } catch (IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }  
    }
}
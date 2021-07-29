import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

/**
 * Saved Data IO Class
 * The methods from this are used read and write saved data from .txt files
 *
 * @author Chris Joubert
 * @version 1.00, 28 July 2021
 */

public class SaveDataIO {
	

	/**
    *
    * Get Saved Data Method
    * <br>
    * The method returns a string containing all the contents of a .txt file
    *
    * @param fileName String containing the file name to be opened
    * @return String containing all the file contents / "" if the file was not opened successfully 
    * @since version 1.00
    */	
	static String getSavedData(String fileName) {
		// set's default value for string to be blank
		// if a blank string is returned it indicates an error occurred
		String stringToReturn = "";
		try {
			File readfile = new File( fileName + ".txt");
			Scanner inputFile = new Scanner(readfile);
			
			while (inputFile.hasNext()) {
				// reads full line in file and then adds new line at the end
				stringToReturn += inputFile.nextLine() + "\n";
			}
			// file is closed after all lines have been read and added to the string to be returned
			inputFile.close();
		
		} catch (FileNotFoundException e) {
			// if an error occurs due to being unable to read the file, a blank string is returned instead
			return stringToReturn;
			}
		// String is returned containing all the lines from the file
		return stringToReturn;
	}
	
	
	/**
    *
    * Set To File Method
    * <br>
    * The method saves passed String to a .txt file with the passed name
    *
    * @param stringToWrite String containing the data to be saved to the file
    * @param fileName String containing the file name to save the data to
    * @since version 1.00
    */	
	static void setToFile(String stringToWrite, String fileName) {
		Formatter file;
		try {
			file = new Formatter( fileName + ".txt");
			file.format(stringToWrite);
			// file is closed after the data is written to the txt file
			file.close();
		// if an error is encountered and file is not working, the method returns
		} catch (FileNotFoundException e) {
			return;
			}
	}
}

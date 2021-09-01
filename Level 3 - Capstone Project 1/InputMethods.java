import java.math.BigDecimal;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 * Input Method Class The class is used to pass methods to request valid input
 * input is then handled along with potential errors
 *
 * @author Chris Joubert
 * @version 1.00, 27 July 2021
 */

public class InputMethods {

	/**
	 *
	 * Get User BigDecimal Request Method <br>
	 * The method requests the user enter a BigDecimal Value, with no pre-filled
	 * value in the input field The values are passed to the BigDecimal method with
	 * the "pre-filled value" set to blank
	 *
	 * @param valueToRequest String to display when requesting input from the user
	 * @param errorToDisplay String to display if an invalid entry is detected
	 * @return BigDecimal value entered by user or -1 if the user canceled
	 * @see getValidBigDecimal
	 * @since version 1.00
	 */
	public static BigDecimal getValidBigDecimal(String valueToRequest, String errorToDisplay) {

		return getValidBigDecimal(valueToRequest, errorToDisplay, "");
	}

	/**
	 *
	 * Get User BigDecimal Request Method <br>
	 * The method requests the user to enter a BigDecimal Value, with a pre-filled
	 * value in the input field
	 *
	 * @param valueToRequest String to display when requesting input from the user
	 * @param errorToDisplay String to display if an invalid entry is detected
	 * @param prefilledValue String to pre-fill when requesting user input
	 * @return BigDecimal value entered by user or -1 if the user canceled
	 * @since version 1.00
	 */
	public static BigDecimal getValidBigDecimal(String valueToRequest, String errorToDisplay, String prefilledValue) {
		// set to true the loop ends and a value is returned
		boolean validInput = false;
		// when set to true the input field changes to alert the user they entered an
		// incorrect value
		boolean displayError = false;
		// set number to -1 to be detected as no input if the user selects to "cancel"
		BigDecimal numberToReturn = new BigDecimal(-1);
		// while loop continues to loop until a valid BigDecimal is entered
		while (!validInput) {
			String userSelection;
			// requests input from user
			userSelection = (String) JOptionPane.showInputDialog(null,
					(displayError ? errorToDisplay + "\n" : "") + valueToRequest, "Poised Project Management",
					(displayError ? JOptionPane.WARNING_MESSAGE : JOptionPane.DEFAULT_OPTION), null, null,
					prefilledValue);

			// if no value or an alphabetical value is entered the error message is
			// displayed
			// if a correct value is entered, it's cast to a BigDecimal and the loop is
			// ended
			if (userSelection == null) {

				if (getUserConfirmation("Are you sure you want to cancel entry?")) {
					return numberToReturn.negate();
				}

			} else if (userSelection.isEmpty()) {
				displayError = true;
			} else {
				try {
					numberToReturn = new BigDecimal(userSelection);

					if (numberToReturn.compareTo(BigDecimal.ZERO) >= 0) {
						return numberToReturn;
					} else {
						displayError = true;
					}
				} catch (NumberFormatException num) {
					displayError = true;
				}
			}
		}
		return numberToReturn;
	}

	/**
	 * Get User Int Request Method <br>
	 * The method requests the user enter an Int Value, with no pre-filled value in
	 * the input field
	 *
	 * @param valueToRequest String to display when requesting input from the user
	 * @param errorToDisplay String to display if an invalid entry is detected
	 * @return Int value entered by user or -1 if the user canceled
	 * @see getValidInt
	 * @since version 1.00
	 */
	// default method adds no prefilled text to the dialog box
	public static int getValidInt(String valueToRequest, String errorToDisplay) {

		return getValidInt(valueToRequest, errorToDisplay, "");
	}

	/**
	 * Get User Int Request Method <br>
	 * The method requests the user enter a positive Int Value, with a pre-filled
	 * value in the input field
	 *
	 * @param valueToRequest String to display when requesting input from the user
	 * @param errorToDisplay String to display if an invalid entry is detected
	 * @param prefilledValue String to pre-fill when requesting user input
	 * @return Int value entered by user or -1 if the user canceled
	 * @since version 1.00
	 */
	public static int getValidInt(String valueToRequest, String errorToDisplay, String prefilledValue) {
		// remains false until a valid Int is entered
		boolean validInput = false;
		// when set to true the input field changes to alert the user they entered an
		// incorrect value
		boolean displayError = false;
		int numberToReturn = -1;
		// while loop continues to loop until a valid Int is entered
		while (!validInput) {
			String userSelection;
			// requests input from user
			userSelection = (String) JOptionPane.showInputDialog(null,
					(displayError ? errorToDisplay + "\n" : "") + valueToRequest, "Poised Project Management",
					(displayError ? JOptionPane.WARNING_MESSAGE : JOptionPane.DEFAULT_OPTION), null, null,
					prefilledValue);

			// if no value or an alphabetical value is entered the error message is
			// displayed
			// if a correct value is entered, it's cast to an int and the loop is ended
			if (userSelection == null) {

				if (getUserConfirmation("Are you sure you want to cancel entry?")) {
					return -1;
				}

			} else if (userSelection.isEmpty()) {
				displayError = true;
			} else {
				try {
					numberToReturn = Integer.parseInt(userSelection);
					if (numberToReturn >= 0) {
						return numberToReturn;
					} else {
						displayError = true;
					}
				} catch (NumberFormatException num) {
					displayError = true;
				}
			}
		}
		return numberToReturn;
	}

	/**
	 * Get User String Request Method <br>
	 * The method requests the user enter a String Value, without a pre-filled value
	 * in the input field The method also confirms the string does not contain
	 * certain special characters and returns an error if one is detected
	 *
	 * @param valueToRequest String to display when requesting input from the user
	 * @param errorToDisplay String to display if an invalid entry is detected
	 * @return String value entered by user or "" if the user canceled entry
	 * @see getUserString
	 * @since version 1.00
	 */
	// default method adds no prefilled text to the dialog box
	public static String getUserString(String valueToRequest, String errorToDisplay) {

		String defaultCancelMessage = "Are you sure you want to cancel entry?";
		return getUserString(valueToRequest, errorToDisplay, "", defaultCancelMessage);
	}

	/**
	 * Get User String Request Method <br>
	 * The method requests the user enter a String Value, with a pre-filled value in
	 * the input field The method also confirms the string does not contain certain
	 * special characters and returns an error if one is detected
	 *
	 * @param valueToRequest String to display when requesting input from the user
	 * @param errorToDisplay String to display if an invalid entry is detected
	 * @param prefilledValue String to display as a pre-filled value
	 * @param cancelMessage  String to display if the user selects to cancel entry
	 * @return String value entered by user or "" if the user canceled entry
	 * @since version 1.00
	 */
	// default method adds no pre-filled text to the dialog box
	// this method requests a string from the user.
	// if the user passed a blank value they are displayed an error and input is
	// requested again
	public static String getUserString(String valueToRequest, String errorToDisplay, String prefilledValue,
			String cancelMessage) {
		boolean validInput = false;
		// set to true if the user enters an invalid or blank string
		boolean displayError = false;
		// string contains a list of special chars, " and \ are added separately to
		// avoid errors with the string
		String specialCharList = "!#$%*()/:;<=>?[]{|}" + "\\";

		// set's string to empty to be detected as no entry if passed
		String stringToReturn = "";
		// while loop continues to loop until a valid string is entered
		while (!validInput) {
			String userSelection;
			// requests reiterates request in console and pop-up
			userSelection = (String) JOptionPane.showInputDialog(null,
					(displayError ? errorToDisplay + "\n" + "Entries cannot contain: " + specialCharList + "\n" : "")
							+ valueToRequest,
					"Poised Project Management",
					(displayError ? JOptionPane.WARNING_MESSAGE : JOptionPane.DEFAULT_OPTION), null, null,
					prefilledValue);

			// if no value or an alphabetical value is entered the error message is
			// displayed
			// if a correct value is entered, it's returned to the user
			if (userSelection == null) {
				if (getUserConfirmation(cancelMessage)) {
					return "";
				}

			} else if (userSelection.isEmpty()) {
				displayError = true;
			} else {
				displayError = false;
				// loops over current string to confirm no special chars are included
				for (int index = 0; index < userSelection.length(); index++) {

					String currentChar = Character.toString(userSelection.charAt(index));
					// if a special char is detected in the string the method displays an error
					if (specialCharList.contains(currentChar)) {
						displayError = true;

					} else if (index == userSelection.length() - 1 && !specialCharList.contains(currentChar)
							&& displayError == false) {
						stringToReturn = userSelection;
						return stringToReturn;
					}
				}
			}
		}
		return stringToReturn;
	}

	/**
	 * Get Postal Code Request Method <br>
	 * The method requests the user enter a 4 digit South African postal code The
	 * input is cast to an int before being returned to confirm all values are
	 * numeric
	 *
	 * @param tempPersonType String to display when requesting input from the user
	 * @return String value entered by user or "" if the user canceled entry
	 * @since version 1.00
	 */
	// if the string length is not == 4 the postal code is not valid
	public static String getPostalCode(String tempPersonType) {
		// set to true if input it valid. Terminates loop.
		boolean validInput = false;
		// set to true if invalid input is provided
		boolean displayError = false;
		String valueToRequest = "Please enter the " + tempPersonType + "'s Postal Code";
		String errorToDisplay = "Invalid postal code entered!\n"
				+ "Please enter a 4 digit South African postal code.\n";
		// while loop continues to loop until a valid int value of 4 digits long is
		// entered
		while (!validInput) {
			String userSelection;
			// reiterates request to user
			userSelection = (String) JOptionPane.showInputDialog(null,
					(displayError ? errorToDisplay : "") + valueToRequest, "Poised Project Management",
					(displayError ? JOptionPane.WARNING_MESSAGE : JOptionPane.DEFAULT_OPTION), null, null, "0000");
			// if no value or an alphabetical value is entered the error message is
			// displayed
			// if a correct value is entered, it's cast to an int to confirm validity and
			// the loop is ended
			// string value is returned instead of int to retain values starting with 0
			if (userSelection == null) {
				if (InputMethods.getUserConfirmation("Are you sure you want to cancel entry?")) {
					return "";
				}

			} else if (userSelection.isEmpty()) {
				displayError = true;
			} else {
				// function removes all the blank spaces
				userSelection = userSelection.replaceAll("\\s", "");
				// confirms if a number was entered and the number starts with 0 and has 10
				// digits
				if (userSelection.length() == 4) {
					try {
						int isNumber = Integer.parseInt(userSelection);
						if (isNumber > 0) {
							return userSelection;
						} else {
							System.out.println("\n" + errorToDisplay);
						}
					} catch (NumberFormatException num) {
						displayError = true;
					}
				} else {
					displayError = true;
				}
			}
		}
		return "";
	}

	/**
	 * Get User Choice Method <br>
	 * The method requests the user confirm of decline with a "Yes" or "No" question
	 *
	 * @param messageToConfirm String to display when requesting a selection from
	 *                         the user
	 * @return Boolean, Selecting "Yes" returns True, selecting "No" returns False
	 * @since version 1.00
	 */
	// the method requests the user to confirm their choice
	public static boolean getUserConfirmation(String messageToConfirm) {
		boolean boolToReturn = false;
		int userOption = JOptionPane.showConfirmDialog(null, messageToConfirm, "Poised Project Management",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		// option pane returns int of 1 or 0
		// yes = 0, no = 1
		if (userOption == 0) {
			boolToReturn = true;
		}

		return boolToReturn;
	}

	/**
	 * Get Search Index <br>
	 * The method requests user input and filters the passed array to for value that
	 * match the user input If an int array is passed to search, the int array is
	 * cast to a string array first.
	 *
	 * @param arrayToSearch        Int Array to cast to a string array and search
	 *                             for the users's matching input
	 * @param arrayOptionToDisplay String Array to display as selection options for
	 *                             the user ( must match the arrayToSearch in length
	 *                             )
	 * @return Int pointing to the index of the user's selection from the search
	 *         results ( -1 is returned if the user cancels )
	 * @see getSearchIndex
	 * @since version 1.00
	 */
	// if an int array is passed it's cast to a string array before being passed to
	// the next method
	public static int getSearchIndex(int[] arrayToSearch, String[] arrayOptionToDisplay) {
		String[] intArrayToCast = new String[arrayToSearch.length];

		for (int index = 0; index < intArrayToCast.length; index++) {
			intArrayToCast[index] = Integer.toString(arrayToSearch[index]);
		}
		return getSearchIndex(intArrayToCast, arrayOptionToDisplay);
	}

	/**
	 * Get Search Index <br>
	 * The method requests user input and filters the passed array to for value that
	 * match the user input
	 *
	 * @param arrayToSearch        String Array to filter for the user's search
	 *                             request
	 * @param arrayOptionToDisplay String Array to display as selection options for
	 *                             the user ( must match the arrayToSearch in length
	 *                             )
	 * @return Int pointing to the index of the user's selection from the search
	 *         results ( -1 is returned if the user cancels )
	 * @see getSearchIndex
	 * @since version 1.00
	 */
	public static int getSearchIndex(String[] arrayToSearch, String[] arrayOptionToDisplay) {

		// -1 indicates the user canceled input
		int searchIndex = -1;
		// blank spaces are removed to optimize results and avoid no results being
		// returned due to accidental blank spaces
		String valueToFind = getUserString("Please enter the value to search", "You need to enter a valid value")
				.replaceAll("\\s", "");
		// temp array containing all the original index values of the items passed
		int[] indexArray = { -1 };
		// temp array containing all the values that match the user's input
		String[] valueArray = { "" };

		if (!valueToFind.isBlank()) {
			for (int index = 0; index < arrayToSearch.length; index++) {
				// compares the input search with the array and returns results in a non-cases
				// sensative manner
				if (arrayToSearch[index].toLowerCase().contains(valueToFind.toLowerCase())) {
					if (indexArray[0] == -1) {
						valueArray[0] = arrayOptionToDisplay[index];
						indexArray[0] = index;

					} else {
						valueArray = ArrayMethods.addToStringArray(valueArray, arrayOptionToDisplay[index]);
						indexArray = ArrayMethods.addToIntArray(indexArray, index);
					}
				}
			}
			// if the user cancels entering a value, the functions returns -1 to indicate
			// the user canceled their search
		} else if (valueToFind.isBlank()) {
			return searchIndex;
		}

		if (indexArray[0] != -1) {
			// value array is displayed containing all values that match the user's search
			// request
			String menuSelection = (String) JOptionPane.showInputDialog(null, "The following results were found:\n\n",
					"Poised Project Management", JOptionPane.PLAIN_MESSAGE, null, valueArray, valueArray[0]);

			// if the user cancels here, "-1" is returned instead of null to indicate the
			// user canceled
			if (menuSelection == null) {
				return -1;
			}
			searchIndex = indexArray[Arrays.asList(valueArray).indexOf(menuSelection)];

		} else {
			// if no matching results are found an error is displayed and the user is
			// returned to search again
			JOptionPane.showMessageDialog(null, "No results found for " + valueToFind + " please try again",
					"Poised Project Management", JOptionPane.ERROR_MESSAGE);
		}
		return searchIndex;
	}
}

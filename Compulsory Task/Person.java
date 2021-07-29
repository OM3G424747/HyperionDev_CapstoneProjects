import javax.swing.JOptionPane;

/**
 * Person Class
 * The class is used manage personnel contact information 
 * Each parameter of the person class stores an array to allow for multiple accounts 
 * New entries are appended to the end of each array at the last index value
 * 
 * Each account is assigned an account number automatically to allow search methods to 
 * Locate the same account even if details on that account are changed
 * 
 *
 * @author Chris Joubert
 * @version 2.00, 28 July 2021
 */

public class Person {
	
	// all parameters for the class are initialized as arrays
	// accountNum is never to be changed by the end user
	
	String[] personType;
	String[] firstName;
	String[] lastName;
	String[] phoneNumber;
	String[] email;
	String[] street;
	String[] city;
	String[] region;
	String[] country;
	String[] postalCode;
	int[] accountNum;
	
	
	/**
    *
    * Person Class Initialization Method
    * <br>
    * The method sets the initial values for the person class
    * Account number is initialized a 1 and cannot be changed by the end user
    * If initial account values are unknown this should be initialized as "none"
    *
    * @param personType String, contains account type of first account
    * @param firstName String, contains first name of first account
    * @param lastName String, contains surname of first account
    * @param phoneNumber String, contains phone number of first account
    * @param email String, contains email of first account
    * @param street String, contains street address of first account
    * @param city String, contains city name of first account
    * @param region String, contains province name of first account
    * @param country String, contains country name of first account
    * @param postalCode String, contains postal code number of first account
    * @return Int Array containing the number values of the Day[0], Month[1] and Year[2] 
    * @see setNewPerson
    * @since version 1.00
    */	
	
	public Person (String personType,
				   String firstName,
				   String lastName,
				   String phoneNumber,
				   String email,
				   String street,
				   String city,
				   String region,
				   String country,
				   String postalCode)
	
				{this.personType = ArrayMethods.addToStringArray(this.personType, personType);
				 this.firstName = ArrayMethods.addToStringArray(this.firstName, firstName);
				 this.lastName = ArrayMethods.addToStringArray(this.lastName, lastName);
				 this.phoneNumber = ArrayMethods.addToStringArray(this.phoneNumber, phoneNumber);
				 this.email = ArrayMethods.addToStringArray(this.email, email);
				 this.street = ArrayMethods.addToStringArray(this.street, street);
				 this.city = ArrayMethods.addToStringArray(this.city, city);
				 this.region = ArrayMethods.addToStringArray(this.region, region);
				 this.country = ArrayMethods.addToStringArray(this.country, country);
				 this.postalCode = ArrayMethods.addToStringArray(this.postalCode, postalCode);
				 this.accountNum = ArrayMethods.addToIntArray(this.accountNum, 1);
				}
	
	/**
    *
    * String Array to String Method
    * <br>
    * The method converts a String Array to a single String containing all the values from the array separated by a vertical bar
    *
    * @param passedArray String[] array containing all the data to be converted to a string
    * @return String containing all the data from the array separated by a vertical bar
    * @see getAllData
    * @see intArrayToString
    * @since version 2.00
    */	
	private String stringArrayToString(String[] passedArray) {
		String stringToReturn = "";
		
		for(String value : passedArray) {
			stringToReturn += value + "|";	
		}
		stringToReturn += "\n";
		return stringToReturn;
	}
	
	
	/**
    *
    * String Array to String Method
    * <br>
    * The method converts an int Array to a single String containing all the values from the array separated by a vertical bar
    *
    * @param passedArray int array containing all the data to be converted to a string
    * @return String containing all the data from the array separated by a vertical bar
    * @see getAllData
    * @see stringArrayToString
    * @since version 2.00
    */	
	private String intArrayToString(int[] passedArray) {
		String stringToReturn = "";
		
		for(int value : passedArray) {
			stringToReturn += Integer.toString(value) + "|";	
		}
		stringToReturn += "\n";
		return stringToReturn;
	}
	
	
	/**
    *
    * toString Method
    * <br>
    * The method converts an the parameters of a single user account to a String
    *
    * @param accNum int account number of the account to be displayed 
    * @return String containing the data from all the attributes of the account
    * @since version 1.00
    */	
	public String toString(int accNum) {
		String output = "";
		
		// loops over all account numbers till a matching account number is located
		for (int index = 0; index < this.accountNum.length; index ++ ) {
	    	if(this.accountNum[index] == accNum) {
	    	
			output += "\nContact Type: " + this.personType[index];
		    output += "\nContact Firstname: " + this.firstName[index];
		    output += "\nContact Lastname: " + this.lastName[index];
		    output += "\nContact Phone Number: " + this.phoneNumber[index];
		    output += "\nContact Email Address: " + this.email[index];
		    output += "\nContact Street Address: " + this.street[index];
		    output += "\nContact City: " + this.city[index];
		    output += "\nContact Province: " + this.region[index]; 
		    output += "\nContact Country: " + this.country[index];
		    output += "\nContact Postal Code: " + this.postalCode[index];
	    	}
		}
	    return output;
	 }
	
	
	/**
    *
    * Name To String Method
    * <br>
    * The method converts the name and surname of an account to a single string containing both values
    *
    * @param accNum int account number of the account to be displayed 
    * @return String containing the first and last name of the specified account
    * @since version 1.00
    */	
	public String nameToString(int accNum) {
		String output = "";
		// loops over all account numbers till a matching account number is located
		for(int index = 0; index < accountNum.length; index ++ ) {
			if (this.accountNum[index] == accNum) {
				
				output += this.firstName[index] + " ";
				output += this.lastName[index];
			}
		}
		return output;
	}
	
	
	
	
	/**
    *
    * Edit Personnel Account Method
    * <br>
    * The method requested the user to select an account type and then a specific account.
    * The user is then provided with a list of values to edit 
    * The selected value is then altered according to the user's input
    * If non of the selected account type exist, the user is prompted to create one
    * Account numbers are never changed to help locate the same account even if values are changed 
    *
    * @see getAllData
    * @see stringArrayToString
    * @since version 1.00
    */	
	public void editPersonAccount() {
		// the user is asked to select an account type
		String personTypeToSearch = setPersonType("Please selected the curret category of the account you'd like to edit:\n\n");
		// if blank entry is returned it means the user canceled the selection
		// the method is then left without making changes
		if (personTypeToSearch.isBlank()) {
			return;
		}
		int selectionIndex = 0;
		int[] accountTypeArray = getAccTypeIndex(this.personType, personTypeToSearch);
		String[] accountSelectArray = new String[accountTypeArray.length];
		// if no accounts of the selected type exist the user is asked to create a new account of that type
		// if they select yes the account time is passed to the setNewPerson method for creation
		if(accountTypeArray[0] == -1) {
			if(InputMethods.getUserConfirmation("No " + personTypeToSearch +
													" Accounts currently exist.\n" +
													"Would you like to create a new contact instead?")) {
				// creates new contact is the user selects yes
				setNewPerson(personTypeToSearch);
			}
		// a new temp array is created to display all the available accounts for the selected account type
		}else {
			for(int index = 0; index < accountTypeArray.length; index ++) {
						
						// the account values are passed to an new array that displays them all in a list
						accountSelectArray[index] = this.firstName[accountTypeArray[index]] + " " +
													this.lastName[accountTypeArray[index]] + 
													" - " + this.personType[accountTypeArray[index]];	
			}
			// all available accounts are displayed for selection in a drop down box
			String menuSelection = (String)JOptionPane.showInputDialog( 
					  null, 
					  "Please Select An Account:\n\n", 
					  "Poised Project Management", 
					  JOptionPane.PLAIN_MESSAGE,
					  null, accountSelectArray, 
					  accountSelectArray[0]);
			
			// the user cancels the selection process they are asked if they want to return to the main menu
			// otherwise they are looped through the process of updating all the account details
			if (menuSelection == null) {
				if(InputMethods.getUserConfirmation("Are you sure you want to cancel entry and return to main menu?")) {
					return;
				}
			}else {
				for(int value = 0; value < accountSelectArray.length; value ++ ) {
					if(menuSelection.equals(accountSelectArray[value])) {
						
						selectionIndex = accountTypeArray[value];
					}
				}
				// displays an array with all the available parameters the user can edit
				String[] editTypes = {"Account Type", "Name and Last Name", "Phone Number", "Email Address", "Physical Address"};
				String editSelection = (String)JOptionPane.showInputDialog( 
						  null, 
						  "Please the section of the account you'd like to edit:\n\n", 
						  "Poised Project Management", 
						  JOptionPane.PLAIN_MESSAGE,
						  null, editTypes, 
						  editTypes[2]);
				
				// selection to edit Account Type 
				if(editSelection.equals(editTypes[0])) {
					String tempPersonType = setPersonType("Please select the new account type to assign to this account:\n\n");
					if (tempPersonType.isBlank()) {
						return;
					}
					// displays changes made to the account
					JOptionPane.showMessageDialog(null,
							"The Account Type has been updated for " + this.firstName[selectionIndex] + " to:\n" +
							"Old Account Type: " + this.personType[selectionIndex] +
							"\nNew Account Type: " + tempPersonType,
						    "Poised Project Management",
						    JOptionPane.INFORMATION_MESSAGE);
					// if the value is not blank the old value is overwritten with the new one
					this.personType[selectionIndex] = tempPersonType;
				
				// selection to edit First and Last Names
				} else if(editSelection.equals(editTypes[1])) {
					String tempFirstName = InputMethods.getUserString(
							"Please enter the " + this.personType[selectionIndex] + "'s new firstname", 
							"You need to enter their firstname in order to continue",
							this.firstName[selectionIndex],
							"Are you sure you want to cancel entry?"
							);
					if (tempFirstName.isBlank()) {
						return;
					}
					String tempLastName = InputMethods.getUserString(
							"Please enter the " + this.personType[selectionIndex] + "'s new lastname", 
							"You need to enter their lastname in order to continue",
							this.lastName[selectionIndex],
							"Are you sure you want to cancel entry?"
							);
					if (tempLastName.isBlank()) {
						return;
					}
					// displays changes made to the account
					JOptionPane.showMessageDialog(null,
							"The first and last name has been updated for " + this.firstName[selectionIndex] + " to:\n" +
							"Old First and Last Name: " + this.firstName[selectionIndex] + " " + this.lastName[selectionIndex] +
							"\nNew First and Last Name: " + tempFirstName + " " + tempLastName,
						    "Poised Project Management",
						    JOptionPane.INFORMATION_MESSAGE);
					// if the values are not blank the old values are overwritten with the new ones
					this.firstName[selectionIndex] = tempFirstName;
					this.lastName[selectionIndex] = tempLastName;
				
				// selection to edit Phone Number
				} else if(editSelection.equals(editTypes[2])) {
					String tempPhoneNumber = getPhoneNumber(this.personType[selectionIndex], this.phoneNumber[selectionIndex]);
					if (tempPhoneNumber.isBlank()) {
						return;
					}
					// displays changes made to the account
					JOptionPane.showMessageDialog(null,
							"The phone number has been updated for " + this.firstName[selectionIndex] + " to:\n" +
							"Old Phone Number: " + this.phoneNumber[selectionIndex] +
							"\nNew Phone Number: " + tempPhoneNumber,
						    "Poised Project Management",
						    JOptionPane.INFORMATION_MESSAGE);
					// if the value is not blank the old value is overwritten with the new one
					this.phoneNumber[selectionIndex] = tempPhoneNumber;

				// selection to edit Email 
				} else if (editSelection.equals(editTypes[3])) {
					String tempEmail = getValidEmail(this.personType[selectionIndex], this.email[selectionIndex]);
					if (tempEmail.isBlank()) {
						return;
					}
					// displays changes made to the account
					JOptionPane.showMessageDialog(null,
							"The email has been updated for " + this.firstName[selectionIndex] + " to:\n" +
							"Old Email: " + this.email[selectionIndex] +
							"\nNew Email: " + tempEmail,
						    "Poised Project Management",
						    JOptionPane.INFORMATION_MESSAGE);
					// if the value is not blank the old value is overwritten with the new one
					this.email[selectionIndex] = tempEmail;
					
				// selection to edit Physical Address
				} else if (editSelection.equals(editTypes[4])) {
					String tempStreet = InputMethods.getUserString(
							"Please enter the " + this.personType[selectionIndex] + "'s street address", 
							"You need to enter their street address in order to continue",
							this.street[selectionIndex],
							"Are you sure you want to cancel entry?"
							);
					if (tempStreet.isBlank()) {
						return;
					}
					String tempCity = InputMethods.getUserString(
							"Please enter the " + this.personType[selectionIndex] + "'s current City", 
							"You need to enter their current City in order to continue",
							this.city[selectionIndex],
							"Are you sure you want to cancel entry?"
							);
					if (tempCity.isBlank()) {
						return;
					}
					String tempRegion =  InputMethods.getUserString(
							"Please enter the " + this.personType[selectionIndex] + "'s current Province", 
							"You need to enter their current Province in order to continue",
							this.region[selectionIndex],
							"Are you sure you want to cancel entry?"
							);
					if (tempRegion.isBlank()) {
						return;
					}
					String tempCountry =  InputMethods.getUserString(
							"Please enter the " + this.personType[selectionIndex] + "'s current Country", 
							"You need to enter their current Country in order to continue",
							this.country[selectionIndex],
							"Are you sure you want to cancel entry?"
							);
					if (tempCountry.isBlank()) {
						return;
					}
					String tempPostalCode = InputMethods.getPostalCode(this.personType[selectionIndex]);
					if (tempPostalCode.isBlank()) {
						return;
					}
					// displays changes made to the account
					JOptionPane.showMessageDialog(null,
							"The address has been updated for " + this.firstName[selectionIndex] + " to:\n" +
							"\nOld Address" +
							"\nStreet: " + this.street[selectionIndex] +
							"\nCity: " + this.city[selectionIndex] +
							"\nProvince: " + this.region[selectionIndex] +
							"\nCountry: " + this.country[selectionIndex] +
							"\nPostal Code: " + this.postalCode[selectionIndex] +
							"\n\nNew Address" +
							"\nStreet: " + tempStreet +
							"\nCity: " + tempCity +
							"\nProvince: " + tempRegion +
							"\nCountry: " + tempCountry +
							"\nPostal Code: " + tempPostalCode,
						    "Poised Project Management",
						    JOptionPane.INFORMATION_MESSAGE);
					// if the values are not blank the old values are overwritten with the new ones
					this.street[selectionIndex] = tempStreet;
					this.city[selectionIndex] = tempCity;
					this.region[selectionIndex] = tempRegion;
					this.country[selectionIndex] = tempCountry;
					this.postalCode[selectionIndex] = tempPostalCode;
				}
				// profile is only updated at the end of each selection	
				// this is done to avoid having blank information added to a profile due to the user canceling midway
				// account number is never editable because it should always point towards the same person's account
			} 
		}
	}
	

	/**
    *
    * Get Saved Data Method
    * <br>
    * The method converts saved data stored in a txt file to parameter data 
    * This method overwrites any data that might have previously been added as values
    *
    * @param savedData String containing all account information
    * @since version 2.00
    */	
	public void getSavedData(String savedData) {
		// initial string is split to an array containing all the rows of data passed
		String[] dataArray = savedData.split("\\n");
		// rows are then split to columns
		this.personType = dataArray[0].split("\\|");
		this.firstName = dataArray[1].split("\\|");
		this.lastName = dataArray[2].split("\\|");
		this.phoneNumber = dataArray[3].split("\\|");
		this.email = dataArray[4].split("\\|");
		this.street = dataArray[5].split("\\|");
		this.city = dataArray[6].split("\\|");
		this.region = dataArray[7].split("\\|");
		this.country = dataArray[8].split("\\|");
		this.postalCode = dataArray[9].split("\\|");
		
		// int array for account numbers is looped over to parse each value to an int
		String[] intArrayToCast = dataArray[10].split("\\|");
		int[] tempIntArray = new int[intArrayToCast.length];
		for(int index = 0; index <  intArrayToCast.length; index ++) {
			tempIntArray[index] = Integer.parseInt(intArrayToCast[index]);
		}
		this.accountNum = tempIntArray;
	}
	
	
	/**
    *
    * Get Account Type Index Method
    * <br>
    * Returns an array containing all the index numbers of accounts that match the same account type 
    *
    * @param accountType String[] array containing all account types to be filtered
    * @param accountTypeNeeded String the value to be filtered for 
    * @return accountArrayIndex int[] containing an array of all the index values of account that match the same account type
    * @see getAccountNumber
    * @see editPersonAccount
    * @since version 2.00
    */	
	public int[] getAccTypeIndex(String [] accountType, String accountTypeNeeded ) {
		int[] accountArrayIndex;
		int accountTypeCounter = 0;
			
		for (int index = 0; index < accountType.length; index ++) {
			if (accountType[index].equals(accountTypeNeeded)) {
				accountTypeCounter ++; 
			}
		}
		if (accountTypeCounter > 0) {
			accountArrayIndex = new int[accountTypeCounter];
			int newArrayIndexCounter = 0;
			for (int typeIndex = 0; typeIndex < accountType.length; typeIndex ++) {
				if (accountType[typeIndex].equals(accountTypeNeeded)) {
					accountArrayIndex[newArrayIndexCounter] = typeIndex;
					newArrayIndexCounter ++; 
				}
			}
		}else {
			// set's variable to identify if array is empty
			// is arrays are empty -1 is returned to result in an error prompting the user to first create an account
			accountArrayIndex = new int[1];
			accountArrayIndex[0] = -1;
			return accountArrayIndex;
		}
		return accountArrayIndex;
	}

	
	/**
    *
    * Get All Data Method
    * <br>
    * Returns all account data as a single string for saving in a txt file 
    *
    * @return allDataToReturn String returns all data from the class and it's parameters in a single string 
    * @see stringArrayToString
    * @see intArrayToString
    * @since version 2.00
    */	
	public String getAllData() {
		String allDataToReturn = "";
		
		allDataToReturn += stringArrayToString(this.personType);
		allDataToReturn += stringArrayToString(this.firstName);
		allDataToReturn += stringArrayToString(this.lastName);
		allDataToReturn += stringArrayToString(this.phoneNumber);
		allDataToReturn += stringArrayToString(this.email);
		allDataToReturn += stringArrayToString(this.street);
		allDataToReturn += stringArrayToString(this.city);
		allDataToReturn += stringArrayToString(this.region);
		allDataToReturn += stringArrayToString(this.country);
		allDataToReturn += stringArrayToString(this.postalCode);
		allDataToReturn += intArrayToString(this.accountNum);
		
		return allDataToReturn;
	}
	
	
	/**
    *
    * Get Last Name Method
    * <br>
    * Returns all account data as a single string for saving in a txt file 
    *
    * @param accNum int the account number of the last name to be retrieved 
    * @return lastNameToReturn String the Last Name value of the account associated with the account number
    * @since version 1.00
    */	
	public String getLastName(int accNum) {
		String lastNameToReturn = "";
		for(int index = 0; index < this.accountNum.length; index ++) {
			if(accNum == this.accountNum[index]) {
				return this.lastName[index];
			}
		}
		return lastNameToReturn;
	}
	
	
	/**
    *
    * Get Phone Number Method
    * <br>
    * Requests the user enter a valid South African phone number
    * Phone numbers must start with + and contain the dialing code
    * For example: +27 78 258 2424
    * Method automatically removes blank spaces and does not count them towards the total length 
    *
    * @param tempPersonType String contains the account type to be displayed when requesting a phone number
    * @param currentNum String contains the pre-filled value for the number field 
    * @return userSelection String containing phone number entered / or "" if the user canceled entry
    * @since version 2.00
    */	
	private String getPhoneNumber(String tempPersonType, String currentNum) {
		boolean validInput = false;
		boolean displayError = false;
		String errorToDisplay = "Invalid phonenumber entered!\n";
		String valueToRequest =  "Please enter a South African phone Number for the " + 
                				  tempPersonType + "\nPlease include the dialing code";

		// while loop continues to loop until a valid double is entered
		while (!validInput) {
	    	String userSelection;
			// requests user enter a phone number with +27 pre-filled 
	    	// if currentNum is not blank it's filled with an existing number instead of +27
			userSelection =  (String)JOptionPane.showInputDialog(null,
																(displayError? errorToDisplay:"") + valueToRequest, 
																"Poised Project Management",
																(displayError ? JOptionPane.WARNING_MESSAGE : JOptionPane.DEFAULT_OPTION), 
																null, null,
																(currentNum.isBlank() ? "+27": currentNum));

			// if no value or an alphabetical value is entered the error message is displayed
			// if a correct value is entered, it's cast to a double and the loop is ended
			if (userSelection == null) {
				if(InputMethods.getUserConfirmation("Are you sure you want to cancel entry?")) {
					return "";
				}
			} else if (userSelection.isEmpty()) {
				 displayError = true;
				
			} else {
				// function removes all the blank spaces 	
				userSelection = userSelection.replaceAll("\\s","");
				String areaCodeSymbol = "+";
				// confirms the number starts with 0 and has 12 digits including dialing code 
				if (areaCodeSymbol.contains(Character.toString(userSelection.charAt(0))) && userSelection.length() == 12) {
					// confirms a number was entered by casting the number to an int
					String numberToTest = "";
					for(int number =  1; number < userSelection.length(); number ++) {
						numberToTest += userSelection.charAt(number);	
						
						try {
							int isNumber =  Integer.parseInt(numberToTest );
							// confirms the user didn't enter only a series of zeroes 
							if(isNumber > 0) {
								return userSelection;
							} else {
								displayError = true;
								}
						} catch (NumberFormatException num){
							displayError = true;
						}
					}	
				}else {
					displayError = true;
				}
			}
		}
		return "";	
	}
		
		
	/**
    *
    * Get Valid Email Method
    * <br>
    * Requests the user enter a valid email address
    * The method confirms the entry contains an "@" and is at least 6 chars long
    *
    * @param tempPersonType String contains the account type to be displayed when requesting an email
    * @param currentEmail String contains the pre-filled value for the email
    * @return emailToReturn String containing phone number entered / or "" if the user canceled entry
    * @since version 2.00
    */		
	public String getValidEmail(String tempPersonType, String currentEmail) {
		boolean validInput = false;
		boolean displayError = false;
		String emailToReturn = "";
		String valueToRequest = "Please enter the " + tempPersonType + "s' email address";
		String errorToDisplay = "Invalid email entered!\n" + "Email must at least contain an @\n";
		// while loop continues to loop until a valid double is entered
		while (!validInput) {
			// included in loop to update message with error message
			
			String userSelection;
			//requests user input
			userSelection =  (String)JOptionPane.showInputDialog(null,
																(displayError? errorToDisplay:"") + valueToRequest, 
																"Poised Project Management",
																(displayError ? JOptionPane.WARNING_MESSAGE : JOptionPane.DEFAULT_OPTION), 
																null, null,
																(currentEmail.isBlank() ? "": currentEmail));
														
			// if no value or an alphabetical value is entered the error message is displayed
			if (userSelection == null) {
				
				if(InputMethods.getUserConfirmation("Are you sure you want to cancel entry?")) {
					return "";
				}
			} else if (userSelection.isEmpty()) {
				return "";
			
			} else {
				// confirms the email is at least 6 digits long and contains an @ 
				userSelection = userSelection.replaceAll("\\s","");
				for (int letter = 0; letter < userSelection.length(); letter ++) {
					if (userSelection.charAt(letter) == '@' && userSelection.length() > 5) {
						emailToReturn =  userSelection;
						validInput = true;
					
					} else {
						displayError = true;
					}
				}
			}
		}
		return emailToReturn;	
	}
	
	
	/**
    *
    * Get Account Number Method
    * <br>
    * Requests the user select an account type and then displays a list of accounts associated with that account type
    * If no matching account types are found the user is requested to create a new account
    * The account number is returned after an account is selected or created
    *
    * @param accType String contains the account type filter and display related account of
    * @param isNewAccount boolean overwrites request to select an account and skips to account creations process if set to true
    * @return accNumber int containing account number of the selected account / or -1 if the user canceled entry
    * @since version 2.00
    */	
	public int getAccountNumber(String accType, boolean isNewAccount) {
		int accNumber = 0;
		
		String personTypeToSearch = accType;
		// if the user cancels the method returns -1 to signal that the process was canceled
		// passing "" indicates the user canceled 
		if(personTypeToSearch.isBlank()) {
			return -1;
		}	
		int selectionIndex = 0;
		// a temp array is created with all the index numbers of the selected account type
		int[] accountTypeArray = getAccTypeIndex(this.personType, personTypeToSearch);
		String[] accountSelectArray = new String[accountTypeArray.length];
		// if the getAccTypeIndex returns -1, it means the array is empty and the user is asked to create a new account
		
		if(accountTypeArray[0] == -1 && !isNewAccount ) {
			// creates new contact is the user selects yes
			boolean createNewAcc = InputMethods.getUserConfirmation("No " + personTypeToSearch +
					" Accounts currently exist.\n" +
					"Would you like to create a new contact instead?");
			
			if(createNewAcc) {
				int newAccount = setNewPerson(personTypeToSearch);
				return newAccount;
				
			} else if(!createNewAcc){
				return -1;
			}
		// if the array is not empty the user is shown a list of all account with the specified account type
		}else if(!isNewAccount) {
			for(int index = 0; index < accountTypeArray.length; index ++) {
						
						// the account values are passed to an new array that displays them all in a list
						accountSelectArray[index] = this.firstName[accountTypeArray[index]] + " " +
													this.lastName[accountTypeArray[index]] + 
													" - " + this.personType[accountTypeArray[index]];	
			}
			String menuSelection = (String)JOptionPane.showInputDialog( 
					  null, 
					  "Please Select the " + 
					  personTypeToSearch + "\n\n", 
					  "Poised Project Management", 
					  JOptionPane.PLAIN_MESSAGE,
					  null, accountSelectArray, 
					  accountSelectArray[0]);
			// if the user cancels the method returns -1 to signal that the process was canceled
			if(menuSelection == null){
				accNumber = - 1;
			
			}else {
				// alternatively the array is looped over and the account number matching the user selection is returned
				for(int value = 0; value < accountSelectArray.length; value ++ ) {
					if(menuSelection.equals(accountSelectArray[value])) {
						selectionIndex = accountTypeArray[value];
						accNumber = this.accountNum[selectionIndex];
					}
				}
			}
		// returns true if the user selects to create a new account
		}else {
			accNumber = setNewPerson(personTypeToSearch);
			return accNumber;
		}
		return accNumber;
	}

	
	/**
    *
    * Set Personnel Type Method
    * <br>
    * Requests the user select an account type 
    *
    * @return personSelection String containing account type selected
    * @see editPersonAccount
    * @since version 2.00
    */	
	public static String setPersonType() {
		
		return setPersonType("Please Select The Account Type:\n\n");
	}
	
	
	/**
    *
    * Set Personnel Type Method
    * <br>
    * Requests the user select an account type 
    *
    * @param displayRequest String sets the request to be displayed to the user
    * @return personSelection String containing account type selected
    * @see editPersonAccount
    * @since version 2.00
    */	
	public static String setPersonType(String displayRequest) {
		String[] personOptions = {"Architect", 
								  "Contractor", 
								  "Customer"
									}; 
		
		String personSelection = (String)JOptionPane.showInputDialog( 
				  null, 
				  displayRequest, 
				  "Poised Project Management", 
				  JOptionPane.PLAIN_MESSAGE,
				  null, personOptions, 
				  "Architect");
		
		if (personSelection == null) {
			
			if(InputMethods.getUserConfirmation("Are you sure you want to cancel selection?")) {
				return "";
			}
		}
	return personSelection;	
	}
	
	
	/**
    *
    * Set Personnel Type Method
    * <br>
    * Displays a selection to create or edit existing accounts
    * The option to edit is removed is it's detected that no accounts currently exist
    *
    * @return personSelection String containing the selection to edit or create a new account
    * @since version 1.00
    */	
	public String setCreateOrEdit() {
		String[] personOptions = {"Create New Contact Profile"}; 
		//add the option to edit an existing contact if at least 1 contact has been created
		if(!this.firstName[0].equals("None")) {
			personOptions = ArrayMethods.addToStringArray(personOptions, "Edit Existing Contact Profile");
		}
		String personSelection = (String)JOptionPane.showInputDialog( 
				  null, 
				  "Please Select Your Task:\n\n", 
				  "Poised Project Management", 
				  JOptionPane.PLAIN_MESSAGE,
				  null, personOptions, 
				  "Create New Project");
	
		if(personSelection==null) {
			return "";
		}	
	return personSelection;	
	}
	
	
	/**
    *
    * Set Account Num Method
    * <br>
    * Appends Account Number Array with the next account number in sequence
    * This method is used when new account are created or added to assign a new account number
    * @see setNewPerson
    * @since version 1.00
    */	
	// method used to set account numbers of newly created account
	// account number is always 1 digit higher than the last account number on the array
	private void setAccountNum() {
		// set's value to be 1 higher than the last account created
		// this ensures a unique number for every account created 
		int newAccountNum = this.accountNum[accountNum.length -1] + 1;
		this.accountNum =  ArrayMethods.addToIntArray( this.accountNum, newAccountNum);
	}
	
	
	/**
    *
    * Set New Account Method
    * <br>
    * Used to create a new account 
    * 
    * @param accType String contains account type to assign to the new account
    * @see getAccountNumber
    * @since version 2.00
    */	
	public int setNewAccount(String accType) {	
		boolean isNewAccount = true;
		return getAccountNumber(accType, isNewAccount);
		
	}
	
	
	/**
    *
    * Set New Personnel Method
    * <br>
    * Used to create a new account and append it to the current class arrays
    * It detects if an account currently exists or does not
    * If no account is detected, the index position 0 is overwritten 
    * Alternatively the new account is appended to the existing arrays
    * 
    * @param accType String contains account type to assign to the new account
    * @return accountNum int contains account number of new account / returns -1 if the process was canceled 
    * @see editPersonAccount
    * @since version 1.00
    */				
	public int setNewPerson(String accountType) {
		// creates temp variables to be appended after all variable as successfully entered without the user canceling
		String tempPersonType = accountType;
		if (tempPersonType.isBlank()) {
			return -1;
		}
		String tempFirstName = InputMethods.getUserString(
				"Please enter the " + tempPersonType + "'s firstname", 
				"You need to enter their firstname in order to continue"
				);
		if (tempFirstName.isBlank()) {
			return -1;
		}
		String tempLastName = InputMethods.getUserString(
				"Please enter the " + tempPersonType + "'s lastname", 
				"You need to enter their lastname in order to continue"
				);
		if (tempLastName.isBlank()) {
			return -1;
		}
		// add condition to check if number is 10 digits long
		String tempPhoneNumber = getPhoneNumber(tempPersonType, "");
		if (tempPhoneNumber.isBlank()) {
			return -1;
		}
		String tempEmail = getValidEmail(tempPersonType, "");
		if (tempEmail.isBlank()) {
			return -1;
		}
		String tempStreet = InputMethods.getUserString(
				"Please enter the " + tempPersonType + "'s street address", 
				"You need to enter their street address in order to continue"
				);
		if (tempStreet.isBlank()) {
			return -1;
		}
		String tempCity = InputMethods.getUserString(
				"Please enter the " + tempPersonType + "'s current City", 
				"You need to enter their current City in order to continue"
				);
		if (tempCity.isBlank()) {
			return -1;
		}
		String tempRegion =  InputMethods.getUserString(
				"Please enter the " + tempPersonType + "'s current Province", 
				"You need to enter their current Province in order to continue"
				);
		if (tempRegion.isBlank()) {
			return -1;
		}
		String tempCountry =  InputMethods.getUserString(
				"Please enter the " + tempPersonType + "'s current Country", 
				"You need to enter their current Country in order to continue",
				"South Africa",
				"Are you sure you want to cancel entry?"
				);
		if (tempCountry.isBlank()) {
			return -1;
		}
		String tempPostalCode = InputMethods.getPostalCode(tempPersonType);
		if (tempPostalCode.isBlank()) {
			return -1;
		}
		// variables are set to the 0 index if the current arrays are empty
		if (this.personType[0].equals("None") || this.personType[0].isBlank()) {
			
			this.firstName[0] = tempFirstName;
			this.lastName[0] = tempLastName;
			this.phoneNumber[0] = tempPhoneNumber;
			this.email[0] = tempEmail;
			this.street[0] = tempStreet; 
			this.city[0] = tempCity;
			this.region[0] = tempRegion;
			this.country[0] = tempCountry;
			this.postalCode[0] = tempPostalCode;
			this.personType[0] = tempPersonType;
			this.accountNum[0] = 1;
			
			JOptionPane.showMessageDialog(null,
					"The following " + tempPersonType + " has been created:" +
					"\n" + toString(this.accountNum[0]),
				    "Poised Project Management",
				    JOptionPane.INFORMATION_MESSAGE);
			// account number is skipped for first instance, due to first account number being set to 1 by default
			 return this.accountNum[0];
		// values are appended to existing arrays if the arrays are currently not set to "none"	 
		} else {
			
			this.personType = ArrayMethods.addToStringArray(this.personType, tempPersonType);
			this.firstName = ArrayMethods.addToStringArray(this.firstName, tempFirstName);
			this.lastName = ArrayMethods.addToStringArray(this.lastName, tempLastName);
			this.phoneNumber = ArrayMethods.addToStringArray(this.phoneNumber, tempPhoneNumber);
			this.email = ArrayMethods.addToStringArray(this.email, tempEmail);
			this.street = ArrayMethods.addToStringArray(this.street, tempStreet);
			this.city = ArrayMethods.addToStringArray(this.city, tempCity);
			this.region = ArrayMethods.addToStringArray(this.region, tempRegion);
			this.country = ArrayMethods.addToStringArray(this.country, tempCountry);
			this.postalCode = ArrayMethods.addToStringArray(this.postalCode, tempPostalCode);
			
			// adds new account number to account number list
			setAccountNum(); 
			
			// displays the new information that was added 
			JOptionPane.showMessageDialog(null,
					"The following " + tempPersonType + " has been created:" +
					"\n" + toString(this.accountNum[this.accountNum.length - 1]),
				    "Poised Project Management",
				    JOptionPane.INFORMATION_MESSAGE);

			return this.accountNum[this.accountNum.length - 1];
		 }
	}
}

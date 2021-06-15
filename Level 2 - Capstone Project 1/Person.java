import javax.swing.JOptionPane;

public class Person {
	
	
	String[] personType;
	String[] firstName;
	String[] lastName;
	int[] phoneNumber;
	String[] email;
	String[] street;
	String[] city;
	String[] region;
	String[] country;
	int[] postalCode;
	int[] accountNum;
	
	public Person (String personType,
				   String firstName,
				   String lastName,
				   int phoneNumber,
				   String email,
				   String street,
				   String city,
				   String region,
				   String country,
				   int postalCode)
	
				{this.personType = Project.addToStringArray(this.personType, personType);
				 this.firstName = Project.addToStringArray(this.firstName, firstName);
				 this.lastName = Project.addToStringArray(this.lastName, lastName);
				 this.phoneNumber = Project.addToIntArray(this.phoneNumber, phoneNumber);
				 this.email = Project.addToStringArray(this.email, email);
				 this.street = Project.addToStringArray(this.street, street);
				 this.city = Project.addToStringArray(this.city, city);
				 this.region = Project.addToStringArray(this.region, region);
				 this.country = Project.addToStringArray(this.country, country);
				 this.postalCode = Project.addToIntArray(this.postalCode, postalCode);
				 this.accountNum = Project.addToIntArray(this.accountNum, 1);
				}
	
	
	// to string method returns the person's contact details based on the account number passed
	public String toString(int accNum) {
		String output = "";
		
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
	
	
	// returns the first and last name of the person who's account number is passed
	// the values are both returned as a single string 
	public String  nameToString(int accNum) {
		String output = "";
		for(int index = 0; index < accountNum.length; index ++ ) {
			if (this.accountNum[index] == accNum) {
				
				output += this.firstName[index] + " ";
				output += this.lastName[index];
			}
		}
		return output;
	}
	
	
	// method used to display all accounts of the same type as a list
	// the method returns an array with the indexes of all contacts in an array that match the string being passed
	// the array that's returned contains all the index values of accounts the match the passed account type
	// this is then used with drop down selected boxes to display all account of the same type once selected by the user
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
				// is arrays are empty -1 is returned to result in an error prompting the user to first create an acccount
				accountArrayIndex = new int[1];
				accountArrayIndex[0] = -1;
			}
			return accountArrayIndex;
		}
	
	
	// method used to return the contact last name based on the passed account number
	// this is used when creating default project names
	public String getLastName(int accNum) {
		String lastNameToReturn = "";
		
		for(int index = 0; index < this.accountNum.length; index ++) {
			
			if(accNum == this.accountNum[index]) {
				lastNameToReturn = this.lastName[index];
			}
		}
		return lastNameToReturn;
	}
	
	
	// returns a string based on the user selection
	// this method is used to assign a contact type to each person's account
	public static String setPersonType() {
		String[] personOptions = {"Architect", 
								  "Contractor", 
								  "Customer"
									}; 
		
		String personSelection = (String)JOptionPane.showInputDialog( 
				  null, 
				  "Please Select The Account Type:\n\n", 
				  "Poised Project Management", 
				  JOptionPane.PLAIN_MESSAGE,
				  null, personOptions, 
				  "Architect");
		
		if (personSelection == null) {
			
			if(projectManagementSystem.confirmUserChoice("Are you sure you want to cancel selection?")) {
				personSelection = "";
			}
		}
	return personSelection;	
	}
	
	
	// method is used to return a user selection of creating a new contact or editing an existing one
	// the method only allows the user the option to select editing an account if at least 1 contact has been created
	public String setCreateOrEdit() {
		String[] personOptions = {"Create New Contact Profile"}; 
		//add the option to edit an existing contact if at least 1 contact has been created
		if(!this.firstName[0].equals("None")) {
			personOptions = Project.addToStringArray(personOptions, "Edit Existing Contact Profile");
		}
		String personSelection = (String)JOptionPane.showInputDialog( 
				  null, 
				  "Please Select Your Task:\n\n", 
				  "Poised Project Management", 
				  JOptionPane.PLAIN_MESSAGE,
				  null, personOptions, 
				  "Create New Project");
	
		if(personSelection==null) {
			personSelection = "";
		}	
	return personSelection;	
	}
	
	
	// method used to set account numbers of newly created account
	// account number is always 1 digit higher than the last account number on the array
	private void setAccountNum(int[] accountNum) {
		// set's value to be 1 higher than the last account created
		// this ensures a unique number for every account created 
		int newAccountNum = accountNum[accountNum.length -1] + 1;
		this.accountNum =  Project.addToIntArray( this.accountNum, newAccountNum);
		
	}
	
	
	// method used to edit account info
	// the user is requested to select an account type and then the desired account of that set type
	// if no accounts of that specific type exist, the user is first requested to create a new account of that type
	// if not the user is asked to set all the different parameters of that person's account
	// the account details are only set after all parameters have been entered
	// the account number is not changed because this always needs to point to the same account 
	public void editPersonAccount() {
		// the user is asked to select an account type
		String personTypeToSearch = setPersonType();
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
			
			if(projectManagementSystem.confirmUserChoice("No " + personTypeToSearch +
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
				if(projectManagementSystem.confirmUserChoice("Are you sure you want to cancel entry and return to main menu?")) {
					return;
				}
				
			}else {
				for(int value = 0; value < accountSelectArray.length; value ++ ) {
					if(menuSelection.equals(accountSelectArray[value])) {
						
						selectionIndex = accountTypeArray[value];
					}
				}
				
				String tempPersonType = setPersonType();
				if (tempPersonType.isBlank()) {
					return;
				}
				String tempFirstName = projectManagementSystem.getUserString(
						"Please enter the " + tempPersonType + "'s firstname", 
						"You need to enter their firstname in order to continue"
						);
				if (tempFirstName.isBlank()) {
					return;
				}
				String tempLastName = projectManagementSystem.getUserString(
						"Please enter the " + tempPersonType + "'s lastname", 
						"You need to enter their lastname in order to continue"
						);
				if (tempLastName.isBlank()) {
					return;
				}
				// add condition to check if number is 10 digits long
				int tempPhoneNumber = projectManagementSystem.getValidInt(
						"Please enter the " + tempPersonType + "'s Phone Number", 
						"You need to enter their Phone Number in order to continue"
						);
				if (tempPhoneNumber == -1) {
					return;
				}
				String tempEmail = projectManagementSystem.getUserString(
						"Please enter the " + tempPersonType + "'s Email", 
						"You need to enter their Email in order to continue"
						);
				if (tempEmail.isBlank()) {
					return;
				}
				String tempStreet = projectManagementSystem.getUserString(
						"Please enter the " + tempPersonType + "'s street address", 
						"You need to enter their street address in order to continue"
						);
				if (tempStreet.isBlank()) {
					return;
				}
				String tempCity = projectManagementSystem.getUserString(
						"Please enter the " + tempPersonType + "'s current City", 
						"You need to enter their current City in order to continue"
						);
				if (tempCity.isBlank()) {
					return;
				}
				String tempRegion =  projectManagementSystem.getUserString(
						"Please enter the " + tempPersonType + "'s current Province", 
						"You need to enter their current Province in order to continue"
						);
				if (tempRegion.isBlank()) {
					return;
				}
				String tempCountry =  projectManagementSystem.getUserString(
						"Please enter the " + tempPersonType + "'s current Country", 
						"You need to enter their current Country in order to continue"
						);
				if (tempCountry.isBlank()) {
					return;
				}
				int tempPostalCode = projectManagementSystem.getValidInt(
						"Please enter the " + tempPersonType + "'s Postal Code", 
						"You need to enter their Postal Code in order to continue"
						);
				if (tempPostalCode == -1) {
					return;
				}
				
				// profile is only updated at the end if the used doesn't cancel halfway through the process 
				this.personType[selectionIndex] = tempPersonType;
				this.firstName[selectionIndex] = tempFirstName;
				this.lastName[selectionIndex] = tempLastName;
				// add condition to check if number is 10 digits long
				this.phoneNumber[selectionIndex] = tempPhoneNumber;
				this.email[selectionIndex] = tempEmail;
				this.street[selectionIndex] = tempStreet;
				this.city[selectionIndex] = tempCity;
				this.region[selectionIndex] = tempRegion;
				this.country[selectionIndex] = tempCountry;
				this.postalCode[selectionIndex] = tempPostalCode;
				// account number is skipped because it should not be edited to always point towards the same person's account
			} 
		}
	}
	
	
	// this method is used to locate an account number based on the selected account type
	// if no account is found matching that account type, the user is asked if they would like to create a new one
	// if the specified account type exists, the user is given a list of accounts to select from
	// after the user selects an account, the index of that account is passed and each value is updated / edited
	public int getAccountNumber(String accType) {
		int accNumber = 0;
		
		String personTypeToSearch = accType;
		// if the user cancels the method returns -1 to signal that the process was canceled
		if(personTypeToSearch.isBlank()) {
			return -1;
		}	
		int selectionIndex = 0;
		// a temp array is created with all the index numbers of the selected account type
		int[] accountTypeArray = getAccTypeIndex(this.personType, personTypeToSearch);
		String[] accountSelectArray = new String[accountTypeArray.length];
		// if the getAccTypeIndex returns -1, it means the array is empty and the user is asked to create a new account
		if(accountTypeArray[0] == -1) {
			// creates new contact is the user selects yes
			boolean createNewAcc = projectManagementSystem.confirmUserChoice("No " + personTypeToSearch +
					" Accounts currently exist.\n" +
					"Would you like to create a new contact instead?");
			
			if(createNewAcc) {
				int newAccount = setNewPerson(personTypeToSearch);
				if (newAccount == 1) {
					accNumber = this.accountNum[this.accountNum.length - 1];
				}
			} else if(!createNewAcc){
				return -1;
			}
		// if the array is not empty the user is shown a list of all account with the specified account type
		}else {
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
		}
		return accNumber;
	}
	
				
	// method creates a new contact
	// the method asks the user to enter all the parameters for the contact
	// if no accounts have been created the values are added to the 0 index
	// alternatively the values are all appended to the existing arrays
	// the method returns -1 to indicate the user canceled the process
	// the method returns the account number after the account was successfully created
	public int setNewPerson(String accountType) {
		
		// creates temp variables to be appended after all variable as successfully entered without the user canceling
		String tempPersonType = accountType;
		if (tempPersonType.isBlank()) {
			return -1;
		}
		String tempFirstName = projectManagementSystem.getUserString(
				"Please enter the " + tempPersonType + "'s firstname", 
				"You need to enter their firstname in order to continue"
				);
		if (tempFirstName.isBlank()) {
			return -1;
		}
		String tempLastName = projectManagementSystem.getUserString(
				"Please enter the " + tempPersonType + "'s lastname", 
				"You need to enter their lastname in order to continue"
				);
		if (tempLastName.isBlank()) {
			return -1;
		}
		// add condition to check if number is 10 digits long
		int tempPhoneNumber = projectManagementSystem.getValidInt(
				"Please enter the " + tempPersonType + "'s Phone Number", 
				"You need to enter their Phone Number in order to continue"
				);
		if (tempPhoneNumber == -1) {
			return -1;
		}
		String tempEmail = projectManagementSystem.getUserString(
				"Please enter the " + tempPersonType + "'s Email", 
				"You need to enter their Email in order to continue"
				);
		if (tempEmail.isBlank()) {
			return -1;
		}
		String tempStreet = projectManagementSystem.getUserString(
				"Please enter the " + tempPersonType + "'s street address", 
				"You need to enter their street address in order to continue"
				);
		if (tempStreet.isBlank()) {
			return -1;
		}
		String tempCity = projectManagementSystem.getUserString(
				"Please enter the " + tempPersonType + "'s current City", 
				"You need to enter their current City in order to continue"
				);
		if (tempCity.isBlank()) {
			return -1;
		}
		String tempRegion =  projectManagementSystem.getUserString(
				"Please enter the " + tempPersonType + "'s current Province", 
				"You need to enter their current Province in order to continue"
				);
		if (tempRegion.isBlank()) {
			return -1;
		}
		String tempCountry =  projectManagementSystem.getUserString(
				"Please enter the " + tempPersonType + "'s current Country", 
				"You need to enter their current Country in order to continue"
				);
		if (tempCountry.isBlank()) {
			return -1;
		}
		int tempPostalCode = projectManagementSystem.getValidInt(
				"Please enter the " + tempPersonType + "'s Postal Code", 
				"You need to enter their Postal Code in order to continue"
				);
		if (tempPostalCode == -1) {
			return -1;
		}
		
		// variables are set to the 0 index if the current arrays are empty
		if (this.personType[0].equals("None")) {
			
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
			// account number is skipped for first instance, due to first account number being set to 1 by default
			 return this.accountNum[0];
		// values are appended to existing arrays if the arrays are currently not set to "none"	 
		} else {
			
			this.personType = Project.addToStringArray(this.personType, tempPersonType);
			 
			this.firstName = Project.addToStringArray(this.firstName, tempFirstName);
			 
			this.lastName = Project.addToStringArray(this.lastName, tempLastName);
			 
			this.phoneNumber = Project.addToIntArray(this.phoneNumber, tempPhoneNumber);
			 
			this.email = Project.addToStringArray(this.email, tempEmail);
			 
			this.street = Project.addToStringArray(this.street, tempStreet);
			 
			this.city = Project.addToStringArray(this.city, tempCity);
			 
			this.region = Project.addToStringArray(this.region, tempRegion);
			 
			this.country = Project.addToStringArray(this.country, tempCountry);
			 
			this.postalCode = Project.addToIntArray(this.postalCode, tempPostalCode);
			 
			// adds new account number to account number list
			setAccountNum(this.accountNum); 
			return this.accountNum[this.accountNum.length - 1];
		 }
	}
}

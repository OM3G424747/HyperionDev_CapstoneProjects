import javax.swing.JOptionPane;

public class projectManagementSystem {

	// method used to request a double variable's value from the user
	// the method check is the value is a valid double before returning it
	// if it's not, the method will ask the user for their input again
	// if the user cancels, the method will return -1 to signal the user has canceled the process 
	public static double getValidDouble(String valueToRequest, String errorToDisplay) {
		boolean validInput = false;
		// set number to -1 to be detected as no input if the user selects to "cancel"
		double numberToReturn = -1 ;
		// while loop continues to loop until a valid double is entered
		while (!validInput) {
	    	String userSelection;
			//requests reiterates request in console and popup
	    	System.out.println("\n"+ valueToRequest);
			userSelection =  JOptionPane.showInputDialog(valueToRequest);
			
			// if no value or an alphabetical value is entered the error message is displayed
			// if a correct value is entered, it's cast to a double and the loop is ended
			if (userSelection == null) {
				
				if(confirmUserChoice("Are you sure you want to cancel entry?")) {
					validInput = true;
				}
			
			}else if (userSelection.isEmpty()) {
				System.out.println("\n" + errorToDisplay);
			}
			else {
					try {
						System.out.println(userSelection);
						numberToReturn =  Double.parseDouble(userSelection);
						if(numberToReturn > 0) {
						validInput = true;
						} else {
							System.out.println("\n" + errorToDisplay);
						}
					} catch (NumberFormatException num){
						System.out.println("\n" + errorToDisplay);
				}
			}
		  }
		return numberToReturn;
	}
	
	// this method uses the same algorithm as the getValidDouble method, but handles Int values instead
	public static int getValidInt(String valueToRequest, String errorToDisplay) {
		boolean validInput = false;
		int numberToReturn = -1 ;
		// while loop continues to loop until a valid double is entered
		while (!validInput) {
	    	String userSelection;
			//requests reiterates request in console and popup
	    	System.out.println("\n" + valueToRequest);
			userSelection =  JOptionPane.showInputDialog(valueToRequest);
			
			// if no value or an alphabetical value is entered the error message is displayed
			// if a correct value is entered, it's cast to a double and the loop is ended
			if (userSelection == null) {
				
				if(confirmUserChoice("Are you sure you want to cancel entry?")) {
					validInput = true;
				}
			
			}else if (userSelection.isEmpty()) {
				System.out.println(errorToDisplay);
			}
			else {
					try {
						System.out.println(userSelection);
						numberToReturn =  Integer.parseInt(userSelection);
						if(numberToReturn > 0) {
							validInput = true;
							} else {
								System.out.println("\n" + errorToDisplay);
							}
					} catch (NumberFormatException num){
						System.out.println("\n" + errorToDisplay);
				}
			}
		  }
		return numberToReturn;
	}
	
	// this method requests a string from the user.
	// if the user passed a blank value they are displayed an error and input is requested again
	public static String getUserString(String valueToRequest, String errorToDisplay) {
		boolean validInput = false;
		// set's string to empty to be detected as no entry if passed
		String stringToReturn = "";
		// while loop continues to loop until a valid string is entered
		while (!validInput) {
	    	String userSelection;
			//requests reiterates request in console and popup
	    	System.out.println("\n" + valueToRequest);
			userSelection =  JOptionPane.showInputDialog(valueToRequest);
			
			// if no value or an alphabetical value is entered the error message is displayed
			// if a correct value is entered, it's cast to a double and the loop is ended
			if (userSelection == null) {
				
				if(confirmUserChoice("Are you sure you want to cancel entry?")) {
					validInput = true;
				}
			
			}else if (userSelection.isEmpty()) {
				System.out.println("\n" + errorToDisplay);
			}
			else {
				System.out.println(userSelection);
				stringToReturn =  userSelection;
				validInput = true;
			}
		  }
		return stringToReturn;
	}
	
	// the method asks the user if they would like to 
	public static boolean confirmUserChoice(String messageToConfirm) {
		boolean boolToReturn = false;
		int userOption =  JOptionPane.showConfirmDialog(
													null,
												    messageToConfirm,
												    "Poised Project Management",
												    JOptionPane.YES_NO_OPTION);
		
		// option pane returns int of 1 or 0
		// yes = 0, no = 1
		if(userOption == 0) {
			boolToReturn = true;
		}
		
		return boolToReturn;
	}
	
	
	
	public static void main (String [] args) 
	
	{
		// TODO Auto-generated method stub
		
		// Initialized Project class 
		Project project = new Project( 0, "None", 0, 0, 0, "None", "None");
		Person person = new Person( "None", "None", "None", 0, "None", "None", "None", "None", "None", 0);
		
		// Sets Main Menu Options
		String[] mainMenuOptions = {"Create New Project", 
									"Change Due Date", 
									"Change Total Paid To Date",
									"Update/Create Contact Profile",
									"Finalize Project"
									}; 
		
		Boolean isAwaitingInput = true;
		
		
		Boolean isCreatingNewProject = false;
		
		
		while (isAwaitingInput) {
			
			
			String menuSelection = (String)JOptionPane.showInputDialog( 
							  null, 
							  "Please Select Your Task:\n\n", 
							  "Poised Project Management", 
							  JOptionPane.PLAIN_MESSAGE,
							  null, mainMenuOptions, 
							  "Create New Project");
					  
			
			// Close App
			if (menuSelection == null) {
				
				if(confirmUserChoice("Are you sure you want to close the app?")) {
					isAwaitingInput = false;
				}
				
			// Create New Project
			}else if(menuSelection.equals(mainMenuOptions[0])) {
				isCreatingNewProject = true;
				
				while(isCreatingNewProject) {
					System.out.println("\nYou selected " + mainMenuOptions[0]);
					// sets the Architect that will be assigned to the project
					// canceling returns user to main menu instantly 
					int archAccNum = person.getAccountNumber("Architect");
					if (archAccNum <= 0 ) {
						isCreatingNewProject = false;
						break;
					}
					System.out.println("\nThe following contact has been created: \n" + person.toString(archAccNum));
					// sets the Contractor that will be assigned to the project
					// canceling returns user to main menu instantly 
					int contAccNum = person.getAccountNumber("Contractor");
					if (contAccNum <= 0 ) {
						isCreatingNewProject = false;
						break;
					}
					System.out.println("\nThe following contact has been created: \n" + person.toString(contAccNum));
					// sets the "customer that will be assigned to the project
					// canceling returns user to main menu instantly 
					int custAccNum = person.getAccountNumber("Customer");
					if (custAccNum <= 0) {
						isCreatingNewProject = false;
						break;
					} 
					System.out.println("\nThe following contact has been created: \n" + person.toString(custAccNum));
				
					// assigns the selected Architect, Contractor and Customer account numbers, 
					project.setNewProject(archAccNum, contAccNum, custAccNum, person.getLastName(custAccNum));
					System.out.println("The follwoing new project has been created:\n" +
							project.toString(project.projectNum.length - 1, 
							person.nameToString(archAccNum),
							person.nameToString(contAccNum),
							person.nameToString(custAccNum)));
					
					isCreatingNewProject = false; 
					
				}
				
				
				// Change Due Date
			} else if (menuSelection.equals(mainMenuOptions[1])) {
				
				System.out.println("\nYou selected " + mainMenuOptions[1]);
				project.editProject("Due Date");
			
				// Change Total Paid To Date
			} else if (menuSelection.equals(mainMenuOptions[2])) {
				
				System.out.println("\nYou selected " + mainMenuOptions[2]);
				project.editProject("Amount Paid");
				
				// Update Contact's Profile Detail
			} else if (menuSelection.equals(mainMenuOptions[3])) {
				
				System.out.println("\nYou selected " + mainMenuOptions[3]);
				String profileSelection = person.setCreateOrEdit();
				
				System.out.println(profileSelection);
				if (profileSelection.equals("Create New Contact Profile")) {
					int newAcc = person.getAccountNumber(Person.setPersonType());
					System.out.println("\nThe following contact has been created: \n" + person.toString(newAcc));
					
				} else if (profileSelection.equals("Edit Existing Contact Profile")) {
					person.editPersonAccount();
					
				}
				
				// Finalize Project
			} else if (menuSelection.equals(mainMenuOptions[4])) {
				
				System.out.println("\nYou selected " + mainMenuOptions[4]);
				project.editProject("Finalized");
				
			} 		
		}		
	}	
}
	
	
	
	
	


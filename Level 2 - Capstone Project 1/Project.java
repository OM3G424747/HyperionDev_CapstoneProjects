import javax.swing.JOptionPane;


public class Project {

	// the following variables are assigned to the project class on initialization 
	// for the Architect, Contractor and Customer are assigned an account number.
	// the account number is called when contact information is needed on either one of the three persons
	// when projects are created, the finalized variable is set to false by default
	int[] projectNum;
	String[] buildingType;
	int[] erfNum;
	double[] totalCost;
	double[] totalPaid;
	String[] deadLine;
	int[] projectArchitect; 
	int[] projectContractor;
	int[] projectCustomer;
	String[] projectName;
	boolean[] isFinalized; 
	
	// class constructor method used for the Project class
	public Project(int newProjectNum,
			   String newBuildingType,
			   int newERFNum,
			   double newTotalCost,
			   double newTotalPaid,
			   String newDeadLine,
			   String newProjectName) 

			{this.projectNum = addToIntArray(this.projectNum, newProjectNum);
			 this.buildingType = addToStringArray(this.buildingType, newBuildingType);
			 this.erfNum = addToIntArray(this.erfNum, newERFNum);
			 this.totalCost = addToDoubleArray(this.totalCost, newTotalCost);
			 this.totalPaid = addToDoubleArray(this.totalPaid, newTotalPaid);
			 this.deadLine = addToStringArray(this.deadLine, newDeadLine);
			 this.projectArchitect = addToIntArray(this.projectArchitect, 0);
			 this.projectContractor = addToIntArray(this.projectContractor, 0);
			 this.projectCustomer = addToIntArray(this.projectCustomer, 0);
			 this.projectName = addToStringArray(this.projectName, newProjectName);
			 this.isFinalized = addToBoolArray(this.isFinalized, false);
			}
	
	
	// the method is passed an array with a value to append to the end of the array
	// the method creates a new array, duplicates the values of the existing array 
	// and returns the new array with the added value
	// addToBoolArray is used for boolean arrays 
	// addToIntArray is used for int arrays 
	// addToStringArray is used for String arrays 
	// addToDoubleArray is used for Double arrays 
	// all 4 of these methods function the same with the only difference being the variable types they handle
	
	public static boolean[] addToBoolArray(boolean[] passedArray, boolean boolToAdd) {
		// index value of the new bool to be added to the new array
		// it is also used to determine the length of the new array
		// by default it's set to 1 to ensure an array of at least 1 value is always passed even if the passed array is empty
		int newValueIndex = 1;
		
		// if the passed array is not empty, the Value index is set to match the current length + 1
		if (passedArray != null) {
			newValueIndex += passedArray.length + 1;
		}
		// the new array is created to the length of the value index
		boolean [] newArray = new boolean[newValueIndex];
		
		// old array is looped over and the values are duplicated onto the new array
		// the the last index value is then manually set to the passed variable 
		if (passedArray != null) {
			for(int counter = 0; counter < passedArray.length; counter ++)
				newArray[counter] = passedArray[counter];
			
			// the last value in the new array is manually set to the new variable
			newArray[newValueIndex - 1] = boolToAdd;
		// if the list is empty, the else statement returns true 
		} else {
			newArray[0] = boolToAdd;
		}
		// the new array is returned 
		return newArray;
	}
	

	// this method follows the same template as algorithm as the addToBoolArray
	// the only difference is that it handles int variables instead of booleans 
	public static int[] addToIntArray(int [] passedArray, int intToAdd) {
		// new array to be returned
		int newValueIndex = 1;
		if (passedArray != null) {
			newValueIndex = passedArray.length + 1;
		}
		int [] newArray = new int[newValueIndex];
		
		// old array is looped over and the values are duplicated onto the new array
		if (passedArray != null) {
			for(int counter = 0; counter < passedArray.length; counter ++)
				newArray[counter] = passedArray[counter];
			
			// the new int is added to the end of the new array
			newArray[newValueIndex - 1] = intToAdd;
		} else {
			newArray[0] = intToAdd;
		}
		// the new array is returned 
		return newArray;
	}
			
	
	
	// this method follows the same template as algorithm as the addToBoolArray
	// the only difference is that it handles String variables instead of booleans 
	public static String[] addToStringArray(String [] passedArray, String intToAdd) {
		// new array to be returned
		int newValueIndex = 1;
		if (passedArray != null) {
			newValueIndex = passedArray.length + 1;
		}
		String [] newArray = new String[newValueIndex];
		
		// old array is looped over and the values are duplicated onto the new array
		if (passedArray != null) {
			for(int counter = 0; counter < passedArray.length; counter ++)
				newArray[counter] = passedArray[counter];
			
			// the new int is added to the end of the new array
			newArray[newValueIndex - 1] = intToAdd;
		} else {
			newArray[0] = intToAdd;
		}
		// the new array is returned 
		return newArray;
	}
			
			
	// this method follows the same template as algorithm as the addToBoolArray
	// the only difference is that it handles Double variables instead of booleans 	
	public static double[] addToDoubleArray(double [] passedArray, double intToAdd) {
		// new array to be returned
		int newValueIndex = 1;
		if (passedArray != null) {
			newValueIndex = passedArray.length + 1;
			System.out.println("This returned True!");
		}
		double [] newArray = new double[newValueIndex];
		
		// old array is looped over and the values are duplicated onto the new array
		if (passedArray != null) {
			for(int counter = 0; counter < passedArray.length; counter ++)
				newArray[counter] = passedArray[counter];
			
			// the new int is added to the end of the new array
			newArray[newValueIndex - 1] = intToAdd;
		} else {
			newArray[0] = intToAdd;
		}
		// the new array is returned 
		return newArray;
	}
	
	
	
	// creates a default "project name" if the user selects not to set one
	private String setProjectName(String buildingType, String custSurname) {
		// new projectName variable is initialized as empty
		String projectName = "";
		// the building type and surname are then combined in the string and returned
		projectName += buildingType + " ";
		projectName += custSurname;
			
		return projectName;
	}
	
	
	// to string method displays the current values of the project based on the passed index
	public String toString(int index, String archName, String contName, String custName) {
	      String output = "\nProject Name: " + this.projectName[index];
	      output += "\nProject Number: " + this.projectNum[index];
	      output += "\nBuilding Type: " + this.buildingType[index];
	      output += "\nERF Number: " + this.erfNum[index];
	      output += "\nTotal Project Cost: R" + this.totalCost[index];
	      output += "\nTotal Paid to Date: R" + this.totalPaid[index];
	      output += "\nProject Deadline: " + this.deadLine[index];
	      output += "\nArchitect assigned to project: " + archName; 
	      output += "\nContractor assigned to project: " + contName;
	      output += "\nProject customer: " + custName;
	      output += "\nProject is finalized: " + this.isFinalized[index];
	      
	      return output;
	   }
	
	
	
	// method used to edit current projects values
	// if no project current exists the user is asked to first create a project
	// alternatively the user is asked to select the project they would like to edit
	// the values are displayed in the console before they are edited and after they are edited
	// 1 of 3 parameters care currently passed to the method:
	// 1 - "Due Date", 2 - "Amount Paid" or 3 - "Finalized"
	// the value passed will determine the parameter to be edited
	public void editProject(String parameterToEdit) {
		// if no projects exist the user is given an error message and returned to the main menu
		if (this.projectName[0].equals("None")) {
			JOptionPane.showMessageDialog(null,
						"No projects are currently available\n" + 
						"Please create a new project first",
					    "Poised Project Management",
					    JOptionPane.ERROR_MESSAGE);
		// if at least 1 project has been created the user is allowed to select the project they would like to edit
		}else {	
			// TO-DO:
			// a step will be added asking the user if they want to enter a project name / number
			// or if they would like to select from a list of current project
			// the latter will default to the current option 
			String menuSelection = (String)JOptionPane.showInputDialog( 
					  null, 
					  "Please Select a Project:\n\n", 
					  "Poised Project Management", 
					  JOptionPane.PLAIN_MESSAGE,
					  null, this.projectName, 
					  this.projectName[0]);
			
			//if the user cancels while selecting a project they are returned to the main menu	
			if (menuSelection == null) {
				System.out.println("\nReturning to main menu\n");
				
			// alternatively the project index is selected based on the user's input
			// the index is then passed and the user is asked to edit the parameter of the project on that index
			}else {
				for(int index = 0; index < this.projectName.length; index ++ ) {
						if(menuSelection.equals(this.projectName[index])) {
							
							// sets a new due date for the user on the selected project
							if(parameterToEdit.equals("Due Date")) {
								System.out.println(this.projectName[index] + " currently has the following Deadline:\n"+
										   "Deadline: " + this.deadLine[index]);
								this.deadLine[index] = projectManagementSystem.getUserString(
										"Please enter the project due date", 
										"You need to enter a due date to continue"
										);
								System.out.println(this.projectName[index] + " now has a Deadline of:\n"+
										   "Deadline: " + this.deadLine[index]);
							
							// sets a new amount paid for the user on the selected project	
							}else if(parameterToEdit.equals("Amount Paid")) {
								System.out.println(this.projectName[index] + " currently has the following total paid to date:\n"+
										   "Total Paid: R" + this.totalPaid[index]);
								this.totalPaid[index] = projectManagementSystem.getValidDouble(
										"Please enter the total paid towards the project to date", 
										"Please enter a number using numeric values only"
										);
								System.out.println(this.projectName[index] + " now has the following sum paid to date:\n"+
										   "Total Paid: R" + this.totalPaid[index]);
								
							// sets the selected project as finalized or not finalized for the user
							}else if(parameterToEdit.equals("Finalized")) {
								
								System.out.println(this.projectName[index] + " is currently marked as:\n"+
												   "Finalized: " + this.isFinalized[index]);
								this.isFinalized[index] = projectManagementSystem.confirmUserChoice(
										"Would you like to mark the project as finalized?\n" +
										"Select Yes to mark as finalized\n" +
										"Select No to mark as not finalized");
								System.out.println(this.projectName[index] + " is now marked as:\n"+
										   "Finalized: " + this.isFinalized[index]);
							}
						}
					}
			}
		}
	}
	
	
	
	// method used to set a new project
	// the method confirms if project list is set to "None" meaning no project were created yet
	// if it is, the values at the 0 index are edited
	// if not, the values are appended at the end of each parameter array
	// temporary parameter variables are first created and only appended after all required values have been set
	// this prevents having only half of a project created and not having index values match up
	// the account number of the Architect, Contractor and Customer are added instead of their names.
	// this account number will be called when creating an invoice to call the account info based on the account number
	public void setNewProject(int archAccNum, int contAccNum, int custAccNum, String custLastName) {
		
		int tempProjectArchitect = archAccNum;
		if (tempProjectArchitect == -1) {
			return;
		}
		
		int tempProjectContractor = contAccNum;
		if (tempProjectContractor == -1) {
			return;
		}
		
		int tempProjectCustomer = custAccNum;
		if (tempProjectCustomer == -1) {
			return;
		}
		
		// temp variables are created and assigned values with the getValidInt and getUserString methods
		// if the process is canceled at any point the user is returned to the main menu
		int tempProjectNum = projectManagementSystem.getValidInt(
				"Please enter a project number to assign", 
				"Please enter a number using numeric values only"
				);
		if (tempProjectNum == -1) {
			return;
		}
		
		String tempBuildingType = projectManagementSystem.getUserString(
				"Please enter the building type", 
				"You need to enter a building type in order to continue"
				);
		if (tempBuildingType.isBlank()) {
			return;
		}
		
		int tempERFNum = projectManagementSystem.getValidInt(
				"Please enter an ERFnumber", 
				"Please enter a number using numeric values only"
				);
		if (tempERFNum == -1) {
			return;
		}
		
		double tempTotalCost = projectManagementSystem.getValidDouble(
				"Please enter the total cost of the project", 
				"Please enter a number using numeric values only"
				);
		if (tempTotalCost == -1) {
			return;
		}
		
		double tempTotalPaid = projectManagementSystem.getValidDouble(
				"Please enter the total paid towards the project to date", 
				"Please enter a number using numeric values only"
				);
		if (tempTotalPaid == -1) {
			return;
		}
		
		String tempDeadLine = projectManagementSystem.getUserString(
				"Please enter the project due date", 
				"You need to enter a due date to continue"
				);
		if (tempDeadLine.isBlank()) {
			return;
		}
		
		
		// the user is asked if they  would like to set a custom project name
		// if not, the project name defaults to "Building Type" + "Last Name"
		String tempProjectName = "";
		if (projectManagementSystem.confirmUserChoice("Would you like to set a custom project name?")){
			tempProjectName = projectManagementSystem.getUserString(
					"Please enter a name for the project", 
					"You need to enter a name"
					);
			
		
		} else { tempProjectName = setProjectName(tempBuildingType, custLastName);} 
		
		// this condition checks if custom name entry was canceled, if it was all info is not appended
		// the user is returned to the main menu
		if (tempProjectName.isBlank()) {
			return;
			
		// if no values were canceled and the 0 index is still empty, the 0 index is set to the temp values	
		}else {
			if (this.projectName[0].equals("None")) {
				// set's project number
				this.projectNum[0] = tempProjectNum;
				this.buildingType[0] = tempBuildingType;
				this.erfNum[0] = tempERFNum;
				this.totalCost[0] = tempTotalCost;
				this.totalPaid[0] = tempTotalPaid;
				this.deadLine[0] = tempDeadLine;
				// modify to detect if no achitects are available and ask to register a new one if none are
				this.projectArchitect[0] = tempProjectArchitect;
				// modify to detect if no contracts are available and ask to register a new one if none are
				this.projectContractor[0] = tempProjectContractor;
				// modify to detect if no customers are available and ask to register a new one if none are
				this.projectCustomer[0] = tempProjectCustomer;
				this.projectName[0] = tempProjectName;
				// this.isFinalized is skipped due to it being initialized as false with the class by default
			
			// if the 0 index is not empty, the values are appended to each parameter 
			} else {
			
				this.projectNum = addToIntArray(this.projectNum, tempProjectNum);
				
				this.buildingType = addToStringArray(this.buildingType, tempBuildingType);
				
				this.erfNum = addToIntArray(this.erfNum, tempERFNum);
				
				this.totalCost = addToDoubleArray(this.totalCost, tempTotalCost);
				
				this.totalPaid = addToDoubleArray(this.totalPaid, tempTotalPaid);
				
				this.deadLine = addToStringArray(this.deadLine, tempDeadLine);
				
				//if no architects are registered the user is asked to first register one before the project is created
				this.projectArchitect = addToIntArray(this.projectArchitect, tempProjectArchitect);
				
				//if no contractors are registered the user is asked to first register one before the project is created
				this.projectContractor = addToIntArray(this.projectContractor, tempProjectContractor);
				
				// if no customers are registered the user is asked to first register one before the project is created
				this.projectCustomer = addToIntArray(this.projectCustomer, tempProjectCustomer);
				
				this.projectName = addToStringArray(this.projectName, tempProjectName);
				
				// finalized parameter is automatically appended as false	
				this.isFinalized = addToBoolArray(this.isFinalized, false);
				
			}
		}
	}
}

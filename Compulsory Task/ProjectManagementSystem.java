import java.math.BigDecimal;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Project Management System Class
 * The class is the main class used to call methods and other classes to create the project management system
 * 
 * The class loads the parameters of Project and Person classes and then continues to loop until the user cancels 
 *
 * @author Chris Joubert
 * @version 1.00, 28 July 2021
 */

public class ProjectManagementSystem {

	// project settings menu settings
	public static int yearRange = 30; // default year range for due date selection

	public static void main (String [] args) 
	
	{
		
		// Initialized Project class 
		Project project = new Project( 0, "None", 0,"None","None","None","None","None", BigDecimal.ZERO, BigDecimal.ZERO, "None", "None");
		
		// Initialized Person class 
		Person person = new Person( "None", "None", "None", "None", "None", "None", "None", "None", "None", "None");
		// bool values used to determine if an error was encountered while loading saved data
		boolean loadProjectSuccess;
		boolean loadPersonSuccess;
		boolean loadSettingsSuccess;
		
		// loads project saved data
		try {
			person.getSavedData(SaveDataIO.getSavedData("Person"));
			loadPersonSuccess = true;
		} catch(ArrayIndexOutOfBoundsException e) {
			loadPersonSuccess = false;
		}
		
		// loads project saved data
		try {
			project.getSavedData(SaveDataIO.getSavedData("Project"));
			loadProjectSuccess = true;
		} catch(ArrayIndexOutOfBoundsException e) {
			loadProjectSuccess = false;
		}
		
		// loads project settings
		try {
			String tempYearRange = SaveDataIO.getSavedData("Settings").replaceAll("\\s", "");
			yearRange = Integer.parseInt(tempYearRange) ;
			loadSettingsSuccess = true;
		} catch(NumberFormatException e) {
			loadSettingsSuccess = false;
		}
		
		
		if (!loadProjectSuccess || !loadPersonSuccess || !loadSettingsSuccess) {
			
			JOptionPane.showMessageDialog(null,
					"The saved data for the following items were not found:\n" + 
					(loadProjectSuccess ? "":"\n -  Projects Saved Data") + 
					(loadPersonSuccess ? "":"\n -  Accounts Profile Saved Data") +
					(loadSettingsSuccess ? "":"\n -  Settings Saved Data") +
					"\n\nIf you already have saved data for these, please close the app restore the save files and try again\n" +
					"Alternatively a new file will automatically be created when next needed",
				    "Poised Project Management",
				    JOptionPane.ERROR_MESSAGE);
		}

			
		// Sets Main Menu Options
		// add edit project option and refactor code for items belonging to the setting 
		String[] mainMenuOptions = {"Create New Project", 
									"Lookup / Edit Projects", 
									"Update/Create Contact Profile",
									"Settings"
									}; 
		// sets main menu image
		ImageIcon menuIcon = new ImageIcon("assets/menu.png");
		// sets bool to continue looping the app till the user cancels
		Boolean isAwaitingInput = true;
		// continues to loop is creating project till the user cancels 
		Boolean isCreatingNewProject = false;
		
		// main app loop
		while (isAwaitingInput) {
			
			String menuSelection = (String)JOptionPane.showInputDialog( 
							   null, 
							  "Please Select Your Task:\n\n", 
							  "Poised Project Management", 
							  JOptionPane.INFORMATION_MESSAGE,
							  menuIcon, mainMenuOptions, 
							  "Create New Project");
					  	
			// Close App Selection
			if (menuSelection == null) {
				if(InputMethods.getUserConfirmation("Are you sure you want to close the app?")) {
					break;
				}
				
			// Create New Project
			}else if(menuSelection.equals(mainMenuOptions[0])) {
				isCreatingNewProject = true;
				
				while(isCreatingNewProject) {
					// sets the Architect that will be assigned to the project
					// canceling returns user to main menu instantly 
					int archAccNum = person.getAccountNumber("Architect", false);
					if (archAccNum <= 0 ) {
						break;
						
					}
					// saves personnel account info 
					SaveDataIO.setToFile(person.getAllData(), "Person");
					// sets the Contractor that will be assigned to the project
					// canceling returns user to main menu instantly 
					int contAccNum = person.getAccountNumber("Contractor", false);
					if (contAccNum <= 0 ) {
						break;
						
					}
					// saves personnel account info 
					SaveDataIO.setToFile(person.getAllData(), "Person");
					// sets the "customer that will be assigned to the project
					// canceling returns user to main menu instantly 
					int custAccNum = person.getAccountNumber("Customer", false);
					if (custAccNum <= 0) {
						break;
					
					}
					// saves personnel account info 
					SaveDataIO.setToFile(person.getAllData(), "Person");
					// assigns the selected Architect, Contractor and Customer account numbers, 
					project.setNewProject(archAccNum, contAccNum, custAccNum, person.getLastName(custAccNum));
					// saves project data to a Project.txt file
					SaveDataIO.setToFile(project.getAllData(), "Project");
					isCreatingNewProject = false; 	
				}
				
				
			// Lookup / Edit Projects
			} else if (menuSelection.equals(mainMenuOptions[1])) {
				
				// displays error if no projects are currently created
				if(project.deadLine[0].equals("None")) {
					JOptionPane.showMessageDialog(null,
							"No projects currently available!\n" +
						    "Please create a new project to use this feature",
						    "Poised Project Management",
						    JOptionPane.ERROR_MESSAGE);
				} else {
				
					int projectIndex = -1;
					int personAccID;
					
					// loops until a project is selected or the user cancels
					while(projectIndex < 0 ) {
						
						// sets search icon for account lookup
						ImageIcon searchIcon = new ImageIcon("assets/search.png");
						
						String[] lookUpMethod = {"View All OverDue Projects", "View All Uncompleted Projects", "Search"};
						String lookUpSelection = (String)JOptionPane.showInputDialog( 
								  null, 
								  "Please select a lookup method:\n\n", 
								  "Poised Project Management", 
								  JOptionPane.PLAIN_MESSAGE,
								  searchIcon, lookUpMethod , 
								  lookUpMethod[2] );
						
						// null indicates the user canceled
						if(lookUpSelection == null) {
							if (InputMethods.getUserConfirmation("Are you sure you'd like to return to the main menu?")) {
								break;
							}
							
						// view all overdue projects that are also unfinished 
						}else if(lookUpSelection.equals(lookUpMethod[0])) {
							projectIndex = project.getOverDueSelection();
						
						// view unfinished projects
						} else if (lookUpSelection.equals(lookUpMethod[1])) {
							projectIndex = project.getUnfinishedProjects();
							
						// search for projects
						} else if (lookUpSelection.equals(lookUpMethod[2])) {
							
							// selection for searching by user input
							String[] searchOptions = {"Search by Project Name", 
									"Search by Project Number", 
									"Search by Project's Associated Personnel Phone Number",
									"Search by Project's Associated Personnel Email"
									}; 
							
							String searchSelection = (String)JOptionPane.showInputDialog( 
									  null, 
									  "Please Select A Project Search Method:\n\n", 
									  "Poised Project Management", 
									  JOptionPane.PLAIN_MESSAGE,
									  null, searchOptions, 
									  "Create New Project");
							
							// null if the user selected to cancel the search
							if(searchSelection == null) {
								if (InputMethods.getUserConfirmation("Are you sure you'd like to return to the main menu?")) {
									break;
								}
							
							// returns true if "Search by Project Name" was selected
							}else if (searchSelection.equals(searchOptions[0])) {
								projectIndex = InputMethods.getSearchIndex(project.projectName, project.getAccountListValues());
								
							// returns true if "Search by Project Number" was selected
							} else if (searchSelection.equals(searchOptions[1])) {
								projectIndex = InputMethods.getSearchIndex(project.projectNum, project.getAccountListValues());
								
							// returns true if "Search by Project Client Phone Number" was selected
							} else if (searchSelection.equals(searchOptions[2])) {
								personAccID = InputMethods.getSearchIndex(person.phoneNumber, person.firstName);
								// uses account ID to lookup associated projects 
								if (personAccID >= 0) {
									projectIndex =  project.getAccountNumProjects(person.accountNum[personAccID], person.firstName[personAccID]);
								}
							// returns true if "Search by Project Client Email" was selected	
							} else if (searchSelection.equals(searchOptions[3])) {
								personAccID = InputMethods.getSearchIndex(person.email, person.firstName);
								// uses account ID to lookup associated projects 
								if (personAccID >= 0) {	
									projectIndex =  project.getAccountNumProjects(person.accountNum[personAccID], person.firstName[personAccID]);
								}
							}
						}
					}
					// this passes the selected account to edit it's values
					if (projectIndex >= 0) {
						project.editProject(projectIndex);
						if(project.editPersonnelChecker(projectIndex)) {
							int tempArchAccNum;
							int tempContAccNum;
							int tempCustAccNum;
							
							// first value (INDEX 0) equals what to replace it with ( 1 = New Contact, 2 = existing contact)
							// second value (INDEX 1) equals type ( 1 = Architect, 2 = Contractor, 3 = Customer )
							
							// executes if the user selects to edit the Architect
							if (project.personToChange[1] == 1) {	
								if (project.personToChange[0] == 2) {
									tempArchAccNum = person.getAccountNumber("Architect", false);
									if(tempArchAccNum > 0){
										if(InputMethods.getUserConfirmation("Are your sure you want to change the project's assigned Architect?\n" + 
												"\nOld Project Architect: " + person.nameToString(project.projectArchitect[projectIndex]) +
												"\nNew Project Architect: " + person.nameToString(tempArchAccNum))) {
											project.projectArchitect[projectIndex] = tempArchAccNum;
										}
									}
								}else if (project.personToChange[0] == 1) {
									tempArchAccNum = person.setNewAccount("Architect");
									if(tempArchAccNum > 0){
										if(InputMethods.getUserConfirmation("Are your sure you want to change the project's assigned Architect?\n" + 
												"\nOld Project Architect: " + person.nameToString(project.projectArchitect[projectIndex]) +
												"\nNew Project Architect: " + person.nameToString(tempArchAccNum))) {
											
											project.projectArchitect[projectIndex] = tempArchAccNum;
										}
									}
								}	
							// executes if the user selects to edit the Contractor
							}else if (project.personToChange[1] == 2) {		
								if (project.personToChange[0] == 2) {
									
									tempContAccNum = person.getAccountNumber("Contractor", false);
									
									if(tempContAccNum > 0){
										if(InputMethods.getUserConfirmation("Are your sure you want to change the project's assigned Contractor?\n" + 
												"\nOld Project Contractor: " + person.nameToString(project.projectContractor[projectIndex]) +
												"\nNew Project Contractor: " + person.nameToString(tempContAccNum))) {
											project.projectContractor[projectIndex] = tempContAccNum;
										}
									}
								}else if (project.personToChange[0] == 1) {
									
									tempContAccNum = person.setNewAccount("Contractor");
			
									if(tempContAccNum > 0){
										if(InputMethods.getUserConfirmation("Are your sure you want to change the project's assigned Contractor?\n" + 
												"\nOld Project Contractor: " + person.nameToString(project.projectContractor[projectIndex]) +
												"\nNew Project Contractor: " + person.nameToString(tempContAccNum))) {
											project.projectContractor[projectIndex] = tempContAccNum;
										}
									}
								}
								// executes if the user selects to edit the Customer	
							}else if (project.personToChange[1] == 3) {
								if (project.personToChange[0] == 2) {
									tempCustAccNum = person.getAccountNumber("Customer", false);
									
									if(tempCustAccNum > 0){
										if(InputMethods.getUserConfirmation("Are your sure you want to change the project's assigned Customer?\n" + 
												"\nOld Project Customer: " + person.nameToString(project.projectCustomer[projectIndex]) +
												"\nNew Project Customer: " + person.nameToString(tempCustAccNum))) {
											project.projectCustomer[projectIndex] = tempCustAccNum;
										}
									}
								}else if (project.personToChange[0] == 1) {	
									tempCustAccNum = person.setNewAccount("Customer");
									
									if(tempCustAccNum > 0){
										if(InputMethods.getUserConfirmation("Are your sure you want to change the project's assigned Customer?\n" + 
												"\nOld Project Customer: " + person.nameToString(project.projectCustomer[projectIndex]) +
												"\nNew Project Customer: " + person.nameToString(tempCustAccNum))) {
											project.projectCustomer[projectIndex] = tempCustAccNum;
										}
									}
								}
							}
						// executes if project.invoiceToCreate has been assigned a project index
						} else if (project.checkInvoice() >= 0) {
							while(project.checkInvoice() >=  0) {
								
								String cancelWarning = "Are you sure you want to cancel and return to the main menu?\n" +
										 			   "WARNING! The project will remain " + 
										 			   (project.isFinalized[project.checkInvoice()] ? "finalized":"not finalized") + 
										 			   " and an invoice will not be created!";
								
								String[] finalizeOPtions = { "Create and Save Invoice", "Cancel"};
			
								// selecting index 0 returns option to finalize / create an invoice
								// selecting index 1 returns option to cancel / unfinalize
								
								int confirmInvoice = JOptionPane.showOptionDialog(null,
										"The following invoice will be created and saved: \n" + 
										project.toInvoice(project.checkInvoice(), 
											             person.nameToString( project.projectArchitect[project.checkInvoice()]),
											             person.nameToString( project.projectContractor[project.checkInvoice()]),
											             person.nameToString( project.projectCustomer[project.checkInvoice()])),
									    "Poised Project Management",
									    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, finalizeOPtions, null);
								
								
								if (confirmInvoice == 1) {
									if (InputMethods.getUserConfirmation(cancelWarning)){
										project.resetInvoice();
									}
									
								} else if (confirmInvoice == 0) {
									// displays warning if user attempts to cancel
									String invoiceName = InputMethods.getUserString("Please enter a file name to save the invoice as:", 
										 	"No special characters can be used for the file name!", 
										 	"Completed project - " + project.projectName[project.checkInvoice()],
										 	 cancelWarning);
	
									// if the user cancels while entering a file name, the changes  will not be saved

									if(!invoiceName.isBlank()) {
			
										SaveDataIO.setToFile(project.toInvoice(project.checkInvoice(), 
												             person.nameToString( project.projectArchitect[project.checkInvoice()]),
												             person.nameToString( project.projectContractor[project.checkInvoice()]),
												             person.nameToString( project.projectCustomer[project.checkInvoice()])),
															 invoiceName);
										// resets project.invoiceToCreate to -1
										project.resetInvoice();
									// a blank invoice indicates the user canceled text entry
									}else if(invoiceName.isBlank()) {
										// resets project.invoiceToCreate to -1
										project.resetInvoice();
									}
								}							
							}
						}
					}
					// saves to file all project and person changes
					SaveDataIO.setToFile(project.getAllData(), "Project");
					SaveDataIO.setToFile(person.getAllData(), "Person");
					project.resetPeronnelEdit();
				}
			// "Update/Create Contact Profile" - used to edit existing user profiles or create new ones
			} else if (menuSelection.equals(mainMenuOptions[2])) {
				String profileSelection = person.setCreateOrEdit();

				if (profileSelection.equals("Create New Contact Profile")) {
					person.setNewAccount(Person.setPersonType());
					SaveDataIO.setToFile(person.getAllData(), "Person");
					
				} else if (profileSelection.equals("Edit Existing Contact Profile")) {
					person.editPersonAccount();
					SaveDataIO.setToFile(person.getAllData(), "Person");
				}
								
			// Settings
			} else if (menuSelection.equals(mainMenuOptions[3])) {
				
				int tempDateRange = InputMethods.getValidInt("Please enter the year max year range for due date creation.\n" +
															 "This setting will limit the maximum number of available years" +
															 "in addition to the current year, when creating a due date for projects",
															 "You need to enter a possitive numeric value.",
															 Integer.toString(yearRange));

				if(tempDateRange >= 0) {
					yearRange = tempDateRange;
					SaveDataIO.setToFile(Integer.toString(yearRange), "Settings");
				}
			}
		}
	}	
}
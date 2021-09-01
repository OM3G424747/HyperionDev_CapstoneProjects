import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

/**
 * Project Management System Class The class is the main class used to call
 * methods and other classes to create the project management system
 * 
 * The class loads the parameters of Project and Person classes and then
 * continues to loop until the user cancels
 *
 * @author Chris Joubert
 * @version 3.00, 01 September 2021
 */

public class ProjectManagementSystem {

	// project settings menu settings
	public static int yearRange = 30; // default year range for due date selection

	public static void main(String[] args)

	{

		// Initialized Project class
		Project project = new Project(0, "None", 0, "None", "None", "None", "None", "None", BigDecimal.ZERO,
				BigDecimal.ZERO, "None", "None");

		// Initialized Person class
		Person person = new Person("None", "None", "None", "None", "None", "None", "None", "None", "None", "None");
		// bool values used to determine if an error was encountered while loading saved
		// data

		// Initial login checks user's credentials
		// If login is canceled the app terminates
		// If login is failed an error is displayed and login loops over again
		// Clerk profile was added for future and potential

		String databaseURL = "jdbc:mysql://localhost:3306/";
		String username = "root";
		String password = "";

		// If userSignIn is set to false, the app will not continue to the main loop
		// If the required Database or Table are found the databaseExists and
		// tableExists bool are set to true
		// If the databaseExists and tableExists bool are kept on false, the app will
		// create the database and table before proceeding
		boolean userSignIn = true;
		boolean databaseExists = false;
		boolean[] tableExists = { false, false, false, false };

		// used to confirm Database is correct before proceeding
		boolean dbConfirmed = false;

		while (userSignIn) {
			password = "";

			// UserPasswordEnter is set to 1 until the user selects "OK"
			// After okay is selected it's switched to 0 and the app continues to attempt to
			// login with the users's credentials
			int userPasswordEnter = 1;
			while (userPasswordEnter == 1) {
				JPanel passPanel = new JPanel();
				JLabel passLabel = new JLabel("Enter a password:", SwingConstants.CENTER);

				// Sets size of password field and label
				JPasswordField passField = new JPasswordField(10);
				passPanel.add(passLabel);
				passPanel.add(passField);
				String[] options = new String[] { "OK", "Cancel" };
				userPasswordEnter = JOptionPane.showOptionDialog(null, passPanel, "Poised Project Management",
						JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				char[] passChars = passField.getPassword();

				// Casts password to string
				password = String.valueOf(passChars);

				// If the user selects to cancel, userPasswordEnter will remain on 1 and ask the
				// user to verify
				// If the user cancels again the password request will loop again
				// Alternatively the loop will end the app will terminate
				if (userPasswordEnter == 1) {
					if (InputMethods.getUserConfirmation("Are you sure you want to exit the app?")) {
						userSignIn = false;
						break;

					}
				}
			}

			// Attempts to login with the passed credentials
			// If the login is successful, it's confirmed if the database exists, if it
			// does, the connection is closed and the Database is logged into
			// If it doesn't the database is created and then signed into
			if (userSignIn) {
				try {
					Connection connection = DriverManager.getConnection(databaseURL, username, password);
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery("SHOW DATABASES");
					while (resultSet.next()) {

						String currentDB = resultSet.getString(1);

						// Check for correct database in existing list
						if (currentDB.equals("poisepms")) {
							// Confirms the database exists
							databaseExists = true;
						}
					}

					// Creates database if it doesn't exist
					if (!databaseExists) {

						String poiseDB = "CREATE DATABASE PoisePMS";
						statement.executeUpdate(poiseDB);

					}

					// Close all current connections and end the loop.
					resultSet.close();
					statement.close();
					connection.close();
					break;

					// Catch throws exceptions if the user enters an incorrect password
				} catch (SQLException e) {

					// User receives the following error indicating their login was not successful
					JOptionPane.showMessageDialog(null,
							"Invalid Password\n" + (password.isBlank() ? "Password cannot be empty\n" : "")
									+ "Please try again or contact you system admin for more assistance",
							"Poised Project Management", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		// Sign in to the database if login was valid
		// The app then confirms if the poisepms database exists and if it doesn't it
		// creates it with the default values
		if (userSignIn) {
			// Reconnect and connect to database
			databaseURL = "jdbc:mysql://localhost:3306/poisepms?useSSL=false";
			try {

				// Creates a direct line to the database for running our queries
				Connection connection = DriverManager.getConnection(databaseURL, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SHOW TABLES");
				int dataBaseArrayCount = 0;
				String[] currentDB = new String[4];
				// Confirms the database exists
				while (resultSet.next()) {

					currentDB[dataBaseArrayCount] = resultSet.getString(1);
					dataBaseArrayCount++;

				}

				if (currentDB[0] != null) {

					if (currentDB[0].equals("acc_info")) {

						// Sets bool to true if database exists
						// If the table exists creation is skipped and existing values are loaded
						tableExists[0] = true;
					}

					if (currentDB[1].equals("address_info")) {

						// Sets bool to true if database exists
						// If the table exists creation is skipped and existing values are loaded
						tableExists[1] = true;
					}

					if (currentDB[2].equals("cust_info")) {

						// Sets bool to true if database exists
						// If the table exists creation is skipped and existing values are loaded
						tableExists[2] = true;
					}

					if (currentDB[3].equals("proj_info")) {

						// Sets bool to true if database exists
						// If the table exists creation is skipped and existing values are loaded
						tableExists[3] = true;
					}
				}

				if (!tableExists[0] || !tableExists[1] || !tableExists[2] || !tableExists[3]) {

					if (InputMethods
							.getUserConfirmation("The follow tables were not found and will need to be created:\n"
									+ (tableExists[3] ? "" : "\n -  proj_info for Project Info")
									+ (tableExists[2] ? "" : "\n -  cust_info for Customer Info")
									+ (tableExists[1] ? "" : "\n -  address_info for Address Informaion")
									+ (tableExists[0] ? "" : "\n -  acc_info for Account information")
									+ "\n\nIf you already have saved data for these, please close the app restore the save data and try again\n"
									+ "Alternatively new data tables will be created with no data, however, this could cause anomalies\n"
									+ "Would you like to proceed in creating the required tables?")) {

						// Creates database if it doesn't exist and the bool was not set to true
						if (!tableExists[3]) {
							try {

								String projTable = "CREATE TABLE proj_info ( proj_num int, erf_num int, address_id int, due_date date, finalize_date date, engineer_id int, manager_id int, architect_id int, primary key (proj_num))";
								statement.executeUpdate(projTable);

							} catch (SQLException e) {

								JOptionPane.showMessageDialog(null,
										"Unable to create proj_num table\n"
												+ "Please contact your system admin for more assistance",
										"Poised Project Management", JOptionPane.ERROR_MESSAGE);
							}
						}

						// Creates database if it doesn't exist and the bool was not set to true
						if (!tableExists[2]) {
							try {

								String custTable = "CREATE TABLE cust_info ( proj_num int, proj_name varchar(50), build_type varchar(50), cost decimal(20,2), paid decimal(20,2), customer_id int, primary key (proj_num))";
								statement.executeUpdate(custTable);

							} catch (SQLException e) {

								JOptionPane.showMessageDialog(null,
										"Unable to create cust_info table\n"
												+ "Please contact your system admin for more assistance",
										"Poised Project Management", JOptionPane.ERROR_MESSAGE);
							}
						}

						// Creates database if it doesn't exist and the bool was not set to true
						if (!tableExists[1]) {
							try {

								String addressTable = "CREATE TABLE address_info ( address_id int, country varchar(50), region varchar(50), city varchar(50), street varchar(50), post_code varchar(5), primary key (address_id))";
								statement.executeUpdate(addressTable);

							} catch (SQLException e) {

								JOptionPane.showMessageDialog(null,
										"Unable to create address_info table\n"
												+ "Please contact you system admin for more assistance",
										"Poised Project Management", JOptionPane.ERROR_MESSAGE);
							}
						}

						// Creates database if it doesn't exist and the bool was not set to true
						if (!tableExists[0]) {
							try {

								String accTable = "CREATE TABLE acc_info ( account_id int, account_type varchar(50), first_name varchar(50), last_name varchar(50), email varchar(50),  phone_num varchar(20), address_id int, primary key (account_id))";
								statement.executeUpdate(accTable);

							} catch (SQLException e) {

								JOptionPane.showMessageDialog(null,
										"Unable to create acc_info table\n"
												+ "Please contact you system admin for more assistance",
										"Poised Project Management", JOptionPane.ERROR_MESSAGE);
							}
						}

						// confirms all required tables are present before proceeding
						dbConfirmed = true;
					}

				} else {

					// confirms all required tables are present before proceeding
					dbConfirmed = true;
				}

			} catch (SQLException e) {

				// User receives the following error indicating their login was not successful
				JOptionPane.showMessageDialog(null,
						"Could not create table\n" + "Please try again or contact you system admin for more assistance",
						"Poised Project Management", JOptionPane.ERROR_MESSAGE);
			}
		}

		// if the databse exists and all tables are created the app will proceed here to
		// load data from the databases
		if (dbConfirmed) {

			try {
				Connection connection = DriverManager.getConnection(databaseURL, username, password);
				Statement statement = connection.createStatement();

				// bool values used to determine if an error was encountered while loading saved
				// data
				boolean loadProjectSuccess;
				boolean loadPersonSuccess;

				// loads project saved data
				try {
					person.getSavedData(SaveDataIO.getSavedData(statement, "Person"));
					loadPersonSuccess = true;
				} catch (ArrayIndexOutOfBoundsException e) {
					loadPersonSuccess = false;
					e.printStackTrace();
				}

				// loads project saved data
				try {
					project.getSavedData(SaveDataIO.getSavedData(statement, "Project"));
					loadProjectSuccess = true;
				} catch (ArrayIndexOutOfBoundsException e) {
					loadProjectSuccess = false;
				}

				if (!loadProjectSuccess || !loadPersonSuccess) {

					JOptionPane.showMessageDialog(null, "The saved data for the following items were not loaded:\n"
							+ (loadProjectSuccess ? "" : "\n -  Projects Saved Data")
							+ (loadPersonSuccess ? "" : "\n -  Accounts Profile Saved Data")
							+ "\n\nIf you already have saved data for these, please close the app restore the save data and try again\n"
							+ "Alternatively please contact your system admin", "Poised Project Management",
							JOptionPane.ERROR_MESSAGE);
				}

				// Sets Main Menu Options
				// add edit project option and refactor code for items belonging to the setting
				String[] mainMenuOptions = { "Create New Project", "Lookup / Edit Projects",
						"Update/Create Contact Profile", "Settings" };
				// sets main menu image
				ImageIcon menuIcon = new ImageIcon("assets/menu.png");
				// sets bool to continue looping the app till the user cancels
				Boolean isAwaitingInput = true;
				// continues to loop is creating project till the user cancels
				Boolean isCreatingNewProject = false;

				// main app loop
				while (isAwaitingInput) {

					String menuSelection = (String) JOptionPane.showInputDialog(null, "Please Select Your Task:\n\n",
							"Poised Project Management", JOptionPane.INFORMATION_MESSAGE, menuIcon, mainMenuOptions,
							"Create New Project");

					// Close App Selection
					if (menuSelection == null) {
						if (InputMethods.getUserConfirmation("Are you sure you want to close the app?")) {
							break;
						}

						// Create New Project
					} else if (menuSelection.equals(mainMenuOptions[0])) {
						isCreatingNewProject = true;

						while (isCreatingNewProject) {

							// sets the Architect that will be assigned to the project
							// canceling returns user to main menu instantly
							int manaAccNum = person.getAccountNumber(statement, "Manager", false);
							if (manaAccNum <= 0) {
								break;

							}
							// saves personnel account info
							int manaIndex = person.getAccountIndex(manaAccNum);
							SaveDataIO.setToDatabase(statement, person.getAllData(manaIndex), "Person");

							// sets the Architect that will be assigned to the project
							// canceling returns user to main menu instantly
							int archAccNum = person.getAccountNumber(statement, "Architect", false);
							if (archAccNum <= 0) {
								break;

							}
							// saves personnel account info
							int archIndex = person.getAccountIndex(archAccNum);
							SaveDataIO.setToDatabase(statement, person.getAllData(archIndex), "Person");
							// sets the Contractor that will be assigned to the project
							// canceling returns user to main menu instantly
							int contAccNum = person.getAccountNumber(statement, "Engineer", false);
							if (contAccNum <= 0) {
								break;

							}
							// saves personnel account info
							int contIndex = person.getAccountIndex(contAccNum);
							SaveDataIO.setToDatabase(statement, person.getAllData(contIndex), "Person");
							// sets the "customer that will be assigned to the project
							// canceling returns user to main menu instantly
							int custAccNum = person.getAccountNumber(statement, "Customer", false);
							if (custAccNum <= 0) {
								break;

							}

							// saves personnel account info
							int custIndex = person.getAccountIndex(custAccNum);
							SaveDataIO.setToDatabase(statement, person.getAllData(custIndex), "Person");
							// assigns the selected Architect, Contractor and Customer account numbers,
							int tempProjectNum = project.setNewProject(statement, manaAccNum, archAccNum, contAccNum,
									custAccNum, person.getLastName(custAccNum));
							if (tempProjectNum < 0) {
								break;

							}

							// saves project data to relevant tables
							SaveDataIO.setToDatabase(statement, project.getAllData(tempProjectNum), "Project");
							isCreatingNewProject = false;
						}

						// Lookup / Edit Projects
					} else if (menuSelection.equals(mainMenuOptions[1])) {

						// set's value for index of personel account to be updated
						int tempAccNum = -1;

						// displays error if no projects are currently created
						if (project.deadLine[0].equals("None")) {
							JOptionPane.showMessageDialog(null,
									"No projects currently available!\n"
											+ "Please create a new project to use this feature",
									"Poised Project Management", JOptionPane.ERROR_MESSAGE);
						} else {

							int projectIndex = -1;
							int personAccID;

							// loops until a project is selected or the user cancels
							while (projectIndex < 0) {

								// sets search icon for account lookup
								ImageIcon searchIcon = new ImageIcon("assets/search.png");

								String[] lookUpMethod = { "View All OverDue Projects", "View All Uncompleted Projects",
										"Search" };
								String lookUpSelection = (String) JOptionPane.showInputDialog(null,
										"Please select a lookup method:\n\n", "Poised Project Management",
										JOptionPane.PLAIN_MESSAGE, searchIcon, lookUpMethod, lookUpMethod[2]);

								// null indicates the user canceled
								if (lookUpSelection == null) {
									if (InputMethods.getUserConfirmation(
											"Are you sure you'd like to return to the main menu?")) {
										break;
									}

									// view all overdue projects that are also unfinished
								} else if (lookUpSelection.equals(lookUpMethod[0])) {
									projectIndex = project.getOverDueSelection();

									// view unfinished projects
								} else if (lookUpSelection.equals(lookUpMethod[1])) {
									projectIndex = project.getUnfinishedProjects();

									// search for projects
								} else if (lookUpSelection.equals(lookUpMethod[2])) {

									// selection for searching by user input
									String[] searchOptions = { "Search by Project Name", "Search by Project Number",
											"Search by Project's Associated Personnel Phone Number",
											"Search by Project's Associated Personnel Email" };

									String searchSelection = (String) JOptionPane.showInputDialog(null,
											"Please Select A Project Search Method:\n\n", "Poised Project Management",
											JOptionPane.PLAIN_MESSAGE, null, searchOptions, "Create New Project");

									// null if the user selected to cancel the search
									if (searchSelection == null) {
										if (InputMethods.getUserConfirmation(
												"Are you sure you'd like to return to the main menu?")) {
											break;
										}

										// returns true if "Search by Project Name" was selected
									} else if (searchSelection.equals(searchOptions[0])) {
										projectIndex = InputMethods.getSearchIndex(project.projectName,
												project.getAccountListValues());

										// returns true if "Search by Project Number" was selected
									} else if (searchSelection.equals(searchOptions[1])) {
										projectIndex = InputMethods.getSearchIndex(project.projectNum,
												project.getAccountListValues());

										// returns true if "Search by Project Client Phone Number" was selected
									} else if (searchSelection.equals(searchOptions[2])) {
										personAccID = InputMethods.getSearchIndex(person.phoneNumber, person.firstName);
										// uses account ID to lookup associated projects
										if (personAccID >= 0) {
											projectIndex = project.getAccountNumProjects(person.accountNum[personAccID],
													person.firstName[personAccID]);
										}
										// returns true if "Search by Project Client Email" was selected
									} else if (searchSelection.equals(searchOptions[3])) {
										personAccID = InputMethods.getSearchIndex(person.email, person.firstName);
										// uses account ID to lookup associated projects
										if (personAccID >= 0) {
											projectIndex = project.getAccountNumProjects(person.accountNum[personAccID],
													person.firstName[personAccID]);
										}
									}
								}
							}
							// this passes the selected account to edit it's values
							if (projectIndex >= 0) {
								project.editProject(projectIndex);
								if (project.editPersonnelChecker()) {

									// first value (INDEX 0) equals what to replace it with ( 1 = New Contact, 2 =
									// existing contact)
									// second value (INDEX 1) equals type ( 1 = Manager, 2 = Architect, 3 =
									// Engineer, 4 = Customer )

									// executes if the user selects to edit the Architect
									if (project.personToChange[1] == 1) {
										if (project.personToChange[0] == 2) {
											tempAccNum = person.getAccountNumber(statement, "Manager", false);
											if (tempAccNum > 0) {
												if (InputMethods.getUserConfirmation(
														"Are your sure you want to change the project's assigned Architect?\n"
																+ "\nOld Project Manager: "
																+ person.nameToString(
																		project.projectManager[projectIndex])
																+ "\nNew Project Manager: "
																+ person.nameToString(tempAccNum))) {
												}
												project.projectArchitect[projectIndex] = tempAccNum;
											}
										} else if (project.personToChange[0] == 1) {
											tempAccNum = person.setNewAccount(statement, "Manager");
											if (tempAccNum > 0) {
												if (InputMethods.getUserConfirmation(
														"Are your sure you want to change the project's assigned Architect?\n"
																+ "\nOld Project Architect: "
																+ person.nameToString(
																		project.projectArchitect[projectIndex])
																+ "\nNew Project Architect: "
																+ person.nameToString(tempAccNum))) {

													project.projectArchitect[projectIndex] = tempAccNum;
												}
											}
										}

										// executes if the user selects to edit the Architect
									} else if (project.personToChange[1] == 2) {
										if (project.personToChange[0] == 2) {
											tempAccNum = person.getAccountNumber(statement, "Architect", false);
											if (tempAccNum > 0) {
												if (InputMethods.getUserConfirmation(
														"Are your sure you want to change the project's assigned Architect?\n"
																+ "\nOld Project Architect: "
																+ person.nameToString(
																		project.projectArchitect[projectIndex])
																+ "\nNew Project Architect: "
																+ person.nameToString(tempAccNum))) {
													project.projectArchitect[projectIndex] = tempAccNum;
												}
											}
										} else if (project.personToChange[0] == 1) {
											tempAccNum = person.setNewAccount(statement, "Architect");
											if (tempAccNum > 0) {
												if (InputMethods.getUserConfirmation(
														"Are your sure you want to change the project's assigned Architect?\n"
																+ "\nOld Project Architect: "
																+ person.nameToString(
																		project.projectArchitect[projectIndex])
																+ "\nNew Project Architect: "
																+ person.nameToString(tempAccNum))) {

													project.projectArchitect[projectIndex] = tempAccNum;
												}
											}
										}
										// executes if the user selects to edit the Engineer
									} else if (project.personToChange[1] == 3) {
										if (project.personToChange[0] == 2) {

											tempAccNum = person.getAccountNumber(statement, "Engineer", false);

											if (tempAccNum > 0) {
												if (InputMethods.getUserConfirmation(
														"Are your sure you want to change the project's assigned Engineer?\n"
																+ "\nOld Project Engineer: "
																+ person.nameToString(
																		project.projectContractor[projectIndex])
																+ "\nNew Project Engineer: "
																+ person.nameToString(tempAccNum))) {
													project.projectContractor[projectIndex] = tempAccNum;
												}
											}
										} else if (project.personToChange[0] == 1) {

											tempAccNum = person.setNewAccount(statement, "Engineer");

											if (tempAccNum > 0) {
												if (InputMethods.getUserConfirmation(
														"Are your sure you want to change the project's assigned Engineer?\n"
																+ "\nOld Project Engineer: "
																+ person.nameToString(
																		project.projectContractor[projectIndex])
																+ "\nNew Project Engineer: "
																+ person.nameToString(tempAccNum))) {
													project.projectContractor[projectIndex] = tempAccNum;
												}
											}
										}
										// executes if the user selects to edit the Customer
									} else if (project.personToChange[1] == 4) {
										if (project.personToChange[0] == 2) {
											tempAccNum = person.getAccountNumber(statement, "Customer", false);

											if (tempAccNum > 0) {
												if (InputMethods.getUserConfirmation(
														"Are your sure you want to change the project's assigned Customer?\n"
																+ "\nOld Project Customer: "
																+ person.nameToString(
																		project.projectCustomer[projectIndex])
																+ "\nNew Project Customer: "
																+ person.nameToString(tempAccNum))) {
													project.projectCustomer[projectIndex] = tempAccNum;
												}
											}
										} else if (project.personToChange[0] == 1) {
											tempAccNum = person.setNewAccount(statement, "Customer");

											if (tempAccNum > 0) {
												if (InputMethods.getUserConfirmation(
														"Are your sure you want to change the project's assigned Customer?\n"
																+ "\nOld Project Customer: "
																+ person.nameToString(
																		project.projectCustomer[projectIndex])
																+ "\nNew Project Customer: "
																+ person.nameToString(tempAccNum))) {
													project.projectCustomer[projectIndex] = tempAccNum;
												}
											}
										}
									}
									// executes if project.invoiceToCreate has been assigned a project index
								} else if (project.checkInvoice() >= 0) {
									while (project.checkInvoice() >= 0) {

										String cancelWarning = "Are you sure you want to cancel and return to the main menu?\n"
												+ "WARNING! The project will remain "
												+ (project.completionDate[project.checkInvoice()]
														.equals("Not Finzalized Yet") ? "finalized" : "not finalized")
												+ " and an invoice will not be created!";

										String[] finalizeOPtions = { "Create and Save Invoice", "Cancel" };

										// selecting index 0 returns option to finalize / create an invoice
										// selecting index 1 returns option to cancel / unfinalize

										int confirmInvoice = JOptionPane.showOptionDialog(null,
												"The following invoice will be created and saved: \n"
														+ project.toInvoice(statement, project.checkInvoice()),
												"Poised Project Management", JOptionPane.YES_NO_OPTION,
												JOptionPane.INFORMATION_MESSAGE, null, finalizeOPtions, null);

										if (confirmInvoice == 1) {
											if (InputMethods.getUserConfirmation(cancelWarning)) {
												project.resetInvoice();
											}

										} else if (confirmInvoice == 0) {
											// displays warning if user attempts to cancel
											String invoiceName = InputMethods.getUserString(
													"Please enter a file name to save the invoice as:",
													"No special characters can be used for the file name!",
													"Completed project - "
															+ project.projectName[project.checkInvoice()],
													cancelWarning);

											// if the user cancels while entering a file name, the changes will not be
											// saved
											if (!invoiceName.isBlank()) {

												SaveDataIO.setToFile(
														project.toInvoice(statement, project.checkInvoice()),
														invoiceName);
												// resets project.invoiceToCreate to -1
												project.resetInvoice();
												// a blank invoice indicates the user canceled text entry
											} else if (invoiceName.isBlank()) {
												// resets project.invoiceToCreate to -1
												project.resetInvoice();
											}
										}
									}
								}
							}

							if (tempAccNum >= 0) {

								// get the index of the account number to save the new account details
								int accountIndex = person.getAccountIndex(tempAccNum);
								if (accountIndex >= 0) {
									// saves any newly created personnel accounts
									SaveDataIO.setToDatabase(statement, person.getAllData(accountIndex), "Person");
								}
							}

							if (projectIndex >= 0) {
								// saves any changes made to projects
								SaveDataIO.setToDatabase(statement, project.getAllData(projectIndex), "Project");
							}

							project.resetPeronnelEdit();
						}

						// "Update/Create Contact Profile" - used to edit existing user profiles or
						// create new ones
					} else if (menuSelection.equals(mainMenuOptions[2])) {
						String profileSelection = person.setCreateOrEdit();

						if (profileSelection.equals("Create New Contact Profile")) {
							int newPersonNum = person.setNewAccount(statement, Person.setPersonType());

							if (newPersonNum >= 0) {
								int accountIndex = person.getAccountIndex(newPersonNum);
								SaveDataIO.setToDatabase(statement, person.getAllData(accountIndex), "Person");
							}

						} else if (profileSelection.equals("Edit Existing Contact Profile")) {
							int newPersonNum = person.editPersonAccount(statement);
							if (newPersonNum >= 0) {

								int accountIndex = person.getAccountIndex(newPersonNum);
								SaveDataIO.setToDatabase(statement, person.getAllData(accountIndex), "Person");
							}
						}

						// Settings
					} else if (menuSelection.equals(mainMenuOptions[3])) {

						int tempDateRange = InputMethods.getValidInt(
								"Please enter the year max year range for due date creation.\n"
										+ "This setting will limit the maximum number of available years"
										+ "in addition to the current year, when creating a due date for projects",
								"You need to enter a possitive numeric value.", Integer.toString(yearRange));

						if (tempDateRange >= 0) {
							yearRange = tempDateRange;
						}
					}
				}

				// returns any errors that occur during the main loop
			} catch (SQLException e) {

				e.printStackTrace();

			}
		}

	}
}
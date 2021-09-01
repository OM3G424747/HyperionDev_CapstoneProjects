import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Project Class The class is used manage project information Each parameter of
 * the project class stores an array to allow for project details New entries
 * are appended to the end of each array at the last index value
 * 
 * Each project is automatically assigned as false for for finalized Similarly
 * each project is assigned a completions date after being completed
 *
 * @author Chris Joubert
 * @version 3.00, 01 September 2021
 */

public class Project {

	// the following variables are assigned to the project class on initialization
	// for the Architect, Contractor and Customer are assigned an account number.
	// the account number is called when contact information is needed on either one
	// of the three persons
	// when projects are created, the finalized variable is set to false by default
	int[] projectNum;
	String[] buildingType;
	int[] erfNum;
	String[] street;
	String[] city;
	String[] region;
	String[] country;
	String[] postalCode;
	BigDecimal[] totalCost;
	BigDecimal[] totalPaid;
	String[] deadLine;
	String[] completionDate;
	int[] projectManager;
	int[] projectArchitect;
	int[] projectContractor;
	int[] projectCustomer;
	String[] projectName;
	int[] addressID;

	// stores the values for which person to change
	// first value (INDEX 0) equals what to replace it with ( 1 = New Contact, 2 =
	// existing contact)
	// second value (INDEX 1) equals type ( 1 = Manager, 2 = Architect, 3 =
	// Engineer, 4 = Customer )
	int[] personToChange = { 0, 0 };

	// int to store the index number of the project that needs an invoice to be
	// created
	// -1 means no project has been recently finalized or no project needs an
	// invoice
	int invoiceToCreate = -1;

	/**
	 *
	 * Person Class Initialization Method <br>
	 * The method sets the initial values for the person class Account number is
	 * initialized a 1 and cannot be changed by the end user If initial account
	 * values are unknown this should be initialized as "none"
	 *
	 * @param newProjectNum   int contains the project number for the starting value
	 * @param newBuildingType String contains the building type for the starting
	 *                        value
	 * @param newERFNum       int contains the ERF number for the starting value
	 * @param street          String contains the street for the starting value
	 * @param city            String contains the city for the starting value
	 * @param region          String contains the province for the starting value
	 * @param country         String contains the country for the starting value
	 * @param postalCode      String contains the postal code for the starting value
	 * @param newTotalCost    BigDecimal contains the Total Cost for the starting
	 *                        value
	 * @param newTotalPaid    BigDecimal contains the Total Paid for the starting
	 *                        value
	 * @param newDeadLine     String contains the Due Date for the starting value
	 * @param newProjectName  String contains the project name for the starting
	 *                        value
	 * @see setNewProject
	 * @since version 1.00
	 */
	public Project(int newProjectNum, String newBuildingType, int newERFNum, String street, String city, String region,
			String country, String postalCode, BigDecimal newTotalCost, BigDecimal newTotalPaid, String newDeadLine,
			String newProjectName)

	{
		this.projectNum = ArrayMethods.addToIntArray(this.projectNum, newProjectNum);
		this.buildingType = ArrayMethods.addToStringArray(this.buildingType, newBuildingType);
		this.erfNum = ArrayMethods.addToIntArray(this.erfNum, newERFNum);
		this.street = ArrayMethods.addToStringArray(this.street, street);
		this.city = ArrayMethods.addToStringArray(this.city, city);
		this.region = ArrayMethods.addToStringArray(this.region, region);
		this.country = ArrayMethods.addToStringArray(this.country, country);
		this.postalCode = ArrayMethods.addToStringArray(this.postalCode, postalCode);
		this.totalCost = ArrayMethods.addToBigDecimalArray(this.totalCost, newTotalCost);
		this.totalPaid = ArrayMethods.addToBigDecimalArray(this.totalPaid, newTotalPaid);
		this.deadLine = ArrayMethods.addToStringArray(this.deadLine, newDeadLine);
		this.completionDate = ArrayMethods.addToStringArray(this.completionDate, "00-00-0000");
		this.projectManager = ArrayMethods.addToIntArray(this.projectManager, 0);
		this.projectArchitect = ArrayMethods.addToIntArray(this.projectArchitect, 0);
		this.projectContractor = ArrayMethods.addToIntArray(this.projectContractor, 0);
		this.projectCustomer = ArrayMethods.addToIntArray(this.projectCustomer, 0);
		this.projectName = ArrayMethods.addToStringArray(this.projectName, newProjectName);
		this.addressID = ArrayMethods.addToIntArray(this.addressID, 0);
	}

	/**
	 *
	 * Edit Project Method <br>
	 * Method used to edit current projects values The method selects a project
	 * based on the passed index number and then allows the user to edit a selected
	 * parameter of that project If no project current exists the user is asked to
	 * first create a project
	 *
	 * @param projectIndex int contains the index number of the project to be edited
	 * @since version 2.00
	 */
	public void editProject(int projectIndex) {
		ImageIcon menuIcon = new ImageIcon("assets/edit.png");
		String[] editOptions = { "Edit Project Name", "Due Date", "Amount Paid", "Change Project Address",
				"Finalize / Unfinalize", "Change Assigned Personnel", "View Project / Create Invoice" };

		// if no projects exist the user is given an error message and returned to the
		// main menu
		if (this.projectName[0].equals("None")) {
			JOptionPane.showMessageDialog(null,
					"No projects are currently available\n" + "Please create a new project first",
					"Poised Project Management", JOptionPane.ERROR_MESSAGE);
			// if at least 1 project has been created the user is allowed to select the
			// project they would like to edit
		} else {
			String parameterToEdit = (String) JOptionPane.showInputDialog(null,
					"Please select a what you'd like to edit:\n\n", "Poised Project Management",
					JOptionPane.PLAIN_MESSAGE, menuIcon, editOptions, editOptions[0]);

			// if the user cancels while selecting a project they are returned to the main
			// menu
			if (parameterToEdit == null) {
				return;

				// alternatively the project index is selected based on the user's input
				// the index is then passed and the user is asked to edit the parameter of the
				// project on that index
			} else {

				// sets a new due date for the user on the selected project
				if (parameterToEdit.equals("Due Date")) {
					String tempDeadLine = DateMethods.setDate(ProjectManagementSystem.yearRange);
					// confirms a value has been entered and the user didn't cancel
					if (!tempDeadLine.isBlank()) {

						if (InputMethods
								.getUserConfirmation("Are your sure you want to change the project's due date?\n"
										+ "\nOld Project Due Date: " + this.deadLine[projectIndex]
										+ "\nNew Project Due Date: " + tempDeadLine)) {

							this.deadLine[projectIndex] = tempDeadLine;
						}
					}
					// sets a new amount paid for the user on the selected project
				} else if (parameterToEdit.equals("Edit Project Name")) {

					String tempProjectName = InputMethods.getUserString(
							"Please enter a new name for project: " + this.projectName[projectIndex],
							"You need to enter a new project name to continue", this.projectName[projectIndex],
							"Are you sure you'd like to cancel entering a new project name?");

					// confirms a value has been entered and the user didn't cancel
					if (tempProjectName == null || tempProjectName.isBlank()) {
						return;

					} else {
						if (InputMethods.getUserConfirmation(
								"Are your sure you want to change the project name?\n" + "\nOld Project Name: "
										+ this.projectName[projectIndex] + "\nNew Project Name: " + tempProjectName)) {
							this.projectName[projectIndex] = tempProjectName;
						}
					}
					// sets a new amount paid for the user selected project
				} else if (parameterToEdit.equals("Amount Paid")) {
					BigDecimal tempTotalPaid = InputMethods.getValidBigDecimal("Total value of project: R"
							+ this.totalCost[projectIndex] + "\nTotal currently outstanding: R"
							+ this.totalCost[projectIndex].subtract(this.totalPaid[projectIndex])
							+ "\nTotal currently paid towards the project to date: R" + this.totalPaid[projectIndex]
							+ "\n\nPlease enter the new total paid towards the project to date",
							"Please enter a number using numeric values only!",
							this.totalPaid[projectIndex].toString());
					// confirms a value has been entered and the user didn't cancel and the value is
					// not negative
					if (tempTotalPaid.compareTo(BigDecimal.ZERO) >= 0) {

						// confirms the user changes to take effect
						if (InputMethods.getUserConfirmation(
								"Are your sure you want to change the current total Paid?\n" + "\nOld Total Paid: R"
										+ this.totalPaid[projectIndex] + "\nNew Total Paid: R" + tempTotalPaid)) {

							this.totalPaid[projectIndex] = tempTotalPaid;
							if (this.totalPaid[projectIndex].compareTo(this.totalCost[projectIndex]) >= 0) {

								this.completionDate[projectIndex] = DateMethods.getTodaysDate();
							}
						}
					}
					// sets the the address for the selected project
				} else if (parameterToEdit.equals("Change Project Address")) {
					String tempStreet = InputMethods.getUserString(
							"Please enter the new street address for: " + this.projectName[projectIndex],
							"You need to enter a street address in order to continue", this.street[projectIndex],
							"Are you sure you want to cancel entering a new address?");
					// confirms a value has been entered and the user didn't cancel
					if (tempStreet.isBlank()) {
						return;
					}
					String tempCity = InputMethods.getUserString(
							"Please enter the new City location for: " + this.projectName[projectIndex],
							"You need to enter a City name in order to continue", this.city[projectIndex],
							"Are you sure you want to cancel entering a new address?");
					// confirms a value has been entered and the user didn't cancel
					if (tempCity.isBlank()) {
						return;
					}
					String tempRegion = InputMethods.getUserString(
							"Please enter the new Province location for: " + this.projectName[projectIndex],
							"You need to enter a Province name in order to continue", this.region[projectIndex],
							"Are you sure you want to cancel entering a new address?");
					// confirms a value has been entered and the user didn't cancel
					if (tempRegion.isBlank()) {
						return;
					}
					String tempCountry = InputMethods.getUserString(
							"Please enter the new Country location for: " + this.projectName[projectIndex],
							"You need to enter a new Country location for the project", this.country[projectIndex],
							"Are you sure you want to cancel entering a new address?");
					// confirms a value has been entered and the user didn't cancel
					if (tempCountry.isBlank()) {
						return;
					}
					String tempPostalCode = InputMethods.getPostalCode(this.buildingType[projectIndex]);
					// confirms a value has been entered and the user didn't cancel
					if (tempPostalCode.isBlank()) {
						return;
					}

					// confirms the user's selection before applying it
					if (InputMethods.getUserConfirmation("The project address for " + this.projectName[projectIndex]
							+ " will be changed to:\n\n" + "Street: " + tempStreet + "\n" + "City: " + tempCity + "\n"
							+ "Province: " + tempRegion + "\n" + "Country: " + tempCountry + "\n" + "Postal Code: "
							+ tempPostalCode + "\n\n" + "Are you sure you want to proceed?")) {

						this.street[projectIndex] = tempStreet;
						this.city[projectIndex] = tempCity;
						this.region[projectIndex] = tempRegion;
						this.country[projectIndex] = tempCountry;
						this.postalCode[projectIndex] = tempPostalCode;
					}

					// sets the selected project to be finalized or unfinalized
				} else if (parameterToEdit.equals("Finalize / Unfinalize")) {
					// sets options for finalize project option pane
					String[] finalizeOPtions = {
							(!this.completionDate[projectIndex].equals("00-00-0000") ? "Create invoice"
									: "Set as finalized"),
							(!this.completionDate[projectIndex].equals("00-00-0000") ? " Set as not finalized"
									: "Cancel") };

					// selecting index 0 returns option to finalize / create an invoice
					// selecting index 1 returns option to cancel / unfinalize
					int selectAction = JOptionPane.showOptionDialog(null,
							"Would you like to mark the project as finalized?\n", "Poised Project Management",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, finalizeOPtions, null);

					// finalizes project
					if (selectAction == 0) {
						this.completionDate[projectIndex] = DateMethods.getTodaysDate();
						// uses big decimal comparison to confirm if the project has been fully paid for
						// if it's fully paid, the method will return 0 if more than the total is paid
						// -1 will be returned
						if (this.totalPaid[projectIndex].compareTo(this.totalCost[projectIndex]) < 0) {
							invoiceToCreate = projectIndex;
						}

						// sets project as not finalized
					} else if (selectAction == 1) {
						this.completionDate[projectIndex] = "00-00-0000";

					}

					// sets to create an invoice for the selected project
				} else if (parameterToEdit.equals("View Project / Create Invoice")) {
					invoiceToCreate = projectIndex;
					return;

					// sets new assigned personnel for the selected project
				} else if (parameterToEdit.equals("Change Assigned Personnel")) {

					// selection for available account types to change
					String[] personOptions = { "Manager", "Architect", "Engineer", "Customer" };
					String selectPersonType = (String) JOptionPane.showInputDialog(null,
							"Please select which personnel type you'd like to change:\n\n", "Poised Project Management",
							JOptionPane.PLAIN_MESSAGE, null, personOptions, personOptions[0]);

					if (selectPersonType == null) {
						return;

					} else {

						// requests user if they want to replace the personnel with an existing profile
						// or a new one
						String[] selectionMSGOptions = { "Create new profile and replace",
								"Replace with existing profile" };
						String messageOption = "Which action would you like to complete";

						// method returns 0 for "Create New Profile & 1 for Select Existing Profile
						// returns -1 if cancel is selected
						int selectAction = JOptionPane.showOptionDialog(null, messageOption,
								"Poised Project Management", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
								null, selectionMSGOptions, null);

						if (selectAction == -1) {
							return;

							// create new profile and replace
						} else if (selectAction == 0) {
							this.personToChange[0] = 1;

							if (selectPersonType.equals("Manager")) {
								this.personToChange[1] = 1;

							} else if (selectPersonType.equals("Architect")) {
								this.personToChange[1] = 2;

							} else if (selectPersonType.equals("Engineer")) {
								this.personToChange[1] = 3;

							} else if (selectPersonType.equals("Customer")) {
								this.personToChange[1] = 4;

							}

							// Replace with existing profile
						} else if (selectAction == 1) {
							this.personToChange[0] = 2;

							if (selectPersonType.equals("Manager")) {
								this.personToChange[1] = 1;

							} else if (selectPersonType.equals("Architect")) {
								this.personToChange[1] = 2;

							} else if (selectPersonType.equals("Engineer")) {
								this.personToChange[1] = 3;

							} else if (selectPersonType.equals("Customer")) {
								this.personToChange[1] = 4;

							}
						}
					}
					return;
				}
			}
		}
	}

	/**
	 *
	 * Check Invoice Method <br>
	 * Checks if an invoice needs to be created by returning the value of of the
	 * "invoiceToCreate" parameter -1 indicates no invoices should be created >= 0
	 * indicate the index number of the account that needs an invoice
	 *
	 * @return invoiceToCreate int, contains the index of the project that needs an
	 *         invoice generated
	 * @see editProject
	 * @since version 2.00
	 */
	public int checkInvoice() {

		return this.invoiceToCreate;
	}

	/**
	 *
	 * Reset Invoice Method <br>
	 * Resets the invoiceToCreate parameter to avoid having further invoices
	 * generated
	 * 
	 * @see editProject
	 * @since version 2.00
	 */
	public void resetInvoice() {
		this.invoiceToCreate = -1;

	}

	/**
	 *
	 * Edit Personnel Checker Method <br>
	 * Returns true if personToChange's index 0 is set to "> 0" this indicates
	 * personel on an account needs to be changed
	 * 
	 * @return true/false boolean - true indicates personel needs to be changed
	 * @see editProject
	 * @since version 2.00
	 */
	// this method checks if a project's personnel was set to "edit" ( index 0 set
	// to 1 or 2 )
	public boolean editPersonnelChecker() {

		if (this.personToChange[0] > 0) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * Reset Personnel Edit Method <br>
	 * Resets personnelToChange to indicate no further personnel accounts need to be
	 * changed or edited
	 * 
	 * @since version 2.00
	 */
	public void resetPeronnelEdit() {
		this.personToChange[0] = 0;
		this.personToChange[1] = 0;

	}

	/**
	 *
	 * To String Method <br>
	 * Returns a string of the project's parameters in a single formatted string
	 * 
	 * @param index int, contains the index of the project to be displayed in the
	 *              invoice
	 * @return output String, contains the project's parameters
	 * @see setNewProject
	 * @since version 1.00
	 */
	public String toString(int index) {
		String output = "\nProject Namer: " + this.projectName[index];
		output += "\nProject Number: " + this.projectNum[index];
		output += "\nBuilding Type: " + this.buildingType[index];
		output += "\nERF Number: " + this.erfNum[index];
		output += "\nTotal Cost: R" + this.totalCost[index];
		output += "\nCurrent Total Paid: R" + this.totalPaid[index];
		output += "\nTotal Due: R" + this.totalCost[index].subtract(this.totalPaid[index]);
		output += "\nCurrent Due Date: " + this.deadLine[index];
		output += "\nStreet: " + this.street[index];
		output += "\nCity: " + this.city[index];
		output += "\nProvince: " + this.region[index];
		output += "\nCountry: " + this.country[index];
		output += "\nPostal Code: " + this.postalCode[index];

		return output;
	}

	/**
	 *
	 * To Invoice Method <br>
	 * Returns all the project's details in one formatted string to display on
	 * screen and save to txt files
	 * 
	 * @param statement Statement used to query the SQL sever to locate the
	 *                  customers contact details
	 * @param index     int, contains the index of the project to be displayed in
	 *                  the invoice
	 * @throws SQLException if an error is encountered with SQL queries
	 * @return output String, contains the project's parameters formatted as an
	 *         invoice
	 * @since version 3.00
	 */
	public String toInvoice(Statement statement, int index) throws SQLException {

		// gets the clients contact details for the invoice
		String sqlCustDetails = "SELECT first_name, last_name, email, phone_num FROM acc_info WHERE account_id = "
				+ this.projectCustomer[index];
		String custFirstName = "";
		String custLastName = "";
		String custEmail = "";
		String custPhoneNum = "";

		ResultSet resultSet = statement.executeQuery(sqlCustDetails);

		while (resultSet.next()) {

			custFirstName = resultSet.getString(1);
			custLastName = resultSet.getString(2);
			custEmail = resultSet.getString(3);
			custPhoneNum = resultSet.getString(4);

		}

		String output = "\n\nPOISED PROJECT MANAGEMENT";
		output += "\n\n----------------------------------------------------------------------";
		output += "\nProject Details";
		output += "\n----------------------------------------------------------------------";
		output += "\nProject Name: " + this.projectName[index];
		output += "\nProject Number: " + this.projectNum[index];
		output += "\nBuilding Type: " + this.buildingType[index];
		output += "\nERF Number: " + this.erfNum[index];
		output += "\nProject customer: " + custFirstName + " " + custLastName;
		output += "\nCustomer Email: " + custEmail;
		output += "\nCustomer Phone Number: " + custPhoneNum;
		;
		output += "\n\n----------------------------------------------------------------------";
		output += "\nProject Costs";
		output += "\n----------------------------------------------------------------------";
		output += "\nTotal Project Cost: R" + this.totalCost[index];
		output += "\nTotal Paid to Date: R" + this.totalPaid[index];
		output += "\n----------------------------------------------------------------------";
		output += "\nTotal Due: R" + this.totalCost[index].subtract(this.totalPaid[index]);
		output += "\n\n----------------------------------------------------------------------";
		output += "\nProject Address";
		output += "\n----------------------------------------------------------------------";
		output += "\nStreet: " + this.street[index];
		output += "\nCity: " + this.city[index];
		output += "\nProvince: " + this.region[index];
		output += "\nCountry: " + this.country[index];
		output += "\nPostal Code: " + this.postalCode[index];
		output += "\n\n----------------------------------------------------------------------";
		output += "\nProject Date Range and Completion";
		output += "\n----------------------------------------------------------------------";
		output += "\nProject Due Date: " + this.deadLine[index];
		output += "\nProject Completion Date: "
				+ (this.completionDate[index].equals("00-00-0000") ? "Not Finalized Yet" : this.completionDate[index]);
		output += "\n----------------------------------------------------------------------\n\n";

		return output;
	}

	/**
	 *
	 * String To Int Array Method <br>
	 * Converts a string to an int array Values must be separated by "|"
	 * 
	 * @param stringToCast String, contains the string to be cast to an int array
	 * @return intArrayToReturn int[], contains the array of integers cast from the
	 *         passed string
	 * @see getSavedData
	 * @since version 2.00
	 */
	private int[] stringToIntArray(String stringToCast) {
		String[] intArrayToCast = stringToCast.split("\\|");

		int[] intArrayToReturn = new int[intArrayToCast.length];
		for (int index = 0; index < intArrayToCast.length; index++) {
			if (intArrayToCast[index].isBlank()) {
				intArrayToReturn[index] = 0;
			} else {
				intArrayToReturn[index] = Integer.parseInt(intArrayToCast[index]);
			}
		}
		return intArrayToReturn;
	}

	/**
	 *
	 * String To BigDecimal Array Method <br>
	 * Converts a string to a BigDecimal array Values must be separated by "|"
	 * 
	 * @param stringToCast String, contains the string to be cast to a BigDecimal
	 *                     array
	 * @return bigDecimalArrayToReturn BigDecimal[], contains the array of
	 *         BigDecimal values cast from the passed string
	 * @see getSavedData
	 * @since version 2.00
	 */
	private BigDecimal[] stringToBigDecimalArray(String stringToCast) {
		String[] bigDecimalArrayToCast = stringToCast.split("\\|");

		BigDecimal[] bigDecimalArrayToReturn = new BigDecimal[bigDecimalArrayToCast.length];
		for (int index = 0; index < bigDecimalArrayToCast.length; index++) {
			bigDecimalArrayToReturn[index] = new BigDecimal(bigDecimalArrayToCast[index]);
		}
		return bigDecimalArrayToReturn;
	}

	/**
	 *
	 * Get Saved Data Method <br>
	 * Used to set the parameters of the projects based on the passed string from a
	 * saved file
	 * 
	 * @param savedData String, contains the data to be assigned to each of the
	 *                  parameters for the projects that were saved
	 * @since version 2.00
	 */
	public void getSavedData(String savedData) {
		// convers the passed string to "rows" by splitting values as each line break
		String[] dataArray = savedData.split("\\n");

		// values are next split according to "columns" where a "|" is detected
		// casts values to int array
		this.projectNum = stringToIntArray(dataArray[0]);
		this.buildingType = dataArray[1].split("\\|");
		// casts values to int array
		this.erfNum = stringToIntArray(dataArray[2]);
		this.street = dataArray[3].split("\\|");
		this.city = dataArray[4].split("\\|");
		this.region = dataArray[5].split("\\|");
		this.country = dataArray[6].split("\\|");
		this.postalCode = dataArray[7].split("\\|");
		// casts values to BigDecimal array
		this.totalCost = stringToBigDecimalArray(dataArray[8]);
		// casts values to BigDecimal array
		this.totalPaid = stringToBigDecimalArray(dataArray[9]);
		this.deadLine = dataArray[10].split("\\|");
		this.completionDate = dataArray[11].split("\\|");
		// casts values to int array
		this.projectManager = stringToIntArray(dataArray[12]);
		// casts values to int array
		this.projectArchitect = stringToIntArray(dataArray[13]);
		// casts values to int array
		this.projectContractor = stringToIntArray(dataArray[14]);
		// casts values to int array
		this.projectCustomer = stringToIntArray(dataArray[15]);
		this.projectName = dataArray[16].split("\\|");
		this.addressID = stringToIntArray(dataArray[17]);
	}

	/**
	 *
	 * Get All Data Method <br>
	 * Stores all data from a project in a single array
	 * 
	 * @param projectIndex int used to select the project to return all the values
	 *                     from
	 * @return allDataToReturn String, contains all the current data from every
	 *         project parameter
	 * @since version 3.00
	 */
	public String[] getAllData(int projectIndex) {
		String[] allDataToReturn = new String[18];

		allDataToReturn[0] = Integer.toString(this.projectNum[projectIndex]);
		allDataToReturn[1] = this.buildingType[projectIndex];
		allDataToReturn[2] = Integer.toString(this.erfNum[projectIndex]);

		allDataToReturn[3] = this.street[projectIndex];
		allDataToReturn[4] = this.city[projectIndex];
		allDataToReturn[5] = this.region[projectIndex];
		allDataToReturn[6] = this.country[projectIndex];
		allDataToReturn[7] = this.postalCode[projectIndex];

		allDataToReturn[8] = this.totalCost[projectIndex].toString();
		allDataToReturn[9] = this.totalPaid[projectIndex].toString();
		allDataToReturn[10] = this.deadLine[projectIndex];
		allDataToReturn[11] = this.completionDate[projectIndex];
		allDataToReturn[12] = Integer.toString(this.projectManager[projectIndex]);
		allDataToReturn[13] = Integer.toString(this.projectArchitect[projectIndex]);
		allDataToReturn[14] = Integer.toString(this.projectContractor[projectIndex]);
		allDataToReturn[15] = Integer.toString(this.projectCustomer[projectIndex]);
		allDataToReturn[16] = this.projectName[projectIndex];
		allDataToReturn[17] = Integer.toString(this.addressID[projectIndex]);

		return allDataToReturn;
	}

	/**
	 *
	 * Get Unfinished Projects Method <br>
	 * Creates a selection menu displaying all unfinished projects If no unfinished
	 * projects are found an error is displayed
	 * 
	 * @return searchIndex int, contains index number of selected project / -1 of
	 *         input is canceled
	 * @since version 2.00
	 */
	public int getUnfinishedProjects() {
		// -1 indicates the user canceled input
		int searchIndex = -1;
		String[] arrayOptionsToDisplay = getAccountListValues();
		int[] indexArray = { -1 };
		String[] valueArray = { "" };

		for (int index = 0; index < this.deadLine.length; index++) {
			// compares the date in the list with today's date and adds any value that
			// returns "true" as overdue
			if (this.completionDate[index].equals("00-00-0000")) {
				if (indexArray[0] == -1) {
					valueArray[0] = arrayOptionsToDisplay[index];
					indexArray[0] = index;

				} else {
					valueArray = ArrayMethods.addToStringArray(valueArray, arrayOptionsToDisplay[index]);
					indexArray = ArrayMethods.addToIntArray(indexArray, index);
				}
			}
		}

		if (indexArray[0] != -1) {
			String menuSelection = (String) JOptionPane.showInputDialog(null, "The following results were found:\n\n",
					"Poised Project Management", JOptionPane.PLAIN_MESSAGE, null, valueArray, valueArray[0]);
			// if the user cancels here, "-1" is returned instead of null to indicate the
			// user canceled
			if (menuSelection == null) {
				return -1;
			}

			searchIndex = indexArray[Arrays.asList(valueArray).indexOf(menuSelection)];

		} else {
			JOptionPane.showMessageDialog(null, "No unfinished projects found.", "Poised Project Management",
					JOptionPane.ERROR_MESSAGE);

		}
		return searchIndex;
	}

	/**
	 *
	 * Get Account List Values Method <br>
	 * Creates an array list containing details for accounts to be displayed in
	 * dropdown selections
	 * 
	 * @return newValueList String, contains array with values for drop down box
	 *         selections
	 * @since version 2.00
	 */
	public String[] getAccountListValues() {
		// initializes list length based on number of existing projects
		String[] newValueList = new String[this.deadLine.length];
		// sets each values to contain a summary of the project's current state
		for (int index = 0; index < newValueList.length; index++) {
			newValueList[index] = this.projectName[index] + " - "
					+ (!this.completionDate[index].equals("00-00-0000") ? "Completed on: " + this.completionDate[index]
							: "Not Finalized")
					+ " - "
					+ ((this.completionDate[index].equals("00-00-0000")
							&& DateMethods.getDateCheck(this.deadLine[index])) ? "OVERDUE Due date: " : "Due Date: ")
					+ this.deadLine[index];
		}
		return newValueList;
	}

	/**
	 *
	 * Get Account Num Projects Method <br>
	 * Searches existing projects for a personnel account that matches the passed
	 * parameters Requests the user to selected an associated project If the person
	 * is not associated with a project, the method returns -1
	 * 
	 * @param accountNum int, contains account number of the person to search for as
	 *                   associated with the accounts
	 * @param personName String, contains person's name to be displayed if no
	 *                   associated projects are located
	 * @return searchIndex String, contains the index of the project associated with
	 *         the person that was selected
	 * @since version 2.00
	 */
	public int getAccountNumProjects(int accountNum, String personName) {
		// -1 indicates the user canceled input
		int searchIndex = -1;

		int[] indexArray = { -1 };
		String[] valueArray = { "" };
		String[] projectInfo = getAccountListValues();

		// all 3 personnel types are looped over to confirm if the account number is
		// present
		for (int index = 0; index < this.projectCustomer.length; index++) {

			if (this.projectCustomer[index] == accountNum || this.projectContractor[index] == accountNum
					|| this.projectArchitect[index] == accountNum) {
				if (indexArray[0] == -1) {
					valueArray[0] = projectInfo[index];
					indexArray[0] = index;

				} else {
					valueArray = ArrayMethods.addToStringArray(valueArray, projectInfo[index]);
					indexArray = ArrayMethods.addToIntArray(indexArray, index);

				}
			}
		}

		// displays found values if the personnel account is associated with a project
		if (indexArray[0] != -1) {
			String menuSelection = (String) JOptionPane.showInputDialog(null, "The following results were found:\n\n",
					"Poised Project Management", JOptionPane.PLAIN_MESSAGE, null, valueArray, valueArray[0]);

			// if the user cancels here, "-1" is returned instead of null to indicate the
			// user canceled
			if (menuSelection == null) {
				return -1;
			}

			searchIndex = indexArray[Arrays.asList(valueArray).indexOf(menuSelection)];

			// displays if no project is currently associated with the personnel account
		} else {
			JOptionPane.showMessageDialog(null,
					personName + " is not currently associated with a project, please try again",
					"Poised Project Management", JOptionPane.ERROR_MESSAGE);
		}
		return searchIndex;
	}

	/**
	 *
	 * Get Over Due Selection Method <br>
	 * Searches existing projects for projects that are currently overdue Requests
	 * the user to select an overdue project from the list If no overdue projects
	 * are found or the user cancels, the method returns -1
	 * 
	 * @return searchIndex String, contains the index value of the selected overdue
	 *         project
	 * @since version 2.00
	 */
	// method to return a list of all projects past their due date
	public int getOverDueSelection() {

		// -1 indicates the user canceled input
		int searchIndex = -1;
		// sets the value to check to today's date
		String valueToCheck = DateMethods.getTodaysDate();
		String[] arrayOptionsToDisplay = getAccountListValues();
		int[] indexArray = { -1 };
		String[] valueArray = { "" };

		for (int index = 0; index < this.deadLine.length; index++) {
			// compares the date in the list with today's date and adds any value that
			// returns "true" as overdue
			if (DateMethods.getDateCheck(this.deadLine[index]) && this.completionDate[index].equals("00-00-0000")) {
				if (indexArray[0] == -1) {
					valueArray[0] = arrayOptionsToDisplay[index];
					indexArray[0] = index;

				} else {
					valueArray = ArrayMethods.addToStringArray(valueArray, arrayOptionsToDisplay[index]);
					indexArray = ArrayMethods.addToIntArray(indexArray, index);
				}
			}
		}
		if (indexArray[0] != -1) {
			String menuSelection = (String) JOptionPane.showInputDialog(null, "The following results were found:\n\n",
					"Poised Project Management", JOptionPane.PLAIN_MESSAGE, null, valueArray, valueArray[0]);

			// if the user cancels here, "-1" is returned instead of null to indicate the
			// user canceled
			if (menuSelection == null) {
				return -1;
			}

			searchIndex = indexArray[Arrays.asList(valueArray).indexOf(menuSelection)];
		} else {
			JOptionPane.showMessageDialog(null,
					"No results found for projects with a due date older than or equal to: " + valueToCheck
							+ "\nNo overdue projects exist currently or have been completed.",
					"Poised Project Management", JOptionPane.ERROR_MESSAGE);

		}
		return searchIndex;
	}

	/**
	 *
	 * Set Project Name Method <br>
	 * Returns a project name that combines the last name of the client with the
	 * building type
	 *
	 * @param buildingType String contains the current building type
	 * @param custSurname  String contains the current surname of the client
	 * @return projectName String returns a combination of the Building type and
	 *         last name
	 * @see setNewProject
	 * @since version 1.00
	 */
	// creates a default "project name" if the user selects not to set one
	private String setProjectName(String buildingType, String custSurname) {
		// new projectName variable is initialized as empty
		String projectName = "";
		// the building type and surname are then combined in the string and returned
		projectName += buildingType + " ";
		projectName += custSurname;

		return projectName;
	}

	// TODO - add for addressID
	/**
	 *
	 * Set New Project Method <br>
	 * Used to create a new project Requests the user to enter the values for each
	 * parameter Each parameter is then appended to the arrays after all have been
	 * entered
	 * 
	 * @param statement    Statement used to confirm the selected account number is
	 *                     not in use and determine project index
	 * @param manaAccNum   int contains Manager account number to be assigned to the
	 *                     project
	 * @param archAccNum   int contains Architect account number to be assigned to
	 *                     the project
	 * @param contAccNum   int contains Engineer account number to be assigned to
	 *                     the project
	 * @param custAccNum   int contains Customer account number to be assigned to
	 *                     the project
	 * @param custLastName String contains the current surname of the client
	 * @throws SQLException if an error is encountered with SQL queries
	 * @return int Account index values containing the index of the new account
	 * @since version 3.00
	 */
	public int setNewProject(Statement statement, int manaAccnum, int archAccNum, int contAccNum, int custAccNum,
			String custLastName) throws SQLException {
		// returning -1 means the process was canceled

		int tempProjectManager = manaAccnum;
		if (tempProjectManager == -1) {
			return -1;
		}

		int tempProjectArchitect = archAccNum;
		if (tempProjectArchitect == -1) {
			return -1;
		}
		int tempProjectContractor = contAccNum;
		if (tempProjectContractor == -1) {
			return -1;
		}
		int tempProjectCustomer = custAccNum;
		if (tempProjectCustomer == -1) {
			return -1;
		}
		// temp variables are created and assigned values with the getValidInt and
		// getUserString methods
		// if the process is canceled at any point the user is returned to the main menu

		// confirms no duplicate keys are added selected during creation
		boolean selectProjNum = true;
		int tempProjectNum = 0;
		while (selectProjNum) {

			tempProjectNum = InputMethods.getValidInt("Please enter a project number to assign",
					"Please enter a number using numeric values only", Integer.toString(tempProjectNum));

			if (tempProjectNum == -1) {
				return -1;
			} else {

				ResultSet resultSet = statement
						.executeQuery("SELECT proj_num FROM proj_info WHERE proj_num = " + tempProjectNum);
				int duplicateValueCount = 0;
				while (resultSet.next()) {

					duplicateValueCount++;
				}

				if (duplicateValueCount > 0) {

					// returns error if number is already in use on another project
					JOptionPane.showMessageDialog(null,
							"Project number " + tempProjectNum
									+ " is already in use, please select a different project numer",
							"Poised Project Management", JOptionPane.ERROR_MESSAGE);

				} else if (duplicateValueCount == 0) {

					selectProjNum = false;

				}
			}
		}

		String tempBuildingType = InputMethods.getUserString("Please enter the building type",
				"You need to enter a building type in order to continue");
		if (tempBuildingType.isBlank()) {
			return -1;
		}
		int tempERFNum = InputMethods.getValidInt("Please enter an ERFnumber",
				"Please enter a number using numeric values only");
		if (tempERFNum == -1) {
			return -1;
		}
		String tempStreet = InputMethods.getUserString("Please enter the " + tempBuildingType + "'s street address",
				"You need to enter their street address in order to continue");
		if (tempStreet.isBlank()) {
			return -1;
		}
		String tempCity = InputMethods.getUserString("Please enter the " + tempBuildingType + "'s current City",
				"You need to enter their current City in order to continue");
		if (tempCity.isBlank()) {
			return -1;
		}
		String tempRegion = InputMethods.getUserString("Please enter the " + tempBuildingType + "'s current Province",
				"You need to enter their current Province in order to continue");
		if (tempRegion.isBlank()) {
			return -1;
		}
		String tempCountry = InputMethods.getUserString("Please enter the " + tempBuildingType + "'s current Country",
				"You need to enter a new country location for the project", "South Africa",
				"Are you sure you want to cancel entry?");
		if (tempCountry.isBlank()) {
			return -1;
		}
		String tempPostalCode = InputMethods.getPostalCode(tempBuildingType);
		if (tempPostalCode.isBlank()) {
			return -1;
		}
		BigDecimal tempTotalCost = InputMethods.getValidBigDecimal("Please enter the total cost of the project",
				"Please enter a number using numeric values only");
		if (tempTotalCost.compareTo(BigDecimal.ZERO) == -1) {
			return -1;
		}
		BigDecimal tempTotalPaid = InputMethods.getValidBigDecimal(
				"Please enter the total paid towards the project to date",
				"Please enter a number using numeric values only");
		if (tempTotalPaid.compareTo(BigDecimal.ZERO) == -1) {
			return -1;
		}
		String tempDeadLine = DateMethods.setDate(ProjectManagementSystem.yearRange);
		if (tempDeadLine.isBlank()) {
			return -1;
		}
		// the user is asked if they would like to set a custom project name
		// if not, the project name defaults to "Building Type" + "Last Name"
		String tempProjectName = "";
		if (InputMethods.getUserConfirmation("Would you like to set a custom project name?")) {
			tempProjectName = InputMethods.getUserString("Please enter a name for the project",
					"You need to enter a name");

		} else {
			tempProjectName = setProjectName(tempBuildingType, custLastName);
		}
		// this condition checks if custom name entry was canceled, if it was all info
		// is not appended
		// the user is returned to the main menu
		if (tempProjectName.isBlank()) {
			return -1;

			// if no values were canceled and the 0 index is still empty, the 0 index is set
			// to the temp values
		} else {
			if (this.projectName[0].equals("None")) {
				// set's project number
				this.projectNum[0] = tempProjectNum;
				this.buildingType[0] = tempBuildingType;
				this.erfNum[0] = tempERFNum;
				this.street[0] = tempStreet;
				this.city[0] = tempCity;
				this.region[0] = tempRegion;
				this.country[0] = tempCountry;
				this.postalCode[0] = tempPostalCode;
				this.totalCost[0] = tempTotalCost;
				this.totalPaid[0] = tempTotalPaid;
				this.deadLine[0] = tempDeadLine;

				this.projectManager[0] = tempProjectManager;
				// modify to detect if no architects are available and ask to register a new one
				// if none are
				this.projectArchitect[0] = tempProjectArchitect;
				// modify to detect if no contracts are available and ask to register a new one
				// if none are
				this.projectContractor[0] = tempProjectContractor;
				// modify to detect if no customers are available and ask to register a new one
				// if none are
				this.projectCustomer[0] = tempProjectCustomer;
				this.projectName[0] = tempProjectName;
				// this.isFinalized and this.completionDate is skipped due to the project not
				// being finalized or completed on creation
				this.addressID[0] = 0;

				// displays the project that was created
				JOptionPane.showMessageDialog(null,
						"The following project has been created:" + "\n" + toString(this.projectName.length - 1),
						"Poised Project Management", JOptionPane.INFORMATION_MESSAGE);

				// checks if total paid matches cost, if it does the project is automatically
				// finalized
				if (this.totalPaid[0].compareTo(this.totalCost[0]) >= 0) {

					this.completionDate[0] = DateMethods.getTodaysDate();
				}

				return 0;

				// if the 0 index is not empty, the values are appended to each parameter
			} else {
				this.projectNum = ArrayMethods.addToIntArray(this.projectNum, tempProjectNum);
				this.buildingType = ArrayMethods.addToStringArray(this.buildingType, tempBuildingType);
				this.erfNum = ArrayMethods.addToIntArray(this.erfNum, tempERFNum);
				this.street = ArrayMethods.addToStringArray(this.street, tempStreet);
				this.city = ArrayMethods.addToStringArray(this.city, tempCity);
				this.region = ArrayMethods.addToStringArray(this.region, tempRegion);
				this.country = ArrayMethods.addToStringArray(this.country, tempCountry);
				this.postalCode = ArrayMethods.addToStringArray(this.postalCode, tempPostalCode);
				this.totalCost = ArrayMethods.addToBigDecimalArray(this.totalCost, tempTotalCost);
				this.totalPaid = ArrayMethods.addToBigDecimalArray(this.totalPaid, tempTotalPaid);
				this.deadLine = ArrayMethods.addToStringArray(this.deadLine, tempDeadLine);
				// completion date parameter is automatically appended as "Not Finalized Yet"
				this.completionDate = ArrayMethods.addToStringArray(this.completionDate, "00-00-0000");

				// if no managers are registered the user is asked to first register one before
				// the project is created
				this.projectManager = ArrayMethods.addToIntArray(this.projectManager, tempProjectManager);
				// if no architects are registered the user is asked to first register one
				// before the project is created
				this.projectArchitect = ArrayMethods.addToIntArray(this.projectArchitect, tempProjectArchitect);
				// if no contractors are registered the user is asked to first register one
				// before the project is created
				this.projectContractor = ArrayMethods.addToIntArray(this.projectContractor, tempProjectContractor);
				// if no customers are registered the user is asked to first register one before
				// the project is created
				this.projectCustomer = ArrayMethods.addToIntArray(this.projectCustomer, tempProjectCustomer);
				this.projectName = ArrayMethods.addToStringArray(this.projectName, tempProjectName);
				this.addressID = ArrayMethods.addToIntArray(this.addressID, SaveDataIO.getNewAddressID(statement));

				// displays the created project
				JOptionPane.showMessageDialog(null,
						"The following project has been created:" + "\n" + toString(this.projectName.length - 1),
						"Poised Project Management", JOptionPane.INFORMATION_MESSAGE);

				// string to check if current value already exists in DB
				String sqlIndexRequet = "SELECT COUNT(proj_num) FROM proj_info";

				int projectIndex = -1;

				// get's the current number of existing projects to determine the index value of
				// the new project
				ResultSet resultSet = statement.executeQuery(sqlIndexRequet);
				while (resultSet.next()) {

					projectIndex = resultSet.getInt(1);
				}

				// checks if total paid matches cost, if it does the project is automatically
				// finalized
				if (this.totalPaid[projectIndex].compareTo(this.totalCost[projectIndex]) >= 0) {

					this.completionDate[projectIndex] = DateMethods.getTodaysDate();
				}

				return projectIndex;
			}
		}
	}
}

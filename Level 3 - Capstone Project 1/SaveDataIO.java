import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Formatter;

/**
 * Saved Data IO Class The methods from this are used read and write saved data
 * from .txt files
 *
 * @author Chris Joubert
 * @version 3.00, 01 September 2021
 */

public class SaveDataIO {

	/**
	 *
	 * Get Saved Data Method <br>
	 * The method returns a string containing all the contents of a table
	 *
	 * @param statement Statement used to query the SQL server to retrieve the saved
	 *                  data for each required table
	 * @param dataType  String containing the file name to be opened
	 * @throws SQLException if an error is encountered with SQL queries
	 * @return String containing all the file contents / "" if the file was not
	 *         opened successfully
	 * @since version 3.00
	 */
	static String getSavedData(Statement statement, String dataType) throws SQLException {

		String stringToReturn = "";
		String sqlRequest = "";

		if (dataType.equals("Person")) {

			sqlRequest = "SELECT * FROM acc_info INNER JOIN address_info ON acc_info.address_id = address_info.address_id";

			// query returns the following results
			// 1 = account_id
			// 2 = account_type
			// 3 = first_name
			// 4 = last_name
			// 5 = email
			// 6 = phone_num
			// 7 = address_id
			// 8 = address_id
			// 9 = country
			// 10 = region
			// 11 = city
			// 12 = street
			// 13 = post_code

			// stores a index values of the data to be requested for the person class
			int[] personDataIndex = { 2, 3, 4, 6, 5, 12, 11, 10, 9, 13, 1, 7 };

			// set's default value for string to be blank
			// if a blank string is returned it indicates an error occurred
			for (int value = 0; value < personDataIndex.length; value++) {
				ResultSet resultSet = statement.executeQuery(sqlRequest);
				while (resultSet.next()) {

					stringToReturn += resultSet.getString(personDataIndex[value]) + "|";

				}
				stringToReturn += "\n";

			}

		} else if (dataType.equals("Project")) {

			// query returns the following results
			// 1 = proj_num
			// 2 = erf_num
			// 3 = address_id
			// 4 = due_date
			// 5 = finalize_date
			// 6 = engineer_id
			// 7 = manager_id
			// 8 = architect_id
			// 9 = address_id
			// 10 = country
			// 11 = region
			// 12 = city
			// 13 = street
			// 14 = post_code
			// 15 = proj_num
			// 16 = proj_name
			// 17 = build_type
			// 18 = cost
			// 19 = paid
			// 20 = customer_id
			sqlRequest = "SELECT * FROM proj_info INNER JOIN address_info ON proj_info.address_id = address_info.address_id INNER JOIN cust_info on proj_info.proj_num = cust_info.proj_num";

			// set's default value for string to be blank
			// if a blank string is returned it indicates an error occurred
			int[] personDataIndex = { 1, 17, 2, 13, 12, 11, 10, 14, 18, 19, 4, 5, 7, 8, 6, 20, 16, 3 };

			for (int value = 0; value < personDataIndex.length; value++) {
				ResultSet resultSet = statement.executeQuery(sqlRequest);
				while (resultSet.next()) {

					if (personDataIndex[value] == 4 || personDataIndex[value] == 5) {

						// switches date format from YYYY-MM-DD to DD-MM-YYYY
						String[] stringDateArray = resultSet.getString(personDataIndex[value]).split("-");
						String newDateFormat = stringDateArray[2] + "-" + stringDateArray[1] + "-" + stringDateArray[0];

						stringToReturn += newDateFormat + "|";

					} else {
						stringToReturn += resultSet.getString(personDataIndex[value]) + "|";
					}

				}
				stringToReturn += "\n";
			}

		}

		return stringToReturn;
	}

	/**
	 *
	 * Set To File Method <br>
	 * The method saves passed String array to the SQL server Array if first checked
	 * to determine if the data is new or existing
	 * 
	 * New Arrays are inserted into the relevant tables If the key value already
	 * exists on a table the data is looped over and new data is set to the relevant
	 * rows
	 *
	 * @param statement     Statement used to pass the new values on to the SQL
	 *                      server
	 * @param stringToWrite String[] containing the data to be saved to the file
	 * @param dataType      String containing the file name to save the data to
	 * @throws SQLException if an error is encountered with SQL queriesf
	 * @since version 3.00
	 */
	static void setToDatabase(Statement statement, String[] arrayToWrite, String dataType) throws SQLException {

		// string to check if current value already exists in DB
		String sqlRequestID = sqlSelect("COUNT(" + (dataType.equals("Person") ? "account_id" : "proj_num") + ")")
				+ sqlFrom(dataType.equals("Person") ? "acc_info" : "proj_info")
				+ sqlWhere(dataType.equals("Person") ? "account_id" : "proj_num") + " = ";

		// switches to true of the data is a new entry
		Boolean newValue = false;
		// appends the relevant key to the query
		String idCheck = sqlRequestID + arrayToWrite[0];

		// confirms if passed array is a new entry or and edited entry
		ResultSet resultSet = statement.executeQuery(idCheck);
		while (resultSet.next()) {

			if (resultSet.getInt(1) == 0) {
				newValue = true;

			}
		}

		// adds new row to relevant personnel tables if data is new
		if (dataType.equals("Person") && newValue) {

			// Update Acc_info table
			statement.executeUpdate(sqlInsert("acc_info") + sqlAccValues(arrayToWrite[0], arrayToWrite[1],
					arrayToWrite[2], arrayToWrite[3], arrayToWrite[5], arrayToWrite[4], arrayToWrite[11]));

			// Update Address_info table
			statement.executeUpdate(sqlInsert("address_info") + sqlAddressValues(arrayToWrite[11], arrayToWrite[9],
					arrayToWrite[8], arrayToWrite[7], arrayToWrite[6], arrayToWrite[10]));

			// adds new row to relevant project tables if the data is new
		} else if (dataType.equals("Project") && newValue) {

			statement.executeUpdate(
					sqlInsert("proj_info") + sqlProjValues(arrayToWrite[0], arrayToWrite[2], arrayToWrite[17],
							arrayToWrite[10], arrayToWrite[11], arrayToWrite[14], arrayToWrite[12], arrayToWrite[13]));

			statement.executeUpdate(sqlInsert("cust_info") + sqlCustValues(arrayToWrite[0], arrayToWrite[16],
					arrayToWrite[1], arrayToWrite[8], arrayToWrite[9], arrayToWrite[15]));

			statement.executeUpdate(sqlInsert("address_info") + sqlAddressValues(arrayToWrite[17], arrayToWrite[6],
					arrayToWrite[5], arrayToWrite[4], arrayToWrite[3], arrayToWrite[7]));

			// if the data is not new it means it's related to an existing project / person
			// each relevant value is looped over and compared to existing values to
			// determine if change were made
			// any changes will replace existing values
		} else if (dataType.equals("Person") && !newValue) {

			// index values for "arrayToWrite" if set to person are:
			// [0] accountNum
			// [1] personType
			// [2] firstName
			// [3] lastName
			// [4] phoneNumber
			// [5] email
			// [6] street
			// [7] city
			// [8] region
			// [9] country
			// [10] postalCode
			// [11] addressID

			String[] accTableValues = { "account_type", "first_name", "last_name", "email", "phone_num" };
			// stores the index of each value in the accTableValues array in the passed
			// "arrayToWrite"
			int[] accTableIndex = { 1, 2, 3, 5, 4 };

			// -1 indicates no changes
			int[] trueAccIndex = { -1, -1 };

			for (int index = 0; index < accTableValues.length; index++) {

				String accCheck = sqlSelect(sqlCount(accTableValues[index])) + sqlFrom("acc_info")
						+ sqlWhere(accTableValues[index] + " = " + sqlString(arrayToWrite[accTableIndex[index]])
								+ " AND account_id = " + arrayToWrite[0]);

				resultSet = statement.executeQuery(accCheck);
				while (resultSet.next()) {

					if (resultSet.getInt(1) == 0) {

						if (accTableValues[index].equals("last_name")) {
							trueAccIndex[1] = index;
						} else {
							trueAccIndex[0] = index;
						}
					}
				}
			}

			// updates acc_info table if a value was found to be changed
			if (trueAccIndex[0] >= 0) {
				statement.executeUpdate(sqlUpdate("acc_info", accTableValues[trueAccIndex[0]],
						sqlString(arrayToWrite[accTableIndex[trueAccIndex[0]]])
								+ sqlWhere("account_id = " + arrayToWrite[0])));

			}

			// updates acc_info table's last_name if it's found to be changed
			if (trueAccIndex[1] >= 0) {
				statement.executeUpdate(sqlUpdate("acc_info", accTableValues[trueAccIndex[1]],
						sqlString(arrayToWrite[accTableIndex[trueAccIndex[1]]])
								+ sqlWhere("account_id = " + arrayToWrite[0])));

			}

			// checks for address changes on the person's account
			String getAddressID = sqlSelect("address_id") + sqlFrom("acc_info")
					+ sqlWhere("account_id = " + arrayToWrite[0]);

			int currentAddressID = -1;

			resultSet = statement.executeQuery(getAddressID);
			while (resultSet.next()) {

				currentAddressID = resultSet.getInt(1);

			}

			String[] addTableValues = { "country", "region", "city", "street", "post_code" };
			// stores the index of each value in the addTableValues array in the passed
			// "arrayToWrite"
			int[] addTableIndex = { 9, 8, 7, 6, 10 };
			// stores the values that were found to have changed while performing the check
			// -1 indicates no changes
			int[] trueAddIndex = { -1, -1, -1, -1, -1 };

			for (int index = 0; index < addTableValues.length; index++) {

				String addressCheck = sqlSelect(sqlCount(addTableValues[index])) + sqlFrom("address_info")
						+ sqlWhere(addTableValues[index] + " = " + sqlString(
								arrayToWrite[addTableIndex[index]] + " AND address_id = " + currentAddressID));

				// sets values for trueAddIndex to track current changes to selected address
				resultSet = statement.executeQuery(addressCheck);
				while (resultSet.next()) {

					if (resultSet.getInt(1) == 0) {

						trueAddIndex[index] = addTableIndex[index];

					}
				}
			}

			for (int index = 0; index < trueAddIndex.length; index++) {

				if (trueAddIndex[index] >= 0) {
					statement.executeUpdate(sqlUpdate("address_info", addTableValues[index],
							sqlString(arrayToWrite[trueAddIndex[index]])) + sqlWhere("address_id") + " = "
							+ currentAddressID);

				}

			}

		} else if (dataType.equals("Project") && !newValue) {

			// arrayToWrite index if values is set to Project
			// [0] projectNum
			// [1] buildingType
			// [2] erfNum
			// [3] street
			// [4] city
			// [5] region
			// [6] country
			// [7] postalCode
			// [8] totalCost
			// [9] totalPaid
			// [10] deadLine
			// [11] completionDate
			// [12] projectManager
			// [13] projectArchitect
			// [14] projectContractor
			// [15] projectCustomer
			// [16] projectName
			// [17] addressID

			// check project table for changes
			String[] projTableValues = { "proj_num", "erf_num", "due_date", "finalize_date", "engineer_id",
					"manager_id", "architect_id" };
			// stores the index of each value in the projTableValues array in the passed
			// "arrayToWrite"
			int[] projTableIndex = { 0, 2, 10, 11, 14, 12, 13 };
			// -1 indicates no changes
			int trueProjIndex = -1;

			for (int index = 0; index < projTableIndex.length; index++) {

				String projCheck = sqlSelect(sqlCount(projTableValues[index])) + sqlFrom("proj_info")
						+ sqlWhere(projTableValues[index] + " = "
								+ (projTableValues[index].equals("due_date")
										|| projTableValues[index].equals("finalize_date")
												? sqlToDate(arrayToWrite[projTableIndex[index]])
												: arrayToWrite[projTableIndex[index]]));

				// checks which values changed relevant to the proj_info table
				resultSet = statement.executeQuery(projCheck);
				while (resultSet.next()) {

					if (resultSet.getInt(1) == 0) {

						trueProjIndex = index;

					}
				}
			}

			// updated proj_info table if a value was found to be changed
			if (trueProjIndex >= 0) {
				statement.executeUpdate(sqlUpdate("proj_info", projTableValues[trueProjIndex],
						(projTableValues[trueProjIndex].equals("due_date")
								|| projTableValues[trueProjIndex].equals("finalize_date")
										? sqlToDate(arrayToWrite[projTableIndex[trueProjIndex]])
										: arrayToWrite[projTableIndex[trueProjIndex]])));
				return;

			}

			// check customer table for changes
			String[] custTableValues = { "proj_num", "proj_name", "build_type", "cost", "paid ", "customer_id" };
			// stores the index of each value in the custTableValues array in the passed
			// "arrayToWrite"
			int[] custTableIndex = { 0, 16, 1, 8, 9, 15 };
			// -1 indicates no changes
			int trueCustIndex = -1;

			for (int index = 0; index < custTableValues.length; index++) {

				String custCheck = sqlSelect(sqlCount(custTableValues[index])) + sqlFrom("cust_info")
						+ sqlWhere(custTableValues[index] + " = "
								+ (custTableValues[index].equals("proj_num")
										|| custTableValues[index].equals("customer_id")
												? arrayToWrite[custTableIndex[index]]
												: sqlString(arrayToWrite[custTableIndex[index]])));

				// confirms which changes took place relevant to the cust_info table
				resultSet = statement.executeQuery(custCheck);
				while (resultSet.next()) {

					if (resultSet.getInt(1) == 0) {

						trueCustIndex = index;

					}
				}
			}

			// updated cust_info table if a value was found to be changed
			if (trueCustIndex >= 0) {
				statement.executeUpdate(sqlUpdate("cust_info", custTableValues[trueCustIndex],
						(custTableValues[trueCustIndex].equals("proj_num")
								|| custTableValues[trueCustIndex].equals("customer_id")
										? arrayToWrite[custTableIndex[trueCustIndex]]
										: sqlString(arrayToWrite[custTableIndex[trueCustIndex]]))));
				return;

			}

			// checks which changes where made relevant to the address_info table
			String getAddressID = sqlSelect("address_id") + sqlFrom("proj_info")
					+ sqlWhere(projTableValues[0] + " = " + arrayToWrite[0]);

			int currentAddressID = -1;

			resultSet = statement.executeQuery(getAddressID);
			while (resultSet.next()) {

				currentAddressID = resultSet.getInt(1);

			}

			String[] addTableValues = { "country", "region", "city", "street", "post_code" };
			// stores the index of each value in the custTableValues array in the passed
			// "arrayToWrite"
			int[] addTableIndex = { 6, 5, 4, 3, 7 };
			// array tracks different fields of table to list which had changes
			// -1 indicates no changes
			int[] trueAddIndex = { -1, -1, -1, -1, -1 };

			for (int index = 0; index < addTableValues.length; index++) {

				String addressCheck = sqlSelect(sqlCount(addTableValues[index])) + sqlFrom("address_info")
						+ sqlWhere(addTableValues[index] + " = " + sqlString(
								arrayToWrite[addTableIndex[index]] + " AND address_id = " + currentAddressID));

				resultSet = statement.executeQuery(addressCheck);
				while (resultSet.next()) {

					if (resultSet.getInt(1) == 0) {

						trueAddIndex[index] = addTableIndex[index];

					}
				}
			}

			for (int index = 0; index < trueAddIndex.length; index++) {

				if (trueAddIndex[index] >= 0) {
					statement.executeUpdate(sqlUpdate("address_info", addTableValues[index],
							sqlString(arrayToWrite[trueAddIndex[index]])) + sqlWhere("address_id") + " = "
							+ currentAddressID);

				}

			}

		}

	}

	/**
	 *
	 * SQL Select format method <br>
	 * Formats passed value to be correctly placed with "SELECT" syntax
	 * 
	 * used only to improve formatting and layout of SQL queries
	 * 
	 *
	 * @param String selection, value to select from the database
	 * @return correctly formatted String with passed values for
	 * @see SaveDataIO
	 * @since version 3.00
	 */
	private static String sqlSelect(String selection) {

		return "SELECT " + selection;

	}

	/**
	 *
	 * SQL Update format method <br>
	 * Formats passed value to be correctly placed with "UPDATE" syntax
	 * 
	 * used only to improve formatting and layout of SQL queries
	 * 
	 *
	 * @param String table, value to select table from the database
	 * @param String column, value to select column from the database
	 * @param String value, value to change the current value to
	 * @return correctly formatted String with passed values for
	 * @see SaveDataIO
	 * @since version 3.00
	 */
	private static String sqlUpdate(String table, String column, String value) {

		return "UPDATE " + table + " SET " + column + " = " + value;

	}

	/**
	 *
	 * SQL Count format method <br>
	 * Formats passed value to be correctly placed with "COUNT" syntax
	 * 
	 * used only to improve formatting and layout of SQL queries
	 * 
	 *
	 * @param String selection, value to select table from the database to be
	 *               counted
	 * @return correctly formatted String with passed values for
	 * @see SaveDataIO
	 * @since version 3.00
	 */
	private static String sqlCount(String selection) {

		return " COUNT( " + selection + ") ";
	}

	/**
	 *
	 * SQL String format method <br>
	 * Formats passed value to be correctly placed with Apostrophes for char values
	 * or other values that require Apostrophes
	 * 
	 * used only to improve formatting and layout of SQL queries
	 * 
	 *
	 * @param String value, value to be returned with apostrophes around it
	 * @return correctly formatted String with passed values for
	 * @see SaveDataIO
	 * @since version 3.00
	 */
	private static String sqlString(String value) {
		return " '" + value + "' ";

	}

	/**
	 *
	 * SQL Insert format method <br>
	 * Formats passed value to be correctly placed with "INSERT INTO" syntax for SQL
	 * 
	 * used only to improve formatting and layout of SQL queries
	 * 
	 *
	 * @param String table, value to be returned for table to be selected
	 * @return correctly formatted String with passed values for
	 * @see SaveDataIO
	 * @since version 3.00
	 */
	private static String sqlInsert(String table) {

		return "INSERT INTO " + table;
	}

	/**
	 *
	 * SQL From format method <br>
	 * Formats passed value to be correctly placed with "FROM" syntax for SQL
	 * 
	 * used only to improve formatting and layout of SQL queries
	 * 
	 *
	 * @param String table, value to be returned for selecting tables
	 * @return correctly formatted String with passed values for
	 * @see SaveDataIO
	 * @since version 3.00
	 */
	private static String sqlFrom(String table) {

		return " FROM " + table;
	}

	/**
	 *
	 * SQL Where format method <br>
	 * Formats passed value to be correctly placed with "WHERE" syntax for SQL
	 * 
	 * used only to improve formatting and layout of SQL queries
	 * 
	 *
	 * @param String selection, value to be returned for filtering queries
	 * @return correctly formatted String with passed values for
	 * @see SaveDataIO
	 * @since version 3.00
	 */
	private static String sqlWhere(String selection) {

		return " WHERE " + selection;
	}

	/**
	 *
	 * SQL toDate format method <br>
	 * Formats passed value to be correctly placed with "STR_TO_DATE" syntax for SQL
	 * 
	 * used only to improve formatting and layout of SQL queries
	 * 
	 *
	 * @param String selection, value to be returned for adding or searching a date
	 *               in the correct format
	 * @return correctly formatted String with passed values for
	 * @see SaveDataIO
	 * @since version 3.00
	 */
	private static String sqlToDate(String date) {

		return " STR_TO_DATE('" + date + "', '%d-%m-%Y')";
	}

	/**
	 *
	 * SQL Project Values format method <br>
	 * Formats passed value to be correctly placed in correct format and order for
	 * inserting into proj_info table
	 * 
	 * used only to improve formatting and layout of SQL queries
	 * 
	 *
	 * @param String primaryKey, contains primary key for proj_info table
	 * @param String erfNum, contains erf number for proj_info table
	 * @param String addressID, value containing addressID of project
	 * @param String dueDate, value for current due date
	 * @param String finalizeDate, value for current finalized date
	 * @param String engineerID, contains the account ID of the engineer assigned to
	 *               the project
	 * @param String managerID, contains the account ID of the manager assigned to
	 *               the project
	 * @param String architectID, contains the account ID of the architect assigned
	 *               to the project
	 * @return correctly formatted String with passed values for
	 * @see SaveDataIO
	 * @since version 3.00
	 */
	private static String sqlProjValues(String primaryKey, String erfNum, String addressID, String dueDate,
			String finalizeDate, String engineerID, String managerID, String architectID) {

		String sqlValues = " VALUES (" + primaryKey + "," + erfNum + "," + addressID + "," + "" + sqlToDate(dueDate)
				+ "," + "" + sqlToDate(finalizeDate) + "," + engineerID + "," + managerID + "," + architectID + ")";

		return sqlValues;

	}

	/**
	 *
	 * SQL Customer Values format method <br>
	 * Formats passed value to be correctly placed in correct format and order for
	 * inserting into cust_info table
	 * 
	 * used only to improve formatting and layout of SQL queries
	 * 
	 *
	 * @param String primaryKey, contains primary key for cust_info table
	 * @param String projName, contains project name currently assigned to the
	 *               project
	 * @param String buildType, value containing the current type of building
	 * @param String cost, value for project total cost
	 * @param String paid, value for total paid towards project so far
	 * @param String customerID, contains the account ID of the customer assigned to
	 *               the project
	 * @return correctly formatted String with passed values for
	 * @see SaveDataIO
	 * @since version 3.00
	 */
	private static String sqlCustValues(String primaryKey, String projName, String buildType, String cost, String paid,
			String customerID) {

		String sqlValues = " VALUES (" + primaryKey + "," + "'" + projName + "'," + "'" + buildType + "'," + cost + ","
				+ paid + "," + customerID + ")";

		return sqlValues;

	}

	/**
	 *
	 * SQL Address Values format method <br>
	 * Formats passed value to be correctly placed in correct format and order for
	 * inserting into address_info table
	 * 
	 * used only to improve formatting and layout of SQL queries
	 * 
	 *
	 * @param String primaryKey, contains primary key for address_info table
	 * @param String country, contains the country associated with the project /
	 *               person
	 * @param String region, contains the region/province associated with the
	 *               project / person
	 * @param String city, contains the city associated with the project / person
	 * @param String street, contains the street associated with the project /
	 *               person
	 * @param String postalCode, contains the postal code associated with the
	 *               project / person
	 * @return correctly formatted String with passed values for
	 * @see SaveDataIO
	 * @since version 3.00
	 */
	private static String sqlAddressValues(String primaryKey, String country, String region, String city, String street,
			String postalCode) {

		String sqlValues = " VALUES (" + primaryKey + "," + "'" + country + "'," + "'" + region + "'," + "'" + city
				+ "'," + "'" + street + "'," + "'" + postalCode + "')";

		return sqlValues;

	}

	/**
	 *
	 * SQL Account Values format method <br>
	 * Formats passed value to be correctly placed in correct format and order for
	 * inserting into acc_info table
	 * 
	 * used only to improve formatting and layout of SQL queries
	 * 
	 *
	 * @param String primaryKey, contains primary key for address_info table
	 * @param String accType, contains the account type associated with the person
	 * @param String firstName, contains the first name associated with the person
	 * @param String lastName, contains the last name associated with the person
	 * @param String email, contains the email associated with the person
	 * @param String phoneNum, contains the phone number associated with the person
	 * @param String addressID, contains the address_id key associated with the
	 *               person for the address_info table
	 * @return correctly formatted String with passed values for
	 * @see SaveDataIO
	 * @since version 3.00
	 */
	private static String sqlAccValues(String primaryKey, String accType, String firstName, String lastName,
			String email, String phoneNum, String addressID) {

		String sqlValues = " VALUES (" + primaryKey + "," + "'" + accType + "'," + "'" + firstName + "'," + "'"
				+ lastName + "'," + "'" + email + "'," + "'" + phoneNum + "'," + addressID + ")";

		return sqlValues;

	}

	/**
	 *
	 * New Address Key Method <br>
	 * Queries the address_info table to get the current max address_id value to
	 * return a new new value 1 digit higher
	 * 
	 * @param statement Statement used to confirm the the current highest ID value
	 *                  on the address_info table
	 * @throws SQLException if an error is encountered with SQL queries
	 * @return int newAddress ID for use with Key for ddress_info table
	 * @since version 3.00
	 */

	public static int getNewAddressID(Statement statement) throws SQLException {
		String requestLastID = sqlSelect("MAX(address_id)") + sqlFrom("address_info");
		int maxID = 0;

		ResultSet resultSet = statement.executeQuery(requestLastID);
		while (resultSet.next()) {

			maxID = resultSet.getInt(1);
		}

		// adds 1 to generate new address ID
		maxID++;

		return maxID;

	}

	/**
	 *
	 * Set To File Method <br>
	 * The method saves passed String to a .txt file with the passed name
	 *
	 * @param stringToWrite String containing the data to be saved to the file
	 * @param fileName      String containing the file name to save the data to
	 * @since version 1.00
	 */
	static void setToFile(String stringToWrite, String fileName) {
		Formatter file;

		try {
			file = new Formatter(fileName + ".txt");
			file.format(stringToWrite);
			// file is closed after the data is written to the txt file
			file.close();
			// if an error is encountered and file is not working, the method returns
		} catch (FileNotFoundException e) {
			return;
		}
	}
}

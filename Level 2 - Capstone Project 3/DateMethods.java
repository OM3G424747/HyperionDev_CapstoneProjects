import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

/**
 * Date Method Class
 * The class contains methods use to set a valid date or confirm if a date is past a specified date
 *
 * @author Chris Joubert
 * @version 1.00, 27 July 2021
 */

public class DateMethods {
    
	
	/**
    *
    * Get Today's Date Method
    * <br>
    * Method returns today's date in the form of a string
    *
    * @return Stringy containing today's date (Formated to DD-MM-YY )
    * @since version 1.00
    */
	public static String getTodaysDate() {
	
		LocalDateTime currentDate = LocalDateTime.now();
	    DateTimeFormatter newDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    String dateToString = currentDate.format(newDateFormat);
	
	    return dateToString;
	}
	
	
	/**
    *
    * Get Date Array Method
    * <br>
    * The method returns a new array containing the numeric values of the date passed
    *
    * @param passedDate String containing containing a date formatted in DD-MM-YYYY
    * @return Int Array containing the number values of the Day[0], Month[1] and Year[2] 
    * @see getDateCheck
    * @since version 1.00
    */	
	// converts passed date string to an int array
	private static int[] getDateArray(String passedDate) {
		String[] stringDateArray = passedDate.split("-");
		int[] intDateArray = new int[stringDateArray.length];
		
		for(int index = 0; index < stringDateArray.length; index ++) {
			intDateArray[index] = Integer.parseInt(stringDateArray[index]);
		}
		return intDateArray;
	}
	
	
	
	/**
    *
    * Get Date Check Method
    * <br>
    * Checks if the date passed is matching or older than today's date
    * The method returns true if the date is matches or is older than today's date
    *
    * @param dateToCheck String containing containing a date formatted in DD-MM-YYYY
    * @return Boolean - True if the date matches today's date or is older
    * @see setDate
    * @since version 1.00
    */	
	public static boolean getDateCheck(String dateToCheck) {
		
		return getDateCheck(dateToCheck, getTodaysDate());
	}
	
	
	/**
    *
    * Get Date Check Method
    * <br>
    * Checks if the date passed is matching or older than the date passed
    * The method returns true if the date is matches or is older than the passed date
    *
    * @param dateToCheck String containing containing a date formatted in DD-MM-YYYY
    * @param dueDate String containing containing a date formatted in DD-MM-YYYY
    * @return Boolean - True if the date matches the passed date, or is older
    * @since version 1.00
    */
	public static boolean getDateCheck(String dateToCheck, String dueDate) {
		
		boolean isPastDue = false;
		int[] passedDate = getDateArray(dateToCheck);
		int[] dueDay = getDateArray(dueDate);
		// compares year values
		if (passedDate[2] < dueDay[2]) {
			isPastDue = true;
			
		}else if (passedDate[2] == dueDay[2]) {
			
			// compares month values
			if(passedDate[1] < dueDay[1]) {
				isPastDue = true;
				
			} else if (passedDate[1] == dueDay[1]) {
				
				// compares day values
				if (passedDate[0] <= dueDay[0]) {
					isPastDue = true;
				}
			}
		}

		return isPastDue;
	}
	
	
	/**
    *
    * Set Year Method
    * <br>
    * Requests the user to select a valid year within the defined year range
    * The range of years available is determined from the current year onward
    * A year range of 0 will result in only 1 year being available
    *
    * @param yearRange Int containing current year range to display
    * @return String containing the selected year / "" is returned if the user cancels 
    * @see setDate
    * @see setMonth
    * @since version 1.00
    */
	private static String setYear(int yearRange) {
		// get's the current date and saves it as an array
		int[] currentDate = getDateArray(getTodaysDate());
		// sets current year as default selected value for selection list
		String currentYear = Integer.toString(currentDate[2]);
		// sets select year to "blank", this indicates a "cancel" if returned 
		String selectYear = "";
		// sets the current year as a starting value for the menu selection
		String[] yearSelection = {currentYear};
		// sets bool to enable while loop until the user selects a valid option or cancels
		boolean isValidSelection = false;
		 
		// for loop adds default range from settings
		for(int availableYears = 1; availableYears <= yearRange; availableYears ++ ) {
			currentDate[2] ++; 
			yearSelection = ArrayMethods.addToStringArray(yearSelection, Integer.toString(currentDate[2]));
		}
		 
		while(!isValidSelection) {
		 
			selectYear = (String)JOptionPane.showInputDialog( 
						null, 
						"Please select the due year for the project:\n\n", 
						"Poised Project Management", 
						JOptionPane.PLAIN_MESSAGE,
						null, yearSelection, 
						currentYear);
			
			if (selectYear == null) {
				if (InputMethods.getUserConfirmation("Are you sure you want to cancel entering a date?\n" + 
													 "This will return you to the main menu!")) {
					// returns a blank to indicate a "cancel" has been selected
					return "";
				}	
			}else {
				return selectYear;
			}
		}
		return selectYear;
		
	}
	
	
	/**
    *
    * Set Month Method
    * <br>
    * Requests the user to select a valid month
    * If the passed year matches the current year, only the remaining months in the year will be displayed as available options
    *
    * @param selectedYear String containing user selected year
    * @return String containing the selected month / "" is returned if the user cancels 
    * @see setDate
    * @see setYear
    * @since version 1.00
    */
	private static String setMonth(String selectedYear) {
		String selectMonth = "";
		
		// get's the current date and saves it as an array
		int[] currentDate = getDateArray(getTodaysDate());
		
		// confirm year selection and if it's the current year, then the array should only show the current month till the end of the year
		// sets starting month to be altered based on the year selection to avoid selecting a month in the past
		int startingMonth = 1;
		String[] availableMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", 
								   "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		
		// if the current year was selected as a due year, the available months are reduced to avoid duplicates 
		int yearSelection = Integer.parseInt(selectedYear);
		
		if(yearSelection == currentDate[2]) {
			startingMonth = currentDate[1];
		}
		// sets month selection starting value based on year selection
		String[] monthSelection = {availableMonths[startingMonth - 1]};
				
		// sets bool to enable while loop until the user selects a valid option or cancels
		boolean isValidSelection = false;
		 
		// for loop adds default range from settings
		for(int monthIndex = startingMonth + 1; monthIndex <= 12; monthIndex ++ ) { 
			monthSelection = ArrayMethods.addToStringArray(monthSelection, availableMonths[monthIndex - 1]);
		}
		while(!isValidSelection) {
		 
			selectMonth = (String)JOptionPane.showInputDialog( 
						null, 
						"Please select the due month for the project:\n\n", 
						"Poised Project Management", 
						JOptionPane.PLAIN_MESSAGE,
						null, monthSelection, 
						availableMonths[startingMonth - 1]);
			
			if (selectMonth == null) {
				if (InputMethods.getUserConfirmation("Are you sure you want to cancel entering a date?\n" + 
													 "This will return you to the main menu!")) {
					// returns a blank to indicate a "cancel" has been selected
					return "";
				}	
			}else {
				// returns the selected month as a number in string format
				for(int monthIndex = 0; monthIndex <= 11; monthIndex ++) {
					
					if(availableMonths[monthIndex].equals(selectMonth)) {
						// adds 1 to account for index count starting on 0
						return Integer.toString(monthIndex + 1); 
					}	
				}
				return selectMonth;
			}
		}
		return selectMonth;
	}
	
	
	/**
    *
    * Set Day Method
    * <br>
    * Requests the user to select a valid day/date from within a month
    * If the passed year and month matches the current year and month, only the remaining days in the month will be displayed as available options
    *
    * @param selectedMonth String containing user selected month
    * @param selectedYear String containing user selected year
    * @return String containing the selected day / "" is returned if the user cancels 
    * @see setDate
    * @see setMonth
    * @see setYear
    * @since version 1.00
    */
	private static String setDay(String selectedMonth, String selectedYear) {
		String selectDay = "";
		// get's the current date and saves it as an array
		int[] currentDate = getDateArray(getTodaysDate());

		// sets starting month to be altered based on the year selection to avoid selecting a month in the past
		int dateRange = 31;
		int startingDay = 1;
		
		// if the current year was selected as a due year, the available months are reduced to avoid duplicates 
		int yearSelection = Integer.parseInt(selectedYear);
		int monthSelection = Integer.parseInt(selectedMonth);
		
		// confirms if year is a leap year if Feb is the selected month
		if (yearSelection % 4 == 0) {
			if (monthSelection == 2) {
				dateRange = 29;	
			}
		}else if (monthSelection == 2) {
			dateRange = 28;
		
		} else {
			// confirms if the selected month is in the 2nd half of the year to determine available days	
			if (monthSelection <= 7) {
				if (monthSelection % 2 == 0) {
					dateRange = 30;
				}
			
			// confirms if the selected month is in the 2nd half of the year to determine available days
			} else if (monthSelection > 7) {
				if (monthSelection % 2 == 1) {
					dateRange = 30;
				}
			}
		}
		// confirms if the current year and month match the selected year and month
		// if it does the starting date is set to limit the available days for the selection
		if(yearSelection == currentDate[2] && monthSelection == currentDate[1]) {
			startingDay = currentDate[0];
		}
		
		// sets month selection starting value based on year selection
		String[] daySelection = {Integer.toString(startingDay)};		
		// sets bool to enable while loop until the user selects a valid option or cancels
		boolean isValidSelection = false;
		// for loop adds default range from settings
		for(int dayIndex = startingDay + 1; dayIndex <= dateRange; dayIndex ++ ) { 
			daySelection = ArrayMethods.addToStringArray(daySelection, Integer.toString(dayIndex));
		}
		while(!isValidSelection) {
			selectDay = (String)JOptionPane.showInputDialog( 
						null, 
						"Please select the due day for the project:\n\n", 
						"Poised Project Management", 
						JOptionPane.PLAIN_MESSAGE,
						null, daySelection, 
						Integer.toString(startingDay));
			
			if (selectDay == null) {
				if (InputMethods.getUserConfirmation("Are you sure you want to cancel entering a date?\n" + 
													 "This will return you to the main menu!")) {
					// returns a blank to indicate a "cancel" has been selected
					return "";
				}	
			}else {
				return selectDay;
			}
		}
		return selectDay;
	}
	
	
	/**
    *
    * Set Date Method
    * <br>
    * Requests the user to select a valid Year, Month and Day
    * The selection is then returned in a String formatted as DD-MM-YYYY
    *
    * @param yearRange int containing the available year range
    * @return String containing the selected date / "" is returned if the user cancels 
    * @see setMonth
    * @see setYear
    * @see setDay
    * @see getDateCheck
    * @since version 1.00
    */
	// method used to set a date beyond today and return it in the format "dd-mm-yyyy"
	public static String setDate(int yearRange) {
		// empty string indicates an error (get's passed if the user cancels )
		boolean isEnteringDate = true;
		String selectedYear = "";
		String selectedMonth = "";
		String selectedDay = "";
		String setDate = "";
		
		while(isEnteringDate){
			// sets selected year
			selectedYear = setYear(yearRange);
			if(selectedYear.equals("")) {
				return"";
			}
			
			// sets selected month
			selectedMonth = setMonth(selectedYear);
			if(selectedMonth.equals("")) {
				return"";
			}
			
			// sets selected day
			selectedDay = setDay(selectedMonth, selectedYear);
			if(selectedDay.equals("")) {
				return"";
			}else{
				// if all values were selected correctly they are added together in a valid format to be returned
				setDate = selectedDay + "-" + selectedMonth + "-" + selectedYear;
				
				// if the current date matches today, the user is warned the project will be marked as overdue
				if(getDateCheck(setDate)) {
					if (InputMethods.getUserConfirmation("Wanring!\nSelecting today as a due date will mark the project as overdue!\n" + 
														 "Are you sure you want to proceed?")) {
						return setDate;
					}
					
				// any other valid date will return the current date
				}else {
					return setDate;
				}	
			}
		}	
		return setDate;
	}
}

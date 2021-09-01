import java.math.BigDecimal;

/**
 * Array Method Class The class is used manage arrays Methods in this class are
 * used to append new values to an existing array
 * 
 * These methods create a new array, duplicate the values of the existing array
 * and returns the new array with the added value on the last index of the new
 * array
 *
 * @author Chris Joubert
 * @version 1.00, 27 July 2021
 */

public class ArrayMethods {

	/**
	 *
	 * Add To Int Array Method <br>
	 * The method returns a new array matching the values of and indexes of the
	 * passed array The passed intToAdd is added to the last index in the new array
	 *
	 * @param passedArray Int Array containing all the values that need to be
	 *                    included in the new array
	 * @param intToAdd    Int to be appended to the new array
	 * @return Int Array containing all original values and the passed value
	 *         appended on the last index
	 * @since version 1.00
	 */
	public static int[] addToIntArray(int[] passedArray, int intToAdd) {
		// new array to be returned
		int newValueIndex = 1;
		if (passedArray != null) {
			newValueIndex = passedArray.length + 1;
		}
		int[] newArray = new int[newValueIndex];
		// old array is looped over and the values are duplicated onto the new array
		if (passedArray != null) {
			for (int counter = 0; counter < passedArray.length; counter++)
				newArray[counter] = passedArray[counter];
			// the new int is added to the end of the new array
			newArray[newValueIndex - 1] = intToAdd;
		} else {
			newArray[0] = intToAdd;
		}
		// the new array is returned
		return newArray;
	}

	/**
	 *
	 * Add To String Array Method <br>
	 * The method returns a new array matching the values of and indexes of the
	 * passed array The passed stringToAdd is added to the last index in the new
	 * array
	 *
	 * @param passedArray String Array containing all the values that need to be
	 *                    included in the new array
	 * @param stringToAdd String to be appended to the new array
	 * @return String Array containing all original values and the passed value
	 *         appended on the last index
	 * @since version 1.00
	 */
	public static String[] addToStringArray(String[] passedArray, String stringToAdd) {
		// new array to be returned
		int newValueIndex = 1;
		if (passedArray != null) {
			newValueIndex = passedArray.length + 1;
		}
		String[] newArray = new String[newValueIndex];
		// old array is looped over and the values are duplicated onto the new array
		if (passedArray != null) {
			for (int counter = 0; counter < passedArray.length; counter++)
				newArray[counter] = passedArray[counter];
			// the new int is added to the end of the new array
			newArray[newValueIndex - 1] = stringToAdd;
		} else {
			newArray[0] = stringToAdd;
		}
		// the new array is returned
		return newArray;
	}

	/**
	 *
	 * Add To BigDecimal Array Method <br>
	 * The method returns a new array matching the values of and indexes of the
	 * passed array The passed BigDecimal is added to the last index in the new
	 * array
	 *
	 * @param passedArray BigDecimal Array containing all the values that need to be
	 *                    included in the new array
	 * @param numToAdd    BigDecimal to be appended to the new array
	 * @return BigDecimal Array containing all original values and the passed value
	 *         appended on the last index
	 * @since version 1.00
	 */
	public static BigDecimal[] addToBigDecimalArray(BigDecimal[] passedArray, BigDecimal numToAdd) {
		// new array to be returned
		int newValueIndex = 1;
		if (passedArray != null) {
			newValueIndex = passedArray.length + 1;
		}
		BigDecimal[] newArray = new BigDecimal[newValueIndex];
		// old array is looped over and the values are duplicated onto the new array
		if (passedArray != null) {
			for (int counter = 0; counter < passedArray.length; counter++)
				newArray[counter] = passedArray[counter];
			// the new num is added to the end of the new array
			newArray[newValueIndex - 1] = numToAdd;
		} else {
			newArray[0] = numToAdd;
		}
		// the new array is returned
		return newArray;
	}
}
